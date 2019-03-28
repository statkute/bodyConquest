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
          summonWave(difficultyLevel);
        lastWave = time;
      }
    }
  }

    public void stopRunning() {
        running = false;
    }

    /**
     * If the difficulty level is EASY, the heuristic to compute l;ane strength only count the number of units there
     * If the difficulty level is HARD, the heuristic computes the lane strength by adding
     * the strengths of all units in that lane, according to the formula:
     * damage*(health/100)/(cooldown/1000)
     *
     * @param difficultyLevel The level of difficulty chosen for the AI
     */
    private void summonWave(DifficultyLevel difficultyLevel) {
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

        double strengthEnemiesTop = 0.0;
        double strengthEnemiesMiddle = 0.0;
        double strengthEnemiesBottom = 0.0;
        double strength = 0.0;
      for(Troop enemy : enemies) {
        Lane enemyLane = enemy.getLane();
          //if difficulty is easy, just count troops to get lane strength
          if (difficultyLevel == DifficultyLevel.EASY) {
              if (enemyLane == Lane.TOP) strengthEnemiesTop++;
              if (enemyLane == Lane.MIDDLE) strengthEnemiesMiddle++;
              if (enemyLane == Lane.BOTTOM) strengthEnemiesBottom++;
          } else {
              //else approximate the strength using a better heuristic
              strength = (double) enemy.getDamage() * ((double) enemy.getHealth() / 100) / ((double) enemy.getCooldown() / 1000);
              if (enemyLane == Lane.TOP) strengthEnemiesTop += strength;
              if (enemyLane == Lane.MIDDLE) strengthEnemiesMiddle += strength;
              if (enemyLane == Lane.BOTTOM) strengthEnemiesBottom += strength;
          }
      }

        double totalStrength = strengthEnemiesTop + strengthEnemiesBottom + strengthEnemiesMiddle;
      int roll = rnd.nextInt(99) + 1;
        if (strengthEnemiesTop > strengthEnemiesBottom && strengthEnemiesTop > strengthEnemiesMiddle) {
            double x = (strengthEnemiesTop / totalStrength) * 90;
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
            if (strengthEnemiesMiddle > strengthEnemiesBottom && strengthEnemiesMiddle > strengthEnemiesTop) {
                double x = (strengthEnemiesMiddle / totalStrength) * 90;
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
                if (strengthEnemiesBottom > strengthEnemiesTop && strengthEnemiesBottom > strengthEnemiesMiddle) {
                    double x = (strengthEnemiesBottom / totalStrength) * 90;
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
