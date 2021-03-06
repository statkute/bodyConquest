package main.com.bodyconquest.gamestates;

import main.com.bodyconquest.constants.*;
import main.com.bodyconquest.entities.BasicObject;
import main.com.bodyconquest.entities.DifficultyLevel;
import main.com.bodyconquest.entities.Map;
import main.com.bodyconquest.entities.MapObject;
import main.com.bodyconquest.entities.Troops.Bacteria;
import main.com.bodyconquest.entities.Troops.Bases.Base;
import main.com.bodyconquest.entities.Troops.Virus;
import main.com.bodyconquest.entities.Troops.Troop;
import main.com.bodyconquest.entities.Troops.Fungus;
import main.com.bodyconquest.entities.abilities.Ability;
import main.com.bodyconquest.entities.projectiles.Projectile;
import main.com.bodyconquest.entities.resources.Resources;
import main.com.bodyconquest.game_logic.BasicTestAI;
import main.com.bodyconquest.game_logic.Game;
import main.com.bodyconquest.game_logic.MultiplayerTestAI;
import main.com.bodyconquest.game_logic.MultiplayerAI;
import main.com.bodyconquest.game_logic.Player;
import main.com.bodyconquest.networking.ServerSender;
import main.com.bodyconquest.networking.utilities.MessageMaker;
import main.com.bodyconquest.networking.utilities.Serialization;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CopyOnWriteArrayList;

/** The game state where all of the encounter logic takes place. */
public class EncounterState {

  private Game game;

  /** The map object that holds all information that needs to be known about the map. */
  private Map map;

  // Troop Arrays (Data type and usage is subject to future change)
  /** The list that stores all MapObject currently on the map. */
  private CopyOnWriteArrayList<MapObject> allMapObjects;

  /** The list that stores all the troops belonging to the top player. */
  private CopyOnWriteArrayList<Troop> troopsTop;
  /** The list that stores all the troops belonging to the bottom player. */
  private CopyOnWriteArrayList<Troop> troopsBottom;

  /** The list that stores all the projectiles that belong to the bottom player. */
  private CopyOnWriteArrayList<Projectile> projectilesBottom;
  /** The list that stores all the projectiles that belong to the top player. */
  private CopyOnWriteArrayList<Projectile> projectilesTop;

  /**
   * The communicator object which acts as a place holder for the Server (and possibly in future the
   * Client). This may remain for quick and easy implementation of single player without using a
   * Client/Server.
   */
  private ServerSender serverSender;

  private Base topBase;
  private Base bottomBase;

  private Player topPlayer;
  private Player bottomPlayer;

  private int totalScoreTop;
  private int totalScoreBottom;

  /** The Counter. */
  int counter = 0;

  // Move resources in side of player
  private Resources topResources;
  private Resources bottomResources;

  private Organ organ;

  private MultiplayerAI multiplayerAI;

  private BasicTestAI singleplayerAI;

  /**
   * Constructor. @param game the game
   *
   * @param game the game
   * @param organ the organ
   */
  public EncounterState(Game game, Organ organ) {
    this.game = game;
    this.organ = organ;

    serverSender = game.getServer().getServerSender();

    game.startEncounterLogic(this);

    allMapObjects = new CopyOnWriteArrayList<>();

    topPlayer = game.getPlayerTop();
    topPlayer.refreshMultipliers();
    bottomPlayer = game.getPlayerBottom();
    bottomPlayer.refreshMultipliers();

    // Initialise unit arrays
    troopsBottom = new CopyOnWriteArrayList<>();
    troopsTop = new CopyOnWriteArrayList<>();

    // Create player bases
    bottomBase = bottomPlayer.getNewBase();
    bottomBase.setPosition(Assets.baseBottomX, Assets.baseBottomY);
    troopsBottom.add(bottomBase);
    allMapObjects.add(bottomBase);

    topBase = topPlayer.getNewBase();
    topBase.setPosition(Assets.baseTopX, Assets.baseTopY);
    troopsTop.add(topBase);
    allMapObjects.add(topBase);

    projectilesBottom = new CopyOnWriteArrayList<>();
    projectilesTop = new CopyOnWriteArrayList<>();

    totalScoreBottom = bottomPlayer.getScore();
    totalScoreTop = topPlayer.getScore();

    bottomResources = new Resources(PlayerType.PLAYER_BOTTOM);
    topResources = new Resources(PlayerType.PLAYER_TOP);

    bottomResources.start();
    topResources.start();

    if (game.getGameType() == GameType.SINGLE_PLAYER) {
      DifficultyLevel difficultyLevel = game.getDifficulty();
      singleplayerAI = new BasicTestAI(this, PlayerType.PLAYER_TOP, topResources, difficultyLevel);
      // multiplayerAI = new MultiplayerAI(this);
      singleplayerAI.start();
      // multiplayerAI.start();
    } else {
      multiplayerAI = new MultiplayerAI(this);
      multiplayerAI.start();
    }
  }

