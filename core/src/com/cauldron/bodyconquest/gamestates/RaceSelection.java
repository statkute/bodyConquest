package com.cauldron.bodyconquest.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class RaceSelection implements Screen {

    private BodyConquest game;
    private Texture background;

    OrthographicCamera camera;


    private Texture disease1;
    private Texture disease2;
    private Texture disease3;
    private Texture selectionFrame1;
    private Texture selectionFrame2;


    public RaceSelection(BodyConquest game){

        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        background = new Texture("core/assets/logocredits.png");
        disease1 = new Texture("core/assets/back.png");
        disease2 = new Texture("core/assets/back.png");
        disease3 =  new Texture("core/assets/back.png");
        selectionFrame1 = new Texture("core/assets/back.png");
        selectionFrame2 = new Texture("core/assets/back.png");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

        background.dispose();
        disease1.dispose();
        disease2.dispose();
        disease3.dispose();
        selectionFrame1.dispose();
        selectionFrame2.dispose();

    }
}
