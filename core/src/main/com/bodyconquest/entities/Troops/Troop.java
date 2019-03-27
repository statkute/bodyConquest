package main.com.bodyconquest.entities.Troops;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.Lane;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.entities.MapObject;
import main.com.bodyconquest.entities.Spawnable;

import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;

/** The parent class for all spawn-able troops that can be spawned by each player (or the AI). */
public abstract class Troop extends MapObject implements Spawnable {

  /** The finalised representation of the minimum health a Troop can have. */
  public static final int NO_HEALTH = 0;

  /**
   * The {@link Lane} that this Troop has been assigned to. (Used to make decisions on who to attack
   * and how to move.
   */
  protected final Lane lane;
  /**
   * The {@link PlayerType} that this Troop is affiliated with. (Used to make decisions on who to
   * attack and how to move.
   */
  //protected final PlayerType playerType;

  /** Enumeration for each type of Unit/Troop that exists. */

  /** The maximum health this Troop can have. */
  protected int maxHealth;
  /** The current health of this Troop. */
  protected int health;

  // Attacking Attributes
  /** The amount of damage dealt by each attack by this Troop. */
  protected int damage;

  /** The time (in milliseconds) that the Troop must wait to attack again after attacking. */
  protected long cooldown;
  /** The time of the last attack (in milliseconds). (Initialised to 0) */
  protected long lastAttack;
  /** The range which the Troop can perform an {@link Troop#attack(Troop)} from. */
  protected int range;

  // Resources Requirements
  /** The lipid cost for a player to spawn this Troop. */
  protected int lipidsCost;
  /** The sugar cost for a player to spawn this Troop. */
  protected int sugarsCost;
  /** The protien cost for a player to spawn this Troop. */
  protected int proteinCost;

  /** The points the user gets for killing this unit. */
  protected int killingPoints;

  // States
  /**
   * The state that represents if the unit is currently attacking. (Unused as all attacks are
   * currently instantaneous)
   */
  protected boolean attacking;

  protected boolean slowed;

  protected boolean ranged;

  // Effect Values
  protected long slowTimeEnd;
  protected int slowPercentage;

  // Properties
  /** The value that represents whether or not this Troop can be attacked by other Troops. */
  protected boolean attackable;

  // Temporary implementation to get images for the HUD
  public Image sprite;
  private int damageCounter;

  private float timeAlive;


  /**
   * The constructor.
   *
   * @param lane The {@link Lane} which this Troop is being spawned onto.
   * @param playerType The {@link PlayerType} which this Troop is assigned to.
   */
  public Troop(Lane lane, PlayerType playerType) {
    this.lane = lane;
    this.playerType = playerType;
    initDefault();
  }

  /** Sets the default values for a Troop. */
  private void initDefault() {
    // Maxed out because I don't want to do fine tuning right now
    stopSpeed = 100000;
    acceleration = 100000;
    lastAttack = 0;
    slowPercentage = 0;
    slowTimeEnd = 0;
    attackable = true;
    moving = true;
    attacking = false;
    collidable = true;
    killingPoints = 0;
    damageCounter = 0;
    ranged = false;
  }

  /**
   * Called by (usually by) {@link MapObject}'s to attack this Troop.
   *
   * @param damage The amount of damage to deal to this Troop.
   */
  public void hit(int damage) {
    setHealth(getHealth() - damage);
    setWasHit(true);
    setTimeOfDmgTaken(System.currentTimeMillis());
  }

  /**
   * Attack the given Troop. (Defaulted to calling the given Troop's {@link Troop#hit(int)} method
   * using this Troop's damage.
   *
   * @param troop The Troop to attack.
   */
  public void attack(Troop troop) {
    troop.hit(damage);
  }

