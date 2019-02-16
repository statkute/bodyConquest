package com.cauldron.bodyconquest.entities.Troops.Bases;

import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.constants.Constants.*;

public abstract class Base extends Troop {

    public Base(Lane lane, PlayerType playerType) {
      super(lane, playerType);
      this.attackable = true;
      this.moving = false;
      this.attacking = false;
      this.
      setSize(150, 150);
    }

}
