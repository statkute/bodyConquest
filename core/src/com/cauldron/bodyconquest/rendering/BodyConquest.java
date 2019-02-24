package com.cauldron.bodyconquest.rendering;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.cauldron.bodyconquest.audio.AudioPlayer;
import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.networking.Client;
import com.cauldron.bodyconquest.networking.Server;
import com.cauldron.bodyconquest.screens.MenuScreen;

/*
The core of the rendering engine, hosts the sprite batch for the current screen,
calls all act methods for actors in stages of children and calls all render functions
for implementing screens.
 */
public class BodyConquest extends Game {

  // private static final Logger log = Logger.getLogger(MyGdxGame.class);

  public static final int V_WIDTH = 800;
  public static final int V_HEIGHT = 600;

  public static final AudioPlayer audioPlayer = new AudioPlayer();

  private FPSLogger fpsLogger = new FPSLogger();
  public SpriteBatch batch;

  private Stage stage;

  private boolean loaded = false;

  // so that we could add text
  public BitmapFont font;

  private Server server;
  private Client client;

  @Override
  public void create() {
    batch = new SpriteBatch();
    font = new BitmapFont();
    audioPlayer.loadSFX("button_click", Constants.buttonSoundPath);
    audioPlayer.loadMusic("music", Constants.music);
    audioPlayer.playMusicLoop("music");
    setScreen(new MenuScreen(this)); //uncomment this to check the screen
  }

  @Override
  public void render() {
    super.render();
    if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
  }

  @Override
  public void dispose() {
    batch.dispose();
    font.dispose();
  }

  public Server getServer() {
    return server;
  }

  public void setServer(Server server) {
    this.server = server;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
