package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.database.DatabaseManager;
import com.cauldron.bodyconquest.rendering.BodyConquest;

public class LoginScreen extends DatabasesScreen implements Screen {

    private Texture login;
    private TextButton loginBtn;
    private Image loginImage;

    DatabaseManager dbManager;

    public LoginScreen(BodyConquest game) {
        super(game);
        loginImage = new Image(login);
        loginBtn = new TextButton("Login",skin);
        listenButton(loginBtn);
        settingSizes();
        settingPositions();
        adding();
        this.dbManager = new DatabaseManager();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void loadAssets() {
        super.loadAssets();
        manager.load(Assets.startLogin, Texture.class);
        manager.finishLoading();
    }

    @Override
    public void getAssets() {
        super.getAssets();
        login = manager.get(Assets.startLogin, Texture.class);
    }

    public void listenButton(TextButton loginBtn){
        loginBtn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                loginBtn.setText("You logged in!");
                textPassword = txfPassword.getText();
                textUsername = txfUsername.getText();
                System.out.println(textUsername + " " + textPassword);
                processRegistration();
            }
        });
    }

    @Override
    public void processRegistration() {
        super.processRegistration();
        game.setScreen( new MenuScreen(game, textUsername));
    }

    @Override
    public void settingPositions(){
        super.settingPositions();
        loginImage.setPosition(BodyConquest.V_WIDTH / 2.0f - loginImage.getWidth() / 2.0f,450.0f);
        loginBtn.setPosition(BodyConquest.V_WIDTH / 2.0f - loginBtn.getWidth() / 2.0f,50.0f);
    }

    @Override
    public void adding(){
        super.adding();
        stage.addActor(loginImage);
        stage.addActor(loginBtn);
    }

    @Override
    public void settingSizes(){
        super.settingSizes();
        loginBtn.setSize(250,50);
        loginImage.setSize(login.getWidth(),login.getHeight());
    }
}
