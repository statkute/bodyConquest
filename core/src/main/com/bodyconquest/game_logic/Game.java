package main.com.bodyconquest.game_logic;

import main.com.bodyconquest.constants.*;
import main.com.bodyconquest.entities.DifficultyLevel;
import main.com.bodyconquest.game_logic.utils.Timer;
import main.com.bodyconquest.gamestates.EncounterState;
import main.com.bodyconquest.networking.Server;
import main.com.bodyconquest.networking.utilities.MessageMaker;
import main.com.bodyconquest.rendering.BodyConquest;

import java.net.SocketException;
import java.util.Random;

/** The type Game. */
public class Game extends Thread {

  private boolean running;
  /** The number of time the game will refresh each second. */
  private final int FPS = 60;
  /** The target time to spend on each epoch of the game. */
  private final long targetTime = 1000 / FPS;

  private Server server;
  private EncounterState encounterState;

  private final GameType gameType;

  private Player playerBottom;
  private Player playerTop;
  private boolean encounter;
  private PlayerType lastPicker;

  /** The Username top. */
  public String usernameTop;

  /** The Username bottom. */
  public String usernameBottom;

  // difficulty of the game for single player
  private DifficultyLevel difficulty;

  /**
   * Constructor
   *
   * @param gameType The type of game that this server is running.
   * @throws SocketException the socket exception
   */
  public Game(GameType gameType) throws SocketException {
    this.gameType = gameType;
    server = new Server();
    server.startServer(gameType);
  }

  private void init() {
    running = true;
  }

  @Override
  public void run() {

    init();

    long start;
    long elapsed;
    long wait;

    // Game loop
    while (running) {
      if (server.isGameEnded()) {
        break;
      }
      start = System.nanoTime();

      update();

      elapsed = System.nanoTime() - start;

      wait = targetTime - elapsed / 1000000;
      if (wait < 0) wait = 5;

      try {
        Thread.sleep(wait);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void update() {
    if (encounter && encounterState != null) {
      encounterState.update();
    }
  }

  /**
   * Gets server.
   *
   * @return the server
   */
  public Server getServer() {
    return server;
  }

  /**
   * Sets player bottom.
   *
   * @param disease the disease
   */
  public void setPlayerBottom(Disease disease) {
    playerBottom = new Player(PlayerType.PLAYER_BOTTOM, disease);
  }

  /**
   * Sets player top.
   *
   * @param disease the disease
   */
  public void setPlayerTop(Disease disease) {
    playerTop = new Player(PlayerType.PLAYER_TOP, disease);
  }

  /**
   * Gets player bottom.
   *
   * @return the player bottom
   */
  public Player getPlayerBottom() {
    return playerBottom;
  }

  /**
   * Gets player top.
   *
   * @return the player top
   */
  public Player getPlayerTop() {
    return playerTop;
  }

  /**
   * Start encounter state.
   *
   * @param organ the organ
   */
  public void startEncounterState(Organ organ) {
    encounterState = new EncounterState(this, organ);
    encounter = true;
  }

  /**
   * Start encounter logic.
   *
   * @param encounterState the encounter state
   */
  public void startEncounterLogic(EncounterState encounterState) {
    server.startEncounterLogic(encounterState);
  }

  /** Start race selection state. */
  public void startRaceSelectionState() {
    server.startRaceSelectionLogic(this);
    if (gameType == GameType.SINGLE_PLAYER) {
      setPlayerTop(Disease.INFLUENZA);
      server
          .getServerSender()
          .sendMessage(MessageMaker.firstPickerMessage(PlayerType.PLAYER_BOTTOM));
      Timer.startTimer(2000);
    } else {
      Random rnd = new Random();
      server
          .getServerSender()
          .sendMessage(
              MessageMaker.firstPickerMessage(
                  (rnd.nextInt(2) == 1 ? PlayerType.PLAYER_BOTTOM : PlayerType.PLAYER_TOP)));
    }
  }

  /**
   * Gets game type.
   *
   * @return the game type
   */
  public GameType getGameType() {
    return gameType;
  }

  /** Close everything. */
  public void closeEverything() {
    server.closeEverything();
  }

  /** Start body state. */
  public void startBodyState() {
    if (gameType == GameType.SINGLE_PLAYER) {
      if (getPlayerBottom().getDisease() != Disease.INFLUENZA) setPlayerTop(Disease.INFLUENZA);
      else if (getPlayerBottom().getDisease() != Disease.ROTAVIRUS) setPlayerTop(Disease.ROTAVIRUS);
      server
          .getServerSender()
          .sendMessage(
              MessageMaker.diseaseMessage(getPlayerTop().getDisease(), PlayerType.PLAYER_TOP));
      usernameTop = "AI";
      Timer.startTimer(2000);
    }

    server
        .getServerSender()
        .sendMessage(MessageMaker.usernameMessage(PlayerType.PLAYER_BOTTOM, usernameBottom));
    server
        .getServerSender()
        .sendMessage(MessageMaker.usernameMessage(PlayerType.PLAYER_TOP, usernameTop));

    server.startBodyLogic();
  }

  /** End encounter. */
  public void endEncounter() {
    encounter = false;
    encounterState = null;
    startBodyState();
  }

  /** Start database state. */
  public void startDatabaseState() {
    server.startDatabaseLogic(this);
  }

  /**
   * Gets last picker.
   *
   * @return the last picker
   */
  public PlayerType getLastPicker() {
    return lastPicker;
  }

  /**
   * Sets last picker.
   *
   * @param lastPicker the last picker
   */
  public void setLastPicker(PlayerType lastPicker) {
    this.lastPicker = lastPicker;
  }

  /**
   * Gets difficulty.
   *
   * @return the difficulty
   */
  public DifficultyLevel getDifficulty() {
    return difficulty;
  }

  /**
   * Sets difficulty.
   *
   * @param difficulty the difficulty
   */
  public void setDifficulty(DifficultyLevel difficulty) {
    this.difficulty = difficulty;
  }
}
