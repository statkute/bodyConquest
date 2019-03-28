package main.com.bodyconquest.entities.Troops;

import main.com.bodyconquest.constants.Lane;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.constants.UnitType;
import main.com.bodyconquest.entities.projectiles.FungusProjectile;
import main.com.bodyconquest.entities.projectiles.Projectile;
import main.com.bodyconquest.entities.projectiles.VirusProjectile;
import main.com.bodyconquest.gamestates.EncounterState;

/**
 * The type Measles fungus.
 */
public class MeaslesFungus extends Fungus {

    /**
     * The constant MAX_HEALTH.
     */
    public static final int MAX_HEALTH = 250;
    /**
     * The constant MAX_SPEED.
     */
    public static final double MAX_SPEED = 0.6;
    /**
     * The COOLDOWN till the next hit.
     */
    public static final long COOLDOWN = 1800;
    /**
     * The constant RANGE.
     */
    public static final int RANGE = 110;
    /**
     * The DAMAGE is deals.
     */
    public static final int DAMAGE = 45;

    /**
     * The constant SUGARS_COST.
     */
    public static final int SUGARS_COST = 20;
    /**
     * The constant PROTEINS_COST.
     */
    public static final int PROTEINS_COST = 20;
    /**
     * The constant LIPIDS_COST.
     */
    public static final int LIPIDS_COST = 50;
    private EncounterState map;

    /**
     * Instantiates a new Measles fungus.
     */
    public MeaslesFungus() {
        super(Lane.BOTTOM, PlayerType.PLAYER_BOTTOM);
        init();
        ranged = true;
    }

    /**
     * Instantiates a new Measles fungus.
     *
     * @param lane       the lane is is spawned it
     * @param playerType the player type it is assigned to
     */
/*
  Each moving unit could be given a queue of checkpoints to reach
  and then one left at the enemy base it would be within range and attack
  */
    public MeaslesFungus(Lane lane, PlayerType playerType) {
        super(lane, playerType);
        ranged = true;
        init();
    }

    /**
     * Instantiates a new Measles fungus.
     *
     * @param map             the map is it given for the projectiles to be spawned in
     * @param lane            the lane is is spawned it
     * @param playerType      the player type it is assigned to
     * @param damageMult      the damage multiplier
     * @param speedMult       the speed multiplier
     * @param healthMult      the health multiplier
     * @param attackSpeedMult the attack speed multiplier
     */
    public MeaslesFungus(EncounterState map, Lane lane, PlayerType playerType, float damageMult, float speedMult, float healthMult, float attackSpeedMult) {
        super(lane, playerType);
        this.map = map;
        ranged = true;
        initSpecific(damageMult, speedMult, healthMult, attackSpeedMult);
    }

    private void initSpecific(float damageMult, float speedMult, float healthMult, float attackSpeedMult) {
        setSize(85, 85);
        setCSize(85, 85);

        // Troop Stats
        health = MAX_HEALTH;
        health = (int) (health * healthMult);
        maxSpeed = MAX_SPEED * (double) speedMult;
        cooldown = (int) (COOLDOWN * attackSpeedMult); // Milliseconds
        lastAttack = 0;
        range = RANGE;
        damage = (int) (DAMAGE * damageMult);
        mapObjectType = UnitType.FUNGUS;

        lipidsCost = LIPIDS_COST;
        sugarsCost = SUGARS_COST;
        proteinCost = PROTEINS_COST;
        ranged = true;
        killingPoints = 10;
    }

    private void init() {
        // Dimensions
        setSize(85, 85);
        setCSize(85, 85);

        // Troop Stats
        health = MAX_HEALTH;
        maxSpeed = MAX_SPEED;
        cooldown = COOLDOWN; // Milliseconds
        lastAttack = 0;
        range = RANGE;
        damage = DAMAGE;
        mapObjectType = UnitType.FUNGUS;

        lipidsCost = LIPIDS_COST;
        sugarsCost = SUGARS_COST;
        proteinCost = PROTEINS_COST;

        killingPoints = 10;
    }

    @Override
    public void attack(Troop troop) {
        if (troop != null && troop.isAttackable()) {
            setMoving(false);
            long time = System.currentTimeMillis();
            if (!attacking && (time > lastAttack + cooldown)) {
                FungusProjectile proj = new FungusProjectile(getDamage(), getCentreX(), getCentreY(), troop.getCentreX(), troop.getCentreY());
                map.addProjectile(proj, playerType);
                lastAttack = time;
            }
        } else {
            if (!moving) setMoving(true);
        }
    }

    @Override
    public String getPortraitLocation() {
        return "core/assets/virus_button.png";
    }
}
