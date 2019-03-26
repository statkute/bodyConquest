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
import main.com.bodyconquest.game_logic.Game;
import main.com.bodyconquest.networking.Client;
import main.com.bodyconquest.networking.Server;
import main.com.bodyconquest.screens.*;
import main.com.bodyconquest.screens.MenuScreen;

import java.io.IOException;

/*
The core of the rendering engine, hosts the sprite batch for the current screen,
calls all act methods for actors in stages of children and calls all render functions
for implementing screens.
 */
public class BodyConquest extends com.badlogic.gdx.Game {

  // private static final Logger log = Logger.getLogger(MyGdxGame.class);

  public enum DifficultyLevel {
    EASY,
    HARD
  }

  public static final int V_WIDTH = 800;
  public static final int V_HEIGHT = 600;

  public final AudioPlayer audioPlayer = new AudioPlayer();

  private FPSLogger fpsLogger = new FPSLogger();
  public SpriteBatch batch;

  private Stage stage;

  private boolean loaded = false;

  // so that we could add text
  public BitmapFont font;
  public BitmapFont timerFont;
  public BitmapFont usernameFont;

  private Game game;
  private Client client;
  public BitmapFont gameFont;
  private DifficultyLevel difficultyLevel;

  @Override
  public void create() {
    batch = new SpriteBatch();
    font = new BitmapFont();
    timerFont = new BitmapFont(Gdx.files.internal(Assets.timerFont));
    usernameFont = new BitmapFont(Gdx.files.internal(Assets.usernameFont));
    gameFont = new BitmapFont(Gdx.files.internal(Assets.gameFont));
    audioPlayer.loadSFX("button_click", Assets.buttonSoundPath);
    audioPlayer.loadMusic("music", Assets.music);
    audioPlayer.playMusicLoop("music");
    difficultyLevel = DifficultyLevel.EASY;
    client = new Client();
    //setScreen(new MenuScreen(this, "GermBoi"));
    setScreen(new MenuScreen(this));
    //setScreen(new LeaderboardScreen(this));
    //setScreen(new WaitingScreen(this,GameType.MULTIPLAYER_HOST));

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

  public Game getGame() {
    return game;
  }

  public Server getServer() { return game.getServer(); }

  public void setGame(Game game) {
    this.game = game;
  }

  public Client getClient() {
      return client;

  }

  public void setClient(Client client) {
    this.client = client;
  }

  public DifficultyLevel getDifficultyLevel() {
    return difficultyLevel;
  }

  public void changeDifficulty () {
    if (difficultyLevel == DifficultyLevel.EASY){
      difficultyLevel = DifficultyLevel.HARD;
    } else {
      difficultyLevel = DifficultyLevel.EASY;
    }
  }
}
