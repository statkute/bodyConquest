package main.com.bodyconquest.entities.Troops;

import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.Lane;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.constants.UnitType;

public class WhiteCell extends Troop {

    public static final int MAX_HEALTH = 150;
    public static final double MAX_SPEED = 1.2;
    public static final long COOLDOWN = 1500;
    public static final int RANGE = 50;
    public static final int DAMAGE = 45;

    public static final int COST = 20;

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
