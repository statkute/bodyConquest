package com.cauldron.bodyconquest.entities;

import com.cauldron.bodyconquest.gamestates.EncounterScreen. *;

import java.util.ArrayList;

public abstract class Troop extends MapObject {

  public static enum UnitType {
    BACTERIA
  }

  protected PlayerType playerType;

  public static final int NO_HEALTH = 0;

  protected int maxHealth;
  protected int health;

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
  protected int range;

  public void setMaxHealth(int maxHealth) {
    this.maxHealth = maxHealth;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public int getMaxHealth() {
    return maxHealth;
  }

  public int getHealth() {
    return health;
  }

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

  public boolean inRange(Troop troop) {
    if (distFrom(troop) < range) {
      return true;
    } else {
      return false;
    }
  }

  public void checkAttack(ArrayList<Troop> enemies) {}

  public void setMoving(boolean b) { moving = b; }
  public void setAttacking(boolean b) { attacking = b; }

  public boolean isDead() {
    return health == NO_HEALTH;
  }

}