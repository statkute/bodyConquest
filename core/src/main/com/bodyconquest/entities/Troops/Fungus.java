package main.com.bodyconquest.entities.Troops;

import main.com.bodyconquest.constants.Lane;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.constants.UnitType;

/**
 * The type Fungus.
 */
public class Fungus extends Troop {

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

    /**
     * Instantiates a new Fungus.
     */
    public Fungus() {
        super(Lane.BOTTOM, PlayerType.PLAYER_BOTTOM);
        init();
    }

    /**
     * Instantiates a new Fungus.
     *
     * @param lane       the lane is is spawned it
     * @param playerType the player type it is assigned to
     */
/*
  Each moving unit could be given a queue of checkpoints to reach
  and then one left at the enemy base it would be within range and attack
  */
    public Fungus(Lane lane, PlayerType playerType) {
        super(lane, playerType);
        init();
    }

    /**
     * Instantiates a new Fungus.
     *
     * @param lane            the lane is is spawned it
     * @param playerType      the player type it is assigned to
     * @param damageMult      the damage multiplier
     * @param speedMult       the speed multiplier
     * @param healthMult      the health multiplier
     * @param attackSpeedMult the attack speed multiplier
     */
    public Fungus(Lane lane, PlayerType playerType, float damageMult, float speedMult, float healthMult, float attackSpeedMult) {
        super(lane, playerType);
        initSpecific(damageMult, speedMult, healthMult, attackSpeedMult);
    }

    private void initSpecific(float damageMult, float speedMult, float healthMult, float attackSpeedMult) {
        setSize(85, 85);
        setCSize(85, 85);

        // Troop Stats
        health = maxHealth = 250;
        health = (int) (health * healthMult);
        maxSpeed = 0.6 * (double) speedMult;
        cooldown = (int) (1800 * attackSpeedMult); // Milliseconds
        lastAttack = 0;
        range = 50;
        damage = (int) (45 * damageMult);
        mapObjectType = UnitType.FUNGUS;

        lipidsCost = LIPIDS_COST;
        sugarsCost = SUGARS_COST;
        proteinCost = PROTEINS_COST;

        killingPoints = 10;
    }

    private void init() {
        // Dimensions
        setSize(85, 85);
        setCSize(85, 85);

        // Troop Stats
        health = maxHealth = 250;
        maxSpeed = 0.6;
        cooldown = 1800; // Milliseconds
        lastAttack = 0;
        range = 50;
        damage = 45;
        mapObjectType = UnitType.FUNGUS;

        lipidsCost = LIPIDS_COST;
        sugarsCost = SUGARS_COST;
        proteinCost = PROTEINS_COST;

        killingPoints = 10;
    }

    @Override
    public String getPortraitLocation() {
        return "core/assets/virus_button_new.png";
    }
}
