package com.cauldron.bodyconquest.entities.Troops.Bases;

import com.cauldron.bodyconquest.constants.Assets.PlayerType;
import com.cauldron.bodyconquest.gamestates.BaseType;

public class MeaslesBase extends Base {

    public MeaslesBase(PlayerType pt){
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
