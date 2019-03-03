package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.constants.GameType;
import com.cauldron.bodyconquest.game_logic.Communicator;
import com.cauldron.bodyconquest.game_logic.Game;
import com.cauldron.bodyconquest.networking.*;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.io.IOException;

public class HostScreen extends AbstractGameScreen implements Screen {

  private Texture header;
  private Texture hostButtton;
  private Texture joinButton;
  private Texture backButton;
  private Rectangle hostBounds;
  private Rectangle joinBounds;
  private Rectangle backBounds;

  private Server server;

  public HostScreen(BodyConquest game) {
    super(game);
    loadAssets();
    getAssets();
    setRectangles();
  }

  @Override
  public void show() {}

  @Override
  public void render(float delta) {

    super.render(delta);
    game.batch.begin();
    game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
    game.batch.draw(header, BodyConquest.V_WIDTH / 2 - header.getWidth() / 2, 450);
    game.batch.draw(hostButtton, BodyConquest.V_WIDTH / 2 - hostButtton.getWidth() / 2, 300);
    game.batch.draw(joinButton, BodyConquest.V_WIDTH / 2 - joinButton.getWidth() / 2, 240);
    game.batch.draw(backButton, BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2, 60);
    checkPressed();
    game.batch.end();
  }

  @Override
  public void checkPressed() {

    if (Gdx.input.justTouched()) {
      if (hostBounds.contains(tmp.x, tmp.y)) {
        System.out.println("host pressed");
        try {
          startGame(GameType.MULTIPLAYER_HOST);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (joinBounds.contains(tmp.x, tmp.y)) {
        System.out.println("join pressed");
        try {
          startGame(GameType.MULTIPLAYER_JOIN);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (backBounds.contains(tmp.x, tmp.y)) {
        System.out.println("back pressed");
        playButtonSound();
        dispose();
        game.setScreen(new MenuScreen(game));
      }
    }
  }

  private void startGame(GameType gameType) throws IOException {
    if (gameType == GameType.SINGLE_PLAYER) {
      System.err.println("[ERROR] Game type is set to single player on the host screen.");
      return;
    }
    playButtonSound();

    dispose();
    game.setScreen(new RaceSelection(game, gameType));
  }

  @Override
  public void loadAssets() {
    super.loadAssets();
    manager.load(Assets.hostHeader, Texture.class);
    manager.load(Assets.hostButton, Texture.class);
    manager.load(Assets.joinButton, Texture.class);
    manager.load(Assets.hostBack, Texture.class);
    manager.finishLoading();
  }

    @Override
    public void getAssets() {
        super.getAssets();
        header = manager.get(Assets.hostHeader,Texture.class);
        hostButtton = manager.get(Assets.hostButton,Texture.class);
        joinButton = manager.get(Assets.joinButton,Texture.class);
        backButton = manager.get(Assets.hostBack,Texture.class);

    }

    @Override
    public void setRectangles() {
        super.setRectangles();
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

        backBounds =
                new Rectangle(
                        BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2,
                        60,
                        backButton.getWidth(),
                        backButton.getHeight());

    }
}
