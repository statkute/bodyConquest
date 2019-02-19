package com.cauldron.bodyconquest.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

/* Controls audio within the game*/
public class AudioPlayer {

  public static float MASTER_VOLUME = 1.0f;
  public static float SFX_VOLUME = 1.0f;
  public static float MUSIC_VOLUME = 1.0f;

  private final float MUTED_VOLUME = 0.0f;

  private HashMap<String, Sound> soundFX;
  private HashMap<String, Music> music;

  private Music currentMusic;

  private boolean mutedSFX;
  private boolean mutedMusic;
  private boolean muted;

  public AudioPlayer(){
    soundFX = new HashMap<String, Sound>();
    music = new HashMap<String, Music>();

    muted = false;
    mutedSFX = false;
    mutedMusic = false;

    currentMusic = null;
  }

  public void toggleMuted() {

    if(muted) {
      muted = false;
      if(currentMusic != null) currentMusic.setVolume(MUSIC_VOLUME);
    } else {
      muted = true;
      if(currentMusic != null) currentMusic.setVolume(MUTED_VOLUME);
    }
  }

  public void toggleMutedSFX() {
    if(mutedSFX) {
      mutedSFX = false;
    } else {
      mutedSFX = true;
    }
  }

  public void toggleMutedMusic() {
    if(mutedMusic) {
      mutedMusic = false;
      if(!muted && currentMusic != null) currentMusic.setVolume(MUSIC_VOLUME);
    } else {
      mutedMusic = true;
      if(currentMusic != null) currentMusic.setVolume(MUTED_VOLUME);
    }
  }

  public void loadSFX(String name, String path) {
    soundFX.put(name, Gdx.audio.newSound(Gdx.files.internal(path)));
  }

  /**
   * Load in any (background) music files that will be used in the game.
   * @param name The string used to refer to a particular music file to play it later on.
   * @param path The path to the location of the audio file to be loaded in.
   */
  public void loadMusic(String name, String path) {
    music.put(name, Gdx.audio.newMusic(Gdx.files.internal(path)));
  }

  public void playSFX(String name) {
    if(muted) {
      soundFX.get(name).play(MUTED_VOLUME * MASTER_VOLUME);
    } else {
      soundFX.get(name).play(SFX_VOLUME * MASTER_VOLUME);
    }
  }

  public void playMusicOnce(String name) {
    playMusic(name, false);
  }

  public void playMusicLoop(String name) {
    playMusic(name, true);
  }

  public void playMusic(String name, boolean loop) {
    if(currentMusic != null) currentMusic.stop();
    currentMusic = music.get(name);
    if(muted) {
      currentMusic.setVolume(MUTED_VOLUME);
    } else {
      currentMusic.setVolume(MUSIC_VOLUME * MASTER_VOLUME);
    }
    currentMusic.setLooping(loop);
    currentMusic.play();
  }

  public void changeMasterVolume(float volume) {
    if(volume > 1.0f || volume < 0.0f) {
      MASTER_VOLUME = volume;
    }
  }

  public void changeSFXVolume(float volume) {
    if(volume > 1.0f || volume < 0.0f) return;
    SFX_VOLUME = volume;
  }

  public void changeMusicVolume(float volume) {
    if(volume > 1.0f || volume < 0.0f) return;
    MUSIC_VOLUME = volume;
    if(!muted) currentMusic.setVolume(MUSIC_VOLUME * MASTER_VOLUME);
  }

}
