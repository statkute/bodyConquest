package com.cauldron.bodyconquest.gamestates;

import com.cauldron.bodyconquest.game_logic.Game;

public class GameStateManager {

  private GameState currentGameState;

  private Game game;

  public GameStateManager(Game game) {
    this.game = game;
  }

  public void update() {
    currentGameState.update();
  }

  // Should maybe accept enum
  public void setCurrentGameState(GameState gs) {
    this.currentGameState = gs;
  }
}
