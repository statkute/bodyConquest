package com.cauldron.bodyconquest.entities.Troops;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cauldron.bodyconquest.gamestates.EncounterState.Lane;
import com.cauldron.bodyconquest.gamestates.EncounterState.PlayerType;
import com.cauldron.bodyconquest.handlers.AnimationWrapper;

public class Bacteria extends Troop {

  public Bacteria() {
    super(Lane.BOT, PlayerType.BOT_PLAYER);
    init();
  }

  /*
  Each moving unit could be given a queue of checkpoints to reach
  and then one left at the enemy base it would be within range and attack
  */
  public Bacteria(PlayerType playerType, Lane lane) {
    super(lane, playerType);
    init();
  }

  private void init() {
    // Dimensions
    setSize(50, 50);
    setCSize(50, 50);

    // Troop Stats
    health = maxHealth = 100;
    maxSpeed = 1;
    cooldown = 1000; // Milliseconds
    range = 50;
    damage = 30;

    // Temporary implementation for images for the HUD
    Animation<TextureRegion> walkAnimation = AnimationWrapper.getSpriteSheet(7, 1, 0.2f, "core/assets/bacteria.png");
    sprite = new Image(walkAnimation.getKeyFrame(0));
  }
}
