package com.cauldron.bodyconquest.game_logic;

import com.badlogic.gdx.Gdx;
import com.cauldron.bodyconquest.gamestates.EncounterState;
import com.cauldron.bodyconquest.constants.Constants.*;

public class BasicTestAI extends Thread {

  private final long COOLDOWN = 5000;
  private boolean running;

  private EncounterState game;
  private PlayerType playerType;

  public BasicTestAI(EncounterState game, PlayerType playerType) {
    this.game = game;
    this.playerType = playerType;
    running = true;
  }

  @Override
  public void run() {
    super.run();
    long time;
    long lastWave = 0;
    while(running){
      time = System.currentTimeMillis();
      if(time > (lastWave + COOLDOWN)) {
        summonWave();
        lastWave = time;
      }
    }
  }

  private void summonWave() {
    Gdx.app.postRunnable(new Runnable() {
      @Override
      public void run() {
        game.spawnUnit(UnitType.BACTERIA, Lane.BOTTOM, playerType);
        game.spawnUnit(UnitType.BACTERIA, Lane.MIDDLE, playerType);
        game.spawnUnit(UnitType.FLU, Lane.TOP, playerType);
      }
    });

  }


}
