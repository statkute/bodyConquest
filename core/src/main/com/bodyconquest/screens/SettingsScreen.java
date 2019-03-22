package main.com.bodyconquest.screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.rendering.BodyConquest;

import java.util.ArrayList;

/** The type Settings screen. */
public class SettingsScreen extends AbstractGameScreen implements Screen {

  private final OrthographicCamera gameCamera;
  private final FitViewport gamePort;
  private final Stage stage;

  private Image header;
  private Texture t_header;
  private Image soundText;
  private Texture t_soundText;
  private Image musicText;
  private Texture t_musicText;
  private Image soundOn;
  private Texture t_soundOn;
  private Image soundOff;
  private Texture t_soundOff;
  private Image musicOn;
  private Texture t_musicOn;
  private Image musicOff;
  private Texture t_musicOff;
  private Image difficultyText;
  private Texture t_difficultyText;
  private Image easy;
  private Texture t_easy;
  private Image hard;
  private Texture t_hard;
  private Image back;
  private Texture t_back;

  private ArrayList<Image> allImages = new ArrayList<>();
  private String username;

  /**
   * Instantiates a new Settings screen.
   *
   * @param game the game
   * @param username the username
   */
  public SettingsScreen(BodyConquest game, String username) {
    super(game);

    gameCamera = new OrthographicCamera();
    gamePort = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, gameCamera);
    stage = new Stage(gamePort);
    Gdx.input.setInputProcessor(stage);

