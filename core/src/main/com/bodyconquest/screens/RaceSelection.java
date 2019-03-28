package main.com.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.Disease;
import main.com.bodyconquest.constants.GameType;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.entities.DifficultyLevel;
import main.com.bodyconquest.game_logic.Communicator;
import main.com.bodyconquest.game_logic.Game;
import main.com.bodyconquest.networking.utilities.MessageMaker;
import main.com.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.io.IOException;

/**
 * The type Race selection.
 */
public class RaceSelection extends AbstractGameScreen implements Screen {

    private Texture header;
    private Texture continueText;
    private Texture waitingText;
    private Texture selectText;
    private Texture blueVirus;
    private Texture greenVirus;
    private Texture yellowVirus;
    private Texture blueVirusSelected;
    private Texture greenVirusSelected;
    private Texture yellowVirusSelected;
    private Texture blueVirusOpponent;
    private Texture greenVirusOpponent;
    private Texture yellowVirusOpponent;
    private Texture blueDescription;
    private Texture greenDescription;
    private Texture yellowDescription;

    private int selection = 0;

    private Rectangle continueBounds;
    private Rectangle blueVirusBounds;
    private Rectangle blueDescriptionBounds;
    private Rectangle greenDescriptionBounds;
    private Rectangle yellowDescriptionBounds;
    private Rectangle greenVirusBounds;
    private Rectangle yellowVirusBounds;

    private PlayerType playerType;
    private GameType gameType;

    private Disease playerDisease;

    private Communicator communicator;

    /**
     * Instantiates a new Race selection screen.
     *
     * @param game     the game
     * @param gameType the game type
     * @throws IOException the io exception
     */
    public RaceSelection(BodyConquest game, GameType gameType) throws IOException {
        super(game);
        this.gameType = gameType;
        loadAssets();
        getAssets();
        setRectangles();

        if (gameType != GameType.MULTIPLAYER_JOIN) {
            game.getGame().startRaceSelectionState();
        }
        game.getClient().setRaceSelectionLogic();

        //communicate the difficulty if in single player mode

        if (gameType == GameType.SINGLE_PLAYER) {
            DifficultyLevel difficultyLevel = game.getDifficultyLevel();
            game.getClient().clientSender.sendMessage(MessageMaker.setDifficultyMessage(difficultyLevel));
        }

        this.communicator = game.getClient().getCommunicator();
        this.playerType = communicator.getPlayerType();
        game.getClient()
                .clientSender
                .sendMessage(
                        MessageMaker.usernameMessage(
                                playerType, game.getClient().getCommunicator().getUsername(playerType)));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(float delta) {

        super.render(delta);

        game.batch.begin();
        game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
        game.batch.draw(header, BodyConquest.V_WIDTH / 2 - header.getWidth() / 2, 450);

        if (communicator.getOpponentDisease() == Disease.INFLUENZA) {
            game.batch.draw(
                    blueVirusOpponent, (BodyConquest.V_WIDTH / 5 - blueVirusOpponent.getWidth() / 2), 220);
        } else {
            game.batch.draw(blueVirus, (BodyConquest.V_WIDTH / 5 - blueVirus.getWidth() / 2), 220);
        }

        if (communicator.getOpponentDisease() == Disease.MEASLES) {
            game.batch.draw(
                    greenVirusOpponent,
                    (BodyConquest.V_WIDTH / 2.0f - greenVirusOpponent.getWidth() / 2),
                    220);
        } else {
            game.batch.draw(greenVirus, (BodyConquest.V_WIDTH / 2.0f - greenVirus.getWidth() / 2), 220);
        }

        if (communicator.getOpponentDisease() == Disease.ROTAVIRUS) {
            game.batch.draw(
                    yellowVirusOpponent,
                    (BodyConquest.V_WIDTH / 5 * 4 - yellowVirusOpponent.getWidth() / 2),
                    220);
        } else {
            game.batch.draw(
                    yellowVirus, (BodyConquest.V_WIDTH / 5 * 4 - yellowVirus.getWidth() / 2), 220);
        }

        game.batch.draw(
                blueDescription,
                (BodyConquest.V_WIDTH / 5.0f - blueDescription.getWidth() / 2.0f / 2.0f),
                165,
                blueDescription.getWidth() / 2.0f,
                blueDescription.getHeight() / 2.0f);
        game.batch.draw(
                greenDescription,
                (BodyConquest.V_WIDTH / 2.0f - greenDescription.getWidth() / 2.0f / 2.0f),
                165,
                greenDescription.getWidth() / 2.0f,
                greenDescription.getHeight() / 2.0f);
        game.batch.draw(
                yellowDescription,
                (BodyConquest.V_WIDTH / 5.0f * 4.0f - yellowDescription.getWidth() / 2.0f / 2.0f),
                165,
                yellowDescription.getWidth() / 2.0f,
                yellowDescription.getHeight() / 2.0f);

        if (selection != 0 && communicator.isPicker()) {
            game.batch.draw(
                    continueText, BodyConquest.V_WIDTH / 2.0f - continueText.getWidth() / 2.0f, 60);
        } else if (selection == 0 && communicator.isPicker()) {
            game.batch.draw(
                    selectText,
                    BodyConquest.V_WIDTH / 2.0f - selectText.getWidth() / 2.2f / 2.0f,
                    60,
                    selectText.getWidth() / 2.2f,
                    selectText.getHeight() / 2.2f);
        }

        if (!communicator.isPicker()) {
            game.batch.draw(
                    waitingText,
                    BodyConquest.V_WIDTH / 2 - waitingText.getWidth() / 2.2f / 2,
                    60,
                    waitingText.getWidth() / 2.2f,
                    waitingText.getHeight() / 2.2f);
        }

        // game.batch.draw(backButton, BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2, 30);

        if (communicator.getStartBodyScreen()) game.setScreen(new BodyScreen(game, gameType));

        checkPressed();
        game.batch.end();
    }

    /**
     * {@inheritDoc}
     */
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


                game.getClient().clientSender.sendMessage(MessageMaker.confirmRaceMessage(playerType));
            }

