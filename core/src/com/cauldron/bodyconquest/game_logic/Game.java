package com.cauldron.bodyconquest.game_logic;

import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.constants.Disease;
import com.cauldron.bodyconquest.constants.GameType;
import com.cauldron.bodyconquest.gamestates.EncounterState;
import com.cauldron.bodyconquest.gamestates.GameStateManager;
import com.cauldron.bodyconquest.networking.Server;

import java.net.SocketException;

public class Game extends Thread {

  private boolean running;
  /** The number of time the game will refresh each second. */
  private final int FPS = 60;
  /** The target time to spend on each epoch of the game. */
  private final long targetTime = 1000 / FPS;

  private Server server;
  private GameStateManager gsm;

  private final GameType gameType;

  private Player playerBottom;
  private Player playerTop;

  /**
   * Constructor
   *
   * @param gameType The type of game that this server is running.
   * @throws SocketException
   */
  public Game(GameType gameType) throws SocketException {
    this.gameType = gameType;
    server = new Server();
    server.startServer(gameType);
  }

  private void init() {
    running = true;
    gsm = new GameStateManager(this);
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
    gsm.update();
  }

  public Server getServer() {
    return server;
  }

  public void setPlayerBottom(Disease disease) {
    playerBottom = new Player(Assets.PlayerType.PLAYER_BOTTOM, disease);
  }

  public void setPlayerTop(Disease disease) {
    playerTop = new Player(Assets.PlayerType.PLAYER_TOP, disease);
  }

  public Player getPlayerBottom() {
    return playerBottom;
  }

  public Player getPlayerTop() {
    return playerTop;
  }

  public void startEncounterState() {
    // Right now the Single player AI disease is set to INFLUENZA
    if (gameType == GameType.SINGLE_PLAYER) setPlayerTop(Disease.INFLUENZA);
    EncounterState encounterState = new EncounterState(this);
    gsm.setCurrentGameState(encounterState);
  }

  public void startEncounterLogic(EncounterState encounterState) {
    server.startEncounterLogic(encounterState);
  }

  public void startRaceSelectionState() {
    server.startRaceSelectionLogic(this);
  }

  public GameType getGameType() {
    return gameType;
  }

  public void closeEverything() {
    server.closeEverything();
  }

}