  /**
   * Check attack interactions between the two troop lists. Initiates any resulting attack sequences
   * caused from troops being eligible to attack another troop.
   *
   * @param troopsP1 First list of troops.
   * @param troopsP2 Second list of troops.
   */
  private void checkAttack(
      CopyOnWriteArrayList<Troop> troopsP1, CopyOnWriteArrayList<Troop> troopsP2) {
    CopyOnWriteArrayList<Troop> deadTroops = new CopyOnWriteArrayList<Troop>();

    for (Troop troop : troopsP1) {
      if (troop.isDead()) {
        deadTroops.add(troop);
        continue;
      }
      troop.checkAttack(troopsP2);
    }

    for (Troop troop : deadTroops) {
      if (troop.getPlayerType() == PlayerType.PLAYER_TOP) {
        totalScoreBottom += troop.getKillingPoints();
      } else if (troop.getPlayerType() == PlayerType.PLAYER_BOTTOM) {
        totalScoreTop += troop.getKillingPoints();
      }
      troopsP1.remove(troop);
      allMapObjects.remove(troop);
    }
  }

  /**
   * Check collision interactions between the projectiles in the given projectile list with the
   * troops in the given troop list. Initiates any resulting attack sequences caused from troops
   * being hit.
   *
   * @param projectiles The list of projectiles to check interactions with.
   * @param enemies The list of troops to check interactions with.
   */
  private void checkProjectiles(
      CopyOnWriteArrayList<Projectile> projectiles, CopyOnWriteArrayList<Troop> enemies) {
    CopyOnWriteArrayList<Projectile> finishedProjectiles = new CopyOnWriteArrayList<Projectile>();
    for (Projectile proj : projectiles) {
      proj.checkHit(enemies);
      if (proj.getRemove()) finishedProjectiles.add(proj);
    }

    for (Projectile proj : finishedProjectiles) {
      projectiles.remove(proj);
      allMapObjects.remove(proj);
    }
  }

  /** {@inheritDoc} */
  public void update() {
    counter++;

    for (MapObject mo : allMapObjects) mo.update();

    checkCollisions(troopsBottom, troopsTop);
    checkCollisions(troopsTop, troopsBottom);

    // Update All Units
    checkAttack(troopsTop, troopsBottom);
    checkAttack(troopsBottom, troopsTop);
    checkProjectiles(projectilesTop, troopsBottom);
    checkProjectiles(projectilesBottom, troopsTop);

    if (counter == 2) {

      if (topBase.getHealth() <= 0) {
        endGame(PlayerType.PLAYER_BOTTOM);
      }
      if (bottomBase.getHealth() <= 0) {
        endGame(PlayerType.PLAYER_TOP);
      }

      sendMapObjectUpdates();

      sendPlayerHealthUpdates();

      sendResourceUpdates();

      sendPlayerScoreUpdates();

      counter = 0;
    }
  }

