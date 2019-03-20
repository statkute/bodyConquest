package main.com.bodyconquest.gamestates;

import main.com.bodyconquest.game_logic.Game;

public abstract class GameState {

  protected final Game game;

  public GameState(Game game){
    this.game = game;
  }

  public abstract void update() throws InterruptedException;
}
