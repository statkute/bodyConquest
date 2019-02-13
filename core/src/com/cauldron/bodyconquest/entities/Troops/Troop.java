package com.cauldron.bodyconquest.entities.Troops;

import com.cauldron.bodyconquest.entities.MapObject;
import com.cauldron.bodyconquest.entities.Troops.Bases.BacteriaBase;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;
import com.cauldron.bodyconquest.gamestates.EncounterScreen.*;

import java.util.ArrayList;

public abstract class Troop extends MapObject {

  public static final int NO_HEALTH = 0;
  protected final Lane lane;

  public enum UnitType {
    BACTERIA, FLU, VIRUS
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

  // Resources Requirements
  private int lipidsCost;
  private int sugarsCost;
  private int proteinCost;


  // States
  protected boolean attacking;
  protected boolean moving;

  protected boolean attackable;

  protected PlayerType playerType;

  public Troop(Lane lane) {
    this.lane = lane;
    this.collideable = true;
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

  public Lane getLane() { return lane; }

  public boolean isAttackable() {
    return attackable;
  }

  public void setMoving(boolean b) { moving = b; }
  public void setAttacking(boolean b) { attacking = b; }

  public boolean isDead() {
    return health == NO_HEALTH;
  }

  public void attack(Troop troop) {
    troop.hit(damage);
}

  public void checkAttack(ArrayList<Troop> enemies) {
    Troop closestEnemy = null;
    for (Troop enemy : enemies) {
      if (closestEnemy == null) closestEnemy = enemy;
      // Attack closest enemy
      closestEnemy = distFrom(enemy) < distFrom(closestEnemy) ? enemy : closestEnemy;
    }
    if (closestEnemy != null && closestEnemy.isAttackable() && inRange(closestEnemy)) {
      setMoving(false);
      long time = System.currentTimeMillis();
      if (!attacking && (time > lastAttack + cooldown)) {
        attack(closestEnemy);
        lastAttack = time;
      }
    } else {
      if (!moving) setMoving(true);
    }
  }

//  public void checkCollision(ArrayList<MapObject> mapObjects) {
//    for(MapObject o : mapObjects) {
//      //if(o.isCollideable())
//    }
//  }
  
  
  @Override
  public String toString() {
    return this.getClass().toString();
  }
}
