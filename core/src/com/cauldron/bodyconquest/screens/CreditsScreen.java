package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class CreditsScreen implements Screen {

  private BodyConquest game;
  private Texture backButton;
  private Texture background;
  private Rectangle backBounds;

  OrthographicCamera camera;

  public CreditsScreen(BodyConquest game) {
    this.game = game;
    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 600);

    background = new Texture("core/assets/logocredits.png");
    backButton = new Texture("core/assets/back.png");
    backBounds = new Rectangle(550, 30, backButton.getWidth(), backButton.getHeight());
  }

  @Override
  public void show() {}

  @Override
  public void render(float delta) {

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.update();
    game.batch.setProjectionMatrix(camera.combined);
    game.batch.begin();
    game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
    game.batch.draw(backButton, 550, 30);
    checkPressed();
    game.batch.end();
  }

  public void checkPressed() {

    if (Gdx.input.isTouched()) {
      Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(tmp);
      if (backBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        game.setScreen(new MenuScreen(game));
        dispose();
      }
    }
  }

  @Override
  public void resize(int width, int height) {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    backButton.dispose();
    background.dispose();
  }
  public void playButtonSound(){
    Constants.buttonSound.play();
  }
}
