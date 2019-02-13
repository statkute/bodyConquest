package com.cauldron.bodyconquest.gamestates;

public class GameStateManager {

  private GameState currentGameState;

  public void update() {
    currentGameState.update();
  }

  // Should maybe accept enum
  public void setCurrentGameState(GameState gs) {
    this.currentGameState = gs;
  }
}
