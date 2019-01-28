package com.cauldron.bodyconquest.rendering;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;

/*
The core of the rendering engine, hosts the sprite batch for the current screen,
calls all act methods for actors in stages of children and calls all render functions
for implementing screens.
 */
public class BodyConquest extends Game {

	//private static final Logger log = Logger.getLogger(MyGdxGame.class);

	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 600;

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
	}

	@Override
	public void render () {
		super.render();
	}

  @Override
  public void dispose() {
    batch.dispose();
    img.dispose();
    stage.dispose();
  }
}
