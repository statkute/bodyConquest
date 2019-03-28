package main.com.bodyconquest.game_logic;

import main.com.bodyconquest.constants.Disease;
import main.com.bodyconquest.constants.Organ;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.entities.Troops.Bases.Base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/** The type Player. */
public class Player {

  private Disease disease;
  private ArrayList<Organ> claimedOrgans;
  private final PlayerType playerType;
  private int score;
  private float damageMult, speedMult, attackSpeedMult, healthMult;

  /**
   * Instantiates a new Player.
   *
   * @param playerType the player type
   * @param playerDisease the player disease
   */
  public Player(PlayerType playerType, Disease playerDisease) {
    this.playerType = playerType;
    this.disease = playerDisease;
    claimedOrgans = new ArrayList<>();
    score = 0;
    damageMult = speedMult = attackSpeedMult = healthMult = 1;
  }

  /**
   * Gets disease.
   *
   * @return the disease
   */
  public Disease getDisease() {
    return disease;
  }

  /**
   * Sets disease.
   *
   * @param disease the disease
   */
  public void setDisease(Disease disease) {
    this.disease = disease;
  }

  /**
   * Claim organ.
   *
   * @param organ the organ
   */
  public void claimOrgan(Organ organ) {
    claimedOrgans.add(organ);
  }

  /**
   * Gets new base.
   *
   * @return the new base
   */
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

  /**
   * Gets score.
   *
   * @return the score
   */
  public int getScore() {
    return score;
  }

  /**
   * Sets score.
   *
   * @param score the score
   */
  public void setScore(int score) {
    this.score = score;
  }

  /** Refresh multipliers. */
  public void refreshMultipliers() {
    damageMult = speedMult = attackSpeedMult = healthMult = 1;
    for (Organ o : claimedOrgans) {
      damageMult *= o.getDamageMult();
      speedMult *= o.getSpeedMult();
      attackSpeedMult *= o.getAttackSpeedMult();
      healthMult *= o.getHealthMult();
    }
  }

  /**
   * Gets damage mult.
   *
   * @return the damage mult
   */
  public float getDamageMult() {
    return damageMult;
  }

  /**
   * Gets speed mult.
   *
   * @return the speed mult
   */
  public float getSpeedMult() {
    return speedMult;
  }

  /**
   * Gets attack speed mult.
   *
   * @return the attack speed mult
   */
  public float getAttackSpeedMult() {
    return attackSpeedMult;
  }

  /**
   * Gets health mult.
   *
   * @return the health mult
   */
  public float getHealthMult() {
    return healthMult;
  }
}
