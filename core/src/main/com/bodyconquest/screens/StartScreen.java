package main.com.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.rendering.BodyConquest;

/**
 * The type Start screen.
 */
public class StartScreen extends AbstractGameScreen implements Screen {

  private Texture title;

  private Texture register;

  private Texture login;

  private Texture exitButton;

  private Rectangle registerBounds;

  private Rectangle loginBounds;

  private Rectangle exitBounds;

  /**
   * Instantiates a new Start screen.
   *
   * @param game the game
   */
  public StartScreen(BodyConquest game) {
    super(game);
    loadAssets();
    getAssets();
    setRectangles();
  }

  /** {@inheritDoc} */
  @Override
  public void render(float delta) {
    super.render(delta);
    game.batch.begin();
    game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
    game.batch.draw(title, BodyConquest.V_WIDTH / 2.0f - title.getWidth() / 2.0f, 450 * BodyConquest.scaleRatioHeight);
    game.batch.draw(
        login,
        BodyConquest.V_WIDTH / 2.0f - login.getWidth() / 2.0f,
        300 * BodyConquest.scaleRatioHeight,
        login.getWidth(),
        login.getHeight());
    game.batch.draw(
        register,
        BodyConquest.V_WIDTH / 2.0f - register.getWidth() / 2.0f,
        240 * BodyConquest.scaleRatioHeight,
        register.getWidth(),
        register.getHeight());
    game.batch.draw(exitButton, BodyConquest.V_WIDTH / 2.0f - exitButton.getWidth() / 2.0f, 60 * BodyConquest.scaleRatioHeight);
    checkPressed();
    game.batch.end();
  }

  /** {@inheritDoc} */
  @Override
  public void checkPressed() {
    super.checkPressed();

    if (Gdx.input.justTouched()) {

      if (loginBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        // System.out.println("Login touched");
        dispose();
        game.setScreen(new LoginScreen(game));
      }

      if (registerBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        // System.out.println("register touched");
        dispose();
        game.setScreen(new RegisteringScreen(game));
      }

        if (exitBounds.contains(tmp.x, tmp.y)) {
            playButtonSound();
            dispose();
            Gdx.app.exit();
            System.exit(0);
        }
    }
  }

  /** {@inheritDoc} */
  @Override
  public void loadAssets() {
    super.loadAssets();
    manager.load(Assets.startTitle, Texture.class);
    manager.load(Assets.startLogin, Texture.class);
    manager.load(Assets.startRegister, Texture.class);
    manager.load(Assets.exitButton, Texture.class);
    manager.finishLoading();
  }

  /** {@inheritDoc} */
  @Override
  public void getAssets() {
    super.getAssets();
    title = manager.get(Assets.startTitle, Texture.class);
    login = manager.get(Assets.startLogin, Texture.class);
    register = manager.get(Assets.startRegister, Texture.class);
    exitButton = manager.get(Assets.exitButton, Texture.class);
  }

  /** {@inheritDoc} */
  @Override
  public void setRectangles() {
    super.setRectangles();
    loginBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2.0f - login.getWidth()/ 2.0f,
            300 * BodyConquest.scaleRatioHeight,
            login.getWidth(),
            login.getHeight());
    registerBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2.0f - register.getWidth() / 2.0f,
            240 * BodyConquest.scaleRatioHeight,
            register.getWidth(),
            register.getHeight());
    exitBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2.0f - exitButton.getWidth() / 2.0f,
            60 * BodyConquest.scaleRatioHeight,
            exitButton.getWidth(),
            exitButton.getHeight());
  }
}
