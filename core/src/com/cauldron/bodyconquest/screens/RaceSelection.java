package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.constants.Disease;
import com.cauldron.bodyconquest.constants.GameType;
import com.cauldron.bodyconquest.game_logic.Communicator;
import com.cauldron.bodyconquest.game_logic.Game;
import com.cauldron.bodyconquest.networking.Server;
import com.cauldron.bodyconquest.networking.utilities.MessageMaker;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.io.IOException;
import java.net.SocketException;
import java.util.Random;

public class RaceSelection extends AbstractGameScreen implements Screen {

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

  private int selection = 0;

  private Rectangle continueBounds;
  private Rectangle backBounds;
  private Rectangle blueVirusBounds;
  private Rectangle greenVirusBounds;
  private Rectangle yellowVirusBounds;

  private Game g;

  private Assets.PlayerType playerType;
  private GameType gameType;

  public RaceSelection(
      BodyConquest game, GameType gameType) throws IOException {
    super(game);
    this.gameType = gameType;
    loadAssets();
    getAssets();
    setRectangles();

    if(gameType != GameType.MULTIPLAYER_JOIN) {
      g = new Game(gameType);
      game.setGame(g);
      g.startRaceSelectionState();
      g.start();
    }

    game.getClient().startClient();

    if(gameType != GameType.MULTIPLAYER_JOIN) {
      playerType = Assets.PlayerType.PLAYER_BOTTOM;
    } else {
      playerType = Assets.PlayerType.PLAYER_TOP;
    }

    game.getClient().getCommunicator().setPlayerType(playerType);
    game.getClient().setRaceSelectionLogic();
  }

  @Override
  public void render(float delta) {

    super.render(delta);

    game.batch.begin();
    game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
    game.batch.draw(header, BodyConquest.V_WIDTH / 2 - header.getWidth() / 2, 450);
    game.batch.draw(blueVirus, (BodyConquest.V_WIDTH / 5 - blueVirus.getWidth() / 2), 220);
    game.batch.draw(greenVirus, (BodyConquest.V_WIDTH / 2 - greenVirus.getWidth() / 2), 220);
    game.batch.draw(yellowVirus, (BodyConquest.V_WIDTH / 5 * 4 - yellowVirus.getWidth() / 2), 220);
    game.batch.draw(
        blueDescription, (BodyConquest.V_WIDTH / 5 - blueDescription.getWidth() / 2), 160);
    game.batch.draw(
        greenDescription, (BodyConquest.V_WIDTH / 2 - greenDescription.getWidth() / 2), 160);
    game.batch.draw(
        yellowDescription, (BodyConquest.V_WIDTH / 5 * 4 - yellowDescription.getWidth() / 2), 160);
    if(selection != 0) game.batch.draw(continueText, BodyConquest.V_WIDTH / 2 - continueText.getWidth() / 2, 80);
    game.batch.draw(backButton, BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2, 30);


    checkPressed();
    game.batch.end();
  }

  public void checkPressed()  {

    super.checkPressed();

    if (Gdx.input.justTouched()) {
      System.out.println(continueBounds == null);
      if (game.getGame() == null) {
        System.err.println("Server not instantiated");
        return;
      }
      if (continueBounds.contains(tmp.x, tmp.y) && selection != 0) {
        System.out.println("continue is pressed");
        playButtonSound();

        // Should actually start the encounter state once all players have confirmed their Disease
        if (gameType != GameType.MULTIPLAYER_JOIN) g.startBodyState();
        game.setScreen(new BodyScreen(game, gameType));
        //dispose();
      }

      checkSelection();

      if (backBounds.contains(tmp.x, tmp.y)) {
        System.out.println("back pressed");
        playButtonSound();
        if (server != null) {
          server.closeEverything();
        }
        game.getClient().closeEverything();
        game.setScreen(new MenuScreen(game));
        dispose();
      }
    }
  }

