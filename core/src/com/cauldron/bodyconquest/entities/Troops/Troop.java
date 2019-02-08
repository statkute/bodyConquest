package com.cauldron.bodyconquest.entities.Troops;

import com.cauldron.bodyconquest.entities.MapObject;
import com.cauldron.bodyconquest.gamestates.EncounterScreen.*;

import java.util.ArrayList;

public abstract class Troop extends MapObject {

  /*
  May change to float depending on final implementation
   */
  private int range;
  private double movementSpeed;
  private int attackSpeed;
  private int lipidsCost;
  private int sugarsCost;
  private int proteinCost;
  private int health;

  public Troop() {}

  public Troop(
      int damage,
      int range,
      double movementSpeed,
      int attackSpeed,
      int lipidsCost,
      int sugarsCost,
      int proteinCost,
      int health) {
    this.damage = damage;
    this.range = range;
    this.movementSpeed = movementSpeed;
    this.attackSpeed = attackSpeed;
    this.lipidsCost = lipidsCost;
    this.sugarsCost = sugarsCost;
    this.proteinCost = proteinCost;
    this.health = health;
  }

  public double getAttackSpeed() {
    return attackSpeed;
  }

  public double getDamagePerHit() {
    return damage;
  }

  public double getLipidsCost() {
    return lipidsCost;
  }

  public double getMovementSpeed() {
    return movementSpeed;
  }

  public double getProteinCost() {
    return proteinCost;
  }

  public double getSugarsCost() {
    return sugarsCost;
  }

  public int getRange() {
    return range;
  }

  public static enum UnitType {
    BACTERIA
  }

  protected PlayerType playerType;

  public static final int NO_HEALTH = 0;

  protected int maxHealth;

  protected int damage;
  protected boolean attackable;
  protected long cooldown; // Or attack speed
  //protected int attackCooldown;
  protected long lastAttack;

  protected boolean attacking;
  protected boolean moving;

  /*
  May change to float depending on final implementation
   */

  public void setMaxHealth(int maxHealth) {
    this.maxHealth = maxHealth;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public int getMaxHealth() {
    return maxHealth;
  }

  public int getHealth() { return health; }


  public int getDamge() {
    return damage;
  }

  public boolean isAttackable() {
    return attackable;
  }

  /* With this health can never drop below zero */
  // Can be overridden
  public void hit(int damage) {
    setHealth(Math.max(getHealth() - damage, NO_HEALTH));
  }

  public boolean inRange(com.cauldron.bodyconquest.entities.Troop troop) {
    if (distFrom(troop) < range) {
      return true;
    } else {
      return false;
    }
  }

  public void checkAttack(ArrayList<com.cauldron.bodyconquest.entities.Troop> enemies) {}

  public void setMoving(boolean b) { moving = b; }
  public void setAttacking(boolean b) { attacking = b; }

  public boolean isDead() {
    return health == NO_HEALTH;
  }
}
