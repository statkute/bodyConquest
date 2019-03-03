package com.cauldron.bodyconquest.entities.Troops.Bases;

import com.cauldron.bodyconquest.constants.Assets.Lane;
import com.cauldron.bodyconquest.constants.Assets.MapObjectType;
import com.cauldron.bodyconquest.constants.Assets.PlayerType;

public class MonsterBase extends Base {

    public MonsterBase(Lane lane, PlayerType pt){
        super(lane, pt);
        init();
    }

    private void init(){
        this.health = 75;
        this.damage = 8;
        this.maxHealth = health;
        mapObjectType = MapObjectType.MONSTER_BASE;
    }

    @Override
    public String getPortraitLocation() {
        return null; // Shouldn't really be spawned
    }
}
