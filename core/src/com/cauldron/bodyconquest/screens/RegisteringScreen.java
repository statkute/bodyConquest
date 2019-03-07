package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.rendering.BodyConquest;

public class RegisteringScreen extends AbstractGameScreen implements Screen, Input.TextInputListener {

    String text;

    private Texture password;
    private Texture username;
    private Texture register;

    //MyTextInputListener listener;

    public RegisteringScreen(BodyConquest game) {
        super(game);
        text = "";
        loadAssets();
        getAssets();
        //listener = new MyTextInputListener();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        game.batch.begin();
        game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
        game.batch.draw(register,BodyConquest.V_WIDTH / 2.0f - register.getWidth() / 2.0f, 450);
        game.batch.draw(username,BodyConquest.V_WIDTH / 2.0f - username.getWidth() / 4.0f, 320,username.getWidth()/2.0f,username.getHeight()/2.0f);
        game.batch.draw(password,BodyConquest.V_WIDTH / 2.0f - password.getWidth() / 4.0f, 190,password.getWidth()/2.0f,password.getHeight()/2.0f);
        //Gdx.input.getTextInput(listener, "Register", "Initial Textfield Value", "Hint Value");
        game.batch.end();
    }

    @Override
    public void loadAssets() {
        super.loadAssets();
        manager.load(Assets.registerUsername, Texture.class);
        manager.load(Assets.registerPassword, Texture.class);
        manager.load(Assets.startRegister, Texture.class);
        manager.finishLoading();
    }

    @Override
    public void getAssets() {
        super.getAssets();
        username = manager.get(Assets.registerUsername, Texture.class);
        password = manager.get(Assets.registerPassword, Texture.class);
        register = manager.get(Assets.startRegister, Texture.class);
    }

    @Override
    public void input(String text) {

        this.text = text;

    }

    @Override
    public void canceled() {

        this.text = "";

    }
}
