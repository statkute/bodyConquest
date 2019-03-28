package main.com.bodyconquest.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import main.com.bodyconquest.audio.AudioPlayer;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.GameType;
import main.com.bodyconquest.entities.DifficultyLevel;
import main.com.bodyconquest.game_logic.Game;
import main.com.bodyconquest.networking.Client;
import main.com.bodyconquest.networking.Server;
import main.com.bodyconquest.screens.*;
import main.com.bodyconquest.screens.MenuScreen;

import java.io.IOException;

/**
 * The type Body conquest.
 */
/*
The core of the rendering engine, hosts the sprite batch for the current screen,
calls all act methods for actors in stages of children and calls all render functions
for implementing screens.
 */
public class BodyConquest extends com.badlogic.gdx.Game {
    /**
     * The constant V_WIDTH.
     */
    public static final int V_WIDTH = 800;
    /**
     * The constant V_HEIGHT.
     */
    public static final int V_HEIGHT = 600;

    /**
     * The Audio player.
     */
    public final AudioPlayer audioPlayer = new AudioPlayer();

    private FPSLogger fpsLogger = new FPSLogger();
    /**
     * The Batch.
     */
    public SpriteBatch batch;

    private Stage stage;

    private boolean loaded = false;

    /**
     * The Font.
     */
// so that we could add text
    public BitmapFont font;
    /**
     * The Timer font.
     */
    public BitmapFont timerFont;
    /**
     * The Username font.
     */
    public BitmapFont usernameFont;

    private Game game;
    private Client client;
    /**
     * The Game font.
     */
    public BitmapFont gameFont;
    /**
     * The Difficulty level.
     */
    public DifficultyLevel difficultyLevel;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        timerFont = new BitmapFont(Gdx.files.internal(Assets.timerFont));
        usernameFont = new BitmapFont(Gdx.files.internal(Assets.usernameFont));
        gameFont = new BitmapFont(Gdx.files.internal(Assets.gameFont));
        audioPlayer.loadSFX("button_click", Assets.buttonSoundPath);
        audioPlayer.loadMusic("music", Assets.music);
        audioPlayer.loadMusic("heartbeat", Assets.heartbeat);
        audioPlayer.playMusicLoop("music");
        difficultyLevel = DifficultyLevel.EASY;
        client = new Client();
        // setScreen(new MenuScreen(this, "GermBoi"));
        setScreen(new MenuScreen(this));
        // setScreen(new LeaderboardScreen(this));
        // setScreen(new WaitingScreen(this,GameType.MULTIPLAYER_HOST));

    }

    @Override
    public void render() {
        super.render();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)
                && (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)
                || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT))) {
            audioPlayer.toggleMuted();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    /**
     * Gets game.
     *
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Gets server.
     *
     * @return the server
     */
    public Server getServer() {
        return game.getServer();
    }

    /**
     * Sets game.
     *
     * @param game the game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Gets client.
     *
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Sets client.
     *
     * @param client the client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Gets difficulty level.
     *
     * @return the difficulty level
     */
    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     * Change difficulty.
     */
    public void changeDifficulty() {
        if (difficultyLevel == DifficultyLevel.EASY) {
            difficultyLevel = DifficultyLevel.HARD;
        } else {
            difficultyLevel = DifficultyLevel.EASY;
        }
    }
}
