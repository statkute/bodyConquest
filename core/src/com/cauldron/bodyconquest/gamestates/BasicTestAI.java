package com.cauldron.bodyconquest.gamestates;

import com.badlogic.gdx.Gdx;
import com.cauldron.bodyconquest.entities.Troops.Troop.*;
import com.cauldron.bodyconquest.gamestates.EncounterScreen.*;

public class BasicTestAI extends Thread {

  private final long COOLDOWN = 5000;
  private boolean running;

  private EncounterScreen game;
  private PlayerType playerType;

  public BasicTestAI(EncounterScreen game, PlayerType playerType) {
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
        game.spawnUnit(UnitType.BACTERIA, Lane.BOT, playerType);
        game.spawnUnit(UnitType.BACTERIA, Lane.MID, playerType);
        game.spawnUnit(UnitType.FLU, Lane.TOP, playerType);
      }
    });

  }


}
