package com.cauldron.bodyconquest.entities.Troops;

public abstract class Troop {
  private double damagePerHit;
  private int range;
  private double movementSpeed;
  private double attackSpeed;
  private double lipidsCost;
  private double sugarsCost;
  private double proteinCost;
  private double health;

  public Troop() {}

  public Troop(
      double damagePerHit,
      int range,
      double movementSpeed,
      double attackSpeed,
      double lipidsCost,
      double sugarsCost,
      double proteinCost,
      double health) {
    this.damagePerHit = damagePerHit;
    this.range = range;
    this.movementSpeed = movementSpeed;
    this.attackSpeed = attackSpeed;
    this.lipidsCost = lipidsCost;
    this.sugarsCost = sugarsCost;
    this.proteinCost = proteinCost;
    this.health = health;
  }

  public void dealDamage(double damage) {
    if (damage >= health) {
      health = 0.0;
    } else {
      health -= damage;
    }
  }

  public double getAttackSpeed() {
    return attackSpeed;
  }

  public double getDamagePerHit() {
    return damagePerHit;
  }

  public double getHealth() {
    return health;
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
}
