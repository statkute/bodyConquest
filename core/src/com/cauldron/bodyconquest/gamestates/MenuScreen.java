package com.cauldron.bodyconquest.gamestates;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.Texture;

public class MenuScreen implements Screen {


    private BodyConquest game;
    private Texture background;
    private Texture playButtonMultiplayer;
    private Texture playButtonSinglePlayer;
    private Texture settingsButton;
    private Texture creditsButton;



    OrthographicCamera camera;

    public MenuScreen(BodyConquest game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,480 );

//        background = new Texture();
//        playButtonMultiplayer = new Texture();
//        playButtonSinglePlayer = new Texture();
//        settingsButton = new Texture();
//        creditsButton = new Texture();

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

//        game.batch.begin();
//        game.batch.draw(background,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        game.batch.draw(playButtonMultiplayer); // playMulti
//        game.batch.draw(playButtonSinglePlayer); // playMulti
//        game.batch.draw(settingsButton); // playMulti
//        game.batch.draw(creditsButton); // playMulti

        // add bounds to check if pressed
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.dispose();
        background.dispose();
        playButtonSinglePlayer.dispose();
        playButtonMultiplayer.dispose();
        settingsButton.dispose();
        creditsButton.dispose();

    }
}
