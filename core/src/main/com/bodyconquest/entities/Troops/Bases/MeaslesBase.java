package main.com.bodyconquest.entities.Troops.Bases;

import main.com.bodyconquest.constants.BaseType;
import main.com.bodyconquest.constants.Assets;

public class MeaslesBase extends Base {

    public MeaslesBase(Assets.PlayerType pt){
        super(pt);
        init();
    }

    private void init(){
        this.health = 75;
        this.damage = 8;
        this.maxHealth = health;
        mapObjectType = BaseType.MEASLES_BASE;
    }

    @Override
    public String getPortraitLocation() {
        return null; // Shouldn't really be spawned
    }
}
