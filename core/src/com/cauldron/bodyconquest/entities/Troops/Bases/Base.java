package com.cauldron.bodyconquest.entities.Troops.Bases;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;
import com.cauldron.bodyconquest.gamestates.EncounterScreen.PlayerType;

public abstract class Base extends Troop {

    protected Animation<TextureRegion> imageBase;

    public Base(EncounterScreen.Lane lane, PlayerType playerType) {
      super(lane);
      this.playerType = playerType;
      this.attackable = true;
      this.moving = false;
      this.attacking = false;
      setSize(120, 120);

    }

}
