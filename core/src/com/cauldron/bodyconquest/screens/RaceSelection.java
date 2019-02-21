package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.game_logic.Communicator;
import com.cauldron.bodyconquest.game_logic.Game;
import com.cauldron.bodyconquest.game_logic.utils.Timer;
import com.cauldron.bodyconquest.gamestates.EncounterState;
import com.cauldron.bodyconquest.networking.Server;
import com.cauldron.bodyconquest.networking.utilities.Serialization;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RaceSelection implements Screen {

  private BodyConquest game;
  private Texture background;
  private Texture header;

  private Texture blueVirus;
  private Texture greenVirus;
  private Texture yellowVirus;
  private Texture blueVirusSelected;
  private Texture greenVirusSelected;
  private Texture yellowVirusSelected;

  OrthographicCamera camera;

  private String diseaseName1 = "Disease 1";
  private String diseaseName2 = "Disease 2";
  private String diseaseName3 = "Disease 3";

  private String warning = "You cannot re-select race once it is confirmed";

  private int selection = 0;

  //  private Texture disease1;
  //  private Texture disease2;
  //  private Texture disease3;

  // private Texture disease4;
  private Texture selectionFrame1;
  private Texture selectionFrame2;

  //  private Texture confirmButton;
  //  private Texture playButton;

  private Rectangle headerBounds;

  private Rectangle blueVirusBounds;
  private Rectangle greenVirusBounds;
  private Rectangle yellowVirusBounds;
  //
  //  private Rectangle disease1Bounds;
  //  private Rectangle disease2Bounds;
  //  private Rectangle disease3Bounds;
  //  private Rectangle confirmBounds;
  //  private Rectangle playBounds;

  private Viewport gamePort;
  private Stage stage;

  private List<Texture> listTextures;

  private boolean selected = false;
  private boolean confirmed = false;
  private boolean opponentSelected = false;

  private Game g;

  private Random random;
  private Server server;
  private Communicator communicator;

  public RaceSelection(
      BodyConquest game, Server server, Communicator communicator, String gameType) {
    this.communicator = communicator;
    this.server = server;
    this.game = game;
    setup();

    g = new Game(server, communicator, gameType);
    g.start();
    server.startServerLogic(g.getEncounterState());
  }

  public RaceSelection(BodyConquest game, Communicator communicator) {
    System.out.println("inside");
    this.communicator = communicator;
    this.game = game;
    this.server = null;
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

    headerBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - header.getWidth() / 2,
            450,
            header.getWidth(),
            header.getHeight());

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
    //    confirmButton = new Texture("core/assets/confirmbutton.v3.png");
    //    disease1 = new Texture("core/assets/bacteria1resized.png");
    //    disease2 = new Texture("core/assets/monster1resized.png");
    //    disease3 = new Texture("core/assets/monster2resized.png");
    //    selectionFrame1 = new Texture("core/assets/unknownUserresized.png");
    //    selectionFrame2 = new Texture("core/assets/unknownUserresized.png");
    //    playButton = new Texture("core/assets/playButton.png");
    //
    //    disease1Bounds = new Rectangle(70, 30, disease1.getWidth(), disease1.getHeight());
    //    disease2Bounds = new Rectangle(366, 30, disease2.getWidth(), disease2.getHeight());
    //    disease3Bounds = new Rectangle(642, 30, disease3.getWidth(), disease3.getHeight());
    //    confirmBounds =
    //        new Rectangle(
    //            BodyConquest.V_WIDTH / 2 - confirmButton.getWidth() / 2,
    //            250,
    //            confirmButton.getWidth(),
    //            confirmButton.getHeight());
    //    playBounds =
    //        new Rectangle(
    //            BodyConquest.V_WIDTH / 2 - confirmButton.getWidth() / 2,
    //            250,
    //            playButton.getWidth(),
    //            playButton.getHeight());
    //
    //    listTextures = new ArrayList<Texture>();
    //    listTextures.add(disease1);
    //    listTextures.add(disease2);
    //    listTextures.add(disease3);
    //
    //    random = new Random();
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

    //    if (!selected) {
    //      game.batch.draw(selectionFrame1, 100, 250);
    //      game.batch.draw(selectionFrame2, 550, 250);
    //    }
    //
    //    if (selected && !opponentSelected) {
    //
    //      game.batch.draw(confirmButton, BodyConquest.V_WIDTH / 2 - confirmButton.getWidth() / 2,
    // 250);
    //      game.batch.draw(selectionFrame2, 550, 250);
    //      selectionFrameRendererSelected();
    //    }
    //
    //    if (selected && confirmed && !opponentSelected) {
    //      generateOpponent(selectionFrame1);
    //    }
    //
    //    if (selected && opponentSelected) {
    //      game.batch.draw(playButton, BodyConquest.V_WIDTH / 2 - playButton.getWidth() / 2, 250);
    //      selectionFrameRendererConfirmed();
    //      checkSelectsAfterConfirmation();
    //    }
    //
    //    game.batch.draw(disease1, 60, 50);
    //    game.batch.draw(disease2, 310, 30);
    //    game.batch.draw(disease3, 610, 40);
    //    game.font.draw(game.batch, diseaseName1, 70, 30);
    //    game.font.draw(game.batch, diseaseName2, 366, 30);
    //    game.font.draw(game.batch, diseaseName3, 642, 30);

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

      if (headerBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        game.setScreen(new EncounterScreen(game, communicator, game.getClient().clientSender));
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

      //      } else if (!confirmed) {
      //
      //        if (disease1Bounds.contains(tmp.x, tmp.y)) {
      //          playButtonSound();
      //
      //          changeTexture(disease1);
      //        } else if (disease2Bounds.contains(tmp.x, tmp.y)) {
      //          playButtonSound();
      //          // System.out.println("disease 2 selected");
      //          changeTexture(disease2);
      //        } else if (disease3Bounds.contains(tmp.x, tmp.y)) {
      //          playButtonSound();
      //          changeTexture(disease3);
      //        } else if (confirmBounds.contains(tmp.x, tmp.y) && selected) {
      //          playButtonSound();
      //          confirmed = true;
      //        }
      //      }
    }
  }

  public void cleanSelections(){
    blueVirus = new Texture("core/assets/bluevirus.png");
    greenVirus = new Texture("core/assets/greenvirus.png");
    yellowVirus = new Texture("core/assets/yellowvirus.png");
  }

  public void changeTexture(Texture newTexture) {

    selected = true;
    selectionFrame1 = newTexture;
  }

  public void generateOpponent(Texture selectedTexture) {

    listTextures.remove(selectedTexture);
    selectionFrame2 = listTextures.get(random.nextInt(listTextures.size()));
    opponentSelected = true;
  }

  //  public void selectionFrameRendererSelected() {
  //
  //    if (selectionFrame1.equals(disease2)) {
  //      game.batch.draw(selectionFrame1, 80, 220);
  //    } else if (selectionFrame1.equals(disease3)) {
  //      game.batch.draw(selectionFrame1, 100, 220);
  //    } else {
  //      game.batch.draw(selectionFrame1, 100, 250);
  //    }
  //  }

  //  public void selectionFrameRendererConfirmed() {
  //
  //    if (selectionFrame1.equals(disease2)) {
  //      game.batch.draw(selectionFrame1, 80, 220);
  //    } else if (selectionFrame1.equals(disease3)) {
  //      game.batch.draw(selectionFrame1, 100, 220);
  //    } else if (selectionFrame1.equals(disease1)) {
  //      game.batch.draw(selectionFrame1, 100, 250);
  //    }
  //
  //    if (selectionFrame2.equals(disease2)) {
  //      game.batch.draw(selectionFrame2, 550, 220);
  //    } else if (selectionFrame2.equals(disease3)) {
  //      game.batch.draw(selectionFrame2, 570, 230);
  //    } else if (selectionFrame2.equals(disease1)) {
  //      game.batch.draw(selectionFrame2, 570, 250);
  //    }
  //  }
  //
  //  public void checkSelectsAfterConfirmation() {
  //    Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
  //    camera.unproject(tmp);
  //    if (disease1Bounds.contains(tmp.x, tmp.y)
  //        || disease2Bounds.contains(tmp.x, tmp.y)
  //        || disease3Bounds.contains(tmp.x, tmp.y)) {
  //      playButtonSound();
  //      game.font.draw(
  //          game.batch, warning, BodyConquest.V_WIDTH / 2 - confirmButton.getWidth() / 2, 200);
  //    }
  //  }

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