  private void checkCollisions(
      CopyOnWriteArrayList<Troop> troops, CopyOnWriteArrayList<Troop> enemyTroops) {
    for (Troop troop : troops) {
      troop.checkCollisions(new CopyOnWriteArrayList<>(enemyTroops));
    }
  }

  /**
   * Called by player AI's or players to spawn troops.
   *
   * @param unitType The unit/troop to be spawned.
   * @param lane The lane the unit/troop will be assigned to.
   * @param playerType The player the unit/troop will be assigned to.
   * @param isMultiplayerAI The flag is true is the unit is spawned by the multiplayer AI, else it
   *     is false/
   */
  public void spawnUnit(
      UnitType unitType, Lane lane, PlayerType playerType, boolean isMultiplayerAI) {
    Troop troop = null;
    Disease disease = getPlayer(playerType).getDisease();
    Resources resources = getResources(playerType);
    Player player = getPlayer(playerType);
    try {

      Troop troopInit = (Troop) unitType.getAssociatedClass().newInstance();

      // these attributes are only relevant for non AI players

      float damageMult = 1;
      float speedMult = 1;
      float attackSpeedMult = 1;
      float healthMult = 1;

      if (!isMultiplayerAI) {
        damageMult = disease.getDamageMult() * player.getDamageMult();
        speedMult = disease.getSpeedMult() * player.getSpeedMult();
        attackSpeedMult = disease.getAttackSpeedMult() * player.getAttackSpeedMult();
        healthMult = disease.getHealthMult() * player.getHealthMult();
      }

      if (!troopInit.isRanged()) {
        // multiplayer AI units cannot be ranged
        if (!isMultiplayerAI) {
          troopInit =
              (Troop)
                  unitType
                      .getAssociatedClass()
                      .getDeclaredConstructor(
                          Lane.class,
                          PlayerType.class,
                          float.class,
                          float.class,
                          float.class,
                          float.class)
                      .newInstance(
                          lane, playerType, damageMult, speedMult, healthMult, attackSpeedMult);
        } // multiplayer AI units don't get multipliers
        else {
          troopInit =
              (Troop)
                  unitType
                      .getAssociatedClass()
                      .getDeclaredConstructor(Lane.class, PlayerType.class)
                      .newInstance(lane, playerType);
        }
      } else {
        troopInit =
            (Troop)
                unitType
                    .getAssociatedClass()
                    .getDeclaredConstructor(
                        EncounterState.class,
                        Lane.class,
                        PlayerType.class,
                        float.class,
                        float.class,
                        float.class,
                        float.class)
                    .newInstance(
                        this, lane, playerType, damageMult, speedMult, healthMult, attackSpeedMult);
      }

      // multiplayer AI manages its own resources
      if (!isMultiplayerAI) {
        if (resources.canAfford(
            troopInit.getLipidCost(), troopInit.getSugarCost(), troopInit.getProteinCost())) {
          resources.buy(
              troopInit.getLipidCost(), troopInit.getSugarCost(), troopInit.getProteinCost());
          troop = troopInit;
        }
      } else {
        troop = troopInit;
      }

    } catch (InstantiationException
        | IllegalAccessException
        | NoSuchMethodException
        | InvocationTargetException e) {
      e.printStackTrace();
    }

    // Return if invalid troop, lane or player type is used
    if (troop == null || lane == null || playerType == null) return;

    if (!isMultiplayerAI) {
      // Spawn units for bottom player
      if (playerType.equals(PlayerType.PLAYER_BOTTOM)) {
        if (lane == Lane.BOTTOM) {
          troop.setPosition(
              Assets.BP_BOT_LANE_SPAWN_X - (troop.getWidth() / 2.0),
              Assets.BP_BOT_LANE_SPAWN_Y - (troop.getHeight() / 2.0));
        } else if (lane == Lane.MIDDLE) {
          troop.setPosition(
              Assets.BP_MID_LANE_SPAWN_X - (troop.getWidth() / 2.0),
              Assets.BP_MID_LANE_SPAWN_Y - (troop.getHeight() / 2.0));
        } else if (lane == Lane.TOP) {
          troop.setPosition(
              Assets.BP_TOP_LANE_SPAWN_X - (troop.getWidth() / 2.0),
              Assets.BP_TOP_LANE_SPAWN_Y - (troop.getHeight() / 2.0));
        }

        troopsBottom.add(troop);
      }

      // Spawn units for top player
      if (playerType.equals(PlayerType.PLAYER_TOP)) {
        // if this unit was actually spawned by the player, and not by the AI in its behalf
        if (lane == Lane.BOTTOM) {
          troop.setPosition(
              Assets.TP_BOT_LANE_SPAWN_X - (troop.getWidth() / 2.0),
              Assets.TP_BOT_LANE_SPAWN_Y - (troop.getHeight() / 2.0));
        } else if (lane == Lane.MIDDLE) {
          troop.setPosition(
              Assets.TP_MID_LANE_SPAWN_X - (troop.getWidth() / 2.0),
              Assets.TP_MID_LANE_SPAWN_Y - (troop.getHeight() / 2.0));
        } else if (lane == Lane.TOP) {
          troop.setPosition(
              Assets.TP_TOP_LANE_SPAWN_X - (troop.getWidth() / 2.0),
              Assets.TP_TOP_LANE_SPAWN_Y - (troop.getHeight() / 2.0));
        }

        troopsTop.add(troop);
      }
    } else {
      // if it's a unit spawned by the AI on behalf of another player, put it in the AI's spawn
      // points
      if (lane == Lane.BOTTOM) {
        troop.setPosition(
            Assets.AI_BOT_LANE_SPAWN_X - (troop.getWidth() / 2.0),
            Assets.AI_BOT_LANE_SPAWN_Y - (troop.getHeight() / 2.0));
      } else if (lane == Lane.TOP) {
        troop.setPosition(
            Assets.AI_TOP_LANE_SPAWN_X - (troop.getWidth() / 2.0),
            Assets.AI_TOP_LANE_SPAWN_Y - (troop.getHeight() / 2.0));
      }

      if (playerType.equals(PlayerType.PLAYER_BOTTOM)) {
        // if AI spawns on behalf of the bottom player, add the troop to its *army*
        troopsBottom.add(troop);
      } else {
        // if AI spawns on behalf of the top player, add the troop to its *army*
        troopsTop.add(troop);
      }
    }

    allMapObjects.add(troop);
  }

