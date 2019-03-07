package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.rendering.BodyConquest;

public class RegisteringScreen extends DatabasesScreen implements Screen{

    private Texture register;
    private TextButton registerBtn;
    private Image registerImage;


    public RegisteringScreen(BodyConquest game) {
        super(game);
        registerImage = new Image(register);
        registerBtn = new TextButton("Register",skin);
        listenButton(registerBtn);
        settingSizes();
        settingPositions();
        adding();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void loadAssets() {
        super.loadAssets();
        manager.load(Assets.startRegister, Texture.class);
        manager.finishLoading();
    }

    @Override
    public void getAssets() {
        super.getAssets();
        register = manager.get(Assets.startRegister, Texture.class);
    }

    public void listenButton(TextButton registerBtn){
        registerBtn.addListener(new ClickListener(){
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

    @Override
    public void processRegistration() {
        super.processRegistration();
        game.setScreen(new  LoginScreen(game));
    }

    @Override
    public void settingPositions(){
        super.settingPositions();
        registerImage.setPosition(BodyConquest.V_WIDTH / 2.0f - registerImage.getWidth() / 2.0f,450.0f);
        registerBtn.setPosition(BodyConquest.V_WIDTH / 2.0f - registerBtn.getWidth() / 2.0f,50.0f);
    }
    @Override
    public void adding(){
        super.adding();
        stage.addActor(registerImage);
        stage.addActor(registerBtn);
    }

    @Override
    public void settingSizes(){
        super.settingSizes();
        registerBtn.setSize(250,50);
        registerImage.setSize(register.getWidth(),register.getHeight());
    }
}
