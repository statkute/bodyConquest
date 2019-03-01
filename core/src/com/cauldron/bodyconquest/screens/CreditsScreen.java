package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class CreditsScreen implements Screen {

  private BodyConquest game;
  private Texture backButton;
  private Texture background;
  private Texture header;
  private Texture alexandru;
  private Texture augustas;
  private Texture brandon;
  private Texture gintare;
  private Texture paul;
  private Rectangle backBounds;

  OrthographicCamera camera;

  public CreditsScreen(BodyConquest game) {
    this.game = game;
    camera = new OrthographicCamera();
    camera.setToOrtho(false, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);

    background = new Texture("core/assets/background_new.png");
    header = new Texture("core/assets/creditsheader_new_big.png");
    backButton = new Texture("core/assets/back_new_big.png");
    alexandru = new Texture("core/assets/alexandru_new_big.png");
    augustas = new Texture("core/assets/augustas_new_big.png");
    brandon = new Texture("core/assets/brandon_new_big.png");
    gintare = new Texture("core/assets/gintare_new_big.png");
    paul = new Texture("core/assets/paul_new_big.png");

    backBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2,
            60 * BodyConquest.scaleRatio,
            backButton.getWidth(),
            backButton.getHeight());
  }

  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.update();
    game.batch.setProjectionMatrix(camera.combined);
    game.batch.begin();

    game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
    game.batch.draw(header, BodyConquest.V_WIDTH / 2 - header.getWidth() / 2, 450 * BodyConquest.scaleRatio);

    game.batch.draw(alexandru, BodyConquest.V_WIDTH / 2 - alexandru.getWidth() / 2, 350 * BodyConquest.scaleRatio);
    game.batch.draw(augustas, BodyConquest.V_WIDTH / 2 - augustas.getWidth() / 2, 300 * BodyConquest.scaleRatio);
    game.batch.draw(brandon, BodyConquest.V_WIDTH / 2 - brandon.getWidth() / 2, 250 * BodyConquest.scaleRatio);
    game.batch.draw(gintare, BodyConquest.V_WIDTH / 2 - gintare.getWidth() / 2, 200 * BodyConquest.scaleRatio);
    game.batch.draw(paul, BodyConquest.V_WIDTH / 2 - paul.getWidth() / 2, 150 * BodyConquest.scaleRatio);

    game.batch.draw(backButton, BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2, 60 * BodyConquest.scaleRatio);

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
    game.audioPlayer.playSFX("button_click");
  }
}
