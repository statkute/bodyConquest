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

/**
 * The type Registering screen.
 */
public class RegisteringScreen extends DatabasesScreen implements Screen {

    private Texture register;
    private TextButton registerBtn;
    private Image registerImage;

    /**
     * The Database manager.
     */
    DatabaseManager dbManager;

    /**
     * Instantiates a new Registering screen.
     *
     * @param game the game
     */
    public RegisteringScreen(BodyConquest game) {
        super(game);
        registerImage = new Image(register);
        registerBtn = new TextButton("Register", skin);
        listenButton(registerBtn);
        settingSizes();
        settingPositions();
        adding();
        this.dbManager = new DatabaseManager();
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
        game.setScreen(new LoginScreen(game));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void settingPositions() {
        super.settingPositions();
        registerImage.setPosition(BodyConquest.V_WIDTH / 2.0f - registerImage.getWidth() / 2.0f, 450.0f);
        registerBtn.setPosition(BodyConquest.V_WIDTH / 2.0f - registerBtn.getWidth() / 2.0f, 50.0f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void adding() {
        super.adding();
        stage.addActor(registerImage);
        stage.addActor(registerBtn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void settingSizes() {
        super.settingSizes();
        registerBtn.setSize(250, 50);
        registerImage.setSize(register.getWidth(), register.getHeight());
    }
}
