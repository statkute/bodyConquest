package main.com.bodyconquest.entities.Troops.Bases;

import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.Lane;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.entities.Troops.Troop;

/**
 * The type Base.
 */
public abstract class Base extends Troop {

    /**
     * Instantiates a new Base.
     *
     * @param playerType the player type it is assigned to
     */
    public Base(PlayerType playerType) {
        super(Lane.ALL, playerType);
        this.attackable = true;
        this.moving = false;
        this.attacking = false;
        this.cooldown = 1500;
        ranged = true;
        this.setSize(Assets.baseWidth, Assets.baseHeight);
        this.setCSize(Assets.baseWidth, Assets.baseHeight);
    }
}
