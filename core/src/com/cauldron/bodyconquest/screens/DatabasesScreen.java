package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.rendering.BodyConquest;

public abstract class DatabasesScreen extends AbstractGameScreen {

    /* Variables */

    protected String textUsername;
    protected String textPassword;

    protected Texture password;
    protected Texture username;
    protected Skin skin;
    protected TextField txfUsername;
    protected TextField txfPassword;

    protected Image passwordImage;
    protected Image usernameImage;

    protected Stage stage;

    public DatabasesScreen(BodyConquest game) {
        super(game);
        loadAssets();
        getAssets();
        passwordImage = new Image(password);
        usernameImage = new Image(username);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        txfUsername = new TextField("",skin);
        txfPassword = new TextField("",skin);
        txfPassword.setPasswordMode(true);
        txfPassword.setPasswordCharacter('*');
        textUsername = "";
        textPassword = "";
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        game.batch.begin();
        stage.act(delta);
        stage.draw();
        game.batch.end();
    }

    @Override
    public void loadAssets() {
        super.loadAssets();
        manager.load(Assets.registerUsername, Texture.class);
        manager.load(Assets.registerPassword, Texture.class);
    }

    @Override
    public void getAssets() {
        super.getAssets();
        username = manager.get(Assets.registerUsername, Texture.class);
        password = manager.get(Assets.registerPassword, Texture.class);
    }

    public void processRegistration() {
        dispose();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    public void settingPositions(){
        passwordImage.setPosition(BodyConquest.V_WIDTH / 2.0f - passwordImage.getWidth() / 2.0f, 220.0f);
        usernameImage.setPosition(BodyConquest.V_WIDTH / 2.0f - usernameImage.getWidth() / 2.0f, 360.0f);
        txfUsername.setPosition(BodyConquest.V_WIDTH / 2.0f - txfUsername.getWidth() / 2.0f,280.0f);
        txfPassword.setPosition(BodyConquest.V_WIDTH / 2.0f - txfPassword.getWidth() / 2.0f,140.0f);
    }

    public void adding(){
        stage.addActor(passwordImage);
        stage.addActor(usernameImage);
        stage.addActor(txfUsername);
        stage.addActor(txfPassword);
    }

    public void settingSizes(){
        txfUsername.setSize(350,50);
        txfPassword.setSize(350,50);
        passwordImage.setSize(passwordImage.getWidth()/2.0f,passwordImage.getHeight()/2.0f);
        usernameImage.setSize(usernameImage.getWidth()/2.0f,usernameImage.getHeight()/2.0f);
    }
}
