package main.com.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.GameType;
import main.com.bodyconquest.rendering.BodyConquest;

import java.io.IOException;

/** The type Menu screen. */
public class MenuScreen extends AbstractGameScreen implements Screen {

  private Texture title;
  private Texture playButtonMultiplayer;
  private Texture playButtonSinglePlayer;
  private Texture settingsButton;
  private Texture creditsButton;
  private Texture exitButton;
  private Texture leaderboardButton;
  private Rectangle multiplayerBounds;
  private Rectangle singleplayerBounds;
  private Rectangle settingsBounds;
  private Rectangle creditsBounds;
  private Rectangle exitBounds;
  private Rectangle leaderboardBounds;

  /** The constant timeOfServer which shows how long the server is running. */
  public static long timeOfServer;

  private String username;

  /**
   * Instantiates a new Menu screen.
   *
   * @param game the game
   */
  public MenuScreen(BodyConquest game) {
    super(game);
    loadAssets();
    getAssets();
    setRectangles();
  }

  /** {@inheritDoc} */
  @Override
  public void show() {}

  /** {@inheritDoc} */
  @Override
  public void render(float delta) {

    super.render(delta);
    game.batch.begin();

    game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
    game.batch.draw(title, BodyConquest.V_WIDTH / 2 - title.getWidth() / 2, 450);
    game.batch.draw(
        playButtonSinglePlayer,
        BodyConquest.V_WIDTH / 2 - playButtonSinglePlayer.getWidth() / 2,
        350);
    game.batch.draw(
        playButtonMultiplayer,
        BodyConquest.V_WIDTH / 2 - playButtonMultiplayer.getWidth() / 2,
        290);
    game.batch.draw(
        leaderboardButton, BodyConquest.V_WIDTH / 2 - leaderboardButton.getWidth() / 2, 230);
    game.batch.draw(settingsButton, BodyConquest.V_WIDTH / 2 - settingsButton.getWidth() / 2, 170);
    game.batch.draw(creditsButton, BodyConquest.V_WIDTH / 2 - creditsButton.getWidth() / 2, 110);
    game.batch.draw(exitButton, BodyConquest.V_WIDTH / 2 - exitButton.getWidth() / 2, 50);

    checkPressed();

    game.batch.end();
  }

  /** {@inheritDoc} */
  @Override
  public void checkPressed() {
    super.checkPressed();

    if (Gdx.input.justTouched()) {
      if (multiplayerBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        timeOfServer = System.currentTimeMillis();
        System.out.println("Multiplayer Is touched");
        dispose();
        game.setScreen(new HostScreen(game));
      }

      if (singleplayerBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        System.out.println("Singleplayer Is touched");
        timeOfServer = System.currentTimeMillis();
        game.setScreen(new StartScreen(game, GameType.SINGLE_PLAYER));
        dispose();
      }
      if (settingsBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        System.out.println("Settings Is touched");
        dispose();
        game.setScreen(new SettingsScreen(game, username));
      }
      if (creditsBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        System.out.println("Credits Is touched");
        dispose();
        game.setScreen(new CreditsScreen(game, username));
      }

      if (exitBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        dispose();
        Gdx.app.exit();
        System.exit(0);
      }

      if (leaderboardBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        System.out.println("Leaderboard Is touched");
        //if (game.getClient().getIsStarted()) {
          dispose();
          game.setScreen(new LeaderboardScreen(game));
        //}
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public void loadAssets() {
    super.loadAssets();
    manager.load(Assets.menuTitle, Texture.class);
    manager.load(Assets.multiplayerButton, Texture.class);
    manager.load(Assets.singleplayerButton, Texture.class);
    manager.load(Assets.settingsButton, Texture.class);
    manager.load(Assets.creditsButton, Texture.class);
    manager.load(Assets.exitButton, Texture.class);
    manager.load(Assets.leaderboardButton, Texture.class);
    manager.finishLoading();
  }

  /** {@inheritDoc} */
  @Override
  public void getAssets() {
    super.getAssets();
    title = manager.get(Assets.menuTitle, Texture.class);
    playButtonMultiplayer = manager.get(Assets.multiplayerButton, Texture.class);
    playButtonSinglePlayer = manager.get(Assets.singleplayerButton, Texture.class);
    settingsButton = manager.get(Assets.settingsButton, Texture.class);
    creditsButton = manager.get(Assets.creditsButton, Texture.class);
    exitButton = manager.get(Assets.exitButton, Texture.class);
    leaderboardButton = manager.get(Assets.leaderboardButton, Texture.class);
  }

  /** {@inheritDoc} */
  @Override
  public void setRectangles() {

    singleplayerBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - playButtonSinglePlayer.getWidth() / 2,
            350,
            playButtonSinglePlayer.getWidth(),
            playButtonSinglePlayer.getHeight());

    multiplayerBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - playButtonMultiplayer.getWidth() / 2,
            290,
            playButtonMultiplayer.getWidth(),
            playButtonMultiplayer.getHeight());

    settingsBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - settingsButton.getWidth() / 2,
            170,
            settingsButton.getWidth(),
            settingsButton.getHeight());
    creditsBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - creditsButton.getWidth() / 2,
            110,
            creditsButton.getWidth(),
            creditsButton.getHeight());
    exitBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - exitButton.getWidth() / 2,
            50,
            exitButton.getWidth(),
            exitButton.getHeight());
    leaderboardBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - leaderboardButton.getWidth() / 2,
            230,
            leaderboardButton.getWidth(),
            leaderboardButton.getHeight());
  }
}
