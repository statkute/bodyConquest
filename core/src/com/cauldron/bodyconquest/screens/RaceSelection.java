package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cauldron.bodyconquest.game_logic.Communicator;
import com.cauldron.bodyconquest.game_logic.Game;
import com.cauldron.bodyconquest.networking.Server;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class RaceSelection implements Screen {

  private BodyConquest game;
  private Texture background;
  private Texture header;
  private Texture continueText;
  private Texture backButton;

  private Texture blueVirus;
  private Texture greenVirus;
  private Texture yellowVirus;
  private Texture blueVirusSelected;
  private Texture greenVirusSelected;
  private Texture yellowVirusSelected;

  private Texture blueDescription;
  private Texture greenDescription;
  private Texture yellowDescription;

  OrthographicCamera camera;

  private int selection = 0;

  private Rectangle continueBounds;
  private Rectangle backBounds;

  private Rectangle blueVirusBounds;
  private Rectangle greenVirusBounds;
  private Rectangle yellowVirusBounds;

  private Viewport gamePort;
  private Stage stage;


  private Game g;
  String gameType;

  private Random random;
  private Server server;
  private Communicator communicator;

  public RaceSelection(
      BodyConquest game, Server server, Communicator communicator, String gameType) {
    this.communicator = communicator;
    this.server = server;
    this.game = game;
    this.gameType = gameType;
    setup();
  }

  public RaceSelection(BodyConquest game, Communicator communicator, String gameType) {
    System.out.println("inside");
    this.communicator = communicator;
    this.game = game;
    this.server = null;
    this.gameType = gameType;
    setup();
  }

  private void setup() {
    camera = new OrthographicCamera();
    camera.setToOrtho(false, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);

    gamePort = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, camera);
    stage = new Stage();

    background = new Texture("core/assets/background_new.png");
    header = new Texture("core/assets/selectvirusheader_new.png");

    blueVirus = new Texture("core/assets/bluevirus.png");
    greenVirus = new Texture("core/assets/greenvirus.png");
    yellowVirus = new Texture("core/assets/yellowvirus.png");

    blueVirusSelected = new Texture("core/assets/bluevirusselected.png");
    greenVirusSelected = new Texture("core/assets/greenvirusselected.png");
    yellowVirusSelected = new Texture("core/assets/yellowvirusselected.png");

    blueDescription = new Texture("core/assets/bluevirus_characteristics.png");
    greenDescription = new Texture("core/assets/greenvirus_characteristics.png");
    yellowDescription = new Texture("core/assets/yellowvirus_characteristics.png");

    continueText = new Texture("core/assets/continue_new.png");
    backButton = new Texture("core/assets/back_new.png");

    continueBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - continueText.getWidth() / 2,
            80,
            continueText.getWidth(),
            continueText.getHeight());

    blueVirusBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 5 - blueVirus.getWidth() / 2,
            220,
            blueVirus.getWidth(),
            blueVirus.getHeight());

    greenVirusBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - greenVirus.getWidth() / 2,
            220,
            greenVirus.getWidth(),
            greenVirus.getHeight());

    yellowVirusBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 5 * 4 - yellowVirus.getWidth() / 2,
            220,
            yellowVirus.getWidth(),
            yellowVirus.getHeight());

    backBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2,
            30,
            backButton.getWidth(),
            backButton.getHeight());
  }

  @Override
  public void show() {
    //    stage.getRoot().getColor().a = 0;
    //    stage.getRoot().addAction(Actions.fadeIn(1.0f));
  }

  @Override
  public void render(float delta) {

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.update();
    game.batch.setProjectionMatrix(camera.combined);

    game.batch.begin();
    game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
    game.batch.draw(header, BodyConquest.V_WIDTH / 2 - header.getWidth() / 2, 450);
    game.batch.draw(blueVirus, (BodyConquest.V_WIDTH / 5 - blueVirus.getWidth() / 2), 220);
    game.batch.draw(greenVirus, (BodyConquest.V_WIDTH / 2 - greenVirus.getWidth() / 2), 220);
    game.batch.draw(yellowVirus, (BodyConquest.V_WIDTH / 5 * 4 - yellowVirus.getWidth() / 2), 220);
    game.batch.draw(blueDescription, (BodyConquest.V_WIDTH / 5 - blueDescription.getWidth() / 2), 160);
    game.batch.draw(greenDescription, (BodyConquest.V_WIDTH / 2 - greenDescription.getWidth() / 2), 160);
    game.batch.draw(yellowDescription, (BodyConquest.V_WIDTH / 5 * 4- yellowDescription.getWidth() / 2), 160);
    game.batch.draw(continueText, BodyConquest.V_WIDTH / 2 - continueText.getWidth() / 2, 80);
    game.batch.draw(backButton, BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2, 30);


    try {
      checkPressed();
    } catch (IOException e) {
      e.printStackTrace();
    }

    game.batch.end();
  }

  public void checkPressed() throws IOException {
    if (Gdx.input.justTouched()) {

      Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(tmp);

      if (continueBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        if (server != null){
          g = new Game(server, communicator, gameType);
          g.start();
          server.startServerLogic(g.getEncounterState());
          game.setScreen(new EncounterScreen(game, communicator, game.getClient(), server));
        } else{
          game.setScreen(new EncounterScreen(game, communicator, game.getClient()));
        }
        dispose();
      }

      if (blueVirusBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        if (selection != 0){
          cleanSelections();
        }
        if (selection != 1){
          blueVirus = blueVirusSelected;
          selection = 1;
        } else{
          selection = 0;
        }
      }

      if (greenVirusBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        if (selection != 0){
          cleanSelections();
        }
        if (selection != 2){
          greenVirus = greenVirusSelected;
          selection = 2;
        } else{
          selection = 0;
        }
      }

      if (yellowVirusBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        if (selection != 0){
          cleanSelections();
        }
        if (selection != 3){
          yellowVirus = yellowVirusSelected;
          selection = 3;
        } else{
          selection = 0;
        }
      }

      if (backBounds.contains(tmp.x, tmp.y)) {
        System.out.println("back pressed");
        playButtonSound();
        game.setScreen(new MenuScreen(game));
        dispose();
      }
    }
  }

  public void cleanSelections(){
    blueVirus = new Texture("core/assets/bluevirus.png");
    greenVirus = new Texture("core/assets/greenvirus.png");
    yellowVirus = new Texture("core/assets/yellowvirus.png");
  }

  @Override
  public void resize(int width, int height) {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {

    //    background.dispose();
    //    disease1.dispose();
    //    disease2.dispose();
    //    disease3.dispose();
    //    playButton.dispose();
    //    confirmButton.dispose();
    //    selectionFrame1.dispose();
    //    selectionFrame2.dispose();
  }

  public Game getG() {
    return g;
  }

  public void playButtonSound() {
    game.audioPlayer.playSFX("button_click");
  }
}
