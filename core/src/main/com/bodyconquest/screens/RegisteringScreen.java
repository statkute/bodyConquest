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

/** The type Registering screen. */
public class RegisteringScreen extends DatabasesScreen implements Screen {

  //    private TextButton registerBtn;
  private Texture t_register;
  private Image registerImage;
  private Texture t_submit;
  private Image submitImage;

  /** The Database manager. */
  DatabaseManager dbManager;

  /**
   * Instantiates a new Registering screen.
   *
   * @param game the game
   */
  public RegisteringScreen(BodyConquest game) {
    super(game);
    registerImage = new Image(t_register);
    submitImage = new Image(t_submit);
    //        registerBtn = new TextButton("Register", skin);
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
    manager.load(Assets.registerheader_big, Texture.class);
    manager.load(Assets.rlSubmit, Texture.class);
    manager.finishLoading();
  }

  /** {@inheritDoc} */
  @Override
  public void getAssets() {
    super.getAssets();
    t_register = manager.get(Assets.registerheader_big, Texture.class);
    t_submit = manager.get(Assets.rlSubmit, Texture.class);
  }

  /**
   * Listen button for a click.
   *
   * @param registerImg the t_register btn
   */
  public void listenButton(Image registerImg) {
    registerImg.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            playButtonSound();
            textPassword = txfPassword.getText();
            textUsername = txfUsername.getText();
            System.out.println(textUsername + " " + textPassword);
            processRegistration();
          }
        });
  }

  /** {@inheritDoc} */
  @Override
  public void processRegistration() {
    super.processRegistration();
    game.setScreen(new LoginScreen(game));
  }

  /** {@inheritDoc} */
  @Override
  public void settingPositions() {
    super.settingPositions();
    registerImage.setPosition(
        BodyConquest.V_WIDTH / 2.0f - registerImage.getWidth() / 2.0f,
        450.0f * BodyConquest.scaleRatioHeight);
    submitImage.setPosition(
        BodyConquest.V_WIDTH / 2.0f - submitImage.getWidth() / 2.0f,
        50.0f * BodyConquest.scaleRatioHeight);
  }

  /** {@inheritDoc} */
  @Override
  public void adding() {
    super.adding();
    stage.addActor(registerImage);
    stage.addActor(submitImage);
  }

  /** {@inheritDoc} */
  @Override
  public void settingSizes() {
    super.settingSizes();
    submitImage.setSize(t_submit.getWidth(), t_submit.getHeight());
    registerImage.setSize(t_register.getWidth(), t_register.getHeight());
  }
}
