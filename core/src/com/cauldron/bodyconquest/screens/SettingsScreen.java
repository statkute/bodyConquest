//package com.cauldron.bodyconquest.screens;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector3;
//import com.cauldron.bodyconquest.constants.Assets;
//import com.cauldron.bodyconquest.rendering.BodyConquest;
//
//import java.io.IOException;
//
//public class SettingsScreen extends AbstractGameScreen implements Screen {
//
//  private Texture header;
//  private Texture backButton;
//  private Texture soundHeader;
//  private Texture musicHeader;
//  private Texture soundOn;
//  private Texture soundOff;
//  private Texture musicOn;
//  private Texture musicOff;
//  private Rectangle soundBounds;
//  private Rectangle musicBounds;
//  private Rectangle soundToggleBounds;
//  private Rectangle musicToggleBounds;
//  private Rectangle backBounds;
//
//  public SettingsScreen(BodyConquest game) {
//    super(game);
//    loadAssets();
//    getAssets();
//    setRectangles();
//  }
//
//  @Override
//  public void render(float delta) {
//
//    super.render(delta);
//
//    game.batch.begin();
//    game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
//    game.batch.draw(
//        header, BodyConquest.V_WIDTH / 2 - header.getWidth() / 2, 450 * BodyConquest.scaleRatio);
//
//    game.batch.draw(soundHeader, BodyConquest.V_WIDTH / 5, 300 * BodyConquest.scaleRatio);
//
//    if (game.audioPlayer.getMutedSFX()) {
//      game.batch.draw(
//          soundOff,
//          BodyConquest.V_WIDTH / 5 * 4 - soundOff.getWidth(),
//          300 * BodyConquest.scaleRatio);
//    } else {
//      game.batch.draw(
//          soundOn,
//          BodyConquest.V_WIDTH / 5 * 4 - soundOn.getWidth(),
//          300 * BodyConquest.scaleRatio);
//    }
//
//    game.batch.draw(musicHeader, BodyConquest.V_WIDTH / 5, 240 * BodyConquest.scaleRatio);
//
//    if (game.audioPlayer.getMutedMusic()) {
//      game.batch.draw(
//          musicOff,
//          BodyConquest.V_WIDTH / 5 * 4 - musicOff.getWidth(),
//          240 * BodyConquest.scaleRatio);
//    } else {
//      game.batch.draw(
//          musicOn,
//          BodyConquest.V_WIDTH / 5 * 4 - musicOn.getWidth(),
//          240 * BodyConquest.scaleRatio);
//    }
//
//    game.batch.draw(
//        backButton,
//        BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2,
//        60 * BodyConquest.scaleRatio);
//
//    checkPressed();
//    game.batch.end();
//  }
//
//  @Override
//  public void checkPressed() {
//
//    super.checkPressed();
//
//    if (Gdx.input.justTouched()) {
//      if (backBounds.contains(tmp.x, tmp.y)) {
//        System.out.println("back pressed");
//        playButtonSound();
//        game.setScreen(new MenuScreen(game));
//        dispose();
//      }
//
//      if (soundBounds.contains(tmp.x, tmp.y) || soundToggleBounds.contains(tmp.x, tmp.y)) {
//        game.audioPlayer.toggleMutedSFX();
//        playButtonSound();
//        if (game.audioPlayer.getMutedSFX()) {
//          game.batch.draw(
//              soundOff,
//              BodyConquest.V_WIDTH / 5 * 4 - soundOff.getWidth(),
//              300 * BodyConquest.scaleRatio);
//        } else {
//          game.batch.draw(
//              soundOn,
//              BodyConquest.V_WIDTH / 5 * 4 - soundOn.getWidth(),
//              300 * BodyConquest.scaleRatio);
//        }
//      }
//
//      if (musicBounds.contains(tmp.x, tmp.y) || musicToggleBounds.contains(tmp.x, tmp.y)) {
//        game.audioPlayer.toggleMutedMusic();
//        playButtonSound();
//        if (game.audioPlayer.getMutedMusic()) {
//          game.batch.draw(
//              musicOff,
//              BodyConquest.V_WIDTH / 5 * 4 - musicOff.getWidth(),
//              240 * BodyConquest.scaleRatio);
//        } else {
//          game.batch.draw(
//              musicOn,
//              BodyConquest.V_WIDTH / 5 * 4 - musicOn.getWidth(),
//              240 * BodyConquest.scaleRatio);
//        }
//      }
//    }
//  }
//
//  @Override
//  public void loadAssets() {
//    super.loadAssets();
//    manager.load(Assets.settingsHeader, Texture.class);
//    manager.load(Assets.settingsSoundHeader, Texture.class);
//    manager.load(Assets.settingsSoundOff, Texture.class);
//    manager.load(Assets.settingsSoundOn, Texture.class);
//    manager.load(Assets.settingsMusicHeader, Texture.class);
//    manager.load(Assets.hostBack, Texture.class);
//    manager.load(Assets.settingsMusicOff, Texture.class);
//    manager.load(Assets.settingsMusicOn, Texture.class);
//    manager.finishLoading();
//  }
//
//  @Override
//  public void getAssets() {
//    super.getAssets();
//    header = manager.get(Assets.settingsHeader, Texture.class);
//    soundHeader = manager.get(Assets.settingsSoundHeader, Texture.class);
//    musicHeader = manager.get(Assets.settingsMusicHeader, Texture.class);
//    soundOn = manager.get(Assets.settingsSoundOn, Texture.class);
//    soundOff = manager.get(Assets.settingsSoundOff, Texture.class);
//    musicOn = manager.get(Assets.settingsMusicOn, Texture.class);
//    backButton = manager.get(Assets.hostBack, Texture.class);
//    musicOff = manager.get(Assets.settingsMusicOff, Texture.class);
//  }
//
//  @Override
//  public void setRectangles() {
//    super.setRectangles();
//    backBounds =
//        new Rectangle(
//            BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2,
//            60 * BodyConquest.scaleRatio,
//            backButton.getWidth(),
//            backButton.getHeight());
//
//    soundBounds =
//        new Rectangle(
//            BodyConquest.V_WIDTH / 5, 300 * BodyConquest.scaleRatio, soundHeader.getWidth(), soundHeader.getHeight());
//
//    musicBounds =
//        new Rectangle(
//            BodyConquest.V_WIDTH / 5, 240 * BodyConquest.scaleRatio, musicHeader.getWidth(), musicHeader.getHeight());
//
//    if (game.audioPlayer.getMutedSFX()) {
//      soundToggleBounds =
//          new Rectangle(
//              BodyConquest.V_WIDTH / 5 * 4 - soundOff.getWidth(),
//              300 * BodyConquest.scaleRatio,
//              soundOff.getWidth(),
//              soundOff.getHeight());
//    } else {
//      soundToggleBounds =
//          new Rectangle(
//              BodyConquest.V_WIDTH / 5 * 4 - soundOn.getWidth(),
//              300 * BodyConquest.scaleRatio,
//              soundOn.getWidth(),
//              soundOn.getHeight());
//    }
//
//    if (game.audioPlayer.getMutedMusic()) {
//      musicToggleBounds =
//          new Rectangle(
//              BodyConquest.V_WIDTH / 5 * 4 - musicOff.getWidth(),
//              240 * BodyConquest.scaleRatio,
//              musicOff.getWidth(),
//              musicOff.getHeight());
//    } else {
//      musicToggleBounds =
//          new Rectangle(
//              BodyConquest.V_WIDTH / 5 * 4 - musicOn.getWidth(),
//              240 * BodyConquest.scaleRatio,
//              musicOn.getWidth(),
//              musicOn.getHeight());
//    }
//  }
//}
