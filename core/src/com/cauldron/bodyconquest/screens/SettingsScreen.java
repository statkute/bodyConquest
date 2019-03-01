package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.cauldron.bodyconquest.rendering.BodyConquest;

import java.io.IOException;

public class SettingsScreen implements Screen {

  private BodyConquest game;
  private Texture background;
  private Texture header;
  private Texture backButton;
  private Texture soundHeader;
  private Texture musicHeader;
  private Texture soundOn;
  private Texture soundOff;
  private Texture musicOn;
  private Texture musicOff;
  private Rectangle soundBounds;
  private Rectangle musicBounds;
  private Rectangle soundToggleBounds;
  private Rectangle musicToggleBounds;
  private Rectangle backBounds;
  OrthographicCamera camera;

  public SettingsScreen(BodyConquest game) {
    this.game = game;
    camera = new OrthographicCamera();
    camera.setToOrtho(false, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
    background = new Texture("core/assets/background_new.png");
    header = new Texture("core/assets/settingsheader_new.png");
    soundHeader = new Texture("core/assets/sound.png");
    musicHeader = new Texture("core/assets/music.png");
    soundOn = new Texture("core/assets/on.png");
    soundOff = new Texture("core/assets/off.png");
    musicOn = new Texture("core/assets/on.png");
    musicOff = new Texture("core/assets/off.png");
    backButton = new Texture("core/assets/back_new.png");

    backBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2,
            60,
            backButton.getWidth(),
            backButton.getHeight());

    soundBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 5, 300, soundHeader.getWidth(), soundHeader.getHeight());

    musicBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 5, 240, musicHeader.getWidth(), musicHeader.getHeight());

    if (game.audioPlayer.getMutedSFX()) {
      soundToggleBounds =
          new Rectangle(
              BodyConquest.V_WIDTH / 5 * 4 - soundOff.getWidth(),
              300,
              soundOff.getWidth(),
              soundOff.getHeight());
    } else {
      soundToggleBounds =
          new Rectangle(
              BodyConquest.V_WIDTH / 5 * 4 - soundOn.getWidth(),
              300,
              soundOn.getWidth(),
              soundOn.getHeight());
    }

    if (game.audioPlayer.getMutedMusic()) {
      musicToggleBounds =
          new Rectangle(
              BodyConquest.V_WIDTH / 5 * 4 - musicOff.getWidth(),
              240,
              musicOff.getWidth(),
              musicOff.getHeight());
    } else {
      musicToggleBounds =
          new Rectangle(
              BodyConquest.V_WIDTH / 5 * 4 - musicOn.getWidth(),
              240,
              musicOn.getWidth(),
              musicOn.getHeight());
    }
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
    game.batch.draw(header, BodyConquest.V_WIDTH / 2 - header.getWidth() / 2, 450);

    game.batch.draw(soundHeader, BodyConquest.V_WIDTH / 5, 300);

    if (game.audioPlayer.getMutedSFX()) {
      game.batch.draw(soundOff, BodyConquest.V_WIDTH / 5 * 4 - soundOff.getWidth(), 300);
    } else {
      game.batch.draw(soundOn, BodyConquest.V_WIDTH / 5 * 4 - soundOn.getWidth(), 300);
    }

    game.batch.draw(musicHeader, BodyConquest.V_WIDTH / 5, 240);

    if (game.audioPlayer.getMutedMusic()) {
      game.batch.draw(musicOff, BodyConquest.V_WIDTH / 5 * 4 - musicOff.getWidth(), 240);
    } else {
      game.batch.draw(musicOn, BodyConquest.V_WIDTH / 5 * 4 - musicOn.getWidth(), 240);
    }

    game.batch.draw(backButton, BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2, 60);

    try {
      checkIfPressed();
    } catch (IOException e) {
      e.printStackTrace();
    }

    game.batch.end();
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

    background.dispose();
    header.dispose();
  }

  public void checkIfPressed() throws IOException {

    Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    camera.unproject(tmp);

    if (Gdx.input.justTouched()) {
      if (backBounds.contains(tmp.x, tmp.y)) {
        System.out.println("back pressed");
        playButtonSound();
        game.setScreen(new MenuScreen(game));
        dispose();
      }

      if(soundBounds.contains(tmp.x, tmp.y) || soundToggleBounds.contains(tmp.x, tmp.y)){
        game.audioPlayer.toggleMutedSFX();
        playButtonSound();
        if (game.audioPlayer.getMutedSFX()) {
          game.batch.draw(soundOff, BodyConquest.V_WIDTH / 5 * 4 - soundOff.getWidth(), 300);
        } else {
          game.batch.draw(soundOn, BodyConquest.V_WIDTH / 5 * 4 - soundOn.getWidth(), 300);
        }
      }

      if(musicBounds.contains(tmp.x, tmp.y) || musicToggleBounds.contains(tmp.x, tmp.y)){
        game.audioPlayer.toggleMutedMusic();
        playButtonSound();
        if (game.audioPlayer.getMutedMusic()) {
          game.batch.draw(musicOff, BodyConquest.V_WIDTH / 5 * 4 - musicOff.getWidth(), 240);
        } else {
          game.batch.draw(musicOn, BodyConquest.V_WIDTH / 5 * 4 - musicOn.getWidth(), 240);
        }
      }
    }
  }

  public void playButtonSound() {
    game.audioPlayer.playSFX("button_click");
  }
}