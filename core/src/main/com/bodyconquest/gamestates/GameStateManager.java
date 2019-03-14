package main.com.bodyconquest.gamestates;

import main.com.bodyconquest.game_logic.Game;

public class GameStateManager {

  private GameState currentGameState;

  private Game game;

  public GameStateManager(Game game) {
    this.game = game;
    currentGameState = null;
  }

  public void update() {
    if(currentGameState == null) return;
    try {
      currentGameState.update();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  // Should maybe accept enum
  public void setCurrentGameState(GameState gs) {
    this.currentGameState = gs;
  }
}
