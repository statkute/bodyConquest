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
import main.com.bodyconquest.networking.utilities.MessageMaker;
import main.com.bodyconquest.rendering.BodyConquest;

import java.io.IOException;

/**
 * The type Login screen.
 */
public class LoginScreen extends DatabasesScreen implements Screen {

    private Texture login;
    private TextButton loginBtn;
    private Image loginImage;
    //private GameType gameType;
    private int counter;
    protected TextButton backBtn;
    protected PlayerType playerType;

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
        if(gameType == GameType.SINGLE_PLAYER || gameType == GameType.MULTIPLAYER_HOST){
            playerType = PlayerType.PLAYER_BOTTOM;
        }
        else{
            playerType = PlayerType.PLAYER_TOP;
        }
        if (counter > 0) {
            loginBtn = new TextButton("WRONG Details", skin);
            loginBtn.getLabel().setColor(Color.BLACK);
            txfUsername.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    loginBtn.getLabel().setColor(1, 1, 1, 1);
                    loginBtn.getLabel().setText("Login");
                }
            });
        } else {
            loginBtn = new TextButton("Login", skin);
        }

        backBtn = new TextButton("Back to Register", skin);
        listenButton(loginBtn);
        settingSizes();
        settingPositions();
        adding();
        backButtonListener(backBtn);
        //this.gameType = gameType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(float delta) {
        super.render(delta);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadAssets() {
        super.loadAssets();
        manager.load(Assets.startLogin, Texture.class);
        manager.finishLoading();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getAssets() {
        super.getAssets();
        login = manager.get(Assets.startLogin, Texture.class);
    }

    /**
     * Listen button for login click.
     *
     * @param loginBtn the login btn
     */
    public void listenButton(TextButton loginBtn) {
        loginBtn.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                loginBtn.setText("You logged in!");
                textPassword = txfPassword.getText();
                textUsername = txfUsername.getText();
                String message = MessageMaker.loginMessage(textUsername, textPassword);
                game.getClient().clientSender.sendMessage(message);
                System.out.println(textUsername + " " + textPassword);
                processRegistration();
            }
        });
    }

    //public void listenBack

    /**
     * {@inheritDoc}
     */
    @Override
    public void processRegistration() {
        super.processRegistration();
        //wait till the login answer is received from server
        while (game.getClient().getCommunicator().getLoggedIsSet().get() == false) {
        }
        if (game.getClient().getCommunicator().getLogged().get() == true) {
            //if successfully logged in, go to menu screen
            try {

                game.getClient().getCommunicator().setUsername(playerType,textUsername);
//                game.getClient().clientSender.sendMessage(MessageMaker.usernameMessage(playerType,textUsername));
                game.setScreen(new RaceSelection(game, gameType));
            } catch (IOException e) {
                System.out.println("Exception when displaying race selection screen");
            }

        } else {
            //else go back to login screen
            //loginBtn.setText("Bad details!");
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void settingPositions() {
        super.settingPositions();
        loginImage.setPosition(BodyConquest.V_WIDTH / 2.0f - loginImage.getWidth() / 2.0f, 450.0f);
        loginBtn.setPosition(BodyConquest.V_WIDTH / 2.0f - loginBtn.getWidth() / 2.0f - 150.f, 50.0f);
        backBtn.setPosition(BodyConquest.V_WIDTH / 2.0f - backBtn.getWidth() / 2.0f + 150.f, 50.0f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void adding() {
        super.adding();
        stage.addActor(loginImage);
        stage.addActor(loginBtn);
        stage.addActor(backBtn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void settingSizes() {
        super.settingSizes();
        loginBtn.setSize(250, 50);
        backBtn.setSize(250, 50);
        loginImage.setSize(login.getWidth(), login.getHeight());
    }

    public void backButtonListener(TextButton backBtn) {
        backBtn.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                game.setScreen(new RegisteringScreen(game, gameType));
            }
        });
    }
}
