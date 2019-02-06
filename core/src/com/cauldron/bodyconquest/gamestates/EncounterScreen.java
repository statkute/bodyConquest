package com.cauldron.bodyconquest.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cauldron.bodyconquest.entities.*;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.cauldron.bodyconquest.entities.Unit;
import com.cauldron.bodyconquest.entities.Unit.*;

import java.util.ArrayList;
import com.cauldron.bodyconquest.entities.Bacteria;
import com.cauldron.bodyconquest.entities.HUD;
import com.cauldron.bodyconquest.entities.MapObject;
import com.cauldron.bodyconquest.entities.SpawnArea;

/*
The screen where the encounters occurs, hosts a number of actors including,
the health bar, unitButtons, resourceBars and player information.
 */
public class EncounterScreen implements Screen {

  public enum PlayerType {
    BOT_PLAYER,
    TOP_PLAYER
  }

  public enum Lanes {
    TOP,
    BOT,
    MID
  }

  public enum Lane {
    TOP,
    BOT,
    MID
  }

  private BodyConquest game;
  private OrthographicCamera gameCamera;
  private Viewport gamePort;

  private List<MapObject> activeUnits;

  private HUD hud;

  public SpawnArea spawnArea;

  private Image map;
  private float mapSize;
  private float mapHeight;
  private float mapWidth;

  private Stage stage;

  private float laneWidth;

  // If kept final change to all caps
  private final float botTurnPointX = 85;
  private final float botTurnPointY = 60;

  // Probably make this final
  private final float botLaneBPSpawnX = 500; // 535;
  private final float botLaneBPSpawnY = 85; // 90;
  private final float midLaneBPSpawnX = 505;
  private final float midLaneBPSpawnY = 185;
  private final float topLaneBPSpawnX = 600;
  private final float topLaneBPSpawnY = 225;

  private final float botLaneTPSpawnX = 170;
  private final float botLaneTPSpawnY = 470;
  // Not yet intialised
  private float midLaneTPSpawnX;
  private float topLaneTPSpawnX;
  private float midLaneTPSpawnY;
  private float topLaneTPSpawnY;

  // Unit Arrays (Data type and usage is subject to future change)
  private ArrayList<Unit> botLaneP1;
  private ArrayList<Unit> botLaneP2;

  private ArrayList<Unit> midLaneP1;
  private ArrayList<Unit> midLaneP2;

  private ArrayList<Unit> topLaneP1;
  private ArrayList<Unit> topLaneP2;

  public EncounterScreen(BodyConquest game) {
    this.game = game;
    gameCamera = new OrthographicCamera();
    gamePort = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, gameCamera);
    stage = new Stage(gamePort);
    Gdx.input.setInputProcessor(stage);

    // Initialise player HUD
    hud = new HUD(game.batch, this, PlayerType.BOT_PLAYER);

    // Set up the map image (PROBLEM: reliant on unit bar which we must now assume the size will be
    // identical for every user)
    map = new Image(new Texture("core/assets/Basic Map v2.png"));
    float topOfUnitBar = hud.unitBar.getTop();
    mapSize = BodyConquest.V_HEIGHT - topOfUnitBar;
    map.setBounds((BodyConquest.V_WIDTH / 2.0f) - (mapSize / 2), topOfUnitBar, mapSize, mapSize);
    stage.addActor(map);

    // Initialise spawn locations for different player types
    spawnArea = new SpawnArea();

    // Initialise unit arrays
    botLaneP1 = new ArrayList<Unit>();
    botLaneP2 = new ArrayList<Unit>();

    midLaneP1 = new ArrayList<Unit>();
    midLaneP2 = new ArrayList<Unit>();

    topLaneP1 = new ArrayList<Unit>();
    topLaneP2 = new ArrayList<Unit>();

