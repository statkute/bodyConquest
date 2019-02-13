package com.cauldron.bodyconquest.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.sun.xml.internal.ws.wsdl.writer.document.soap.Body;

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
  private Stage stage;

  OrthographicCamera camera;

  public MenuScreen(BodyConquest game) {
    this.game = game;
    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 600);
    stage = new Stage();

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
        System.out.println("Multiplayer Is touched");
        //              game.setScreen(new RaceSelectionScreen(game));
        //              dispose();
      }
      if (singleplayerBounds.contains(tmp.x, tmp.y)) {
        //stage.addAction(Actions.fadeOut(5f));
        game.setScreen(new RaceSelection(game));
        dispose();
      }
      if (settingsBounds.contains(tmp.x, tmp.y)) {
        System.out.println("Settings Is touched");
        //              game.setScreen(new SettingsScreen(game));
        //              dispose();
      }
      if (creditsBounds.contains(tmp.x, tmp.y)) {
        game.setScreen(new CreditsScreen(game));
        dispose();
      }
    }
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
}
