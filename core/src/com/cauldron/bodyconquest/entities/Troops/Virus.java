package com.cauldron.bodyconquest.entities.Troops;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cauldron.bodyconquest.handlers.AnimationWrapper;
import com.cauldron.bodyconquest.constants.Constants.*;

public class Virus extends Troop {

  public Virus() {
    super(Lane.BOTTOM, PlayerType.PLAYER_BOTTOM);
    init();
  }

  /*
  Each moving unit could be given a queue of checkpoints to reach
  and then one left at the enemy base it would be within range and attack
  */
  public Virus(PlayerType playerType, Lane lane) {
    super(lane, playerType);
    init();
  }
  private void init(){
    // Dimensions
    setSize(50, 50);

    // Troop Stats
    health = maxHealth = 100;
    maxSpeed = 1;
    cooldown = 1000; // Milliseconds
    lastAttack = 0;
    range = 50;
    damage = 30;
    mapObjectType = MapObjectType.VIRUS;

    // Temporary implementation for images for the HUD
    Animation<TextureRegion> walkAnimation = AnimationWrapper.getSpriteSheet(7, 1, 0.2f, "core/assets/virus.png");
    sprite = new Image(walkAnimation.getKeyFrame(0));
  }

}
