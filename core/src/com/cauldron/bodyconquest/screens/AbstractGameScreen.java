package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.OrthographicCamera;

public abstract class AbstractGameScreen implements Screen {

    protected BodyConquest game;
    protected Viewport viewport;
    protected Camera camera;
    protected AssetManager manager;

    protected Texture background;

    protected Vector3 tmp;

    protected static final float TARGET_SCREEN_WIDTH = Gdx.graphics.getWidth();
    protected static final float TARGET_SCREEN_HEIGHT = Gdx.graphics.getHeight();

    public AbstractGameScreen(BodyConquest game) {
        this.game = game;
        if (camera == null) {
            camera = new OrthographicCamera();
            ((OrthographicCamera) camera).setToOrtho(false, 800, 600);
        }
        if (viewport == null) {
            viewport = new FitViewport(BodyConquest.V_WIDTH,BodyConquest.V_HEIGHT,camera);
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
        manager.dispose();

    }

    public void checkPressed(){
        tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(tmp);
    }

    public void playButtonSound() {
        game.audioPlayer.playSFX("button_click");
    }
}
