package com.cauldron.bodyconquest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

    public static Sprite backgroundSprite;
    private Viewport gameport;

    private Viewport viewport;
    private Camera camera;

    private Stage stage;


	//Logger log = Logger.getLogger(Game.class);

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("core/assets/badlogic.jpg");
        backgroundSprite = new Sprite(img);
        camera = new PerspectiveCamera();
        viewport = new FitViewport(800, 480, camera);
        stage = new Stage(new StretchViewport(800, 480));
	}

    public void renderBackground() {
            backgroundSprite.draw(batch);
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
		batch.begin();
		renderBackground();
        batch.draw(img, 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	@Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        //stage.getViewport().update(width, height, true);
    }

}