  public void cleanSelections() {
    blueVirus = manager.get(Assets.raceBlueVirus, Texture.class);
    greenVirus = manager.get(Assets.raceGreenVirus, Texture.class);
    yellowVirus = manager.get(Assets.raceYellowVirus, Texture.class);
  }

  @Override
  public void loadAssets() {
    super.loadAssets();
    manager.load(Assets.raceHeader, Texture.class);
    manager.load(Assets.raceBlueVirus, Texture.class);
    manager.load(Assets.raceGreenVirus, Texture.class);
    manager.load(Assets.raceYellowVirus, Texture.class);
    manager.load(Assets.raceBlueVirusSelected, Texture.class);
    manager.load(Assets.raceGreenVirusSelected, Texture.class);
    manager.load(Assets.raceYellowVirusSelected, Texture.class);
    manager.load(Assets.raceBlueDescription, Texture.class);
    manager.load(Assets.raceGreenDescription, Texture.class);
    manager.load(Assets.raceYellowDescription, Texture.class);
    manager.load(Assets.raceContinueText, Texture.class);
    manager.load(Assets.raceBackButton, Texture.class);
    manager.finishLoading();
  }

  @Override
  public void getAssets() {
    super.getAssets();
    header = manager.get(Assets.raceHeader, Texture.class);
    continueText = manager.get(Assets.raceContinueText, Texture.class);
    backButton = manager.get(Assets.raceBackButton, Texture.class);
    blueVirus = manager.get(Assets.raceBlueVirus, Texture.class);
    greenVirus = manager.get(Assets.raceGreenVirus, Texture.class);
    yellowVirus = manager.get(Assets.raceYellowVirus, Texture.class);
    blueVirusSelected = manager.get(Assets.raceBlueVirusSelected, Texture.class);
    greenVirusSelected = manager.get(Assets.raceGreenVirusSelected, Texture.class);
    yellowVirusSelected = manager.get(Assets.raceYellowVirusSelected, Texture.class);
    blueDescription = manager.get(Assets.raceBlueDescription, Texture.class);
    greenDescription = manager.get(Assets.raceGreenDescription, Texture.class);
    yellowDescription = manager.get(Assets.raceYellowDescription, Texture.class);
  }

  @Override
  public void setRectangles() {
    super.setRectangles();
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

  public void checkSelection(){

    if (blueVirusBounds.contains(tmp.x, tmp.y)) {
      playButtonSound();
      if (selection != 0) {
        cleanSelections();
      }
      if (selection != 1) {
        Disease playerDisease = Disease.INFLUENZA;
        game.getClient().getCommunicator().setPlayerDisease(playerDisease);
        String message = MessageMaker.diseaseMessage(playerDisease, playerType);
        game.getClient().clientSender.sendMessage(message);
        blueVirus = blueVirusSelected;
        selection = 1;
      } else {
        selection = 0;
      }
    }

    if (greenVirusBounds.contains(tmp.x, tmp.y)) {
      playButtonSound();
      if (selection != 0) {
        cleanSelections();
      }
      if (selection != 2) {
        Disease playerDisease = Disease.MEASLES;
        game.getClient().getCommunicator().setPlayerDisease(playerDisease);
        String message = MessageMaker.diseaseMessage(playerDisease, playerType);
        game.getClient().clientSender.sendMessage(message);

        greenVirus = greenVirusSelected;
        selection = 2;
      } else {
        selection = 0;
      }
    }

    if (yellowVirusBounds.contains(tmp.x, tmp.y)) {
      playButtonSound();
      if (selection != 0) {
        cleanSelections();
      }
      if (selection != 3) {
        Disease playerDisease = Disease.ROTAVIRUS;
        game.getClient().getCommunicator().setPlayerDisease(playerDisease);
        String message = MessageMaker.diseaseMessage(playerDisease, playerType);
        game.getClient().clientSender.sendMessage(message);
        yellowVirus = yellowVirusSelected;
        selection = 3;
      } else {
        selection = 0;
      }
    }

  }
}
