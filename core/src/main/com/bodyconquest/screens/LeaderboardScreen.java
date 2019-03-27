package main.com.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.GameType;
import main.com.bodyconquest.game_logic.Communicator;
import main.com.bodyconquest.networking.Client;
import main.com.bodyconquest.networking.utilities.MessageMaker;
import main.com.bodyconquest.rendering.BodyConquest;

import java.awt.*;
import java.util.*;

import static java.util.stream.Collectors.toMap;

/** The type Leaderboard screen. */
public class LeaderboardScreen extends AbstractGameScreen implements Screen {

  private Texture backButton;
  private Rectangle backBounds;

  private HashMap<String, Integer> leaderboard;
  private LinkedHashMap sorted;
  private Texture header;
  private int place;
  private Client client;
  private Communicator comms;

  /**
   * Instantiates a new Leaderboard Screen.
   *
   * @param game the game
   */
  public LeaderboardScreen(BodyConquest game) {
    super(game);
    leaderboard = new HashMap<>();
    //        leaderboard.put("Alexandru", 20);
    //        leaderboard.put("Augustas", 16);
    //        leaderboard.put("Brandon", 30);
    //        leaderboard.put("Gintare", 15);
    //        leaderboard.put("Paul", 14);
    //        leaderboard.put("Anton", 14);
    //        leaderboard.put("Speed", 13);
    //        leaderboard.put("Tim", 12);
    //        leaderboard.put("Jack", 11);
    //        leaderboard.put("Rose", 10);
    client = game.getClient();
    // setting database logic to get leaderboard from server
    game.getGame().startDatabaseState();

    client.setDatabaseLogic();

    client.clientSender.sendMessage(MessageMaker.getLeaderboardMessage());
    comms = client.getCommunicator();
    loadAssets();
    getAssets();
    setRectangles();
    receiveLeaderboard();
    // System.out.println(leaderboard);
    System.out.println(Arrays.asList(leaderboard));
  }

  @Override
  public void render(float delta) {
    super.render(delta);
    sortLeaderboard();
    game.batch.begin();
    game.batch.draw(header, BodyConquest.V_WIDTH / 2.0f - header.getWidth() / 2.0f, 470);
    game.batch.draw(backButton, BodyConquest.V_WIDTH / 2.0f - backButton.getWidth() / 2.0f, 10);
    game.usernameFont.getData().setScale(1.0f, 1.0f);
    drawLeaderboard();
    drawNumbers();
    checkPressed();
    game.batch.end();
  }

  @Override
  public void loadAssets() {
    super.loadAssets();
    manager.load(Assets.headerLeaderboard, Texture.class);
    manager.load(Assets.backButton, Texture.class);
    manager.finishLoading();
  }

  @Override
  public void getAssets() {
    super.getAssets();
    header = manager.get(Assets.headerLeaderboard, Texture.class);
    backButton = manager.get(Assets.backButton, Texture.class);
  }

  /**
   * Get leaderboard hash map .
   *
   * @return the hash map of leaderboard
   */
  public HashMap<String, Integer> getLeaderboard() {
    return leaderboard;
  }

  /**
   * Receive leaderboard from the server.
   *
   * <p>// * @param leaderboard the leaderboard
   */
  public void receiveLeaderboard() {
    while (comms.getBoardIsSet().get() == false) {}
    this.leaderboard = comms.getBoard();
  }

  /** Sort leaderboard. */
  public void sortLeaderboard() {
    this.sorted =
        leaderboard.entrySet().stream()
            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .collect(
                toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
  }

  /** Draw leaderboard. */
  public void drawLeaderboard() {

    for (Object s : sorted.keySet()) {
      place++;
      switch (place) {
        case 1:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(place) + ".  \t " + s,
              BodyConquest.V_WIDTH / 2.0f - 150.0f,
              450.0f);
          break;
        case 2:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(place) + ".  \t " + s,
              BodyConquest.V_WIDTH / 2.0f - 150.0f,
              410.0f);
          break;
        case 3:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(place) + ".  \t " + s,
              BodyConquest.V_WIDTH / 2.0f - 150.0f,
              370.0f);
          break;
        case 4:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(place) + ".  \t " + s,
              BodyConquest.V_WIDTH / 2.0f - 150.0f,
              330.0f);
          break;
        case 5:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(place) + ".  \t " + s,
              BodyConquest.V_WIDTH / 2.0f - 150.0f,
              290.0f);
          break;
        case 6:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(place) + ".  \t " + s,
              BodyConquest.V_WIDTH / 2.0f - 150.0f,
              250.0f);
          break;
        case 7:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(place) + ".  \t " + s,
              BodyConquest.V_WIDTH / 2.0f - 150.0f,
              210.0f);
          break;
        case 8:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(place) + ".  \t " + s,
              BodyConquest.V_WIDTH / 2.0f - 150.0f,
              170.0f);
          break;
        case 9:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(place) + ".  \t " + s,
              BodyConquest.V_WIDTH / 2.0f - 150.0f,
              130.0f);
          break;
        case 10:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(place) + ".\t " + s,
              BodyConquest.V_WIDTH / 2.0f - 150.0f,
              90.0f);
          break;
      }
    }
    place = 0;
  }

  /** Draw scores. */
  public void drawNumbers() {

    for (Object s : sorted.keySet()) {
      place++;
      switch (place) {
        case 1:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(leaderboard.get(s)),
              BodyConquest.V_WIDTH / 2.0f + 180,
              450.0f);
          break;
        case 2:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(leaderboard.get(s)),
              BodyConquest.V_WIDTH / 2.0f + 180,
              410.0f);
          break;
        case 3:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(leaderboard.get(s)),
              BodyConquest.V_WIDTH / 2.0f + 180,
              370.0f);
          break;
        case 4:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(leaderboard.get(s)),
              BodyConquest.V_WIDTH / 2.0f + 180,
              330.0f);
          break;
        case 5:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(leaderboard.get(s)),
              BodyConquest.V_WIDTH / 2.0f + 180,
              290.0f);
          break;
        case 6:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(leaderboard.get(s)),
              BodyConquest.V_WIDTH / 2.0f + 180,
              250.0f);
          break;
        case 7:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(leaderboard.get(s)),
              BodyConquest.V_WIDTH / 2.0f + 180,
              210.0f);
          break;
        case 8:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(leaderboard.get(s)),
              BodyConquest.V_WIDTH / 2.0f + 180,
              170.0f);
          break;
        case 9:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(leaderboard.get(s)),
              BodyConquest.V_WIDTH / 2.0f + 180,
              130.0f);
          break;
        case 10:
          game.usernameFont.draw(
              game.batch,
              Integer.toString(leaderboard.get(s)),
              BodyConquest.V_WIDTH / 2.0f + 180,
              90.0f);
          break;
      }
    }
    place = 0;
  }

  @Override
  public void setRectangles() {
    super.setRectangles();
    backBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2,
            10,
            backButton.getWidth(),
            backButton.getHeight());
  }

  @Override
  public void checkPressed() {
    super.checkPressed();
    if (Gdx.input.justTouched()) {
      if (backBounds.contains(tmp.x, tmp.y)) {
        playButtonSound();
        dispose();
        game.setScreen(new MenuScreen(game));
      }
    }
  }
}
