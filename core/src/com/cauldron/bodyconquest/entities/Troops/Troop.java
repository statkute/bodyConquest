package com.cauldron.bodyconquest.entities.Troops;

import com.cauldron.bodyconquest.entities.MapObject;
import com.cauldron.bodyconquest.gamestates.EncounterScreen.*;

import java.util.ArrayList;

public abstract class Troop extends MapObject {

  public static final int NO_HEALTH = 0;

  public static enum UnitType {
    BACTERIA,
    VIRUS,
    MONSTER
  }

  protected int maxHealth;
  protected int health;

  // Attacking Attributes
  private int attackSpeed;
  protected int damage;
  protected long cooldown;
  protected long lastAttack;
  protected int range;
  protected double movementSpeed;

  // Resource Requirements
  private int lipidsCost;
  private int sugarsCost;
  private int proteinCost;


  // States
  protected boolean attacking;
  protected boolean moving;

  protected boolean attackable;

  protected PlayerType playerType;

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

  public boolean inRange(Troop troop) {
    if (distFrom(troop) < range) {
      return true;
    } else {
      return false;
    }
  }

  /* With this health can never drop below zero */
  // Can be overridden
  public void hit(int damage) {
    setHealth(Math.max(getHealth() - damage, NO_HEALTH));
  }

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

  public int getDamge() {
    return damage;
  }

  public boolean isAttackable() {
    return attackable;
  }

  public void setMoving(boolean b) { moving = b; }
  public void setAttacking(boolean b) { attacking = b; }

  public boolean isDead() {
    return health == NO_HEALTH;
  }

  public abstract void checkAttack(ArrayList<Troop> enemies);
}
