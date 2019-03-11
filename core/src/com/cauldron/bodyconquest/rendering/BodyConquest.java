package com.cauldron.bodyconquest.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.cauldron.bodyconquest.audio.AudioPlayer;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.game_logic.Game;
import com.cauldron.bodyconquest.networking.Client;
import com.cauldron.bodyconquest.networking.Server;
import com.cauldron.bodyconquest.screens.MenuScreen;
import com.cauldron.bodyconquest.screens.StartScreen;

import java.awt.*;

/*
The core of the rendering engine, hosts the sprite batch for the current screen,
calls all act methods for actors in stages of children and calls all render functions
for implementing screens.
 */
public class BodyConquest extends com.badlogic.gdx.Game {

  // private static final Logger log = Logger.getLogger(MyGdxGame.class);

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

  @Override
  public void create() {
    batch = new SpriteBatch();
    font = new BitmapFont();
    timerFont = new BitmapFont(Gdx.files.internal(Assets.timerFont));
    usernameFont = new BitmapFont(Gdx.files.internal(Assets.usernameFont));
    audioPlayer.loadSFX("button_click", Assets.buttonSoundPath);
    audioPlayer.loadMusic("music", Assets.music);
    audioPlayer.playMusicLoop("music");
    //setScreen(new MenuScreen(this));
    setScreen(new StartScreen(this));
    client = new Client();
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
}
