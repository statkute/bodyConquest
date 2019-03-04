package com.cauldron.bodyconquest.entities.Troops.Bases;

import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.constants.Assets.*;

public abstract class Base extends Troop {

    public Base(PlayerType playerType) {
      super(Lane.ALL, playerType);
      this.attackable = true;
      this.moving = false;
      this.attacking = false;
      this.cooldown = 1500;
      this.setSize(Assets.baseWidth, Assets.baseHeight);
      this.setCSize(Assets.baseWidth, Assets.baseHeight);
    }
}
