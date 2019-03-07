package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.rendering.BodyConquest;

public class StartScreen extends AbstractGameScreen implements Screen {

    private Texture title;
    private Texture register;
    private Texture login;
    private Rectangle registerBounds;
    private Rectangle loginBounds;

    //MyTextInputListener listener = new MyTextInputListener();
    //Gdx.input.getTextInput(listener, "Dialog Title", "Initial Textfield Value", "Hint Value");

    public StartScreen(BodyConquest game) {
        super(game);
        loadAssets();
        getAssets();
        setRectangles();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        game.batch.begin();
        game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
        game.batch.draw(title,BodyConquest.V_WIDTH / 2.0f - title.getWidth() / 2.0f, 450);
        game.batch.draw(register,BodyConquest.V_WIDTH / 2.0f - register.getWidth() / 3.0f, 300,register.getWidth()/1.5f,register.getHeight()/1.50f);
        game.batch.draw(login,BodyConquest.V_WIDTH / 2.0f - login.getWidth() / 3.0f, 200,login.getWidth()/1.5f,login.getHeight()/1.50f);
        checkPressed();
        game.batch.end();
    }

    @Override
    public void checkPressed() {
        super.checkPressed();

        if (Gdx.input.justTouched()) {

            if(loginBounds.contains(tmp.x,tmp.y)){
                playButtonSound();
                //System.out.println("Login touched");
                //dispose();
                //game.setScreen();
            }

            if(registerBounds.contains(tmp.x,tmp.y)){
                playButtonSound();
                //System.out.println("register touched");
                dispose();
                game.setScreen(new RegisteringScreen(game));
            }

        }



    }

    @Override
    public void loadAssets() {
        super.loadAssets();
        manager.load(Assets.startTitle, Texture.class);
        manager.load(Assets.startLogin, Texture.class);
        manager.load(Assets.startRegister, Texture.class);
        manager.finishLoading();
    }

    @Override
    public void getAssets() {
        super.getAssets();
        title = manager.get(Assets.startTitle, Texture.class);
        login = manager.get(Assets.startLogin, Texture.class);
        register = manager.get(Assets.startRegister, Texture.class);
    }

    @Override
    public void setRectangles() {
        super.setRectangles();
        loginBounds = new Rectangle(BodyConquest.V_WIDTH / 2.0f - login.getWidth() / 3.0f, 200,login.getWidth()/1.5f,login.getHeight()/1.50f);
        registerBounds = new Rectangle(BodyConquest.V_WIDTH / 2.0f - register.getWidth() / 3.0f, 300,register.getWidth()/1.5f,register.getHeight()/1.50f);
    }
}
