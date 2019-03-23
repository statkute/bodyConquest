package main.com.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.rendering.BodyConquest;

/**
 * The type Settings screen.
 */
public class SettingsScreen extends AbstractGameScreen implements Screen {

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

    private String username;


    /**
     * Instantiates a new Settings screen.
     *
     * @param game     the game
     * @param username the username
     */
    public SettingsScreen(BodyConquest game, String username) {
        super(game);
        loadAssets();
        getAssets();
        setRectangles();
        this.username = username;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void render(float delta) {

        super.render(delta);

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
        checkPressed();
        game.batch.end();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkPressed() {

        super.checkPressed();

        if (Gdx.input.justTouched()) {
            if (backBounds.contains(tmp.x, tmp.y)) {
                System.out.println("back pressed");
                playButtonSound();
                dispose();
                game.setScreen(new MenuScreen(game));
            }

            if (soundBounds.contains(tmp.x, tmp.y) || soundToggleBounds.contains(tmp.x, tmp.y)) {
                game.audioPlayer.toggleMutedSFX();
                playButtonSound();
                if (game.audioPlayer.getMutedSFX()) {
                    game.batch.draw(soundOff, BodyConquest.V_WIDTH / 5 * 4 - soundOff.getWidth(), 300);
                } else {
                    game.batch.draw(soundOn, BodyConquest.V_WIDTH / 5 * 4 - soundOn.getWidth(), 300);
                }
            }

            if (musicBounds.contains(tmp.x, tmp.y) || musicToggleBounds.contains(tmp.x, tmp.y)) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadAssets() {
        super.loadAssets();
        manager.load(Assets.settingsHeader, Texture.class);
        manager.load(Assets.settingsSoundHeader, Texture.class);
        manager.load(Assets.settingsSoundOff, Texture.class);
        manager.load(Assets.settingsSoundOn, Texture.class);
        manager.load(Assets.settingsMusicHeader, Texture.class);
        manager.load(Assets.hostBack, Texture.class);
        manager.load(Assets.settingsMusicOff, Texture.class);
        manager.load(Assets.settingsMusicOn, Texture.class);
        manager.finishLoading();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getAssets() {
        super.getAssets();
        header = manager.get(Assets.settingsHeader, Texture.class);
        soundHeader = manager.get(Assets.settingsSoundHeader, Texture.class);
        musicHeader = manager.get(Assets.settingsMusicHeader, Texture.class);
        soundOn = manager.get(Assets.settingsSoundOn, Texture.class);
        soundOff = manager.get(Assets.settingsSoundOff, Texture.class);
        musicOn = manager.get(Assets.settingsMusicOn, Texture.class);
        backButton = manager.get(Assets.hostBack, Texture.class);
        musicOff = manager.get(Assets.settingsMusicOff, Texture.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRectangles() {
        super.setRectangles();
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
}
