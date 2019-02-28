package com.cauldron.bodyconquest.entities.Troops.Bases;

import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.gamestates.EncounterState;
import com.cauldron.bodyconquest.constants.Constants.*;

public class BacteriaBase extends Base {

    public BacteriaBase(Lane lane, PlayerType pt){
        super(lane, pt);
        init();
    }

    private void init(){
        this.health = 1000;
        this.damage = 3;
        this.range = 130;
        this.maxHealth = health;
        mapObjectType = MapObjectType.BACTERTIA_BASE;
    }

    @Override
    public void attack(Troop troop) {
        super.attack(troop);
    }

    @Override
    public void update() {

    }
}
