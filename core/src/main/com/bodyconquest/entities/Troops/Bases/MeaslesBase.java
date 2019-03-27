package main.com.bodyconquest.entities.Troops.Bases;

import main.com.bodyconquest.constants.BaseType;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.PlayerType;

public class MeaslesBase extends Base {

    public MeaslesBase(PlayerType pt){
        super(pt);
        init();
    }

    private void init(){
        this.health = 750;
        this.damage = 8;
        this.maxHealth = health;
        mapObjectType = BaseType.MEASLES_BASE;
    }

    @Override
    public String getPortraitLocation() {
        return null; // Shouldn't really be spawned
    }
}
