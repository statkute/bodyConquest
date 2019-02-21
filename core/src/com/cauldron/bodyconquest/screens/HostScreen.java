package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.game_logic.Communicator;
import com.cauldron.bodyconquest.networking.*;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.io.IOException;
import java.net.SocketException;

public class HostScreen implements Screen {

  private BodyConquest game;
  private Texture background;
  private Texture header;
  private Texture hostButtton;
  private Texture joinButton;
  private Rectangle hostBounds;
  private Rectangle joinBounds;
  OrthographicCamera camera;

  private Server server;
  private Client client;

  public HostScreen(BodyConquest game) {
    this.game = game;
    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 600);
    background = new Texture("core/assets/background_new.png");
    header = new Texture("core/assets/multiplayerheader_new.png");
    hostButtton = new Texture("core/assets/host_new.png");
    joinButton = new Texture("core/assets/join_new.png");
    hostBounds = new Rectangle(140, 152, hostButtton.getWidth(), hostButtton.getHeight());
    joinBounds = new Rectangle(480, 125, joinButton.getWidth(), joinButton.getHeight());

    hostBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - hostButtton.getWidth() / 2,
            300,
            hostButtton.getWidth(),
            hostButtton.getHeight());

    joinBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - joinButton.getWidth() / 2,
            240,
            joinButton.getWidth(),
            joinButton.getHeight());
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
    game.batch.draw(header, BodyConquest.V_WIDTH / 2 - header.getWidth() / 2, 400);

    game.batch.draw(hostButtton, BodyConquest.V_WIDTH / 2 - hostButtton.getWidth() / 2, 300);

    game.batch.draw(joinButton, BodyConquest.V_WIDTH / 2 - joinButton.getWidth() / 2, 240);

    try {
      checkIfPressed();
    } catch (IOException e) {
      e.printStackTrace();
    }

    game.batch.end();
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
    hostButtton.dispose();
    joinButton.dispose();
  }

  public void checkIfPressed() throws IOException {

    Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    camera.unproject(tmp);

    if (Gdx.input.justTouched()) {
      if (hostBounds.contains(tmp.x, tmp.y)) {
        System.out.println("host pressed");
        server = new Server();
        server.startServer("multiplayer");
        client = new Client();
        Communicator communicator = new Communicator();
        client.startClient(communicator);
        game.setServer(server);
        game.setClient(client);
        game.setScreen(new RaceSelection(game, server, communicator, "multiplayer"));
      }
      if (joinBounds.contains(tmp.x, tmp.y)) {
        System.out.println("join pressed");
        client = new Client();
        Communicator communicator = new Communicator();
        client.startClient(communicator);
        game.setClient(client);
        System.out.println("setting the raceselection screen");
        game.setScreen(new RaceSelection(game, communicator));
      }
    }
  }
}
