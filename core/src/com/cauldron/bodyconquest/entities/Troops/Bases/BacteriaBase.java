package com.cauldron.bodyconquest.entities.Troops.Bases;

import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.gamestates.EncounterState;

public class BacteriaBase extends Base {

    public BacteriaBase(EncounterState.Lane lane, EncounterState.PlayerType pt){
        super(lane, pt);
        init();
    }

    private void init(){
        this.health = 100;
        this.damage = 3;
        this.range = 130;
    }

    @Override
    public void attack(Troop troop) {
        super.attack(troop);
    }

    @Override
    public void update() {

    }
}
