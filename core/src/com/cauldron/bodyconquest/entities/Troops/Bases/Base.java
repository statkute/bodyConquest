package com.cauldron.bodyconquest.entities.Troops.Bases;

import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.gamestates.EncounterState;
import com.cauldron.bodyconquest.gamestates.EncounterState.PlayerType;

public abstract class Base extends Troop {

    public Base(EncounterState.Lane lane, PlayerType playerType) {
      super(lane, playerType);
      this.attackable = true;
      this.moving = false;
      this.attacking = false;
      setSize(150, 150);
    }

}
