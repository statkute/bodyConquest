package main.com.bodyconquest.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.database.DatabaseManager;
import main.com.bodyconquest.rendering.BodyConquest;

/** The type Login screen. */
public class LoginScreen extends DatabasesScreen implements Screen {

  private Texture t_login;
  private Image loginImage;
  private Texture t_submit;
  private Image submitImage;

  /** The Database manager. */
  DatabaseManager dbManager;

  /**
   * Instantiates a new Login screen.
   *
   * @param game the game
   */
  public LoginScreen(BodyConquest game) {
    super(game);
    loginImage = new Image(t_login);
    submitImage = new Image(t_submit);
    listenButton(submitImage);
    settingSizes();
    settingPositions();
    adding();
    this.dbManager = new DatabaseManager();
  }

  /** {@inheritDoc} */
  @Override
  public void render(float delta) {
    super.render(delta);
  }

  /** {@inheritDoc} */
  @Override
  public void loadAssets() {
    super.loadAssets();
    manager.load(Assets.loginheader_big, Texture.class);
    manager.load(Assets.rlSubmit, Texture.class);
    manager.finishLoading();
  }

  /** {@inheritDoc} */
  @Override
  public void getAssets() {
    super.getAssets();
    t_login = manager.get(Assets.loginheader_big, Texture.class);
    t_submit = manager.get(Assets.rlSubmit, Texture.class);
  }

  /**
   * Listen button for t_login click.
   *
   * @param loginImg the t_login btn
   */
  public void listenButton(Image loginImg) {
    loginImg.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            playButtonSound();
            textPassword = txfPassword.getText();
            textUsername = txfUsername.getText();
            game.setUsername(textUsername);
            game.getClient().setUsername(textUsername);
            System.out.println(textUsername + " " + textPassword);
            processRegistration();
          }
        });
  }

  /** {@inheritDoc} */
  @Override
  public void processRegistration() {
    super.processRegistration();
    game.setScreen(new MenuScreen(game, textUsername));
  }

  /** {@inheritDoc} */
  @Override
  public void settingPositions() {
    super.settingPositions();
    loginImage.setPosition(
        BodyConquest.V_WIDTH / 2.0f - loginImage.getWidth() / 2.0f,
        450.0f * BodyConquest.scaleRatioHeight);
    submitImage.setPosition(
        BodyConquest.V_WIDTH / 2.0f - submitImage.getWidth() / 2.0f,
        50.0f * BodyConquest.scaleRatioHeight);
  }

  /** {@inheritDoc} */
  @Override
  public void adding() {
    super.adding();
    stage.addActor(loginImage);
    stage.addActor(submitImage);
  }

  /** {@inheritDoc} */
  @Override
  public void settingSizes() {
    super.settingSizes();
    submitImage.setSize(t_submit.getWidth(), t_submit.getHeight());
    loginImage.setSize(t_login.getWidth(), t_login.getHeight());
  }
}
