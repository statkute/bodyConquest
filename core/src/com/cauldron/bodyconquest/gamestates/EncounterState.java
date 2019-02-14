package com.cauldron.bodyconquest.gamestates;

import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.entities.BasicObject;
import com.cauldron.bodyconquest.entities.Map;
import com.cauldron.bodyconquest.entities.MapObject;
import com.cauldron.bodyconquest.entities.Projectile;
import com.cauldron.bodyconquest.entities.Troops.Bacteria;
import com.cauldron.bodyconquest.entities.Troops.Bases.BacteriaBase;
import com.cauldron.bodyconquest.entities.Troops.Bases.Base;
import com.cauldron.bodyconquest.entities.Troops.Flu;
import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.entities.Troops.Troop.*;
import com.cauldron.bodyconquest.entities.Troops.Virus;

import java.util.concurrent.CopyOnWriteArrayList;

/*
The screen where the encounters occurs, hosts a number of actors including,
the health bar, unitButtons, resourceBars and player information.
*/
public class EncounterState extends GameState {

  public enum PlayerType {
    BOT_PLAYER,
    TOP_PLAYER
  }

  public enum Lane {
    TOP,
    BOT,
    MID,
    ALL
  }
  
  private Map map;
  private float mapSize;

  // If kept final change to all caps
  private final float botTurnPointX = 150;
  private final float botTurnPointY = 60;
  private final float topTurnpointX = 550;
  private final float topTurnpointY = 525;

  // Probably make this final
  private final float botLaneBPSpawnX = 500; // 535;
  private final float botLaneBPSpawnY = 85; // 90;
  private final float midLaneBPSpawnX = 505;
  private final float midLaneBPSpawnY = 185;
  private final float topLaneBPSpawnX = 600;
  private final float topLaneBPSpawnY = 225;

  private final float botLaneTPSpawnX = 170;
  private final float botLaneTPSpawnY = 470;

  // Not yet initialised
  private float midLaneTPSpawnX;
  private float topLaneTPSpawnX = 175;
  private float midLaneTPSpawnY;
  private float topLaneTPSpawnY = 525;

  // Troop Arrays (Data type and usage is subject to future change)

  private CopyOnWriteArrayList<MapObject> allMapObjects;

  private CopyOnWriteArrayList<Troop> troopsTop;
  private CopyOnWriteArrayList<Troop> troopsBottom;

  private CopyOnWriteArrayList<Projectile> projectilesBottom;
  private CopyOnWriteArrayList<Projectile> projectilesTop;

  private Base topBase;
  private Base bottomBase;

  //public static Sound dropSound;

  private Communicator comms;

  public EncounterState(Communicator comms) {
    this.comms = comms;
    
    map = new Map();

    allMapObjects = new CopyOnWriteArrayList<MapObject>();
    
    // Initialise unit arrays
    troopsBottom = new CopyOnWriteArrayList<Troop>();
    troopsTop = new CopyOnWriteArrayList<Troop>();

    // Create player bases
    bottomBase = new BacteriaBase(Lane.ALL, PlayerType.BOT_PLAYER);
    bottomBase.setPosition(map.getRight() - bottomBase.getWidth(), map.getBottom());
    //stage.addActor(bottomBase);
    troopsBottom.add(bottomBase);
    allMapObjects.add(bottomBase);

    topBase = new BacteriaBase(Lane.ALL, PlayerType.TOP_PLAYER);
    topBase.setPosition(map.getLeft(), map.getTop() - topBase.getHeight());
    //stage.addActor(topBase);
    troopsTop.add(topBase);
    allMapObjects.add(topBase);

    projectilesBottom = new CopyOnWriteArrayList<Projectile>();
    projectilesTop = new CopyOnWriteArrayList<Projectile>();

    new BasicTestAI(this, PlayerType.TOP_PLAYER).start();

    //dropSound = Gdx.audio.newSound(Gdx.files.internal("core/assets/waterDrop.wav"));
  }

  private void checkAttack(CopyOnWriteArrayList<Troop> troopsP1, CopyOnWriteArrayList<Troop> troopsP2) {
    CopyOnWriteArrayList<Troop> deadTroops = new CopyOnWriteArrayList<Troop>();
    for (Troop troop : troopsP1) {
      if (troop.isDead()) {
        deadTroops.add(troop);
        continue;
      }
      troop.checkAttack(troopsP2);
    }
    // This gives particular players a very slight advantage because certain units will be deleted
    // first if they both die
    for (Troop u : deadTroops) {
      troopsP1.remove(u);
      allMapObjects.remove(u);
      //dropSound.play();
    }
  }

