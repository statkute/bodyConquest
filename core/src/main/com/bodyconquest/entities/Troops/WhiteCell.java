package main.com.bodyconquest.entities.Troops;

import main.com.bodyconquest.constants.Lane;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.constants.UnitType;

/**
 * The type White cell.
 */
public class WhiteCell extends Troop {

    /**
     * The constant MAX_HEALTH.
     */
    public static final int MAX_HEALTH = 150;
    /**
     * The constant MAX_SPEED.
     */
    public static final double MAX_SPEED = 0.6;
    /**
     * The constant COOLDOWN.
     */
    public static final long COOLDOWN = 1500;
    /**
     * The constant RANGE.
     */
    public static final int RANGE = 50;
    /**
     * The constant DAMAGE.
     */
    public static final int DAMAGE = 45;

    /**
     * The constant COST - as this unit is an AI unit, cost is only required for inheritance reasons.
     */
    public static final int COST = 20;

    /**
     * Instantiates a new White cell.
     */
    public WhiteCell() {
        super(Lane.BOTTOM, PlayerType.PLAYER_BOTTOM);
        init();
    }

    /**
     * Instantiates a new White cell.
     *
     * @param lane       the lane it is in
     * @param playerType the player type it is assigned to
     */
    public WhiteCell(Lane lane, PlayerType playerType) {
        super(lane, playerType);
        init();
    }

    private void init() {
        // Dimensions
        setSize(100, 100);
        setCSize(50, 50);

        // Troop Stats
        health = maxHealth = MAX_HEALTH;
        maxSpeed = MAX_SPEED;
        cooldown = COOLDOWN; // Milliseconds
        range = RANGE;
        damage = DAMAGE;
        mapObjectType = UnitType.WHITE_CELL;

        //super needs these; useless for multiplayer AI units
        lipidsCost = COST;
        sugarsCost = COST;
        proteinCost = COST;

        //almost worthless
        killingPoints = 1;

        // Temporary implementation for images for the HUD
        //Animation<TextureRegion> walkAnimation = AnimationWrapper.getSpriteSheet(7, 1, 0.2f, "core/assets/bacteria.png");
        //sprite = new Image(walkAnimation.getKeyFrame(0));
    }

    @Override
    public String getPortraitLocation() {
        return "core/assets/bacteria_ai.png";
    }
}