            if (communicator.isPicker()) {
                checkSelection();
            }
        }
    }

    /**
     * Clean selections of the user ( deletes the borders).
     */
    private void cleanSelections() {
        if (communicator.getOpponentDisease() != Disease.INFLUENZA) {
            blueVirus = manager.get(Assets.raceBlueVirus, Texture.class);
        } else {
            blueVirus = manager.get(Assets.raceBlueVirusOpponent, Texture.class);
        }

        if (communicator.getOpponentDisease() != Disease.MEASLES) {
            greenVirus = manager.get(Assets.raceGreenVirus, Texture.class);
        } else {
            greenVirus = manager.get(Assets.raceGreenVirusOpponent, Texture.class);
        }

        if (communicator.getOpponentDisease() != Disease.MEASLES) {
            yellowVirus = manager.get(Assets.raceYellowVirus, Texture.class);
        } else {
            yellowVirus = manager.get(Assets.raceYellowVirusOpponent, Texture.class);
        }
    }

    /**
     * {@inheritDoc}
     */
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
        manager.load(Assets.raceBlueVirusOpponent, Texture.class);
        manager.load(Assets.raceGreenVirusOpponent, Texture.class);
        manager.load(Assets.raceYellowVirusOpponent, Texture.class);
        manager.load(Assets.raceBlueDescription, Texture.class);
        manager.load(Assets.raceGreenDescription, Texture.class);
        manager.load(Assets.raceYellowDescription, Texture.class);
        manager.load(Assets.raceContinueText, Texture.class);
        manager.load(Assets.waitingText, Texture.class);
        manager.load(Assets.selectDiseaseText, Texture.class);
        manager.load(Assets.raceBackButton, Texture.class);
        manager.finishLoading();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getAssets() {
        super.getAssets();
        header = manager.get(Assets.raceHeader, Texture.class);
        continueText = manager.get(Assets.raceContinueText, Texture.class);
        waitingText = manager.get(Assets.waitingText, Texture.class);
        selectText = manager.get(Assets.selectDiseaseText, Texture.class);
        blueVirus = manager.get(Assets.raceBlueVirus, Texture.class);
        greenVirus = manager.get(Assets.raceGreenVirus, Texture.class);
        yellowVirus = manager.get(Assets.raceYellowVirus, Texture.class);
        blueVirusSelected = manager.get(Assets.raceBlueVirusSelected, Texture.class);
        greenVirusSelected = manager.get(Assets.raceGreenVirusSelected, Texture.class);
        yellowVirusSelected = manager.get(Assets.raceYellowVirusSelected, Texture.class);
        blueVirusOpponent = manager.get(Assets.raceBlueVirusOpponent, Texture.class);
        greenVirusOpponent = manager.get(Assets.raceGreenVirusOpponent, Texture.class);
        yellowVirusOpponent = manager.get(Assets.raceYellowVirusOpponent, Texture.class);
        blueDescription = manager.get(Assets.raceBlueDescription, Texture.class);
        greenDescription = manager.get(Assets.raceGreenDescription, Texture.class);
        yellowDescription = manager.get(Assets.raceYellowDescription, Texture.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRectangles() {
        super.setRectangles();
        continueBounds =
                new Rectangle(
                        BodyConquest.V_WIDTH / 2 - continueText.getWidth() / 2,
                        60,
                        continueText.getWidth(),
                        continueText.getHeight());

        blueVirusBounds =
                new Rectangle(
                        BodyConquest.V_WIDTH / 5 - blueVirus.getWidth() / 2,
                        220,
                        blueVirus.getWidth(),
                        blueVirus.getHeight());

        blueDescriptionBounds =
                new Rectangle(
                        BodyConquest.V_WIDTH / 5 - blueDescription.getWidth() / 2.0f / 2.0f,
                        165,
                        blueDescription.getWidth() / 2.0f,
                        blueDescription.getHeight() / 2.0f);

        greenVirusBounds =
                new Rectangle(
                        BodyConquest.V_WIDTH / 2 - greenVirus.getWidth() / 2,
                        220,
                        greenVirus.getWidth(),
                        greenVirus.getHeight());

        greenDescriptionBounds =
                new Rectangle(
                        BodyConquest.V_WIDTH / 2 - greenDescription.getWidth() / 2.0f / 2.0f,
                        165,
                        greenDescription.getWidth() / 2.0f,
                        greenDescription.getHeight() / 2.0f);

        yellowVirusBounds =
                new Rectangle(
                        BodyConquest.V_WIDTH / 5 * 4 - yellowVirus.getWidth() / 2,
                        220,
                        yellowVirus.getWidth(),
                        yellowVirus.getHeight());

        yellowDescriptionBounds =
                new Rectangle(
                        BodyConquest.V_WIDTH / 5 * 4 - yellowDescription.getWidth() / 2.0f / 2.0f,
                        165,
                        yellowDescription.getWidth() / 2.0f,
                        yellowDescription.getHeight() / 2.0f);
    }

    /**
     * Check selection of the virus of the user.
     */
    public void checkSelection() {

        if (blueVirusBounds.contains(tmp.x, tmp.y) || blueDescriptionBounds.contains(tmp.x, tmp.y)) {
            if (communicator.getOpponentDisease() == Disease.INFLUENZA) {
                return;
            }
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

        if (greenVirusBounds.contains(tmp.x, tmp.y) || greenDescriptionBounds.contains(tmp.x, tmp.y)) {
            if (communicator.getOpponentDisease() == Disease.MEASLES) {
                return;
            }
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

        if (yellowVirusBounds.contains(tmp.x, tmp.y)
                || yellowDescriptionBounds.contains(tmp.x, tmp.y)) {
            if (communicator.getOpponentDisease() == Disease.ROTAVIRUS) {
                return;
            }
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