  private void checkProjectiles(CopyOnWriteArrayList<Projectile> projectiles, CopyOnWriteArrayList<Troop> enemies) {
    CopyOnWriteArrayList<Projectile> finishedProjectiles = new CopyOnWriteArrayList<Projectile>();
    for(Projectile proj : projectiles) {
      proj.checkHit(enemies);
      if(proj.getRemove()) finishedProjectiles.add(proj);
    }
    // This gives particular players a very slight advantage because certain units will be deleted
    // first if they both die
    for (Projectile proj : finishedProjectiles){
      projectiles.remove(proj);
      allMapObjects.remove(proj);
    }
  }

  @Override
  public void update() {

    for(MapObject mo : allMapObjects) mo.update();
    // Handle Input

    /* MULTIPLAYER */
    // Send inputs to server

    // Receive update from server

    /* SINGLE PLAYER */

    // Update All Units
    checkAttack(troopsTop, troopsBottom);
    checkAttack(troopsBottom, troopsTop);
    checkProjectiles(projectilesTop, troopsBottom);
    checkProjectiles(projectilesBottom, troopsTop);

    //System.out.println(allMapObjects.size());
    // Synchronize this
    // Change this so it only add new objects
    CopyOnWriteArrayList<BasicObject> sentObjects = new CopyOnWriteArrayList<BasicObject>();
    for(MapObject o : allMapObjects) sentObjects.add(o.getBasicObject());
    comms.populateObjectList(sentObjects);
  }

  public void spawnUnit(UnitType unitType, Lane lane, PlayerType playerType) {
    // Initialise the troop
    Troop troop = null;
    //dropSound.play();

    // Initialise troop type
    if (unitType.equals(UnitType.BACTERIA)) {
      troop = new Bacteria(this, playerType, lane);
    } else if(unitType.equals(UnitType.FLU)){
      troop = new Flu(this, playerType, lane);
    } else if (unitType.equals(UnitType.VIRUS)){
      troop = new Virus(this, playerType, lane);
    }

    // Return if invalid troop, lane or player type is used
    if (troop == null || lane == null || playerType == null) return;

    // Spawn units for bottom player
    if (playerType.equals(PlayerType.BOT_PLAYER)) {
      if (lane == Lane.BOT) {
        troop.setPosition(
                Constants.BP_BOT_LANE_SPAWN_X - (troop.getWidth() / 2), Constants.BP_BOT_LANE_SPAWN_Y - (troop.getHeight() / 2));
      } else if (lane == Lane.MID) {
        troop.setPosition(
                Constants.BP_MID_LANE_SPAWN_X - (troop.getWidth() / 2), Constants.BP_MID_LANE_SPAWN_Y - (troop.getHeight() / 2));
      } else if (lane == Lane.TOP) {
        troop.setPosition(
                Constants.BP_TOP_LANE_SPAWN_X - (troop.getWidth() / 2), Constants.BP_TOP_LANE_SPAWN_Y - (troop.getHeight() / 2));
      }
      troopsBottom.add(troop);
    }

    // Spawn units for top player
    if (playerType.equals(PlayerType.TOP_PLAYER)) {
      if (lane == Lane.BOT) {
        troop.setPosition(
                Constants.TP_BOT_LANE_SPAWN_X - (troop.getWidth() / 2), Constants.TP_BOT_LANE_SPAWN_Y - (troop.getHeight() / 2));
      } else if (lane == Lane.MID) {
        troop.setPosition(
                Constants.TP_MID_LANE_SPAWN_X - (troop.getWidth() / 2), Constants.TP_MID_LANE_SPAWN_Y - (troop.getHeight() / 2));
      } else if (lane == Lane.TOP) {
        troop.setPosition(
                Constants.TP_TOP_LANE_SPAWN_X - (troop.getWidth() / 2), Constants.TP_TOP_LANE_SPAWN_Y - (troop.getHeight() / 2));
      }
      troopsTop.add(troop);
    }
    allMapObjects.add(troop);

  }

  public void addProjectile(Projectile proj, PlayerType playerType) {
    if(playerType == null || proj == null) return;

    if(playerType == PlayerType.BOT_PLAYER) {
      projectilesBottom.add(proj);
    } else if (playerType == PlayerType.TOP_PLAYER) {
      System.out.println("ADD PROJECT TO TOP ROJ");
      projectilesTop.add(proj);
    }

    allMapObjects.add(proj);

  }

  public float getBotTurnPointX() {
    return botTurnPointX;
  }

  public float getBotTurnPointY() {
    return botTurnPointY;
  }

  public float getTopTurnPointX(){
    return topTurnpointX;
  }

  public float getTopTurnPointY(){
    return topTurnpointY;
  }
}
