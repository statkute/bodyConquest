package com.cauldron.bodyconquest.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cauldron.bodyconquest.entities.HUD;
import com.cauldron.bodyconquest.entities.Projectile;
import com.cauldron.bodyconquest.entities.Troops.*;
import com.cauldron.bodyconquest.entities.Troops.Bases.BacteriaBase;
import com.cauldron.bodyconquest.entities.Troops.Bases.Base;
import com.cauldron.bodyconquest.entities.Troops.Flu;
import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.entities.Troops.Troop.UnitType;
import com.cauldron.bodyconquest.rendering.BodyConquest;

import java.util.ArrayList;
import java.util.Arrays;

/*
The screen where the encounters occurs, hosts a number of actors including,
the health bar, unitButtons, resourceBars and player information.
 */
public class EncounterScreen implements Screen {

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

  private BodyConquest game;
  private OrthographicCamera gameCamera;
  private Viewport gamePort;

  private HUD hud;

  private Image map;
  private float mapSize;

  private Stage stage;

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

  private ArrayList<Troop> troopsTop;
  private ArrayList<Troop> troopsBottom;

  private ArrayList<Projectile> projectilesBottom;
  private ArrayList<Projectile> projectilesTop;

  private Base topBase;
  private Base bottomBase;

  public static Sound dropSound;

  public EncounterScreen(BodyConquest game) {
    this.game = game;
    gameCamera = new OrthographicCamera();
    gamePort = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, gameCamera);
    stage = new Stage(gamePort);
    Gdx.input.setInputProcessor(stage);

    // Initialise player HUD
    hud = new HUD(game.batch, this, PlayerType.BOT_PLAYER);

    // Set up map
    map = new Image(new Texture("core/assets/Basic Map v2.png"));
    float topOfUnitBar = hud.getUnitBar().getTop();
    mapSize = BodyConquest.V_HEIGHT - topOfUnitBar;
    map.setBounds((BodyConquest.V_WIDTH / 2.0f) - (mapSize / 2), topOfUnitBar, mapSize, mapSize);
    stage.addActor(map);

    // Initialise unit arrays
    troopsBottom = new ArrayList<Troop>();
    troopsTop = new ArrayList<Troop>();

    // Create player bases
    bottomBase = new BacteriaBase(Lane.ALL, PlayerType.BOT_PLAYER);
    bottomBase.setPosition(getMap().getRight() - bottomBase.getWidth(), getMap().getY());
    stage.addActor(bottomBase);
    troopsBottom.add(bottomBase);

    topBase = new BacteriaBase(Lane.ALL, PlayerType.TOP_PLAYER);
    topBase.setPosition(getMap().getX(), getMap().getTop() - topBase.getHeight());
    stage.addActor(topBase);
    troopsTop.add(topBase);

    projectilesBottom = new ArrayList<Projectile>();
    projectilesTop = new ArrayList<Projectile>();

    new BasicTestAI(this, PlayerType.TOP_PLAYER).start();

