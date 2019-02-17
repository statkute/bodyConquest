package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.game_logic.Communicator;
import com.cauldron.bodyconquest.networking.*;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.cauldron.bodyconquest.screens.CreditsScreen;
import com.cauldron.bodyconquest.screens.RaceSelection;

import java.io.IOException;
import java.net.SocketException;

public class MenuScreen implements Screen {

  private BodyConquest game;
  private Texture background;
  private Texture playButtonMultiplayer;
  private Texture playButtonSinglePlayer;
  private Texture settingsButton;
  private Texture creditsButton;
  private Rectangle multiplayerBounds;
  private Rectangle singleplayerBounds;
  private Rectangle settingsBounds;
  private Rectangle creditsBounds;

  OrthographicCamera camera;

  private Server server;
  private Client client;

  public MenuScreen(BodyConquest game) {
    this.game = game;
    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 600);

    background = new Texture("core/assets/logosmall.png");
    playButtonMultiplayer = new Texture("core/assets/multiplayer1.png");
    playButtonSinglePlayer = new Texture("core/assets/singleplayer.png");
    settingsButton = new Texture("core/assets/settings.png");
    creditsButton = new Texture("core/assets/Credits.png");

    multiplayerBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - playButtonMultiplayer.getWidth() / 2,
            336,
            playButtonMultiplayer.getWidth(),
            playButtonMultiplayer.getHeight());
    singleplayerBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - playButtonSinglePlayer.getWidth() / 2,
            226,
            playButtonSinglePlayer.getWidth(),
            playButtonSinglePlayer.getHeight());
    settingsBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - settingsButton.getWidth() / 2,
            126,
            settingsButton.getWidth(),
            settingsButton.getHeight());
    creditsBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - creditsButton.getWidth() / 2,
            30,
            creditsButton.getWidth(),
            creditsButton.getHeight());
  }

  @Override
  public void show() {}

  @Override
  public void render(float delta) {

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.update();
    game.batch.setProjectionMatrix(camera.combined);

    game.batch.begin();
    game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
    game.batch.draw(
        playButtonMultiplayer,
        BodyConquest.V_WIDTH / 2 - playButtonMultiplayer.getWidth() / 2,
        336);
    game.batch.draw(
        playButtonSinglePlayer,
        BodyConquest.V_WIDTH / 2 - playButtonSinglePlayer.getWidth() / 2,
        226);
    game.batch.draw(settingsButton, BodyConquest.V_WIDTH / 2 - settingsButton.getWidth() / 2, 126);
    game.batch.draw(creditsButton, BodyConquest.V_WIDTH / 2 - creditsButton.getWidth() / 2, 30);

    checkPressed();

    game.batch.end();
  }

  public void checkPressed() {

    Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    camera.unproject(tmp);
    if (Gdx.input.justTouched()) {

      if (multiplayerBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        System.out.println("Multiplayer Is touched");
        //              game.setScreen(new RaceSelectionScreen(game));
        //              dispose();
      }
      if (singleplayerBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        System.out.println("Singleplayer Is touched");
        server = new Server();
        try {
          server.startServer("singleplayer");
          client = new Client();
          Communicator communicator = new Communicator();
          client.startClient(communicator);
          game.setScreen(new RaceSelection(game, server, communicator));
          dispose();
        } catch (SocketException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (settingsBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        System.out.println("Settings Is touched");
        //              game.setScreen(new SettingsScreen(game));
        //              dispose();
      }
      if (creditsBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        game.setScreen(new CreditsScreen(game));
        dispose();
      }
    }
  }

  public Server getServer() {
    return server;
  }

  public Client getClient() {
    return client;
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
    background.dispose();
    playButtonSinglePlayer.dispose();
    playButtonMultiplayer.dispose();
    settingsButton.dispose();
    creditsButton.dispose();
  }

  public void playButtonSound(){
    Constants.buttonSound.play();
  }
}
