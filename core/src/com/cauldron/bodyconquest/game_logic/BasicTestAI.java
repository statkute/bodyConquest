package com.cauldron.bodyconquest.game_logic;

import com.badlogic.gdx.Gdx;
import com.cauldron.bodyconquest.entities.Spawnable;
import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.entities.resources.Resources;
import com.cauldron.bodyconquest.gamestates.EncounterState;
import com.cauldron.bodyconquest.constants.Assets.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class BasicTestAI extends Thread {

  private final int MAX_UNIT_SELECTION = 4;

  private final long COOLDOWN = 1000;
  private boolean running;

  private EncounterState game;
  private PlayerType playerType;
  // Maybe make an interface for Spawnable units.
  private ArrayList<UnitType> units;
  private Resources resources;

  public BasicTestAI(EncounterState game, PlayerType playerType, Resources resources) {
    this.game = game;
    this.playerType = playerType;
    this.resources = resources;
    running = true;
    units = new ArrayList<UnitType>(2);
    units.add(UnitType.BACTERIA);
    // units.add(UnitType.FLU);
    units.add(UnitType.VIRUS);
  }

  @Override
  public void run() {
    super.run();
    long time;
    long lastWave = 0;
    while (running) {
      time = System.currentTimeMillis();
      if (time > (lastWave + COOLDOWN)) {
        summonWave();
        lastWave = time;
      }
    }
  }

  private void summonWave() {
    Random rnd = new Random();
    int unitIndex = rnd.nextInt(2);
    try {
      final UnitType unitType = units.get(unitIndex);
      final Spawnable unit = (Spawnable) unitType.getUnitClass().newInstance();
      while (!resources.canAfford(unit)) {
        Thread.sleep(1000);
      }

      final Lane lane;

      CopyOnWriteArrayList<Troop> enemies = null;
      if(playerType == PlayerType.PLAYER_TOP) {
        enemies = game.getTroopsBottom();
      } else if (playerType == PlayerType.PLAYER_BOTTOM) {
        enemies = game.getTroopsTop();
      }

      int noEnemiesTop = 0;
      int noEnemiesMiddle = 0;
      int noEnemiesBottom = 0;
      for(Troop enemy : enemies) {
        Lane enemyLane = enemy.getLane();
        if (enemyLane == Lane.TOP) noEnemiesTop++;
        if (enemyLane == Lane.MIDDLE) noEnemiesMiddle++;
        if (enemyLane == Lane.BOTTOM) noEnemiesBottom++;
      }

      int totalNoEnemies = noEnemiesTop + noEnemiesBottom + noEnemiesMiddle;
      int roll = rnd.nextInt(99) + 1;
      if(noEnemiesTop > noEnemiesBottom && noEnemiesTop > noEnemiesMiddle) {
        double x = (noEnemiesTop / totalNoEnemies) * 90;
        if(roll > x) {
          lane = Lane.TOP;
        } else {
          if((roll % 2) == 1) {
            lane = Lane.MIDDLE;
          } else {
            lane = Lane.BOTTOM;
          }
        }
      } else {
        if (noEnemiesMiddle > noEnemiesBottom && noEnemiesMiddle > noEnemiesTop) {
          double x = (noEnemiesMiddle / totalNoEnemies) * 90;
          if (roll > x) {
            lane = Lane.MIDDLE;
          } else {
            if ((roll % 2) == 1) {
              lane = Lane.TOP;
            } else {
              lane = Lane.BOTTOM;
            }
          }
        } else {
          if(noEnemiesBottom > noEnemiesTop && noEnemiesBottom > noEnemiesMiddle) {
            double x = (noEnemiesBottom / totalNoEnemies) * 90;
            if(roll > x) {
              lane = Lane.BOTTOM;
            } else {
              if((roll % 2) == 1) {
                lane = Lane.MIDDLE;
              } else {
                lane = Lane.TOP;
              }
            }
          } else {
            return;
          }
        }
      }

      Gdx.app.postRunnable(
          new Runnable() {
            @Override
            public void run() {
              if (resources.canAfford(unit)){
                resources.buy(unit);
              }
              game.spawnUnit(unitType, lane, playerType);
            }
          });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