    dropSound = Gdx.audio.newSound(Gdx.files.internal("core/assets/waterDrop.wav"));
  }

  private void checkAttack(ArrayList<Troop> troopsP1, ArrayList<Troop> troopsP2) {
    ArrayList<Troop> deadTroops = new ArrayList<Troop>();
    for (Troop troop : troopsP1) {
      if (troop.isDead()) {
        deadTroops.add(troop);
        troop.remove();
        continue;
      }
      troop.checkAttack(troopsP2);
    }
    // This gives particular players a very slight advantage because certain units will be deleted
    // first if they both die
    for (Troop u : deadTroops) {
      troopsP1.remove(u);
      dropSound.play();
    }
  }

  private void checkProjectiles(ArrayList<Projectile> projectiles, ArrayList<Troop> enemies) {
    ArrayList<Projectile> finishedProjectiles = new ArrayList<Projectile>();
    for(Projectile proj : projectiles) {
      proj.checkHit(enemies);
      if(proj.getRemove()) finishedProjectiles.add(proj);
    }
    // This gives particular players a very slight advantage because certain units will be deleted
    // first if they both die
    for (Projectile proj : finishedProjectiles) projectiles.remove(proj);
  }


  @Override
  public void show() {}

  public void update(float dt) {
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

  }

  @Override
  public void render(float delta) {
    // Update the camera
    gameCamera.update();


    // Update map objects
    update(delta);


    // Render background
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


    // Combine encounter and hud views
    game.batch.setProjectionMatrix(hud.getStage().getCamera().combined);


    // Make all actors call their act methods
    stage.act();
    // Draw Actors
    stage.draw();


    // Draw HUD
    hud.getStage().draw();


    // Start, draw and end spriteBatch
    game.batch.begin();
    // game.batch.draw();
    game.batch.end();


    // Development Tools (NOT NECESSARY FOR GAMEPLAY) ///////////////////////

    // Get X, Y co-ordinates of mouse click (Not working reliably) WIP
//    if (Gdx.input.isTouched()) {
//      System.out.println(Arrays.toString(troopsTop.toArray()));
//    }
//      Vector3 touchPos = new Vector3();
//      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//      gameCamera.unproject(
//          touchPos,
//          gamePort.getScreenX(),
//          gamePort.getScreenY(),
//          gamePort.getScreenWidth(),
//          gamePort.getScreenHeight());
//      System.out.println(
//          "X: "
//              + (touchPos.x /*- gamePort.getLeftGutterWidth()*/)
//              + "\tY: "
//              + (touchPos.y /*- gamePort.getBottomGutterHeight()*/));
//    }
    ////////////////////////////////////////////////////////////////////////////
  }

  @Override
  public void resize(int width, int height) {
    gamePort.update(width, height);
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    stage.dispose();
    game.dispose();
  }

  public void spawnUnit(UnitType unitType, Lane lane, PlayerType playerType) {
    // Initialise the troop
    Troop troop = null;
    //dropSound.play();

    // Initialise troop type
    if (unitType.equals(UnitType.BACTERIA)) {
      troop = new Bacteria(this, playerType, lane);
    } else if(unitType.equals(UnitType.FLU)){
      troop = new FluNew(this, playerType, lane);
    } else if (unitType.equals(UnitType.VIRUS)){
      troop = new Virus(this, playerType, lane);
    }

    // Return if invalid troop, lane or player type is used
    if (troop == null || lane == null || playerType == null) return;

    // Spawn units for bottom player
    if (playerType.equals(PlayerType.BOT_PLAYER)) {
      if (lane == Lane.BOT) {
        troop.setPosition(
                botLaneBPSpawnX - (troop.getWidth() / 2), botLaneBPSpawnY - (troop.getHeight() / 2));
      } else if (lane == Lane.MID) {
        troop.setPosition(
                midLaneBPSpawnX - (troop.getWidth() / 2), midLaneBPSpawnY - (troop.getHeight() / 2));
      } else if (lane == Lane.TOP) {
        troop.setPosition(
                topLaneBPSpawnX - (troop.getWidth() / 2), topLaneBPSpawnY - (troop.getHeight() / 2));
      }
      troopsBottom.add(troop);
    }

    // Spawn units for top player
    if (playerType.equals(PlayerType.TOP_PLAYER)) {
      if (lane == Lane.BOT) {
        troop.setPosition(
                botLaneTPSpawnX - (troop.getWidth() / 2), botLaneTPSpawnY - (troop.getHeight() / 2));
      } else if (lane == Lane.MID) {
        troop.setPosition(
            midLaneTPSpawnX - (troop.getWidth() / 2), midLaneTPSpawnY - (troop.getHeight() / 2));
      } else if (lane == Lane.TOP) {
        troop.setPosition(
                topLaneTPSpawnX - (troop.getWidth() / 2), topLaneTPSpawnY - (troop.getHeight() / 2));
      }
      troopsTop.add(troop);
    }

    // Maybe add troop to data structure containing all units if the stage isn't one
    stage.addActor(troop);
  }

  public void addProjectile(Projectile proj, PlayerType playerType) {
    if(playerType == null || proj == null) return;

    if(playerType == PlayerType.BOT_PLAYER) {
      projectilesBottom.add(proj);
    } else if (playerType == PlayerType.TOP_PLAYER) {
      projectilesTop.add(proj);
    }

    stage.addActor(proj);
  }

  public Image getMap() { return map; }

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
