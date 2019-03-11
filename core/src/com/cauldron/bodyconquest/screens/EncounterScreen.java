package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.FlushablePool;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cauldron.bodyconquest.constants.*;
import com.cauldron.bodyconquest.constants.Assets.Lane;
import com.cauldron.bodyconquest.constants.Assets.PlayerType;
import com.cauldron.bodyconquest.constants.Assets.UnitType;
import com.cauldron.bodyconquest.entities.BasicObject;
import com.cauldron.bodyconquest.entities.Map;
import com.cauldron.bodyconquest.entities.ViewObject;
import com.cauldron.bodyconquest.game_logic.Communicator;
import com.cauldron.bodyconquest.handlers.AnimationWrapper;
import com.cauldron.bodyconquest.networking.Client;
import com.cauldron.bodyconquest.networking.ClientSender;
import com.cauldron.bodyconquest.networking.Server;
import com.cauldron.bodyconquest.networking.utilities.MessageMaker;
import com.cauldron.bodyconquest.rendering.BodyConquest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.cauldron.bodyconquest.constants.Assets.UnitType.*;
import static com.cauldron.bodyconquest.constants.ProjectileType.FLU_PROJECTILE;
import static com.cauldron.bodyconquest.constants.BaseType.INFLUENZA_BASE;

public class EncounterScreen implements Screen {

  private final float mapSize;
  private final Map map;

  protected static float timeAlive;
  private static float timeOfDmgTakenBottom;
  private static float timeOfDmgTakenTop;

  FPSLogger fps = new FPSLogger();

  private static final float SHAKE_TIME_ON_DMG = 0.5f;
  private static final float SHAKE_DIST = 9.0f;

  public static GameType gameType;


  public static final float BLINK_TIME_AFTER_DMG = 0.07f;

  private final OrthographicCamera gameCamera;
  private final FitViewport gamePort;
  private final Stage stage;
  private final BodyConquest game;
  private final HUD hud;
  private Communicator comms;
  private ClientSender clientSender;
  private Client client;
  private Server server;
  private PlayerType playerType;
  //private Timer timer;
  private DecimalFormat value;


  // To get back to menu screen change this to another encounter screen
  private MenuScreen menuScreen;

  private Disease playerDisease;

  private boolean destroyed = false;

  private ArrayList<ViewObject> viewObjects;
  private CopyOnWriteArrayList<BasicObject> objects;

  private int healthBottomBase;
  private int healthTopBase;

  private int healthBottomBaseBefore;
  private int healthTopBaseBefore;

  int accumulatorAfterBaseConquered = 0;

  float elapsedSeconds;

  float time = 120;

  private String username;

  private ConcurrentHashMap<MapObjectType, TexturePool> poolHashMap;

  public EncounterScreen(BodyConquest game, GameType gameType) {
    this.gameType = gameType;
    this.game = game;
    client = game.getClient();
    client.setEncounterLogic();
    clientSender = client.clientSender;
    comms = client.getCommunicator();

    comms.setStartEncounter(false);

    gameCamera = new OrthographicCamera();
    gamePort = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, gameCamera);
    stage = new Stage(gamePort);
    Gdx.input.setInputProcessor(stage);
    this.username = game.getUsername();

    if (gameType != GameType.MULTIPLAYER_JOIN) {
      server = game.getServer();
      playerType = PlayerType.PLAYER_BOTTOM;
    } else {
      playerType = PlayerType.PLAYER_TOP;
    }

    // Set up map
    //map = new Image(new Texture("core/assets/brainmap.png"));

    long tEnd = System.currentTimeMillis();
    long tDelta = tEnd - MenuScreen.timeOfServer;
    elapsedSeconds = tDelta / 1000.0f;

    map = new Map(comms.getCurrentOrgan(), elapsedSeconds);
    float topOfUnitBar = 27;
    mapSize = BodyConquest.V_HEIGHT - topOfUnitBar;
    map.setBounds((BodyConquest.V_WIDTH / 2.0f) - (mapSize / 2), topOfUnitBar, mapSize, mapSize);
    stage.addActor(map);
    menuScreen = new MenuScreen(game,username);
//    while (comms.getPlayerDisease() == null) {
//      try { Gdx.app.wait(); } catch (InterruptedException e) {e.printStackTrace();}
//    }
    hud = new HUD(this, playerType, comms.getPlayerDisease(), stage);

    accumulatorAfterBaseConquered = 0;
    timeAlive = 0;
    timeOfDmgTakenBottom = -1;
    timeOfDmgTakenTop = -1;

