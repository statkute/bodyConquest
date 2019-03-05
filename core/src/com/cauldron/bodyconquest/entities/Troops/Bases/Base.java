package com.cauldron.bodyconquest.entities.Troops.Bases;

import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.constants.Assets.*;

public abstract class Base extends Troop {

//    private float timeAlive;
//    private float timeOfDmgTaken;
//
//    public static final float BLINK_TIME_AFTER_DMG = 0.25f;


    public Base(PlayerType playerType) {
      super(Lane.ALL, playerType);
      this.attackable = true;
      this.moving = false;
      this.attacking = false;
      this.cooldown = 1500;
//      setTimeAlive(0.0f);
//      setTimeOfDmgTaken(-1.0f);
      this.setSize(Assets.baseWidth, Assets.baseHeight);
      this.setCSize(Assets.baseWidth, Assets.baseHeight);
    }

//    public float getTimeAlive() {
//        return timeAlive;
//    }
//
//    public void setTimeAlive(float timeAlive) {
//        this.timeAlive = timeAlive;
//    }
//
//    public float getTimeOfDmgTaken() {
//        return timeOfDmgTaken;
//    }
//
//    public void setTimeOfDmgTaken(float timeOfDmgTaken) {
//        this.timeOfDmgTaken = timeOfDmgTaken;
//    }
}