  /**
   * Checks if any of the Troops in the given {@link CopyOnWriteArrayList} are eligible to be
   * attacked. By default a Troop is eligible to be attacked if it is in range and attack-able. If
   * both of these conditions are fulfilled then this Troop will stop moving and attempt to attack
   * the Troop (if this Troop's attack isn't on cooldown).
   *
   * @param enemies The {@link CopyOnWriteArrayList} of Troops to check to attack.
   */
  public void checkAttack(CopyOnWriteArrayList<Troop> enemies) {
    Troop closestEnemy = null;
    for (Troop enemy : enemies) {
     if (enemy.getLane() == this.getLane() || enemy.getLane() == Lane.ALL) {
        if (closestEnemy == null) closestEnemy = enemy;
        // Attack closest enemy
        closestEnemy = distFrom(enemy) < distFrom(closestEnemy) ? enemy : closestEnemy;
      }
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

  /** {@inheritDoc} */
  public void update() {
    updateMovement();
    checkEffects();
    move();
    if(this.getWasHit()){
      this.setWasHit(false);
    }
  }

  private void checkEffects() {
    if (slowed && slowTimeEnd < System.currentTimeMillis()) {
      slowed = false;
      slowPercentage = 0;
    }
  }

  /**
   * By default sets the movement direction according to what {@link Lane} and {@link PlayerType}
   * this Troop is assigned to.
   */
  protected void updateMovement() {
    if (moving) {
      if (playerType == PlayerType.PLAYER_BOTTOM) {
        if (lane == Lane.BOTTOM) {
          if (getX() > Assets.BOT_TURNPOINT_X) {
            setDirectionLeft();
          } else {
            setDirectionUp();
          }
        } else if (lane == Lane.MIDDLE) {
          setDirectionUpLeft();
        } else if (lane == Lane.TOP) {
          if (getY() < Assets.TOP_TURNPOINT_Y) {
            setDirectionUp();
          } else {
            setDirectionLeft();
          }
        }
      } else if (playerType == PlayerType.PLAYER_TOP) {
        if (lane == Lane.BOTTOM) {
          if (getCentreY() > Assets.BOT_TURNPOINT_Y) {
            setDirectionDown();
          } else {
            setDirectionRight();
          }
        } else if (lane == Lane.MIDDLE) {
          setDirectionDownRight();
        } else if (lane == Lane.TOP) {
          if (getX() < Assets.TOP_TURNPOINT_X) {
            setDirectionRight();
          } else {
            setDirectionDown();
          }
        }
      }
    }
  }

  @Override
  public void move() {
    double normalMaxSpeed = maxSpeed;
    if (slowed) {
      maxSpeed = maxSpeed * (1 - (slowPercentage / 100f));
    }
    super.move();
    maxSpeed = normalMaxSpeed;
  }

  /**
   * Check if the given Troop is in attacking range of this Troop.
   *
   * @param troop The troop to check.
   * @return True if the given Troop is in attacking range.
   */
  public boolean inRange(Troop troop) {
    if (distFrom(troop) < range) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Set the maximum health value this Troop can have.
   *
   * @param maxHealth The maximum health value this Troop can have.
   */
  public void setMaxHealth(int maxHealth) {
    this.maxHealth = maxHealth;
  }

  /**
   * Set the health value of this troop. If the given value is below {@value NO_HEALTH} then set
   * health to {@value NO_HEALTH}.
   *
   * @param health The health of this Troop.
   */
  public void setHealth(int health) {
    this.health = Math.max(health, NO_HEALTH);
  }

  /**
   * Get the maximum health value of this Troop.
   *
   * @return The maximum health value of this Troop.
   */
  public int getMaxHealth() {
    return maxHealth;
  }

  /**
   * Get the health value of this Troop.
   *
   * @return The health value of this Troop.
   */
  public int getHealth() {
    return health;
  }

  public int getKillingPoints() {
    return killingPoints;
  }

  /**
   * Get the maximum movement speed of this Troop (Note: Not the current movement speed). (Calls
   * {@link MapObject#getMaxSpeed()})
   *
   * @return The maximum movement speed of this Troop.
   */
  public double getMovementSpeed() {
    return getMaxSpeed();
  }

  /**
   * Get the lipid cost to spawn this Troop.
   *
   * @return The lipid cost to spawn this Troop.
   */
  public int getLipidCost() {
    return lipidsCost;
  }

  /**
   * Get the protein cost to spawn this Troop.
   *
   * @return The protein cost to spawn this Troop.
   */
  public int getProteinCost() {
    return proteinCost;
  }

  /**
   * Get the sugar cost to spawn this Troop.
   *
   * @return The sugar cost to spawn this Troop.
   */
  public int getSugarCost() {
    return sugarsCost;
  }

  /**
   * Get the attack range of this Troop.
   *
   * @return The attack range of this Troop.
   */
  public int getRange() {
    return range;
  }

  /**
   * Get the damage dealt by this Troop's attack.
   *
   * @return The damage dealt by this Troop's attack.
   */
  public int getDamage() {
    return damage;
  }

  /**
   * Get the {@link Lane} this Troop has been assigned to.
   *
   * @return The {@link Lane} this Troop has been assigned to.
   */
  public Lane getLane() {
    return lane;
  }

  public PlayerType getPlayerType(){
    return playerType;
  }

  /**
   * Check whether this Troop can be attacked.
   *
   * @return True if this Troop can be attacked.
   */
  public boolean isAttackable() {
    return attackable;
  }

  /**
   * Set the attacking state of this Troop.
   *
   * @param b The attacking state of this Troop.
   */
  public void setAttacking(boolean b) {
    attacking = b;
  }

  /** Sets the health of the Troop to that of a dead Troop. (Defaults to {@value NO_HEALTH}) */
  public void setDead() {
    setHealth(NO_HEALTH);
  }

  /**
   * Check if the Troop is dead (health = {@value NO_HEALTH}).
   *
   * @return True if the troop is dead.
   */
  public boolean isDead() {
    return health == NO_HEALTH;
  }

  /**
   * Gets a String representation of this Troop which only contains the class name of the Troop.
   *
   * @return The String representation of the Troop.
   */
  @Override
  public String toString() {
    return this.getClass().toString();
  }

  public void setSlowed(long time, int slowPercentage) {
    slowed = true;
    this.slowPercentage = slowPercentage;
    slowTimeEnd = System.currentTimeMillis() + time;
  }

  public boolean isRanged() {
    return ranged;
  }

    public long getCooldown() {
        return cooldown;
    }
}
