package main.com.bodyconquest.entities.Troops;

import main.com.bodyconquest.constants.Lane;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.constants.UnitType;

/**
 * The type Bacteria.
 */
public class Bacteria extends Troop {

    /**
     * The constant MAX_HEALTH.
     */
    public static final int MAX_HEALTH = 100;
    /**
     * The constant MAX_SPEED.
     */
    public static final double MAX_SPEED = 1;
    /**
     * The COOLDOWN till the next hit.
     */
    public static final long COOLDOWN = 1000;
    /**
     * The constant RANGE.
     */
    public static final int RANGE = 50;
    /**
     * The DAMAGE is deals.
     */
    public static final int DAMAGE = 30;

    /**
     * The constant SUGARS_COST.
     */
    public static final int SUGARS_COST = 10;
    /**
     * The constant PROTEINS_COST.
     */
    public static final int PROTEINS_COST = 30;
    /**
     * The constant LIPIDS_COST.
     */
    public static final int LIPIDS_COST = 20;

    /**
     * Instantiates a new Bacteria.
     */
    public Bacteria() {
        super(Lane.BOTTOM, PlayerType.PLAYER_BOTTOM);
        init();
    }

    /**
     * Instantiates a new Bacteria.
     *
     * @param lane       the lane is is spawned it
     * @param playerType the player type it is assigned to
     */
    public Bacteria(Lane lane, PlayerType playerType) {
        super(lane, playerType);
        init();
    }

    /**
     * Instantiates a new Bacteria.
     *
     * @param lane            the lane is is spawned it
     * @param playerType      the player type it is assigned to
     * @param damageMult      the damage multiplier
     * @param speedMult       the speed multiplier
     * @param healthMult      the health multiplier
     * @param attackSpeedMult the attack speed multiplier
     */
    public Bacteria(Lane lane, PlayerType playerType, float damageMult, float speedMult, float healthMult, float attackSpeedMult) {
        super(lane, playerType);
        initSpecific(damageMult, speedMult, healthMult, attackSpeedMult);
    }

    private void init() {
        // Dimensions
        setSize(100, 100);
        setCSize(50, 50);

        // Troop Stats
        health = maxHealth = MAX_HEALTH;
        maxSpeed = 1;
        cooldown = 1000; // Milliseconds
        range = 50;
        damage = 30;
        mapObjectType = UnitType.BACTERIA;

        lipidsCost = LIPIDS_COST;
        sugarsCost = SUGARS_COST;
        proteinCost = PROTEINS_COST;

        killingPoints = 5;
    }

    private void initSpecific(float damageMult, float speedMult, float healthMult, float attackSpeedMult) {

        setSize(100, 100);
        setCSize(50, 50);

        // Troop Stats
        health = maxHealth = MAX_HEALTH;
        health = (int) (health * healthMult);
        maxSpeed = 1 * (double) speedMult;
        cooldown = (int) (1000 * attackSpeedMult); // Milliseconds
        range = 50;
        damage = (int) (30 * damageMult);
        mapObjectType = UnitType.BACTERIA;

        lipidsCost = LIPIDS_COST;
        sugarsCost = SUGARS_COST;
        proteinCost = PROTEINS_COST;

        killingPoints = 5;
    }

    @Override
    public String getPortraitLocation() {
        return "core/assets/bacteria_button_new_s.png";
    }
}
