package com.cauldron.bodyconquest.rendering;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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


	// so that we could add text
	public BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
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
		/*if (Gdx.input.isKeyPressed(Keys.ESCAPE)) Gdx.app.exit();
		//if (Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT) && Gdx.input.isKeyJustPressed(Keys.M)) AudioPlayer.toggleMute();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		//batch.begin();
		float delta = Gdx.graphics.getDeltaTime();
		posX += delta * speed;
		//log.info(posX + img.getWidth());
		//aslog.info(Gdx.graphics.getWidth());
		if((posX + img.getWidth()) >= Gdx.graphics.getWidth() || (posX <= 0)) {
			speed = speed * -1;
			//AudioPlayer.play("bounce");
		}
		fpsLogger.log();

		//batch.draw(img, posX, 0);
		//batch.end();*/
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		stage.dispose();
	}

}