    healthTopBaseBefore = 100;
    healthBottomBaseBefore =100;

    poolHashMap = new ConcurrentHashMap<MapObjectType, TexturePool>();

    value = new DecimalFormat("0");
    //timer = new Timer()
  }

  private class TexturePool extends Pool<Animation<TextureRegion>> {

    private int frameCols, frameRows;
    private float frameRate;
    private String pathTexture;

    public TexturePool(String pathTexture, int frameCols, int frameRows, float frameRate){
      super();

      this.frameCols = frameCols;
      this.frameRows = frameRows;
      this.frameRate = frameRate;

      this.pathTexture = pathTexture;
    }

    @Override
    protected Animation<TextureRegion> newObject() {
      return AnimationWrapper.getSpriteSheet(frameCols, frameRows, frameRate, pathTexture);
    }
  }

  @Override
  public void show() {
    stage.getRoot().getColor().a = 0;
    stage.getRoot().addAction(Actions.fadeIn(0.5f));
  }

  @Override
  public void render(float delta) {

    updateResourceBars();

    healthBottomBase = comms.getBottomHealthPercentage();
    healthTopBase = comms.getTopHealthPercentage();

    if(healthBottomBaseBefore != healthBottomBase){
      //System.out.println();
      timeOfDmgTakenBottom = timeAlive;
    }

    if(healthTopBaseBefore != healthTopBase){
      //System.out.println("Health not equals");
      timeOfDmgTakenTop = timeAlive;
    }

    healthBottomBaseBefore = healthBottomBase;
    healthTopBaseBefore = healthTopBase;

    timeAlive += delta;

    //fps.log();

    if (accumulatorAfterBaseConquered < Assets.UPDATESCREENTILL) {
      objects = comms.getAllObjects();

      // Turn BasicObjects from server/communicator into ViewObjects (and gives them a texture)
      viewObjects = new ArrayList<ViewObject>();
      long tEnd = System.currentTimeMillis();
      long tDelta = tEnd - MenuScreen.timeOfServer;
      elapsedSeconds = tDelta / 1000.0f;
      for (BasicObject o : objects) {

        Enum i = o.getMapObjectType();

        if(!poolHashMap.containsKey(i)) poolHashMap.put(o.getMapObjectType(), poolSetup(i));

          viewObjects.add(
                  new ViewObject(
                          o,
                          elapsedSeconds,
                          game.getClient().getCommunicator().getPlayerType(),
                          poolHashMap.get(i).obtain()));


      }

      for (ViewObject vo : viewObjects) {

        stage.addActor(vo);
      }
      // Update the camera
      gameCamera.update();

      // Render background
      Gdx.gl.glClearColor(0, 0, 0, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

      // Combine encounter and hud views
      game.batch.setProjectionMatrix(stage.getCamera().combined);

      // Make all actors call their act methods
      stage.act();
      // Draw Actors
      stage.draw();

      // draw a font with numbers


      shakeCamera();
      // Start, draw and end spriteBatch
      game.batch.begin();

      drawTime();
      drawUsername();
      drawNumbersOnResourceBars();


      updateUnitButtons();

      game.batch.end();
      for (ViewObject vo : viewObjects) {
        poolHashMap.get(vo.getMapObjectType()).free(vo.getWalkAnimation());
        vo.remove();
      }


      if((accumulatorAfterBaseConquered > 5 && !destroyed) || time == 0.0f){
        boolean destroyed = true;
        determineWinner();
        switchScreen(game, menuScreen);
      }
    }



    if (((healthTopBase == Assets.MINHEALTH) || (healthBottomBase == Assets.MINHEALTH))
        && accumulatorAfterBaseConquered < Assets.INCREASEACCUMULATORTILL) {
      accumulatorAfterBaseConquered++;
    }
  }

  private void drawUsername() {
    game.usernameFont.getData().setScale(0.70f,0.70f);

    if(username.length() > 9){
      game.usernameFont.draw(game.batch,username.toLowerCase().substring(0,9),BodyConquest.V_WIDTH - 105.0f,hud.getUnitBar().getImageHeight() + 24.0f);
    }
    else{
      game.usernameFont.draw(game.batch,username.toLowerCase(),BodyConquest.V_WIDTH - 105.0f,hud.getUnitBar().getImageHeight() + 24.0f);
    }

  }

  private void drawTime() {
    time -= Gdx.graphics.getDeltaTime();
    if(time < 0){
      time = 0.0f;
    }
    game.timerFont.getData().setScale(0.75f,0.75f);
    game.timerFont.draw(game.batch,"Time Left",BodyConquest.V_WIDTH - 110.0f,550.0f);
    game.timerFont.getData().setScale(1.25f,1.25f);
    game.timerFont.draw(game.batch,Double.toString(Double.valueOf(value.format(time))),BodyConquest.V_WIDTH - 110.0f,510.0f);
  }

  private void updateResourceBars(){
    int l = comms.getLipidsBottom();
    int p = comms.getProteinsBottom();
    int c = comms.getSugarsBottom();

    hud.updateResourceBars(l, p, c, elapsedSeconds);
  }

  private void updateUnitButtons(){
    Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
    gameCamera.unproject(tmp);

    Actor b0 = stage.getRoot().findActor("bucket0");
    Actor b1 = stage.getRoot().findActor("bucket1");
    Actor b2 = stage.getRoot().findActor("bucket2");
    Rectangle r0 = new Rectangle(b0.getX(),b0.getY(),b0.getWidth(),b0.getHeight());
    Rectangle r1 = new Rectangle(b1.getX(),b1.getY(),b1.getWidth(),b1.getHeight());
    Rectangle r2 = new Rectangle(b2.getX(),b2.getY(),b2.getWidth(),b2.getHeight());
    if(r0.contains(tmp.x, tmp.y)){
      //hud.makeBucketVisible();
      game.font.draw(game.batch,"whateverrrr0000",300,300);
    } else if(r1.contains(tmp.x, tmp.y)){
      game.font.draw(game.batch,"whateverrrr1111",300,300);
    } else if(r2.contains(tmp.x, tmp.y)){
      game.font.draw(game.batch,"whateverrrr2222",300,300);
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
  }

  public void spawnUnit(UnitType unitType, Lane lane, PlayerType playerType) {
    String message = MessageMaker.spawnTroopsMessage(unitType, lane, playerType);
    clientSender.sendMessage(message);
  }

  public void useAbility(AbilityType abilityType, Lane lane, PlayerType playerType) {
    String message = MessageMaker.castAbilityMessage(abilityType, lane, playerType);
    clientSender.sendMessage(message);
  }

  public void useAbility(AbilityType abilityType, int xAxis, int yAxis, PlayerType playerType) {
    String message = MessageMaker.castAbilityMessage(abilityType, xAxis, yAxis, playerType);
    clientSender.sendMessage(message);
  }

  public int getHealthBottomBase() {
    return healthBottomBase;
  }

  public int getHealthTopBase() {
    return healthTopBase;
  }

  public void switchScreen(final BodyConquest game, final Screen newScreen) {
    // System.out.println("Why it does not change the screen");
    stage.getRoot().getColor().a = 1;
    SequenceAction sequenceAction = new SequenceAction();
    sequenceAction.addAction(Actions.fadeOut(1.0f));
    sequenceAction.addAction(
        Actions.run(
            new Runnable() {
              @Override
              public void run() {
                dispose();
                game.setScreen(newScreen);
              }
            }));
    stage.getRoot().addAction(sequenceAction);
    //dispose();
  }

  public void DrawShadowed(String str, float x, float y, float width, int align, Color color) {
    game.font.getData().setScale(4, 4);
    game.font.setColor(Color.BLACK);

    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        game.font.draw(game.batch, str, x + i, y + j, width, align, false);
      }
    }

    game.font.setColor(color);
    game.font.draw(game.batch, str, x, y, width, align, false);
    game.font.setColor(Color.WHITE);
  }

  private void ShowGameResult(String result)
  {
    DrawShadowed(result,
            0,
            BodyConquest.V_HEIGHT / 2 + 30,
            stage.getWidth(),
            Align.center,
            Color.RED);
  }

  private void determineWinner() {
    game.batch.begin();


    if (playerType == PlayerType.PLAYER_BOTTOM){
      if ((healthBottomBase <= 0)||(time == 0.0f && healthBottomBase < healthTopBase)){
        ShowGameResult("DEFEAT!");
        client.closeEverything();
        if (server != null) {
          server.closeEverything();
        }
      } else if ((healthTopBase <= 0) || (time == 0.0f && healthBottomBase > healthTopBase)){
        ShowGameResult("VICTORY!");
        client.closeEverything();
        if (server != null) {
          server.closeEverything();
        }
      }
      else if(time == 0.0f && healthTopBase == healthBottomBase){
        ShowGameResult("DRAW!");
        client.closeEverything();
        if (server != null){
          server.closeEverything();
        }

      }
    } else {
      if (healthTopBase <= 0 || (time == 0.0f && healthBottomBase > healthTopBase)){
        ShowGameResult("DEFEAT!");
        client.closeEverything();
        if (server != null) {
          server.closeEverything();
        }
      } else if (healthBottomBase <= 0 || (time == 0.0f && healthBottomBase < healthTopBase)){
        ShowGameResult("VICTORY!");
        client.closeEverything();
        if (server != null) {
          server.closeEverything();
        }
      }
      else if(time == 0.0f && healthTopBase == healthBottomBase){
        ShowGameResult("DRAW!");
        client.closeEverything();
        if (server != null){
          server.closeEverything();
        }
      }
    }
    game.batch.end();
  }

  public static float getTimeOfDmgTakenBottom() {
    return timeOfDmgTakenBottom;
  }

  public static float getTimeOfDmgTakenTop() {
    return timeOfDmgTakenTop;
  }

  public static float getTimeAlive(){
    return timeAlive;
  }

  private void shakeCamera(){

    if(playerType == PlayerType.PLAYER_TOP || playerType == PlayerType.AI){

      stage.getCamera().position.set(stage.getWidth() / 2, stage.getHeight() / 2, 0);

      //game.getClient().getCommunicator().getPlayerType();
      if (healthTopBase > 0 &&
              getTimeAlive() - getTimeOfDmgTakenTop() < SHAKE_TIME_ON_DMG){

        stage.getCamera().translate(-(SHAKE_DIST/2) + MathUtils.random(SHAKE_DIST),
                -(SHAKE_DIST / 2) + MathUtils.random(SHAKE_DIST), 0);
      }
      stage.getCamera().update();

    }

    if(playerType == PlayerType.PLAYER_BOTTOM){

      stage.getCamera().position.set(stage.getWidth() / 2, stage.getHeight() / 2, 0);

      //game.getClient().getCommunicator().getPlayerType();
      if (healthBottomBase > 0 &&
              getTimeAlive() - getTimeOfDmgTakenBottom() < SHAKE_TIME_ON_DMG){

        stage.getCamera().translate(-(SHAKE_DIST/2) + MathUtils.random(SHAKE_DIST),
                -(SHAKE_DIST / 2) + MathUtils.random(SHAKE_DIST), 0);
      }
      stage.getCamera().update();

    }
  }

  private void drawNumbersOnResourceBars(){
    game.font.getData().setScale(1.0f, 1.0f);
    game.font.draw(game.batch, Integer.toString(comms.getSugarsBottom()), hud.getCarbsResourceBar().getX()+15, hud.getCarbsResourceBar().getY()+30, 10, 1, false);
    game.font.draw(game.batch, Integer.toString(comms.getLipidsBottom()), hud.getLipidsResourceBar().getX()+15, hud.getLipidsResourceBar().getY()+30, 10, 1, false);
    game.font.draw(game.batch, Integer.toString(comms.getProteinsBottom()), hud.getProteinResourceBar().getX()+15, hud.getProteinResourceBar().getY()+30, 10, 1, false);

  }

  private TexturePool poolSetup(Enum i) {

    float frameRate = 0.2f;

      //Enum i = o.getMapObjectType();
      if (FLU.equals(i)) {
        return new TexturePool(
                        Assets.pathFlu,
                        Assets.frameColsFlu,
                        Assets.frameRowsFlu,
                        frameRate);
      } else if (VIRUS.equals(i)) {
        return new TexturePool(
                        Assets.pathVirus,
                        Assets.frameColsVirus,
                        Assets.frameRowsVirus,
                        frameRate);
      } else if (BACTERIA.equals(i)) {
        return new TexturePool(
                        Assets.pathBacteria,
                        Assets.frameColsBacteria,
                        Assets.frameRowsBacteria,
                        frameRate);
      } else if (INFLUENZA_BASE.equals(i)) {
        return new TexturePool(Assets.pathBaseImage, 3, 5, frameRate);
      //        case ROTAVIRUS_BASE:
      //          ////TO DO add Virus base Texture
      //          break;
      //        case MEASLES_BASE:
      //          ////TO DO add Monster base Texture
      //          break;
    } else if (FLU_PROJECTILE.equals(i)) {
      return new TexturePool(
              Assets.pathProjectile,
              Assets.frameColsProjectile,
              Assets.frameRowsProjectile,
              frameRate);
    }return null;
  }


}
