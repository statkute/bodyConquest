package main.com.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.networking.Server;
import main.com.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * The type Abstract game screen.
 */
public abstract class AbstractGameScreen implements Screen {

  /**
   * The Game.
   */
  protected BodyConquest game;
  /**
   * The Viewport.
   */
  protected Viewport viewport;
  /**
   * The Camera.
   */
  protected Camera camera;
  /**
   * The Manager.
   */
  protected AssetManager manager;
  /**
   * The Background.
   */
  protected Texture background;
  /**
   * The vector
   */
  protected Vector3 tmp;
  /**
   * The Server.
   */
  protected Server server;

  /**
   * Instantiates a new Abstract game screen.
   *
   * @param game the game
   */
  public AbstractGameScreen(BodyConquest game) {
    init(game);
  }

  /**
   * Init.
   *
   * @param game The game.
   */
  public void init(BodyConquest game) {

    this.game = game;
    if (camera == null) {
      camera = new OrthographicCamera();
      ((OrthographicCamera) camera).setToOrtho(false, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
    }
    if (viewport == null) {
      viewport = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, camera);
    }
    viewport.apply();
    manager = new AssetManager();
  }

  /** {@inheritDoc} */
  @Override
  public void show() {}

  /** {@inheritDoc} */
  @Override
  public void render(float delta) {

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.update();
    game.batch.setProjectionMatrix(camera.combined);
    viewport.apply();
  }
  /** {@inheritDoc} */
  @Override
  public void resize(int width, int height) {

    viewport.update(width, height, true);
  }

  /** {@inheritDoc} */
  @Override
  public void pause() {}

  /** {@inheritDoc} */
  @Override
  public void resume() {}

  /** {@inheritDoc} */
  @Override
  public void hide() {}

  /** {@inheritDoc} */
  @Override
  public void dispose() {
    manager.dispose();
  }

  /**
   * Check if any button or texture was pressed.
   */
  public void checkPressed() {
    tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    camera.unproject(tmp);
  }

  /**
   * Play button sound.
   */
  public void playButtonSound() {
    game.audioPlayer.playSFX("button_click");
  }

  /**
   * Load assets through asset manager.
   */
  public void loadAssets() {
    manager.load(Assets.menuBackground, Texture.class);
  }

  /**
   * Gets assets so that asset manager could use them.
   */
  public void getAssets() {
    background = manager.get(Assets.menuBackground, Texture.class);
  }

  /**
   * Sets rectangles to get the bounds of the textures or images.
   */
  public void setRectangles() {}
}
