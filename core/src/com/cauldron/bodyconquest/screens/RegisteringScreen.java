package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.rendering.BodyConquest;

public class RegisteringScreen extends AbstractGameScreen implements Screen{

    String textUsername;
    String textPassword;

    private Texture password;
    private Texture username;
    private Texture register;
    private Skin skin;
    private TextButton registerBtn;
    private TextField txfUsername;
    private TextField txfPassword;

    private Image passwordImage;
    private Image usernameImage;
    private Image registerImage;

    private Stage stage;


    public RegisteringScreen(BodyConquest game) {
        super(game);
        loadAssets();
        getAssets();
        passwordImage = new Image(password);
        usernameImage = new Image(username);
        registerImage = new Image(register);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        txfUsername = new TextField("",skin);
        txfPassword = new TextField("",skin);
        registerBtn = new TextButton("Register",skin);
        textUsername = "";
        textPassword = "";
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        settingSizes();
        settingPositions();
        adding();
        listenButton(registerBtn);
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

    public void listenButton(TextButton registerBtn){

        registerBtn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                registerBtn.setText("You registered!");
                textPassword = txfPassword.getText();
                textUsername = txfUsername.getText();
                System.out.println(textUsername + " " + textPassword);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                processRegistration();

            }
        });

    }

    private void processRegistration() {
        dispose();
        //game.setScreen(New LoginScreen(game));
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    public void settingPositions(){
        passwordImage.setPosition(BodyConquest.V_WIDTH / 2.0f - passwordImage.getWidth() / 2.0f, 220.0f);
        usernameImage.setPosition(BodyConquest.V_WIDTH / 2.0f - usernameImage.getWidth() / 2.0f, 360.0f);
        registerImage.setPosition(BodyConquest.V_WIDTH / 2.0f - registerImage.getWidth() / 2.0f,450.0f);
        registerBtn.setPosition(BodyConquest.V_WIDTH / 2.0f - registerBtn.getWidth() / 2.0f,50.0f);
        txfUsername.setPosition(BodyConquest.V_WIDTH / 2.0f - txfUsername.getWidth() / 2.0f,280.0f);
        txfPassword.setPosition(BodyConquest.V_WIDTH / 2.0f - txfPassword.getWidth() / 2.0f,140.0f);
    }

    public void adding(){
        stage.addActor(passwordImage);
        stage.addActor(usernameImage);
        stage.addActor(registerImage);
        stage.addActor(txfUsername);
        stage.addActor(registerBtn);
        stage.addActor(txfPassword);
    }

    public void settingSizes(){

        registerBtn.setSize(250,50);
        txfUsername.setSize(350,50);
        txfPassword.setSize(350,50);
        passwordImage.setSize(passwordImage.getWidth()/2.0f,passwordImage.getHeight()/2.0f);
        usernameImage.setSize(usernameImage.getWidth()/2.0f,usernameImage.getHeight()/2.0f);
        registerImage.setSize(register.getWidth(),register.getHeight());

    }
}
