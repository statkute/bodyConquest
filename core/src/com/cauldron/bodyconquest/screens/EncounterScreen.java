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
import com.cauldron.bodyconquest.constants.Lane;
import com.cauldron.bodyconquest.constants.PlayerType;
import com.cauldron.bodyconquest.constants.UnitType;
import com.cauldron.bodyconquest.entities.BasicObject;
import com.cauldron.bodyconquest.entities.ViewObject;
import com.cauldron.bodyconquest.game_logic.Communicator;
import com.cauldron.bodyconquest.networking.ClientSender;
import com.cauldron.bodyconquest.networking.MessageMaker;
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


    CopyOnWriteArrayList<BasicObject> objects = comms.getAllObjects();
    //if(baseTop.health > 0 && baseBottom)
    // Turn BasicObjects from server/communicator into ViewObjects (and gives them a texture)
    ArrayList<ViewObject> viewObjects = new ArrayList<ViewObject>();
    for (BasicObject o : objects) {
      viewObjects.add(new ViewObject(o));
    }

    for (ViewObject vo : viewObjects) {
      //System.out.println("Adding viewobject");

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
    // game.batch.draw();
    game.batch.end();

    for(ViewObject vo : viewObjects) vo.remove();
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
