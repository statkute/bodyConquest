package com.cauldron.bodyconquest.entities.Troops;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.entities.MapObject;
import com.cauldron.bodyconquest.gamestates.EncounterState.Lane;
import com.cauldron.bodyconquest.gamestates.EncounterState.PlayerType;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Troop extends MapObject {

  public static final int NO_HEALTH = 0;

  protected final Lane lane;
  protected final PlayerType playerType;

  public enum UnitType {
    BACTERIA,
    FLU,
    VIRUS
  }

  protected int maxHealth;
  protected int health;

  // Attacking Attributes
  private int attackSpeed;
  protected int damage;
  protected long cooldown;
  protected long lastAttack;
  protected int range;

  // Resources Requirements
  private int lipidsCost;
  private int sugarsCost;
  private int proteinCost;

  // States
  protected boolean attacking;

  // Properties
  protected boolean attackable;

  // Temporary implementation to get images for the HUD
  public Image sprite;

  public Troop(Lane lane, PlayerType playerType) {
    this.lane = lane;
    this.playerType = playerType;
    initDefault();
  }

  private void initDefault() {
    // Maxed out because I don't want to do fine tuning right now
    stopSpeed = 100000;
    acceleration = 100000;
    lastAttack = 0;
    attackable = true;
    moving = true;
    attacking = false;
    collidable = true;
  }

  public boolean inRange(Troop troop) {
    if (distFrom(troop) < range) {
      return true;
    } else {
      return false;
    }
  }

  /* With this health can never drop below zero */
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

  public int getHealth() {
    return health;
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
    return getMaxSpeed();
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

  public int getDamage() {
    return damage;
  }

  public Lane getLane() {
    return lane;
  }

  public boolean isAttackable() {
    return attackable;
  }

  public void setAttacking(boolean b) {
    attacking = b;
  }

  public boolean isDead() {
    return health == NO_HEALTH;
  }

  public void attack(Troop troop) {
    troop.hit(damage);
  }

  public void checkAttack(CopyOnWriteArrayList<Troop> enemies) {
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

  public void update() {
    updateMovement();
    move();
  }

  protected void updateMovement() {
    if (moving) {
      if (playerType == PlayerType.BOT_PLAYER) {
        if (lane == Lane.BOT) {
          if (getX() > Constants.BOT_TURNPOINT_X) {
            setDirectionLeft();
          } else {
            setDirectionUp();
          }
        } else if (lane == Lane.MID) {
          setDirectionLeft();
          setDirectionUp();
        } else if (lane == Lane.TOP) {
          if (getY() < Constants.TOP_TURNPOINT_Y) {
            setDirectionUp();
          } else {
            setDirectionLeft();
          }
        }
      } else if (playerType == PlayerType.TOP_PLAYER) {
        if (lane == Lane.BOT) {
          if (getCentreY() > Constants.BOT_TURNPOINT_Y) {
            setDirectionDown();
          } else {
            setDirectionRight();
          }
        } else if (lane == Lane.MID) {
          setDirectionDownRight();
        } else if (lane == Lane.TOP) {
          if (getX() < Constants.TOP_TURNPOINT_X) {
            setDirectionRight();
          } else {
            setDirectionDown();
          }
        }
      }
    }
  }

  @Override
  public String toString() {
    return this.getClass().toString();
  }
}
