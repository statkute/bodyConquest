package com.cauldron.bodyconquest.gamestates;

import com.badlogic.gdx.Gdx;
import com.cauldron.bodyconquest.constants.AbilityType;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.constants.Assets.Lane;
import com.cauldron.bodyconquest.constants.Assets.PlayerType;
import com.cauldron.bodyconquest.constants.Assets.UnitType;
import com.cauldron.bodyconquest.constants.GameType;
import com.cauldron.bodyconquest.entities.BasicObject;
import com.cauldron.bodyconquest.entities.Map;
import com.cauldron.bodyconquest.entities.MapObject;
import com.cauldron.bodyconquest.entities.Troops.Bacteria;
import com.cauldron.bodyconquest.entities.Troops.Bases.BacteriaBase;
import com.cauldron.bodyconquest.entities.Troops.Bases.Base;
import com.cauldron.bodyconquest.entities.Troops.Flu;
import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.entities.Troops.Virus;
import com.cauldron.bodyconquest.entities.projectiles.Projectile;
import com.cauldron.bodyconquest.entities.resources.Resources;
import com.cauldron.bodyconquest.game_logic.BasicTestAI;
import com.cauldron.bodyconquest.game_logic.Game;
import com.cauldron.bodyconquest.game_logic.MultiplayerTestAI;
import com.cauldron.bodyconquest.networking.Server;
import com.cauldron.bodyconquest.networking.ServerSender;
import com.cauldron.bodyconquest.networking.utilities.MessageMaker;
import com.cauldron.bodyconquest.networking.utilities.Serialization;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/** The {@link GameState} where all of the encounter logic takes place. */
public class EncounterState extends GameState {

  /**
   * An enumeration for the different assignments {@link Troop}s can have to determine how Troops
   * move and who/what they attack.
   */

  /**
   * An enumeration for the different lanes {@link Troop}s can be assigned to, to determine how
   * those Troops move.
   */

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

  private Resources topResources;
  private Resources bottomResources;

  /**
   * Constructor.
   */
  public EncounterState(Game game) {
    super(game);
    Server server = game.getServer();
    serverSender = server.getServerSender();
    map = new Map();

    game.startEncounterLogic(this);

    allMapObjects = new CopyOnWriteArrayList<MapObject>();

    // Initialise unit arrays
    troopsBottom = new CopyOnWriteArrayList<Troop>();
    troopsTop = new CopyOnWriteArrayList<Troop>();

    // Create player bases
    bottomBase = new BacteriaBase(Lane.ALL, PlayerType.PLAYER_BOTTOM);
    bottomBase.setPosition(Assets.baseBottomX, Assets.baseBottomY);
    troopsBottom.add(bottomBase);
    allMapObjects.add(bottomBase);

    topBase = new BacteriaBase(Lane.ALL, PlayerType.PLAYER_TOP);
    topBase.setPosition(Assets.baseTopX, Assets.baseTopY);
    troopsTop.add(topBase);
    allMapObjects.add(topBase);

    projectilesBottom = new CopyOnWriteArrayList<Projectile>();
    projectilesTop = new CopyOnWriteArrayList<Projectile>();

    // Maybe make constructors for these in the Player class so they can be modified for each player
    // And the modifications can be kept consistent across Encounters
    bottomResources = new Resources(server, PlayerType.PLAYER_BOTTOM);
    topResources = new Resources(server, PlayerType.PLAYER_TOP);

    bottomResources.start();
    topResources.start();

    if (game.getGameType() == GameType.SINGLE_PLAYER){
      BasicTestAI ai = new BasicTestAI(this, PlayerType.PLAYER_TOP, topResources);
      ai.start();
    } else {
      MultiplayerTestAI ai = new MultiplayerTestAI(this);
      ai.start();
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

    for (Troop u : deadTroops) {
      troopsP1.remove(u);
      allMapObjects.remove(u);
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
  @Override
  public void update() {

    // ! Important if you do not want to update encounter state, bases health should go to minus because
    // Encounter state is instantiated before encounter screen and it starts getting health before game is started of the base


//    if(comms.getBottomHealthPercentage() >= 0 && comms.getTopHealthPercentage() >= 0){
//
//     System.out.println(comms.getBottomHealthPercentage());


      for (MapObject mo : allMapObjects) mo.update();

      // Update All Units
      checkAttack(troopsTop, troopsBottom);
      checkAttack(troopsBottom, troopsTop);
      checkProjectiles(projectilesTop, troopsBottom);
      checkProjectiles(projectilesBottom, troopsTop);


      // Change this so it only add new objects
      CopyOnWriteArrayList<BasicObject> sentObjects = new CopyOnWriteArrayList<BasicObject>();
      for (MapObject o : allMapObjects) sentObjects.add(o.getBasicObject());

      // TO DO: send this to the client
      String json = "";
      try {
        json = Serialization.serialize(sentObjects);
        serverSender.sendObjectUpdates(json);

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

      } catch (IOException e) {
        e.printStackTrace();
      }
    //}


  }

  /**
   * Called by player AI's or players to spawn troops.
   *
   * @param unitType The unit/troop to be spawned.
   * @param lane The lane the unit/troop will be assigned to.
   * @param playerType The player the unit/troop will be assigned to.
   */
  public void spawnUnit(UnitType unitType, Lane lane, PlayerType playerType) {
    Troop troop = null;

    // Initialise troop type
    if (unitType.equals(UnitType.BACTERIA)) {
      troop = new Bacteria(playerType, lane);
    } else if (unitType.equals(UnitType.FLU)) {
      troop = new Flu(this, playerType, lane);
    } else if (unitType.equals(UnitType.VIRUS)) {
      troop = new Virus(playerType, lane);
    }

    // Return if invalid troop, lane or player type is used
    if (troop == null || lane == null || playerType == null) return;

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

  private void checkPressed() {
    if(Gdx.input.isKeyJustPressed(1)) {
      //activate1();

    }
  }


  public CopyOnWriteArrayList<Troop> getTroopsTop() {
    return troopsTop;
  }

  public CopyOnWriteArrayList<Troop> getTroopsBottom() {
    return troopsBottom;
  }

  public Resources getBottomResources() {
    return bottomResources;
  }

  public Resources getTopResources() {
    return topResources;
  }

  public void castAbility(AbilityType abilityType, PlayerType playerType, int xDest, int yDest) {
    // Implement functionality
  }

  public void castAbility(AbilityType abilityType, PlayerType playerType, Lane lane) {
    // Implement functionality
  }
}
