package main.com.bodyconquest.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.GameType;
import main.com.bodyconquest.database.DatabaseManager;
import main.com.bodyconquest.networking.utilities.MessageMaker;
import main.com.bodyconquest.rendering.BodyConquest;

/**
 * The type Registering screen.
 */
public class RegisteringScreen extends DatabasesScreen implements Screen {

    private Texture register;
    private TextButton registerBtn;
    private Image registerImage;
    protected TextButton backBtn;

//    private GameType gameType;

    /**
     * Instantiates a new Registering screen.
     *
     * @param game the game
     */
    public RegisteringScreen(BodyConquest game, GameType gameType) {
        super(game,gameType);
        registerImage = new Image(register);
        registerBtn = new TextButton("Register", skin);
        backBtn = new TextButton("Back to Login",skin);
        listenButton(registerBtn);
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
        manager.load(Assets.startRegister, Texture.class);
        manager.finishLoading();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getAssets() {
        super.getAssets();
        register = manager.get(Assets.startRegister, Texture.class);
    }


    /**
     * Listen button for a click.
     *
     * @param registerBtn the register btn
     */

    public void listenButton(TextButton registerBtn) {
        registerBtn.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                registerBtn.setText("You registered!");
                textPassword = txfPassword.getText();
                textUsername = txfUsername.getText();
                String message = MessageMaker.registerMessage(textUsername, textPassword);
                game.getClient().clientSender.sendMessage(message);
                System.out.println(textUsername + " " + textPassword);
                processRegistration();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processRegistration() {
        super.processRegistration();
        //wait till the register answer is received from server
        while (game.getClient().getCommunicator().getRegisteredIsSet().get() == false) {
        }
        //System.out.println("Got after registered is set");
        //System.out.println(game.getClient().getCommunicator().getRegistered());
        //System.out.println(game.getClient().getCommunicator().getRegisteredIsSet());
        if (game.getClient().getCommunicator().getRegistered().get() == true) {
            //if successfully registered, go to login screen
            game.setScreen(new LoginScreen(game, gameType,0));
        } else {
            //else go back to registering screen
            game.setScreen(new RegisteringScreen(game, gameType));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void settingPositions() {
        super.settingPositions();
        registerImage.setPosition(BodyConquest.V_WIDTH / 2.0f - registerImage.getWidth() / 2.0f, 450.0f);
        registerBtn.setPosition(BodyConquest.V_WIDTH / 2.0f - registerBtn.getWidth() / 2.0f -150.0f, 50.0f);
        backBtn.setPosition(BodyConquest.V_WIDTH / 2.0f - backBtn.getWidth() / 2.0f + 150.f, 50.0f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void adding() {
        super.adding();
        stage.addActor(registerImage);
        stage.addActor(registerBtn);
        stage.addActor(backBtn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void settingSizes() {
        super.settingSizes();
        registerBtn.setSize(250, 50);
        registerImage.setSize(register.getWidth(), register.getHeight());
        backBtn.setSize(250, 50);
    }


    public void backButtonListener(TextButton backBtn) {
        backBtn.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                game.setScreen(new LoginScreen(game, gameType,0));
            }
        });
    }
}