    loadAssets();
    getAssets();
    //    setRectangles();
    addActors();
    addButtons();
    this.username = username;
  }

  /** {@inheritDoc} */
  @Override
  public void render(float delta) {
    super.render(delta);
    game.batch.begin();
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    gameCamera.update();
    stage.draw();
    game.batch.end();
  }

  /** {@inheritDoc} */
  @Override
  public void loadAssets() {
    super.loadAssets();
    manager.load(Assets.settingsHeader, Texture.class);
    manager.load(Assets.settingsSoundHeader, Texture.class);
    manager.load(Assets.settingsSoundOff, Texture.class);
    manager.load(Assets.settingsSoundOn, Texture.class);
    manager.load(Assets.settingsMusicHeader, Texture.class);
    manager.load(Assets.settingsMusicOff, Texture.class);
    manager.load(Assets.settingsMusicOn, Texture.class);
    manager.load(Assets.settingsSingleplayerDifficulty, Texture.class);
    manager.load(Assets.settingsEasy, Texture.class);
    manager.load(Assets.settingsHard, Texture.class);
    manager.load(Assets.hostBack, Texture.class);
    manager.finishLoading();
  }

  /** {@inheritDoc} */
  @Override
  public void getAssets() {
    super.getAssets();
    t_header = manager.get(Assets.settingsHeader, Texture.class);
    t_soundText = manager.get(Assets.settingsSoundHeader, Texture.class);
    t_musicText = manager.get(Assets.settingsMusicHeader, Texture.class);
    t_soundOn = manager.get(Assets.settingsSoundOn, Texture.class);
    t_soundOff = manager.get(Assets.settingsSoundOff, Texture.class);
    t_musicOn = manager.get(Assets.settingsMusicOn, Texture.class);
    t_musicOff = manager.get(Assets.settingsMusicOff, Texture.class);
    t_difficultyText = manager.get(Assets.settingsSingleplayerDifficulty, Texture.class);
    t_easy = manager.get(Assets.settingsEasy, Texture.class);
    t_hard = manager.get(Assets.settingsHard, Texture.class);
    t_back = manager.get(Assets.hostBack, Texture.class);
  }

  public void addActors() {
    header = new Image(t_header);
    header.setBounds(
        BodyConquest.V_WIDTH / 2.0f - header.getWidth() / 2.0f,
        460,
        t_header.getWidth(),
        t_header.getHeight());
    allImages.add(header);

    soundText = new Image(t_soundText);
    soundText.setBounds(
        BodyConquest.V_WIDTH / 5.0f, 300, t_soundText.getWidth(), t_soundText.getHeight());
    allImages.add(soundText);

    musicText = new Image(t_musicText);
    musicText.setBounds(
        BodyConquest.V_WIDTH / 5.0f, 240, t_musicText.getWidth(), t_musicText.getHeight());
    allImages.add(musicText);

    soundOn = new Image(t_soundOn);
    soundOn.setBounds(
        BodyConquest.V_WIDTH / 5.0f * 4.0f - soundOn.getWidth(),
        300,
        t_soundOn.getWidth(),
        t_soundOn.getHeight());

    soundOff = new Image(t_soundOff);
    soundOff.setBounds(
        BodyConquest.V_WIDTH / 5.0f * 4.0f - soundOff.getWidth(),
        300,
        t_soundOff.getWidth(),
        t_soundOff.getHeight());

    if (game.audioPlayer.getMutedSFX()) {
      allImages.add(soundOff);
    } else {
      allImages.add(soundOn);
    }

    musicOn = new Image(t_musicOn);
    musicOn.setBounds(
        BodyConquest.V_WIDTH / 5.0f * 4.0f - musicOn.getWidth(),
        240,
        t_musicOn.getWidth(),
        t_musicOn.getHeight());

    musicOff = new Image(t_musicOff);
    musicOff.setBounds(
        BodyConquest.V_WIDTH / 5.0f * 4.0f - musicOff.getWidth(),
        240,
        t_musicOff.getWidth(),
        t_musicOff.getHeight());

    if (game.audioPlayer.getMutedMusic()) {
      allImages.add(musicOff);
    } else {
      allImages.add(musicOn);
    }

    difficultyText = new Image(t_difficultyText);
    difficultyText.setBounds(
        BodyConquest.V_WIDTH / 5.0f,
        180,
        t_difficultyText.getWidth(),
        t_difficultyText.getHeight());
    allImages.add(difficultyText);

    easy = new Image(t_easy);
    easy.setBounds(
        BodyConquest.V_WIDTH / 5.0f * 4.0f - easy.getWidth(),
        180,
        t_easy.getWidth(),
        t_easy.getHeight());

    hard = new Image(t_hard);
    hard.setBounds(
        BodyConquest.V_WIDTH / 5.0f * 4.0f - hard.getWidth(),
        180,
        t_hard.getWidth(),
        t_hard.getHeight());

        if (game.getDifficultyLevel() == BodyConquest.DifficultyLevel.EASY) {
          allImages.add(easy);
        } else {
          allImages.add(hard);
        }

    back = new Image(t_back);
    back.setBounds(
        BodyConquest.V_WIDTH / 2.0f - back.getWidth() / 2.0f,
        60,
        t_back.getWidth(),
        t_back.getHeight());
    allImages.add(back);

    for (Image i : allImages) {
      stage.addActor(i);
    }
  }

  /** Add buttons to the stage. */
  public void addButtons() {
    soundText.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            game.audioPlayer.toggleMutedSFX();
            if (game.audioPlayer.getMutedSFX()) {
              soundOn.remove();
              stage.addActor(soundOff);
            } else {
              playButtonSound();
              soundOff.remove();
              stage.addActor(soundOn);
            }
          }
        });
    soundOn.addListener(soundText.getListeners().peek());
    soundOff.addListener(soundText.getListeners().peek());

    musicText.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            game.audioPlayer.toggleMutedMusic();
            playButtonSound();
            if (game.audioPlayer.getMutedMusic()) {
              musicOn.remove();
              stage.addActor(musicOff);
            } else {
              playButtonSound();
              musicOff.remove();
              stage.addActor(musicOn);
            }
          }
        });
    musicOn.addListener(musicText.getListeners().peek());
    musicOff.addListener(musicText.getListeners().peek());

    difficultyText.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            playButtonSound();
            game.changeDifficulty();
            if (game.getDifficultyLevel() == BodyConquest.DifficultyLevel.EASY) {
              hard.remove();
              stage.addActor(easy);
            } else {
              easy.remove();
              stage.addActor(hard);
            }
          }
        });
    easy.addListener(difficultyText.getListeners().peek());
    hard.addListener(difficultyText.getListeners().peek());

    back.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            playButtonSound();
            game.setScreen(new MenuScreen(game, username));
          }
        });
  }
}
