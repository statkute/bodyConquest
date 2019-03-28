package main.com.bodyconquest.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.GameType;
import main.com.bodyconquest.networking.utilities.Hasher;
import main.com.bodyconquest.networking.utilities.MessageMaker;
import main.com.bodyconquest.rendering.BodyConquest;

/**
 * The type Registering screen.
 */
public class RegisteringScreen extends DatabasesScreen implements Screen {

  private Texture register;
  private Image registerImage;
  private Texture t_submit;
  private Image submitImage;
  private Texture t_login;
  private Image loginImage;

  //    private GameType gameType;

  /**
   * Instantiates a new Registering screen.
   *
   * @param game     the game
   * @param gameType the game type
   */
  public RegisteringScreen(BodyConquest game, GameType gameType) {
    super(game, gameType);
    registerImage = new Image(register);
    settingSizes();
    settingPositions();
    adding();
    addButtons();
    // this.gameType = gameType;
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
    manager.load(Assets.registerHeader, Texture.class);
    manager.load(Assets.submitButtonLow, Texture.class);
    manager.load(Assets.loginButtonLow, Texture.class);
    manager.finishLoading();
  }

  /** {@inheritDoc} */
  @Override
  public void getAssets() {
    super.getAssets();
    register = manager.get(Assets.registerHeader, Texture.class);
    t_submit = manager.get(Assets.submitButtonLow, Texture.class);
    t_login = manager.get(Assets.loginButtonLow, Texture.class);
  }

  /**
   * Add buttons.
   */
  public void addButtons() {
    submitImage.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            playButtonSound();
            textPassword = txfPassword.getText();
            textUsername = txfUsername.getText();
            //String message = MessageMaker.registerMessage(textUsername, textPassword);

            Hasher hasher = new Hasher();
            String hashedPassword = hasher.hash(textPassword);
            String message = MessageMaker.registerMessage(textUsername, hashedPassword);

            game.getClient().clientSender.sendMessage(message);
              //System.out.println(textUsername + " " + textPassword);
            processRegistration();
          }
        });

    loginImage.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            playButtonSound();
            game.setScreen(new LoginScreen(game, gameType, 0));
          }
        });
  }

  /** {@inheritDoc} */
  @Override
  public void processRegistration() {
    super.processRegistration();
    // wait till the register answer is received from server
    while (!game.getClient().getCommunicator().getRegisteredIsSet().get()) {}
    //    System.out.println("Got after registered is set");
    //     System.out.println(game.getClient().getCommunicator().getRegistered());
    //     System.out.println(game.getClient().getCommunicator().getRegisteredIsSet());
    if (game.getClient().getCommunicator().getRegistered().get()) {
      // if successfully registered, go to login screen
      game.setScreen(new LoginScreen(game, gameType, 0));
    } else {
      // else go back to registering screen
      txfUsername.setMessageText("Username already in use; Try again");
      game.setScreen(new RegisteringScreen(game, gameType));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void settingPositions() {
    super.settingPositions();
    registerImage.setPosition(
        BodyConquest.V_WIDTH / 2.0f - registerImage.getWidth() / 2.0f, 450.0f);
    submitImage = new Image(t_submit);
    submitImage.setBounds(
        BodyConquest.V_WIDTH / 2 - t_submit.getWidth() / 2,
        100,
        t_submit.getWidth(),
        t_submit.getHeight());

    loginImage = new Image(t_login);
    loginImage.setBounds(
        BodyConquest.V_WIDTH / 2 - t_login.getWidth() / 2,
        50,
        t_login.getWidth(),
        t_login.getHeight());
  }

  /** {@inheritDoc} */
  @Override
  public void adding() {
    super.adding();
    stage.addActor(registerImage);
    stage.addActor(submitImage);
    stage.addActor(loginImage);
  }

  /** {@inheritDoc} */
  @Override
  public void settingSizes() {
    super.settingSizes();
    registerImage.setSize(register.getWidth(), register.getHeight());
  }
}
