package main.com.bodyconquest.game_logic;

import main.com.bodyconquest.constants.Disease;
import main.com.bodyconquest.constants.Organ;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.entities.Troops.Bases.Base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Player {

  private Disease disease;
  private ArrayList<Organ> claimedOrgans;
  private final PlayerType playerType;
  private int score;
  private float damageMult, speedMult, attackSpeedMult, healthMult;

  public Player(PlayerType playerType, Disease playerDisease) {
    this.playerType = playerType;
    this.disease = playerDisease;
    claimedOrgans = new ArrayList<>();
    score = 0;
    damageMult = speedMult = attackSpeedMult = healthMult = 1;
  }

  public Disease getDisease() {
    return disease;
  }

  public void setDisease(Disease disease) {
    this.disease = disease;
  }

  public void claimOrgan(Organ organ) {
    claimedOrgans.add(organ);
  }

  @SuppressWarnings("unchecked")
  public Base getNewBase() {
    try {
      return (Base)
          disease
              .getBaseType()
              .getAssociatedClass()
              .getDeclaredConstructor(PlayerType.class)
              .newInstance(playerType);
    } catch (IllegalAccessException
        | InstantiationException
        | NoSuchMethodException
        | InvocationTargetException e) {
      System.err.println("[ERROR] Base has peculiar constructor");
      e.printStackTrace();
      return null;
    }
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void refreshMultipliers() {
    damageMult = speedMult = attackSpeedMult = healthMult = 1;
    for (Organ o : claimedOrgans) {
      damageMult *= o.getDamageMult();
      speedMult *= o.getSpeedMult();
      attackSpeedMult *= o.getAttackSpeedMult();
      healthMult *= o.getHealthMult();
    }
  }

  public float getDamageMult() {
    return damageMult;
  }

  public float getSpeedMult() {
    return speedMult;
  }

  public float getAttackSpeedMult() {
    return attackSpeedMult;
  }

  public float getHealthMult() {
    return healthMult;
  }
}
