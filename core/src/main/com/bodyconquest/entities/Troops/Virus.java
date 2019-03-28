package main.com.bodyconquest.entities.Troops;

import main.com.bodyconquest.constants.Lane;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.constants.UnitType;
import main.com.bodyconquest.entities.projectiles.VirusProjectile;
import main.com.bodyconquest.gamestates.EncounterState;

/**
 * The type Virus.
 */
public class Virus extends Troop {

    /**
     * The constant MAX_HEALTH.
     */
    public static final int MAX_HEALTH = 70;
    /**
     * The constant MAX_SPEED.
     */
    public static final double MAX_SPEED = 2;
    /**
     * The COOLDOWN till the next hit.
     */
    public static final long COOLDOWN = 3000;
    /**
     * The constant RANGE.
     */
    public static final int RANGE = 200;
    /**
     * The DAMAGE is deals.
     */
    public static final int DAMAGE = 40;

    /**
     * The constant SUGARS_COST.
     */
    public static final int SUGARS_COST = 80;
    /**
     * The constant PROTEINS_COST.
     */
    public static final int PROTEINS_COST = 30;
    /**
     * The constant LIPIDS_COST.
     */
    public static final int LIPIDS_COST = 20;

    private EncounterState map;

    /**
     * Instantiates a new Virus.
     */
    public Virus() {
        super(Lane.BOTTOM, PlayerType.PLAYER_BOTTOM);
        init();
    }

    /**
     * Instantiates a new Virus.
     *
     * @param lane       the lane is is spawned it
     * @param playerType the player type it is assigned to
     */
    public Virus(Lane lane, PlayerType playerType) {
        super(lane, playerType);
        init();
    }

    /**
     * Instantiates a new Virus.
     *
     * @param map             the map is it given for the projectiles to be spawned in
     * @param lane            the lane is is spawned it
     * @param playerType      the player type it is assigned to
     * @param damageMult      the damage multiplier
     * @param speedMult       the speed multiplier
     * @param healthMult      the health multiplier
     * @param attackSpeedMult the attack speed multiplier
     */
    public Virus(EncounterState map, Lane lane, PlayerType playerType, float damageMult, float speedMult, float healthMult, float attackSpeedMult) {
        super(lane, playerType);
        this.map = map;
        initSpecific(damageMult, speedMult, healthMult, attackSpeedMult);
    }

    private void initSpecific(float damageMult, float speedMult, float healthMult, float attackSpeedMult) {
        setSize(50, 50);
        setCSize(50, 50);
        // Troop Stats
        health = MAX_HEALTH;
        health = (int) (health * healthMult);
        maxSpeed = MAX_SPEED * (double) speedMult;
        attackable = true;
        moving = true;
        attacking = false;
        cooldown = (int) (COOLDOWN * attackSpeedMult); // Milliseconds
        lastAttack = 0;
        range = RANGE;
        damage = (int) (DAMAGE * damageMult);

        mapObjectType = UnitType.VIRUS;

        lipidsCost = LIPIDS_COST;
        sugarsCost = SUGARS_COST;
        proteinCost = PROTEINS_COST;

        killingPoints = 7;

    }

    /**
     * Instantiates a new Virus.
     *
     * @param map        the map is it given for the projectiles to be spawned in
     * @param lane       the lane is is spawned it
     * @param playerType the player type it is assigned to
     */
/*
  Each moving unit could be given a queue of checkpoints to reach
  and then one left at the enemy base it would be within range and attack
  */
    public Virus(EncounterState map, PlayerType playerType, Lane lane) {
        super(lane, playerType);
        this.map = map;
        init();
    }

    private void init() {
        // Dimensions
        setSize(50, 50);
        setCSize(50, 50);
        // Troop Stats
        health = MAX_HEALTH;
        maxSpeed = MAX_SPEED;
        attackable = true;
        moving = true;
        attacking = false;
        cooldown = COOLDOWN; // Milliseconds
        lastAttack = 0;
        range = RANGE;
        damage = DAMAGE;
        ranged = true;

        mapObjectType = UnitType.VIRUS;

        lipidsCost = LIPIDS_COST;
        sugarsCost = SUGARS_COST;
        proteinCost = PROTEINS_COST;

        killingPoints = 7;
    }

    @Override
    public void attack(Troop troop) {
        if (troop != null && troop.isAttackable()) {
            setMoving(false);
            long time = System.currentTimeMillis();
            if (!attacking && (time > lastAttack + cooldown)) {
                VirusProjectile proj = new VirusProjectile(getDamage(), getCentreX(), getCentreY(), troop.getCentreX(), troop.getCentreY());
                map.addProjectile(proj, playerType);
                lastAttack = time;
            }
        } else {
            if (!moving) setMoving(true);
        }
    }


    @Override
    public String getPortraitLocation() {
        return "core/assets/flu_button_new.png";
    }
}
