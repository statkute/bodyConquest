package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.constants.Constants.*;
import com.cauldron.bodyconquest.entities.BasicObject;
import com.cauldron.bodyconquest.entities.ViewObject;
import com.cauldron.bodyconquest.game_logic.Communicator;
import com.cauldron.bodyconquest.networking.ClientSender;
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

  private ArrayList<ViewObject> viewObjects;
  private CopyOnWriteArrayList<BasicObject> objects;

  private int healthBottomBase;
  private int healthTopBase;
  int accumulatorAfterBaseConquered = 0;

  public EncounterScreen(BodyConquest game, Communicator comms, ClientSender clientSender) {
    this.comms = comms;
    this.clientSender = clientSender;
    testInit();
    this.game = game;
    gameCamera = new OrthographicCamera();
    gamePort = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, gameCamera);
    stage = new Stage(gamePort);
    Gdx.input.setInputProcessor(stage);
    hud = new HUD(game.batch, this, PlayerType.PLAYER_BOTTOM);
    accumulatorAfterBaseConquered = 0;

    // Set up map
    map = new Image(new Texture("core/assets/Basic Map v2.png"));
    float topOfUnitBar = hud.getUnitBar().getTop();
    mapSize = BodyConquest.V_HEIGHT - topOfUnitBar;
    map.setBounds((BodyConquest.V_WIDTH / 2.0f) - (mapSize / 2), topOfUnitBar, mapSize, mapSize);
    stage.addActor(map);
  }

  private void testInit() {}

  @Override
  public void show() {}

  @Override
  public void render(float delta) {

    healthBottomBase = comms.getBottomHealthPercentage();
    healthTopBase = comms.getTopHealthPercentage();

    if (accumulatorAfterBaseConquered < Constants.UPDATESCREENTILL) {
      objects = comms.getAllObjects();

      // Turn BasicObjects from server/communicator into ViewObjects (and gives them a texture)
      viewObjects = new ArrayList<ViewObject>();
      long tEnd = System.currentTimeMillis();
      long tDelta = tEnd - MenuScreen.timeOfServer;
      float elapsedSeconds = tDelta / 1000.0f;
      for (BasicObject o : objects) {

        switch (o.getMapObjectType()) {
          case FLU:
            viewObjects.add(
                new ViewObject(
                    o,
                    Constants.pathFlu,
                    Constants.frameColsFlu,
                    Constants.frameRowsFlu,
                    elapsedSeconds));
            break;
          case VIRUS:
            viewObjects.add(
                new ViewObject(
                    o,
                    Constants.pathVirus,
                    Constants.frameColsVirus,
                    Constants.frameRowsVirus,
                    elapsedSeconds));
            break;
          case BACTERIA:
            viewObjects.add(
                new ViewObject(
                    o,
                    Constants.pathBacteria,
                    Constants.frameColsBacteria,
                    Constants.frameRowsBacteria,
                    elapsedSeconds));
            break;
          case BACTERTIA_BASE:
            viewObjects.add(new ViewObject(o, Constants.pathBaseImage, elapsedSeconds));
            break;
            //        case VIRUS_BASE:
            //          ////TO DO add Virus base Texture
            //          break;
            //        case MONSTER_BASE:
            //          ////TO DO add Monster base Texture
            //          break;
            //        case BUCKET:
            //          viewObjects.add(new ViewObject(o,Constants.pathBucket,1,1));
            //          break;
          case FLUPROJECTILE:
            viewObjects.add(
                new ViewObject(
                    o,
                    Constants.pathProjectile,
                    Constants.frameColsProjectile,
                    Constants.frameRowsProjectile,
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
      game.batch.setProjectionMatrix(hud.getStage().getCamera().combined);

      // Make all actors call their act methods
      stage.act();
      // Draw Actors
      stage.draw();

      // Draw HUD
      hud.getStage().draw();

      // Start, draw and end spriteBatch
      game.batch.begin();
      game.batch.end();
      for (ViewObject vo : viewObjects) vo.remove();
    }

    if (((healthTopBase == Constants.MINHEALTH) || (healthBottomBase == Constants.MINHEALTH))
        && accumulatorAfterBaseConquered < Constants.INCREASEACCUMULATORTILL) {

      accumulatorAfterBaseConquered++;

      // TO DO once accumulator is between 40 and 50 make a pop up box to winnign screen or just
      // make a winning screen for Friday
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
    game.dispose();
    stage.dispose();
  }

  public void spawnUnit(UnitType unitType, Lane lane, PlayerType playerType) {
    String message = MessageMaker.spawnTroopsMessage(unitType, lane, playerType);
    clientSender.sendMessage(message);
  }
}
