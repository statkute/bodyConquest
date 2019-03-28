package main.com.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/** The type Credits screen. */
public class CreditsScreen extends AbstractGameScreen implements Screen {

  private Texture backButton;
  private Texture header;
  private Texture alexandru;
  private Texture augustas;
  private Texture brandon;
  private Texture gintare;
  private Texture paul;
  private Rectangle backBounds;
  private String username;

  /**
   * Instantiates a new Credits screen.
   *
   * @param game the game
   * @param username the username
   */
  public CreditsScreen(BodyConquest game, String username) {
    super(game);
    this.username = username;
    loadAssets();
    getAssets();
    setRectangles();
  }

  /** {@inheritDoc} */
  @Override
  public void show() {}

  /** {@inheritDoc} */
  @Override
  public void render(float delta) {

    super.render(delta);
    game.batch.begin();

    game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
    game.batch.draw(header, BodyConquest.V_WIDTH / 2 - header.getWidth() / 2, 450);

    game.batch.draw(alexandru, BodyConquest.V_WIDTH / 2 - alexandru.getWidth() / 2, 350);
    game.batch.draw(augustas, BodyConquest.V_WIDTH / 2 - augustas.getWidth() / 2, 300);
    game.batch.draw(brandon, BodyConquest.V_WIDTH / 2 - brandon.getWidth() / 2, 250);
    game.batch.draw(gintare, BodyConquest.V_WIDTH / 2 - gintare.getWidth() / 2, 200);
    game.batch.draw(paul, BodyConquest.V_WIDTH / 2 - paul.getWidth() / 2, 150);

    game.batch.draw(backButton, BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2, 60);

    checkPressed();
    game.batch.end();
  }

  /** {@inheritDoc} */
  @Override
  public void checkPressed() {

    super.checkPressed();

    if (Gdx.input.justTouched()) {
      if (backBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        dispose();
        game.setScreen(new MenuScreen(game));
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public void resize(int width, int height) {
    super.resize(width, height);
  }

  /** {@inheritDoc} */
  @Override
  public void loadAssets() {
    super.loadAssets();
    manager.load(Assets.creditsHeader, Texture.class);
    manager.load(Assets.alexandru,     Texture.class);
    manager.load(Assets.augustas,      Texture.class);
    manager.load(Assets.brandon,       Texture.class);
    manager.load(Assets.gintare,       Texture.class);
    manager.load(Assets.paul,          Texture.class);
    manager.load(Assets.backButton,    Texture.class);
    manager.finishLoading();
  }

  /** {@inheritDoc} */
  @Override
  public void getAssets() {
    super.getAssets();
    header = manager.get(Assets.creditsHeader,  Texture.class);
    alexandru = manager.get(Assets.alexandru,   Texture.class);
    augustas = manager.get(Assets.augustas,     Texture.class);
    brandon = manager.get(Assets.brandon,       Texture.class);
    gintare = manager.get(Assets.gintare,       Texture.class);
    paul = manager.get(Assets.paul,             Texture.class);
    backButton = manager.get(Assets.backButton, Texture.class);
  }

  /** {@inheritDoc} */
  @Override
  public void setRectangles() {
    super.setRectangles();
    backBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2,
            60,
            backButton.getWidth(),
            backButton.getHeight());
  }
}
