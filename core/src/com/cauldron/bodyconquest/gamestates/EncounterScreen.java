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

/*
The screen where the encounters occurs, hosts a number of actors including,
the health bar, unitButtons, resourceBars and player information.
 */
public class EncounterScreen implements Screen {

  // Probably make this final
  private float botLaneSpawnX;
  private float botLaneSpawnY;
  //If kept final change to all caps
  private final float botTurnPointX = 140;
  private final float botTurnPointY = 100;

  public enum PlayerType {
    BOT_PLAYER,
    TOP_PLAYER
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
  private float mapHeight;
  private float mapWidth;

  Stage stage;
  // Bacteria bct1;

  private float laneWidth;

  private PlayerType playerType;

  // Unit Arrays
  private ArrayList<Unit> botLaneP1;
  private ArrayList<Unit> botLaneP2;

  public EncounterScreen(BodyConquest game, PlayerType playerType) {
    this.playerType = playerType;
    this.game = game;
    gameCamera = new OrthographicCamera();
    // gamePort = new StretchViewport(800, 480, gameCamera);
    gamePort = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, gameCamera);
    stage = new Stage(gamePort);
    Gdx.input.setInputProcessor(stage);
    spawnArea = new SpawnArea();

    botLaneP1 = new ArrayList<Unit>();
    botLaneP2 = new ArrayList<Unit>();

    hud = new HUD(game.batch, this);

    map = new Image(new Texture("core/assets/Basic Map v2.png"));
    float topOfUnitBar = hud.unitBar.getTop();
    mapHeight = BodyConquest.V_HEIGHT - topOfUnitBar;
    mapWidth = mapHeight;
    map.setBounds((BodyConquest.V_WIDTH / 2) - (mapWidth / 2), topOfUnitBar, mapWidth, mapHeight);
    stage.addActor(map);

    // Initialise spawn locations for different player types
    if (playerType.equals(PlayerType.BOT_PLAYER)) {
      // Lane
      botLaneSpawnX = 500;
      botLaneSpawnY = 85;
    }
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
    ArrayList<Unit> temp1 = new ArrayList<Unit>();
    for(Unit unit : botLaneP1) {
      if (unit.isDead()) {
        temp1.add(unit);
        //botLaneP1.remove(unit);
        unit.remove();
        continue;
      }
      unit.checkAttack(botLaneP2);
    }
    ArrayList<Unit> temp2 = new ArrayList<Unit>();
    for(Unit unit : botLaneP2) {
      if (unit.isDead()) {
        temp2.add(unit);
        //botLaneP2.remove(unit);
        unit.remove();
        continue;
      }
      unit.checkAttack(botLaneP1);
    }
    for(Unit u : temp1) botLaneP1.remove(u);
    for(Unit u : temp2) botLaneP2.remove(u);
    // Update Enemy Units
  }

  @Override
  public void render(float delta) {

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
      gameCamera.unproject(touchPos);
      System.out.println("X: " + (touchPos.x - gamePort.getLeftGutterWidth()) + "\tY: " + (touchPos.y - gamePort.getBottomGutterHeight()));
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

  public List<MapObject> getActiveUnits() {
    return activeUnits;
  }

  public void spawnUnit(UnitType unitType, Lane lane) {

    Unit unit = null;
    if(unitType.equals(UnitType.BACTERIA)) {
      unit = new Bacteria(this, playerType, lane);
    }

    // This shouldn't be necessary if play spawn areas are initialised in the constructor
    if (playerType.equals(PlayerType.BOT_PLAYER)) {
      // Too hard coded
      if (lane.equals(Lane.BOT)) {
        unit.setPosition(botLaneSpawnX - (unit.getWidth() / 2), botLaneSpawnY - (unit.getHeight() / 2));
        botLaneP1.add(unit);
        Unit testEnemy = new Bacteria(this, PlayerType.TOP_PLAYER, lane);
        testEnemy.setPosition(140 - (testEnemy.getWidth() / 2), 490 - (testEnemy.getHeight() / 2));
        botLaneP2.add(testEnemy);
        stage.addActor(testEnemy);
      }
      stage.addActor(unit);
    }

    if (playerType.equals(PlayerType.TOP_PLAYER)) {

    }
  }

  /*public void spawnUnit(){

    switch(unitClass) {
      case Bacteria.class:{
    }
    // This shouldn't be necessary if play spawn areas are initialised in the constructor
    if (playerType.equals(PlayerType.BOT_PLAYER)) {
      // Too hard coded
      if (lane.equals(Lane.BOT)) {
        unit.setPosition(botLaneX - (unit.getWidth() / 2), botLaneY - (unit.getHeight() / 2));
      }
      stage.addActor(unit);
    }

    if (playerType.equals(PlayerType.TOP_PLAYER)) {}
  }*/

  public float getBotTurnPointX() {
    return botTurnPointX;
  }

  public float getBotTurnPointY() {
    return botTurnPointY;
  }

}
