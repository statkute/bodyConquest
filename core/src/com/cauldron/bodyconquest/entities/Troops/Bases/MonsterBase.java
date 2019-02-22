package com.cauldron.bodyconquest.entities.Troops.Bases;

import com.cauldron.bodyconquest.constants.Constants.*;

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

}
