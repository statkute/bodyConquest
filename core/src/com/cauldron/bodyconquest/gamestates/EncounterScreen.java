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
import com.cauldron.bodyconquest.rendering.BodyConquest;

/*
The screen where the encounters occurs, hosts a number of actors including,
the health bar, unitButtons, resourceBars and player information.
 */
public class EncounterScreen implements Screen {

  public enum PlayerType {
    BOT_PLAYER,
    TOP_PLAYER
  }

  public enum Lanes{
        TOP, BOT, MID
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

    private float botLaneX;
    private float botLaneY;

    private float midLaneX;
    private float midLaneY;

    private float topLaneX;
    private float topLaneY;

  // If kept final change to all caps
  private final float botTurnPointX = 85;
  private final float botTurnPointY = 60;

  // Probably make this final
  private final float botLaneBPSpawnX = 535;
  private final float botLaneBPSpawnY = 90;
  private final float botLaneTPSpawnX = 170;
  private final float botLaneTPSpawnY = 470;

  // Unit Arrays
  private ArrayList<Unit> botLaneP1;
  private ArrayList<Unit> botLaneP2;

    public EncounterScreen(BodyConquest game) {
        this.game = game;
        gameCamera = new OrthographicCamera();
        //gamePort = new StretchViewport(800, 480, gameCamera);
        gamePort = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, gameCamera);
        stage = new Stage(gamePort);
        Gdx.input.setInputProcessor(stage);
        spawnArea = new SpawnArea();
      botLaneP1 = new ArrayList<Unit>();
      botLaneP2 = new ArrayList<Unit>();

      hud = new HUD(game.batch, this, PlayerType.BOT_PLAYER);

        map = new Image(new Texture("core/assets/Basic Map v2.png"));
        float topOfUnitBar = hud.unitBar.getTop();
      mapSize = BodyConquest.V_HEIGHT - topOfUnitBar;

      map.setBounds((BodyConquest.V_WIDTH / 2.0f) - (mapSize / 2), topOfUnitBar, mapSize, mapSize);
      stage.addActor(map);

      // Initialise spawn locations for different player types
      new BasicTestAI(this, PlayerType.TOP_PLAYER).start();
        // Lanes
        botLaneX = 500;
        botLaneY = 85;

        midLaneX = 505;
        midLaneY = 185;

        topLaneX = 600;
        topLaneY = 225;
    }



  @Override
  public void show() {}

  public void update(float dt) {
    // Handle Input

    /* MULTIPLAYER */
    // Send inputs to server

    // Receive update from server

    /* SINGLE PLAYER */


    // Update Player Units
    checkLanes(botLaneP1, botLaneP2);
    checkLanes(botLaneP2, botLaneP1);

    // Update Enemy Units
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
  public void render(float delta) {

    gameCamera.update();
    update(delta);
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
    // game.batch.draw(map,(BodyConquest.V_WIDTH / 2) - (mapWidth / 2), hud.unitBar.getTop());
    stage.act();
    stage.draw();
    hud.stage.draw();
    // spawnArea.draw(game.batch, 1);
    game.batch.begin();
    // game.batch.draw();
    game.batch.end();

    // Mouse (Development tool)
    if (Gdx.input.isTouched()) {
      Vector3 touchPos = new Vector3();
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      gameCamera.unproject(touchPos, gamePort.getScreenX(), gamePort.getScreenY(), gamePort.getScreenWidth(), gamePort.getScreenHeight());
      System.out.println(
          "X: "
              + (touchPos.x /*- gamePort.getLeftGutterWidth()*/)
              + "\tY: "
              + (touchPos.y /*- gamePort.getBottomGutterHeight()*/));
    }
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

    Unit unit = null;
    if (unitType.equals(UnitType.BACTERIA)) {
      unit = new Bacteria(this, playerType, lane);
    }

    if (unit == null || lane == null || playerType == null) return;

    // This shouldn't be necessary if play spawn areas are initialised in the constructor
    if (playerType.equals(PlayerType.BOT_PLAYER)) {
      // Too hard coded
      if (lane == Lane.BOT) {
        unit.setPosition(
            botLaneBPSpawnX - (unit.getWidth() / 2), botLaneBPSpawnY - (unit.getHeight() / 2));
        botLaneP1.add(unit);
      }
    }

    if (playerType.equals(PlayerType.TOP_PLAYER)) {
      if (lane == Lane.BOT) {
        unit.setPosition(
            botLaneTPSpawnX - (unit.getWidth() / 2), botLaneTPSpawnY - (unit.getHeight() / 2));
        botLaneP2.add(unit);
      }
    }


    // Too hard coded
    if(lane.equals(Lanes.BOT)) {
      unit.setPosition( botLaneX - (unit.getWidth() / 2) , botLaneY - (unit.getHeight() / 2));
    } else if (lane.equals(Lanes.MID)){
      unit.setPosition( midLaneX - (unit.getWidth() / 2) , midLaneY - (unit.getHeight() / 2));
    } else if (lane.equals(Lanes.TOP)){
      unit.setPosition( topLaneX - (unit.getWidth() / 2) , topLaneY - (unit.getHeight() / 2));
    }

    // Maybe add unit to data structure containing all units

    stage.addActor(unit);
  }

  public float getBotTurnPointX() {
    return botTurnPointX;
  }

  public float getBotTurnPointY() {
    return botTurnPointY;
  }
}
