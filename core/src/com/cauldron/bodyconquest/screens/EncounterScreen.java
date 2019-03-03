package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.constants.Assets.Lane;
import com.cauldron.bodyconquest.constants.Assets.PlayerType;
import com.cauldron.bodyconquest.constants.Assets.UnitType;
import com.cauldron.bodyconquest.constants.Disease;
import com.cauldron.bodyconquest.constants.GameType;
import com.cauldron.bodyconquest.entities.BasicObject;
import com.cauldron.bodyconquest.entities.ViewObject;
import com.cauldron.bodyconquest.game_logic.Communicator;
import com.cauldron.bodyconquest.networking.Client;
import com.cauldron.bodyconquest.networking.ClientSender;
import com.cauldron.bodyconquest.networking.Server;
import com.cauldron.bodyconquest.networking.utilities.MessageMaker;
import com.cauldron.bodyconquest.rendering.BodyConquest;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class EncounterScreen implements Screen {

  private final float mapSize;
  private final Image map;

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

  private MenuScreen menuScreen;

  private boolean destroyed = false;


  private ArrayList<ViewObject> viewObjects;
  private CopyOnWriteArrayList<BasicObject> objects;

  private int healthBottomBase;
  private int healthTopBase;
  int accumulatorAfterBaseConquered = 0;

  float elapsedSeconds;

  public EncounterScreen(BodyConquest game, GameType gameType) {
    this.game = game;
    client = game.getClient();
    client.setEncounterLogic();
    clientSender = client.clientSender;
    comms = client.getCommunicator();

    gameCamera = new OrthographicCamera();
    gamePort = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, gameCamera);
    stage = new Stage(gamePort);
    Gdx.input.setInputProcessor(stage);


    if(gameType != GameType.MULTIPLAYER_JOIN) {
      server = game.getServer();
      playerType = PlayerType.PLAYER_BOTTOM;
    } else {
      playerType = PlayerType.PLAYER_TOP;
    }

    // Set up map
    map = new Image(new Texture("core/assets/brainmap.png"));
    float topOfUnitBar = 27;
    mapSize = BodyConquest.V_HEIGHT - topOfUnitBar;
    map.setBounds((BodyConquest.V_WIDTH / 2.0f) - (mapSize / 2), topOfUnitBar, mapSize, mapSize);
    stage.addActor(map);
    menuScreen = new MenuScreen(game);
//    while (comms.getPlayerDisease() == null) {
//      try { Gdx.app.wait(); } catch (InterruptedException e) {e.printStackTrace();}
//    }
    hud = new HUD(game.batch, this, playerType, comms.getPlayerDisease(), stage);

    accumulatorAfterBaseConquered = 0;

  }

  private void testInit() {}

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

    if (accumulatorAfterBaseConquered < Assets.UPDATESCREENTILL) {
      objects = comms.getAllObjects();

      // Turn BasicObjects from server/communicator into ViewObjects (and gives them a texture)
      viewObjects = new ArrayList<ViewObject>();
      long tEnd = System.currentTimeMillis();
      long tDelta = tEnd - MenuScreen.timeOfServer;
      elapsedSeconds = tDelta / 1000.0f;
      for (BasicObject o : objects) {

        switch (o.getMapObjectType()) {
          case FLU:
            viewObjects.add(
                new ViewObject(
                    o,
                    Assets.pathFlu,
                    Assets.frameColsFlu,
                    Assets.frameRowsFlu,
                    elapsedSeconds));
            break;
          case VIRUS:
            viewObjects.add(
                new ViewObject(
                    o,
                    Assets.pathVirus,
                    Assets.frameColsVirus,
                    Assets.frameRowsVirus,
                    elapsedSeconds));
            break;
          case BACTERIA:
            viewObjects.add(
                new ViewObject(
                    o,
                    Assets.pathBacteria,
                    Assets.frameColsBacteria,
                    Assets.frameRowsBacteria,
                    elapsedSeconds));
            break;
          case BACTERTIA_BASE:
            viewObjects.add(new ViewObject(o, Assets.pathBaseImage, elapsedSeconds));
            break;
            //        case VIRUS_BASE:
            //          ////TO DO add Virus base Texture
            //          break;
            //        case MONSTER_BASE:
            //          ////TO DO add Monster base Texture
            //          break;
            //        case BUCKET:
            //          viewObjects.add(new ViewObject(o,Assets.pathBucket,1,1));
            //          break;
          case FLUPROJECTILE:
            viewObjects.add(
                new ViewObject(
                    o,
                    Assets.pathProjectile,
                    Assets.frameColsProjectile,
                    Assets.frameRowsProjectile,
                    elapsedSeconds));
        }
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

      // Draw HUD
      //hud.getStage().draw();


      // Start, draw and end spriteBatch
      game.batch.begin();
      game.batch.end();
      for (ViewObject vo : viewObjects) vo.remove();


      if(accumulatorAfterBaseConquered > 5 && !destroyed){
        boolean destroyed = true;
        determineWinner();
        switchScreen(game,menuScreen);
      }

    }

    if (((healthTopBase == Assets.MINHEALTH) || (healthBottomBase == Assets.MINHEALTH))
        && accumulatorAfterBaseConquered < Assets.INCREASEACCUMULATORTILL) {

      accumulatorAfterBaseConquered++;

    }
  }

  private void updateResourceBars(){
    int l = comms.getLipidsBottom();
    int p = comms.getProteinsBottom();
    int c = comms.getSugarsBottom();

    hud.updateResourceBars(l, p, c, elapsedSeconds);
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

  public int getHealthBottomBase() {
    return healthBottomBase;
  }

  public int getHealthTopBase() {
    return healthTopBase;
  }

  public void switchScreen(final BodyConquest game,final Screen newScreen){
    //System.out.println("Why it does not change the screen");
    stage.getRoot().getColor().a = 1;
    SequenceAction sequenceAction = new SequenceAction();
    sequenceAction.addAction(Actions.fadeOut(1.0f));
    sequenceAction.addAction(
        Actions.run(

            new Runnable() {
              @Override
              public void run() {
                //dispose();
                game.setScreen(newScreen);
              }
            }));
    stage.getRoot().addAction(sequenceAction);
    //dispose();
  }

  public void DrawShadowed(String str, float x, float y, float width, int align, Color color)
  {
    game.font.getData().setScale(4,4);
    game.font.setColor(Color.BLACK);

    for (int i = -1; i < 2; i++)
    {
      for (int j = -1; j < 2; j++)
      {
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

  private void determineWinner(){
    game.batch.begin();

    if (playerType == PlayerType.PLAYER_BOTTOM){
      if (healthBottomBase <= 0){
        ShowGameResult("DEFEAT!");
        client.closeEverything();
        if (server != null){
          server.closeEverything();
        }
      } else if (healthTopBase <= 0){
        ShowGameResult("VICTORY!");
        client.closeEverything();
        if (server != null){
          server.closeEverything();
        }
      }
    } else {
      if (healthTopBase <= 0){
        ShowGameResult("DEFEAT!");
        client.closeEverything();
        if (server != null){
          server.closeEverything();
        }
      } else if (healthBottomBase <= 0){
        ShowGameResult("VICTORY!");
        client.closeEverything();
        if (server != null){
          server.closeEverything();
        }
      }
    }
    game.batch.end();
  }
}
