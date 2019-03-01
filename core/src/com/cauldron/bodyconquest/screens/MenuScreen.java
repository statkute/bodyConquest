package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.game_logic.Communicator;
import com.cauldron.bodyconquest.networking.Client;
import com.cauldron.bodyconquest.networking.Server;
import com.cauldron.bodyconquest.rendering.BodyConquest;

public class MenuScreen extends AbstractGameScreen implements Screen {

  private Texture title;
  private Texture playButtonMultiplayer;
  private Texture playButtonSinglePlayer;
  private Texture settingsButton;
  private Texture creditsButton;
  private Texture exitButton;
  private Rectangle multiplayerBounds;
  private Rectangle singleplayerBounds;
  private Rectangle settingsBounds;
  private Rectangle creditsBounds;
  private Rectangle exitBounds;

  public static long timeOfServer;

  private Server server;
  private Client client;

  public MenuScreen(BodyConquest game) {
    super(game);
    loadAssets();
    getAssets();
    setRectangles();


  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {

    super.render(delta);
    game.batch.begin();
    game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);
    game.batch.draw(title, BodyConquest.V_WIDTH / 2 - title.getWidth() / 2, 450);
    game.batch.draw(
        playButtonSinglePlayer,
        BodyConquest.V_WIDTH / 2 - playButtonSinglePlayer.getWidth() / 2,
        300);
    game.batch.draw(
        playButtonMultiplayer,
        BodyConquest.V_WIDTH / 2 - playButtonMultiplayer.getWidth() / 2,
        240);
    game.batch.draw(settingsButton, BodyConquest.V_WIDTH / 2 - settingsButton.getWidth() / 2, 180);
    game.batch.draw(creditsButton, BodyConquest.V_WIDTH / 2 - creditsButton.getWidth() / 2, 120);
    game.batch.draw(exitButton, BodyConquest.V_WIDTH / 2 - exitButton.getWidth() / 2, 60);

    checkPressed();

    game.batch.end();
  }

  @Override
  public void checkPressed() {

    super.checkPressed();

    if (Gdx.input.justTouched()) {
      if (multiplayerBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        System.out.println("Multiplayer Is touched");
        dispose();
        game.setScreen(new HostScreen(game));
      }

      if (singleplayerBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        System.out.println("Singleplayer Is touched");
        server = new Server();
        timeOfServer = System.currentTimeMillis();
        client = new Client();
        Communicator communicator = new Communicator();
        game.setServer(server);
        game.setClient(client);
        dispose();
        game.setScreen(new RaceSelection(game, server, communicator, "singleplayer"));

      }
      if (settingsBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        System.out.println("Settings Is touched");
        dispose();
        game.setScreen(new SettingsScreen(game));

      }
      if (creditsBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        System.out.println("Credits Is touched");
        dispose();
        game.setScreen(new CreditsScreen(game));

      }

      if (exitBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        dispose();
        Gdx.app.exit();
        System.exit(0);
      }
    }
  }

  public Server getServer() {
    return server;
  }

  public Client getClient() {
    return client;
  }

  @Override
  public void loadAssets(){
//    manager.load(Assets.menuBackground, Texture.class);
    super.loadAssets();
    manager.load(Assets.menuTitle, Texture.class);
    manager.load(Assets.multiplayerButton, Texture.class);
    manager.load(Assets.singleplayerButton, Texture.class);
    manager.load(Assets.settingsButton, Texture.class);
    manager.load(Assets.creditsButton, Texture.class);
    manager.load(Assets.exitButton, Texture.class);
    manager.finishLoading();
  }

  @Override
  public void getAssets(){
    super.getAssets();
//    background = manager.get(Assets.menuBackground, Texture.class);
    title = manager.get(Assets.menuTitle, Texture.class);
    if(title == null)
      System.out.println("NULL");
    playButtonMultiplayer = manager.get(Assets.multiplayerButton, Texture.class);
    playButtonSinglePlayer = manager.get(Assets.singleplayerButton, Texture.class);
    settingsButton = manager.get(Assets.settingsButton, Texture.class);
    creditsButton = manager.get(Assets.creditsButton, Texture.class);
    exitButton = manager.get(Assets.exitButton, Texture.class);
  }

  public void setRectangles(){

    singleplayerBounds =
            new Rectangle(
                    BodyConquest.V_WIDTH / 2 - playButtonSinglePlayer.getWidth() / 2,
                    300,
                    playButtonSinglePlayer.getWidth(),
                    playButtonSinglePlayer.getHeight());

    multiplayerBounds =
            new Rectangle(
                    BodyConquest.V_WIDTH / 2 - playButtonMultiplayer.getWidth() / 2,
                    240,
                    playButtonMultiplayer.getWidth(),
                    playButtonMultiplayer.getHeight());

    settingsBounds =
            new Rectangle(
                    BodyConquest.V_WIDTH / 2 - settingsButton.getWidth() / 2,
                    180,
                    settingsButton.getWidth(),
                    settingsButton.getHeight());
    creditsBounds =
            new Rectangle(
                    BodyConquest.V_WIDTH / 2 - creditsButton.getWidth() / 2,
                    120,
                    creditsButton.getWidth(),
                    creditsButton.getHeight());
    exitBounds =
            new Rectangle(
                    BodyConquest.V_WIDTH / 2 - exitButton.getWidth() / 2,
                    60,
                    exitButton.getWidth(),
                    exitButton.getHeight());

  }
}
