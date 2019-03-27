package main.com.bodyconquest.game_logic;

import com.badlogic.gdx.Gdx;
import main.com.bodyconquest.constants.Lane;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.constants.UnitType;
import main.com.bodyconquest.entities.DifficultyLevel;
import main.com.bodyconquest.entities.Spawnable;
import main.com.bodyconquest.entities.Troops.Troop;
import main.com.bodyconquest.entities.resources.Resources;
import main.com.bodyconquest.gamestates.EncounterState;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class BasicTestAI extends Thread {

  private final int MAX_UNIT_SELECTION = 4;

  private final long COOLDOWN = 2000;
  private boolean running;

  private EncounterState game;
  private PlayerType playerType;
  // Maybe make an interface for Spawnable units.
  private ArrayList<UnitType> units;
  private Resources resources;
  private DifficultyLevel difficultyLevel;

  public BasicTestAI(EncounterState game, PlayerType playerType, Resources resources, DifficultyLevel difficultyLevel) {
    this.game = game;
    this.playerType = playerType;
    this.resources = resources;
    this.difficultyLevel = difficultyLevel;
    running = true;
    units = new ArrayList<>(2);
    units.add(UnitType.BACTERIA);
    // units.add(UnitType.VIRUS);
    units.add(UnitType.FUNGUS);
  }

  @Override
  public void run() {
    super.run();
    long time;
    long lastWave = 0;
    while (running) {
      time = System.currentTimeMillis();
      if (time > (lastWave + COOLDOWN)) {
        if (difficultyLevel == DifficultyLevel.EASY) {
          summonEasyWave();
        }
        lastWave = time;
      }
    }
  }

  /**
   * Method called when the AI settings are on easy
   */
  private void summonEasyWave() {
    Random rnd = new Random();
    int unitIndex = rnd.nextInt(2);
    try {
      final UnitType unitType = units.get(unitIndex);
      final Spawnable unit = (Spawnable) unitType.getAssociatedClass().newInstance();
      while (!resources.canAfford(unit)) {
        Thread.sleep(1000);
      }

      final Lane lane;

      CopyOnWriteArrayList<Troop> enemies = null;
      enemies = game.getEnemyTroops(playerType);

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
        if(roll < x) {
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
          if (roll < x) {
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
            if(roll < x) {
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

      summonUnit(unitType, lane, playerType);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to spawn a unit
   *
   * @param lane       the lane in which the unit is spawned
   * @param playerType the player with which the unit allies itself
   */
  private void summonUnit(UnitType unitType, Lane lane, PlayerType playerType) {
    Gdx.app.postRunnable(new Runnable() {
      @Override
      public void run() {
        game.spawnUnit(unitType, lane, playerType, false);
      }
    });
  }
}
