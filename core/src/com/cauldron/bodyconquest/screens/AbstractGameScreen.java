package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.OrthographicCamera;

public abstract class AbstractGameScreen implements Screen {

    protected BodyConquest game;
    protected Viewport viewport;
    protected Camera camera;
    protected AssetManager manager;

    protected Texture background;

    protected static final float TARGET_SCREEN_WIDTH = 800.0f;
    protected static final float TARGET_SCREEN_HEIGHT = 600.0f;
    private static final float MAX_SCENE_WIDTH = 1920.0f;
    private static final float MAX_SCENE_HEIGHT = 1080.0f;

    public AbstractGameScreen(BodyConquest game) {
        this.game = game;
        if (camera == null) {
            camera = new OrthographicCamera();
        }
        if (viewport == null) {
            viewport = new ExtendViewport(TARGET_SCREEN_WIDTH, TARGET_SCREEN_HEIGHT, MAX_SCENE_WIDTH, MAX_SCENE_HEIGHT, camera);
        }
        viewport.apply();
        manager = new AssetManager();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        viewport.apply();
//        game.batch.setProjectionMatrix(camera.combined);


    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height, true);

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

    }

    public void checkPressed(){
        Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(tmp);
    }

    public void playButtonSound() {
        game.audioPlayer.playSFX("button_click");
    }
}
