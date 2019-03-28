package main.com.bodyconquest.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.GameType;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.database.DatabaseManager;
import main.com.bodyconquest.networking.utilities.Hasher;
import main.com.bodyconquest.networking.utilities.MessageMaker;
import main.com.bodyconquest.rendering.BodyConquest;

import java.io.IOException;

/** The type Login screen. */
public class LoginScreen extends DatabasesScreen implements Screen {

  private Texture login;

  private Image loginImage;
  // private GameType gameType;
  private int counter;

  protected PlayerType playerType;
  private Texture t_submit;
  private Image submitImage;
  private Texture t_register;
  private Image registerImage;

  /**
   * Instantiates a new Login screen.
   *
   * @param game the game
   */
  public LoginScreen(BodyConquest game, GameType gameType, int counter) {
    super(game, gameType);
    loginImage = new Image(login);
    this.counter = counter;
    //        backBtn = new TextButton("Back",skin);
    if (gameType == GameType.SINGLE_PLAYER || gameType == GameType.MULTIPLAYER_HOST) {
      playerType = PlayerType.PLAYER_BOTTOM;
    } else {
      playerType = PlayerType.PLAYER_TOP;
    }
    if (counter > 0) {
      txfUsername.setMessageText("Wrong information; Try again");
      txfPassword.setMessageText("");
    }

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
    manager.load(Assets.loginHeader, Texture.class);
    manager.load(Assets.submitButtonLow, Texture.class);
    manager.load(Assets.registerButtonLow, Texture.class);
    manager.finishLoading();
  }

  /** {@inheritDoc} */
  @Override
  public void getAssets() {
    super.getAssets();
    login = manager.get(Assets.loginHeader, Texture.class);
    t_submit = manager.get(Assets.submitButtonLow, Texture.class);
    t_register = manager.get(Assets.registerButtonLow, Texture.class);
  }

  public void addButtons() {
    submitImage.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            playButtonSound();
            textPassword = txfPassword.getText();
            textUsername = txfUsername.getText();
            //String message = MessageMaker.loginMessage(textUsername, textPassword);

            Hasher hasher = new Hasher();
            String hashedPassword = hasher.hash(textPassword);
            String message = MessageMaker.loginMessage(textUsername, hashedPassword);

            game.getClient().clientSender.sendMessage(message);
            System.out.println(textUsername + " " + textPassword);
            processRegistration();
          }
        });

    registerImage.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            playButtonSound();
            game.setScreen(new RegisteringScreen(game, gameType));
          }
        });
  }

  // public void listenBack

  /** {@inheritDoc} */
  @Override
  public void processRegistration() {
    super.processRegistration();
    // wait till the login answer is received from server
    while (game.getClient().getCommunicator().getLoggedIsSet().get() == false) {}
    if (game.getClient().getCommunicator().getLogged().get() == true) {
      // if successfully logged in, go to menu screen
      try {

        game.getClient().getCommunicator().setUsername(playerType, textUsername);
        //
        // game.getClient().clientSender.sendMessage(MessageMaker.usernameMessage(playerType,textUsername));

          if (gameType == GameType.SINGLE_PLAYER) {

              game.setScreen(new RaceSelection(game, gameType));
          }
        else if (gameType == GameType.MULTIPLAYER_HOST || gameType == GameType.MULTIPLAYER_JOIN) {
          //                    game.setScreen(new WaitingScreen(game, gameType));
          game.setScreen(new RaceSelection(game, gameType));
        }
      } catch (IOException e) {
        System.out.println("Exception when displaying race selection screen");
      }

    } else {
      // else go back to login screen
      // loginBtn.setText("Bad details!");
      //            try {
      //                Thread.sleep(10000);
      //            } catch (InterruptedException e) {
      //                e.printStackTrace();
      //            }
      game.getClient().getCommunicator().setLoggedIsSet(false);
      counter++;
      game.setScreen(new LoginScreen(game, gameType, counter));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void settingPositions() {
    super.settingPositions();
    loginImage.setPosition(BodyConquest.V_WIDTH / 2.0f - loginImage.getWidth() / 2.0f, 450.0f);
    submitImage = new Image(t_submit);
    submitImage.setBounds(
        BodyConquest.V_WIDTH / 2 - t_submit.getWidth() / 2,
        100,
        t_submit.getWidth(),
        t_submit.getHeight());

    registerImage = new Image(t_register);
    registerImage.setBounds(
        BodyConquest.V_WIDTH / 2 - t_register.getWidth() / 2,
        50,
        t_register.getWidth(),
        t_register.getHeight());
  }

  /** {@inheritDoc} */
  @Override
  public void adding() {
    super.adding();
    stage.addActor(loginImage);
    stage.addActor(submitImage);
    stage.addActor(registerImage);
  }

  /** {@inheritDoc} */
  @Override
  public void settingSizes() {
    super.settingSizes();
    //    loginImage.setSize(login.getWidth(), login.getHeight());
  }
}
