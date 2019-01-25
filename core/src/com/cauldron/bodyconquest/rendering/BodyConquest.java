package com.cauldron.bodyconquest.rendering;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;

public class BodyConquest extends Game {

	//private static final Logger log = Logger.getLogger(MyGdxGame.class);

	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;

	private FPSLogger fpsLogger = new FPSLogger();
	public SpriteBatch batch;
	private Texture img;

	private Stage stage;

	private boolean loaded = false;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new EncounterScreen(this));
		/*if(!loaded) {
			//AudioPlayer.init();
			//AudioPlayer.load("/Bounce SFX.mp3", "bounce");
		}*/


		/*batch = new SpriteBatch();
		//img = new Texture("core/assets/badlogic.jpg");
		img = new Texture("core/assets/Default Sprite (Green).png");

		stage = new Stage(new ScreenViewport());
		*//*Texture spriteTexture = new Texture("core/assets/sprite.jpg");
		Image spriteImage = new Image(spriteTexture);
		spriteImage.setSize(100, 100);
		spriteImage.setPosition(
				Gdx.graphics.getWidth() / 3 - spriteImage.getWidth() / 2,
				Gdx.graphics.getHeight() * 2 / 3 - spriteImage.getHeight() / 2);
		stage.addActor(spriteImage);*//*
		Bacteria bct1 = new Bacteria();
		stage.addActor(bct1);*/
	}

	private float posX = 0;
	private float speed = 100;

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		stage.dispose();
	}

}
