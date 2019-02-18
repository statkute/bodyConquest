package com.cauldron.bodyconquest.entities.Troops.Bases;


import com.cauldron.bodyconquest.constants.Lane;
import com.cauldron.bodyconquest.constants.PlayerType;

public class MonsterBase extends Base {

    public MonsterBase(Lane lane, PlayerType pt){
        super(lane, pt);
        init();
    }

    private void init(){
        this.health = 75;
        this.damage = 8;
    }

}