  /**
   * Called by ranged (projectile using) MapObjects to add their projectile to the list of
   * MapObjects.
   *
   * @param projectile The projectile to be added to the EncounterState/Map.
   * @param playerType The player that the projectile belongs to.
   */
  public void addProjectile(Projectile projectile, PlayerType playerType) {
    if (playerType == null || projectile == null) return;

    if (playerType == PlayerType.PLAYER_BOTTOM) {
      projectilesBottom.add(projectile);
    } else if (playerType == PlayerType.PLAYER_TOP) {
      projectilesTop.add(projectile);
    } else {
      return;
    }
    allMapObjects.add(projectile);
  }

  private void sendMapObjectUpdates() {
    CopyOnWriteArrayList<BasicObject> sentObjects = new CopyOnWriteArrayList<BasicObject>();
    for (MapObject o : allMapObjects) sentObjects.add(o.getBasicObject());
    try {
      String json = Serialization.serialize(sentObjects);
      serverSender.sendObjectUpdates(json);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void endGame(PlayerType player) {
    String endingMessage = MessageMaker.organClaimMessage(player, organ);
    serverSender.sendMessage(endingMessage);
    PlayerType picker;
    if (game.getGameType() != GameType.SINGLE_PLAYER) {
      picker =
          game.getLastPicker() == PlayerType.PLAYER_BOTTOM
              ? PlayerType.PLAYER_TOP
              : PlayerType.PLAYER_BOTTOM;
    } else {
      picker = PlayerType.PLAYER_BOTTOM;
    }
    serverSender.sendMessage(MessageMaker.firstPickerMessage(picker));
    game.setLastPicker(picker);

    if (player == PlayerType.PLAYER_BOTTOM) {
      totalScoreBottom += organ.getOrganScore();
      bottomPlayer.claimOrgan(organ);
    } else {
      totalScoreTop += organ.getOrganScore();
      topPlayer.claimOrgan(organ);
    }

    bottomPlayer.setScore(totalScoreBottom);
    topPlayer.setScore(totalScoreTop);

    if (game.getGameType() == GameType.SINGLE_PLAYER) {
      singleplayerAI.stopRunning();
    } else {
      multiplayerAI.stopRunning();
    }

    game.endEncounter();
  }

  private void sendResourceUpdates() {
    topResources.update();
    bottomResources.update();
    serverSender.sendMessage(topResources.getUpdateMessage());
    serverSender.sendMessage(bottomResources.getUpdateMessage());
  }

  private void sendPlayerHealthUpdates() {
    double healthBottom = bottomBase.getHealth();
    double healthBottomMax = bottomBase.getMaxHealth();
    double healthPercentage = (healthBottom / healthBottomMax) * 100.0;
    int healthB = (int) healthPercentage;
    String messageb = MessageMaker.healthUpdate(healthB, PlayerType.PLAYER_BOTTOM);

    double healthTop = topBase.getHealth();
    double healthTopMax = topBase.getMaxHealth();
    double healthPercentageT = (healthTop / healthTopMax) * 100.0;
    int healthT = (int) healthPercentageT;

    String messaget = MessageMaker.healthUpdate(healthT, PlayerType.PLAYER_TOP);

    serverSender.sendMessage(messageb);
    serverSender.sendMessage(messaget);
  }

  private void sendPlayerScoreUpdates() {
    String pointsMessage = MessageMaker.pointsMessage(totalScoreTop, totalScoreBottom);
    serverSender.sendMessage(pointsMessage);
  }

  /**
   * Cast ability.
   *
   * @param abilityType the ability type
   * @param playerType the player type
   * @param xDest the x dest
   * @param yDest the y dest
   */
  public void castAbility(AbilityType abilityType, PlayerType playerType, int xDest, int yDest) {
    // Implement functionality
  }

  /**
   * Cast ability.
   *
   * @param abilityType the ability type
   * @param playerType the player type
   * @param lane the lane
   */
  public void castAbility(AbilityType abilityType, PlayerType playerType, Lane lane) {
    try {
      @SuppressWarnings("unchecked")
      Ability ability =
          (Ability)
              abilityType
                  .getAssociatedClass()
                  .getDeclaredConstructor(Lane.class, PlayerType.class)
                  .newInstance(lane, playerType);
      ability.cast(this);
    } catch (InstantiationException
        | IllegalAccessException
        | NoSuchMethodException
        | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets enemy troops.
   *
   * @param player the player
   * @return the enemy troops
   */
  public CopyOnWriteArrayList<Troop> getEnemyTroops(PlayerType player) {
    return player != PlayerType.PLAYER_BOTTOM ? troopsBottom : troopsTop;
  }

  /**
   * Gets troops.
   *
   * @param player the player
   * @return the troops
   */
  public CopyOnWriteArrayList<Troop> getTroops(PlayerType player) {
    return player == PlayerType.PLAYER_BOTTOM ? troopsBottom : troopsTop;
  }

  /**
   * Gets base.
   *
   * @param player the player
   * @return the base
   */
  public Base getBase(PlayerType player) {
    return player == PlayerType.PLAYER_BOTTOM ? bottomBase : topBase;
  }

  private Player getPlayer(PlayerType playerType) {
    return playerType == PlayerType.PLAYER_BOTTOM ? bottomPlayer : topPlayer;
  }

  /**
   * Gets resources.
   *
   * @param playerType the player type
   * @return the resources
   */
  public Resources getResources(PlayerType playerType) {
    return playerType == PlayerType.PLAYER_BOTTOM ? bottomResources : topResources;
  }
}
