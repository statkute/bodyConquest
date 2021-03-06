package main.com.bodyconquest.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

/**
 * Controls audio within the game
 */
public class AudioPlayer {

    /**
     * The the max volume for all sounds inside the game. (Initialised to {@value MASTER_VOLUME})
     */
    private static float MASTER_VOLUME = 1.0f;
    /**
     * The volume for all sound FX inside the game. (Initialised to {@value SFX_VOLUME})
     */
    private static float SFX_VOLUME = 1.0f;
    /**
     * The volume for all the music inside the game. (Initialised to )
     */
    private float MUSIC_VOLUME = 1.0f;

    /**
     * The volume to set for muted sounds.
     */
    private final float MUTED_VOLUME = 0.0f;

    /**
     * The HashMap that stores all the loaded sound FX.
     */
    private HashMap<String, Sound> soundFX;
    /**
     * The HashMap that stored all the loaded music.
     */
    private HashMap<String, Music> music;

    /**
     * Stores the music that is currently playing. (Null if no music has been set to play yet)
     */
    private Music currentMusic;

    /**
     * checks if SFX sounds are muted
     */
    private boolean mutedSFX;

    /**
     * checks if Music is muted
     */
    private boolean mutedMusic;

    /**
     * checks if everything is muted
     */
    private boolean muted;

    /**
     * Constructor.
     */
    public AudioPlayer() {
        soundFX = new HashMap<String, Sound>();
        music = new HashMap<String, Music>();
        muted = false;
        mutedSFX = false;
        mutedMusic = false;
        currentMusic = null;
    }

    /**
     * Toggle if all sounds are muted.
     */
    public void toggleMuted() {
        if (muted) {
            muted = false;
            if (currentMusic != null) currentMusic.setVolume(MUSIC_VOLUME * 0.3f);
        } else {
            muted = true;
            if (currentMusic != null) currentMusic.setVolume(MUTED_VOLUME);
        }
    }

    /**
     * Toggle if sound FX are muted.
     */
    public void toggleMutedSFX() {
        if (mutedSFX) {
            mutedSFX = false;
        } else {
            mutedSFX = true;
        }
    }

    /**
     * Toggle if music is muted.
     */
    public void toggleMutedMusic() {
        if (mutedMusic) {
            mutedMusic = false;
            if (!muted && currentMusic != null) currentMusic.setVolume(MUSIC_VOLUME * 0.3f);
        } else {
            mutedMusic = true;
            if (currentMusic != null) currentMusic.setVolume(MUTED_VOLUME);
        }
    }

    /**
     * Load in a new sound effect.
     *
     * @param name The name used to play this sound.
     * @param path The path to location of the sound file.
     */
    public void loadSFX(String name, String path) {
        soundFX.put(name, Gdx.audio.newSound(Gdx.files.internal(path)));
    }

    /**
     * Load in any (background) music files that will be used in the game.
     *
     * @param name The string used to refer to a particular music file to play it later on.
     * @param path The path to the location of the audio file to be loaded in.
     */
    public void loadMusic(String name, String path) {
        music.put(name, Gdx.audio.newMusic(Gdx.files.internal(path)));
    }

    /**
     * Play the sound effect that was loaded in with the same name. Plays the sound silently if the
     * all the sounds or SFX have been muted.
     *
     * @param name The sound to play.
     */
    public void playSFX(String name) {
        if (muted || mutedSFX) {
            soundFX.get(name).play(MUTED_VOLUME * MASTER_VOLUME * 0.3f);
        } else {
            soundFX.get(name).play(SFX_VOLUME * MASTER_VOLUME * 0.3f);
            muted = false;
        }
    }

    /**
     * Play the music that was loaded in with the same name once. Plays the music silently if all the
     * sounds or music have been muted.
     *
     * @param name The music to play.
     */
    public void playMusicOnce(String name) {
        playMusic(name, false);
    }

    /**
     * Play the music that was loaded in with the same name on a constant loop. Plays the music
     * silently if all the sounds or music have been muted.
     *
     * @param name The music to play.
     */
    public void playMusicLoop(String name) {
        playMusic(name, true);
    }

    /**
     * Play the music that was loaded in with the same name, specifying whether the music should be
     * played on a loop. Plays the music silently if all the sounds or music have been muted.
     *
     * @param name the name
     * @param loop the loop
     */
    public void playMusic(String name, boolean loop) {
        if (currentMusic != null) currentMusic.stop();
        currentMusic = music.get(name);
        if (muted || mutedMusic) {
            currentMusic.setVolume(MUTED_VOLUME);
        } else {
            currentMusic.setVolume(MUSIC_VOLUME * 0.3f);
            muted = false;
        }
        currentMusic.setLooping(loop);
        currentMusic.play();
    }

    /**
     * Change master volume.
     *
     * @param volume the volume
     */
    public void changeMasterVolume(float volume) {
        if (volume > 1.0f || volume < 0.0f) {
            MASTER_VOLUME = volume;
        }
    }

    /**
     * Change sfx volume.
     *
     * @param volume the volume
     */
    public void changeSFXVolume(float volume) {
        if (volume > 1.0f || volume < 0.0f) return;
        SFX_VOLUME = volume;
    }

    /**
     * Change music volume.
     *
     * @param volume the volume
     */
    public void changeMusicVolume(float volume) {
        if (volume > 1.0f || volume < 0.0f) return;
        MUSIC_VOLUME = volume;
        if (!muted) currentMusic.setVolume(MUSIC_VOLUME * MASTER_VOLUME * 0.3f);
    }

    /**
     * Gets muted sfx sound.
     *
     * @return the muted sfx
     */
    public boolean getMutedSFX() {
        return mutedSFX;
    }

    /**
     * Gets muted music.
     *
     * @return the muted music
     */
    public boolean getMutedMusic() {
        return mutedMusic;
    }

    /**
     * Gets music volume.
     *
     * @return the music volume
     */
    public float getMusicVolume() {
        return MUSIC_VOLUME;
    }

    /**
     * The constant MUSIC_FADE_STEP.
     */
    public final static float MUSIC_FADE_STEP = 0.001f;

    /**
     * The constant MUSIC_FADE_STEP_UP.
     */
    public final static float MUSIC_FADE_STEP_UP = 0.0008f;

    /**
     * The constant MUSIC_FADE_RATE.
     */
    public final static float MUSIC_FADE_RATE = 0.005f;
}