    new BasicTestAI(this, PlayerType.TOP_PLAYER).start();
  }

  private void checkLanes(ArrayList<Unit> laneP1, ArrayList<Unit> laneP2) {
    ArrayList<Unit> deadUnits = new ArrayList<Unit>();
    for (Unit unit : laneP1) {
      if (unit.isDead()) {
        deadUnits.add(unit);
        unit.remove();
        continue;
      }
      unit.checkAttack(laneP2);
    }
    // This gives particular players a very slight advantage because certain units will be deleted
    // first if they both die
    for (Unit u : deadUnits) laneP1.remove(u);
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
    checkLanes(botLaneP1, botLaneP2);
    checkLanes(botLaneP2, botLaneP1);

    checkLanes(midLaneP1, midLaneP2);
    checkLanes(midLaneP2, midLaneP1);

    checkLanes(topLaneP1, topLaneP2);
    checkLanes(topLaneP2, topLaneP1);
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
    game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

    // This draws the map as an image
    // game.batch.draw(map,(BodyConquest.V_WIDTH / 2) - (mapWidth / 2), hud.unitBar.getTop());

    // Make all actors call their act methods
    stage.act();
    // Draw Actors
    stage.draw();

    // Draw HUD
    hud.stage.draw();

    // Draw Spawn Area (Old implementation)
    // spawnArea.draw(game.batch, 1);

    // Start, draw and end spriteBatch
    game.batch.begin();
    // game.batch.draw();
    game.batch.end();

    // Development Tools (NOT NECESSARY FOR GAMEPLAY) ///////////////////////

    // Get X, Y co-ordinates of mouse click (Not working reliably) WIP
    if (Gdx.input.isTouched()) {
      Vector3 touchPos = new Vector3();
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      gameCamera.unproject(
          touchPos,
          gamePort.getScreenX(),
          gamePort.getScreenY(),
          gamePort.getScreenWidth(),
          gamePort.getScreenHeight());
      System.out.println(
          "X: "
              + (touchPos.x /*- gamePort.getLeftGutterWidth()*/)
              + "\tY: "
              + (touchPos.y /*- gamePort.getBottomGutterHeight()*/));
    }
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
    // Initialise the unit
    Unit unit = null;

    // Initialise unit type
    if (unitType.equals(UnitType.BACTERIA)) {
      unit = new Bacteria(this, playerType, lane);
    }

    // Return if invalid unit, lane or player type is used
    if (unit == null || lane == null || playerType == null) return;

    // Spawn units for bottom player
    if (playerType.equals(PlayerType.BOT_PLAYER)) {
      if (lane == Lane.BOT) {
        unit.setPosition(
            botLaneBPSpawnX - (unit.getWidth() / 2), botLaneBPSpawnY - (unit.getHeight() / 2));
        botLaneP1.add(unit);
      } else if (lane == Lane.MID) {
        unit.setPosition(
            midLaneBPSpawnX - (unit.getWidth() / 2), midLaneBPSpawnY - (unit.getHeight() / 2));
        midLaneP1.add(unit);
      } else if (lane == Lane.BOT) {
        unit.setPosition(
            topLaneBPSpawnX - (unit.getWidth() / 2), topLaneBPSpawnY - (unit.getHeight() / 2));
        topLaneP1.add(unit);
      }
    }

    // Spawn units for top player
    if (playerType.equals(PlayerType.TOP_PLAYER)) {
      if (lane == Lane.BOT) {
        unit.setPosition(
            botLaneTPSpawnX - (unit.getWidth() / 2), botLaneTPSpawnY - (unit.getHeight() / 2));
        botLaneP2.add(unit);
      } else if (lane == Lane.MID) {
        unit.setPosition(
            midLaneTPSpawnX - (unit.getWidth() / 2), midLaneTPSpawnY - (unit.getHeight() / 2));
        midLaneP2.add(unit);
      } else if (lane == Lane.BOT) {
        unit.setPosition(
            topLaneTPSpawnX - (unit.getWidth() / 2), topLaneTPSpawnY - (unit.getHeight() / 2));
        topLaneP2.add(unit);
      }
    }

    // Maybe add unit to data structure containing all units if the stage isn't one
    stage.addActor(unit);
  }

  public float getBotTurnPointX() {
    return botTurnPointX;
  }

  public float getBotTurnPointY() {
    return botTurnPointY;
  }
}
