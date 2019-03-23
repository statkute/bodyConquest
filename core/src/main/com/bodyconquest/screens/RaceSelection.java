package main.com.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.Disease;
import main.com.bodyconquest.constants.GameType;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.game_logic.Communicator;
import main.com.bodyconquest.game_logic.Game;
import main.com.bodyconquest.networking.utilities.MessageMaker;
import main.com.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.io.IOException;

/** The type Race selection. */
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

  private PlayerType playerType;
  private GameType gameType;

  private Disease playerDisease;

  private String username;
  private Communicator communicator;

  /**
   * Instantiates a new Race selection screen.
   *
   * @param game the game
   * @param gameType the game type
   * @throws IOException the io exception
   */
  public RaceSelection(BodyConquest game, GameType gameType, String username) throws IOException {
    super(game);
    this.gameType = gameType;
    this.username = username;
    loadAssets();
    getAssets();
    setRectangles();
    game.setUsername(username);

    if (gameType != GameType.MULTIPLAYER_JOIN) {
      g = new Game(gameType);
      game.setGame(g);
      g.startRaceSelectionState();
      g.start();
    }

    game.getClient().startClient();

    if (gameType != GameType.MULTIPLAYER_JOIN) {
      playerType = PlayerType.PLAYER_BOTTOM;
    } else {
      playerType = PlayerType.PLAYER_TOP;
    }

    communicator = game.getClient().getCommunicator();

    communicator.setPlayerType(playerType);
    game.getClient().setRaceSelectionLogic();
  }

  /** {@inheritDoc} */
  @Override
  public void render(float delta) {

    super.render(delta);

    game.batch.begin();
    game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
    game.batch.draw(
        header,
        BodyConquest.V_WIDTH / 2 - header.getWidth() / 2,
        BodyConquest.scaleRatioHeight * 450);
    game.batch.draw(
        blueVirus,
        (BodyConquest.V_WIDTH / 5 - blueVirus.getWidth() * BodyConquest.scaleRatioHeight / 2),
        BodyConquest.scaleRatioHeight * 220,
        blueVirus.getWidth() * BodyConquest.scaleRatioHeight,
        blueVirus.getHeight() * BodyConquest.scaleRatioHeight);
    game.batch.draw(
        greenVirus,
        (BodyConquest.V_WIDTH / 2 - greenVirus.getWidth() * BodyConquest.scaleRatioHeight / 2),
        BodyConquest.scaleRatioHeight * 220,
        greenVirus.getWidth() * BodyConquest.scaleRatioHeight,
        greenVirus.getHeight() * BodyConquest.scaleRatioHeight);
    game.batch.draw(
        yellowVirus,
        (BodyConquest.V_WIDTH / 5 * 4 - yellowVirus.getWidth() * BodyConquest.scaleRatioHeight / 2),
        BodyConquest.scaleRatioHeight * 220,
        yellowVirus.getWidth() * BodyConquest.scaleRatioHeight,
        yellowVirus.getHeight() * BodyConquest.scaleRatioHeight);
    game.batch.draw(
        blueDescription,
        (BodyConquest.V_WIDTH / 5 - blueDescription.getWidth() / 2),
        BodyConquest.scaleRatioHeight * 160);
    game.batch.draw(
        greenDescription,
        (BodyConquest.V_WIDTH / 2 - greenDescription.getWidth() / 2),
        BodyConquest.scaleRatioHeight * 155);
    game.batch.draw(
        yellowDescription,
        (BodyConquest.V_WIDTH / 5 * 4 - yellowDescription.getWidth() / 2),
        BodyConquest.scaleRatioHeight * 160);
    if (selection != 0)
      game.batch.draw(
          continueText,
          BodyConquest.V_WIDTH / 2 - continueText.getWidth() / 2,
          BodyConquest.scaleRatioHeight * 80);
    game.batch.draw(
        backButton,
        BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2,
        BodyConquest.scaleRatioHeight * 30);

    if (communicator.getStartBodyScreen()) game.setScreen(new BodyScreen(game, gameType));

    checkPressed();
    game.batch.end();
  }

  /** {@inheritDoc} */
  @Override
  public void checkPressed() {

    super.checkPressed();

    if (Gdx.input.justTouched()) {
      System.out.println(continueBounds == null);
      if (game.getGame() == null && gameType != GameType.MULTIPLAYER_JOIN) {
        System.err.println("Server not instantiated");
        return;
      }
      if (continueBounds.contains(tmp.x, tmp.y) && selection != 0) {
        System.out.println("continue is pressed");
        playButtonSound();

        // Should actually start the encounter state once all players have confirmed their Disease
        // <<<<<<< HEAD
        //        if (gameType != GameType.MULTIPLAYER_JOIN) g.startEncounterState();
        //        game.setScreen(new EncounterScreen(game, gameType,username));

        game.getClient().clientSender.sendMessage(MessageMaker.confirmRaceMessage(playerType));
      }

      checkSelection();

      if (backBounds.contains(tmp.x, tmp.y)) {
        System.out.println("back pressed");
        playButtonSound();
        if (server != null) {
          server.closeEverything();
        }
        game.getClient().closeEverything();
        game.setScreen(new MenuScreen(game, username));
        dispose();
      }
    }
  }

  /** Clean selections of the user ( deletes the borders). */
  public void cleanSelections() {
    blueVirus = manager.get(Assets.raceBlueVirus, Texture.class);
    greenVirus = manager.get(Assets.raceGreenVirus, Texture.class);
    yellowVirus = manager.get(Assets.raceYellowVirus, Texture.class);
  }

  /** {@inheritDoc} */
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

  /** {@inheritDoc} */
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

  /** {@inheritDoc} */
  @Override
  public void setRectangles() {
    super.setRectangles();
    continueBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - continueText.getWidth() / 2,
            80 * BodyConquest.scaleRatioHeight,
            continueText.getWidth(),
            continueText.getHeight());

    blueVirusBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 5 - blueVirus.getWidth() * BodyConquest.scaleRatioHeight / 2,
            220 * BodyConquest.scaleRatioHeight,
            blueVirus.getWidth() * BodyConquest.scaleRatioHeight,
            blueVirus.getHeight() * BodyConquest.scaleRatioHeight);

    greenVirusBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - greenVirus.getWidth() * BodyConquest.scaleRatioHeight / 2,
            220 * BodyConquest.scaleRatioHeight,
            greenVirus.getWidth() * BodyConquest.scaleRatioHeight,
            greenVirus.getHeight() * BodyConquest.scaleRatioHeight);

    yellowVirusBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 5 * 4
                - yellowVirus.getWidth() * BodyConquest.scaleRatioHeight / 2,
            220 * BodyConquest.scaleRatioHeight,
            yellowVirus.getWidth() * BodyConquest.scaleRatioHeight,
            yellowVirus.getHeight() * BodyConquest.scaleRatioHeight);

    backBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2,
            30 * BodyConquest.scaleRatioHeight,
            backButton.getWidth(),
            backButton.getHeight());
  }

  /** Check selection of the virus of the user. */
  public void checkSelection() {

    if (blueVirusBounds.contains(tmp.x, tmp.y)) {
      playButtonSound();
      if (selection != 0) {
        cleanSelections();
      }
      if (selection != 1) {
        this.playerDisease = Disease.INFLUENZA;
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
        this.playerDisease = Disease.MEASLES;
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
        this.playerDisease = Disease.ROTAVIRUS;
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
