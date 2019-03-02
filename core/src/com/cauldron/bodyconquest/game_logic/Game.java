package com.cauldron.bodyconquest.game_logic;

import com.cauldron.bodyconquest.constants.GameType;
import com.cauldron.bodyconquest.gamestates.EncounterState;
import com.cauldron.bodyconquest.gamestates.GameStateManager;
import com.cauldron.bodyconquest.networking.Server;

import java.net.SocketException;

public class Game extends Thread {

  private boolean running;
  /** The number of time the game will refresh each second. */
  private final int FPS = 60;
  private long targetTime = 1000 / FPS;
  private EncounterState encounterState;
  private Server server;
  private GameStateManager gsm;

  private Player player1;
  private Player player2;

  /**
   * Constructor
   * @param gameType The type of game that this server is running.
   * @throws SocketException
   */
  public Game(GameType gameType) throws SocketException {
    server = new Server();
    server.startServer(gameType);
    player1 = new Player();
    player2 = new Player();
    encounterState = new EncounterState(this, gameType);
  }

  private void init() {
    running = true;
    gsm = new GameStateManager(this);
    // Starting state
    gsm.setCurrentGameState(encounterState);
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

  public Player getPlayer1() {
    return player1;
  }

  public Player getPlayer2() {
    return player2;
  }

  public void startEncounterLogic(EncounterState encounterState) {
    server.startServerLogic(encounterState);
  }
}
