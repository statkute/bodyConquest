package main.com.bodyconquest.entities.Troops.Bases;

import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.entities.Troops.Troop;
import main.com.bodyconquest.constants.BaseType;

/**
 * The type Influenza base.
 */
public class InfluenzaBase extends Base {

    /**
     * Instantiates a new Influenza base.
     *
     * @param pt the player type it is assigned to
     */
    public InfluenzaBase(PlayerType pt) {
        super(pt);
        init();
    }

    private void init() {
        this.health = 1000;
        this.damage = 3;
        this.range = 130;
        this.maxHealth = health;
        mapObjectType = BaseType.INFLUENZA_BASE;
    }

    @Override
    public void attack(Troop troop) {
        super.attack(troop);
    }

    @Override
    public void update() {

    }

    @Override
    public String getPortraitLocation() {
        return null; // Shouldn't really be spawned
    }
}
