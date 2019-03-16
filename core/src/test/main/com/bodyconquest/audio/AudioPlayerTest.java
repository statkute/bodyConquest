package main.com.bodyconquest.audio;

import main.com.bodyconquest.constants.Assets;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;


public class AudioPlayerTest {
    AudioPlayer audioPlayer = new AudioPlayer();

    @Test
    public void toggleMutedSFX() {
        audioPlayer.toggleMutedSFX();
        assertTrue(audioPlayer.getMutedSFX());
        audioPlayer.toggleMutedSFX();
        assertFalse(audioPlayer.getMutedSFX());
    }

    @Test
    public void toggleMutedMusic() {
        audioPlayer.toggleMutedMusic();
        assertTrue(audioPlayer.getMutedMusic());
        audioPlayer.toggleMutedMusic();
        assertFalse(audioPlayer.getMutedMusic());
    }

    @Test
    public void loadSFX() throws Exception{
    }

    @Test
    public void loadMusic() {
    }

    @Test
    public void playSFX() {
    }

    @Test
    public void playMusicOnce() {
    }

    @Test
    public void playMusicLoop() {
    }

    @Test
    public void playMusic() {
    }

    @Test
    public void changeMasterVolume() throws Exception{
        Field master = audioPlayer.getClass().getDeclaredField("MASTER_VOLUME");
        master.setAccessible(true);
        audioPlayer.changeMasterVolume(2f);
        assertEquals(master.get(audioPlayer), 2f);
    }

    @Test
    public void changeSFXVolume() throws Exception{
        Field sfx = audioPlayer.getClass().getDeclaredField("SFX_VOLUME");
        sfx.setAccessible(true);
        audioPlayer.changeSFXVolume(0.5f);
        assertEquals(sfx.get(audioPlayer), 0.5f);
    }

    @Test
    public void changeMusicVolume() throws Exception{
        Field music = audioPlayer.getClass().getDeclaredField("MUSIC_VOLUME");
        music.setAccessible(true);
        audioPlayer.changeMusicVolume(0.5f);
        assertEquals(music.get(audioPlayer), 0.5f);
    }

    @Test
    public void getMutedSFX() {
        boolean m = audioPlayer.getMutedSFX();
        assertFalse(m);
    }

    @Test
    public void getMutedMusic() {
        boolean m = audioPlayer.getMutedMusic();
        assertFalse(m);
    }
}