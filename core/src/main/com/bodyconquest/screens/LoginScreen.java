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
 * The type Login screen.
 */
public class LoginScreen extends DatabasesScreen implements Screen {

    private Texture login;
    private TextButton loginBtn;
    private Image loginImage;

    /**
     * The Database manager.
     */
    DatabaseManager dbManager;

    /**
     * Instantiates a new Login screen.
     *
     * @param game the game
     */
    public LoginScreen(BodyConquest game) {
        super(game);
        loginImage = new Image(login);
        loginBtn = new TextButton("Login", skin);
        listenButton(loginBtn);
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
                game.setUsername(textUsername);
                game.getClient().setUsername(textUsername);
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
        game.setScreen(new MenuScreen(game));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void settingPositions() {
        super.settingPositions();
        loginImage.setPosition(BodyConquest.V_WIDTH / 2.0f - loginImage.getWidth() / 2.0f, 450.0f);
        loginBtn.setPosition(BodyConquest.V_WIDTH / 2.0f - loginBtn.getWidth() / 2.0f, 50.0f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void adding() {
        super.adding();
        stage.addActor(loginImage);
        stage.addActor(loginBtn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void settingSizes() {
        super.settingSizes();
        loginBtn.setSize(250, 50);
        loginImage.setSize(login.getWidth(), login.getHeight());
    }
}
