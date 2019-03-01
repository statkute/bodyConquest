package com.cauldron.bodyconquest.game_logic;

import com.cauldron.bodyconquest.constants.GameType;
import com.cauldron.bodyconquest.gamestates.EncounterState;
import com.cauldron.bodyconquest.gamestates.GameStateManager;
import com.cauldron.bodyconquest.networking.Server;
import com.cauldron.bodyconquest.networking.utilities.Serialization;

public class Game extends Thread {

  private boolean running;
  private int FPS = 60; // The number of time the game will refresh each second
  private long targetTime = 1000 / FPS;
  private EncounterState encounterState;
  private Server server;

  private GameStateManager gsm;

  public Game(Server server, GameType gameType) {
    this.server = server;
    encounterState = new EncounterState(server, gameType);
  }

  private void init() {
    running = true;
    gsm = new GameStateManager();
    //encounterState = new EncounterState(comms, server.getServerSender());
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
      if (server.isGameEnded()){
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

  public EncounterState getEncounterState() {
    return encounterState;
  }
}
