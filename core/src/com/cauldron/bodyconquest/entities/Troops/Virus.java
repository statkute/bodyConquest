package com.cauldron.bodyconquest.entities.Troops;

import com.cauldron.bodyconquest.constants.Constants.Lane;
import com.cauldron.bodyconquest.constants.Constants.MapObjectType;
import com.cauldron.bodyconquest.constants.Constants.PlayerType;

public class Virus extends Troop {

  public static final int SUGARS_COST = 20;
  public static final int PROTEINS_COST = 20;
  public static final int LIPIDS_COST = 40;

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
    setSize(85, 85);
    setCSize(85, 85);

    // Troop Stats
    health = maxHealth = 250;
    maxSpeed = 0.6;
    cooldown = 1800; // Milliseconds
    lastAttack = 0;
    range = 50;
    damage = 45;
    mapObjectType = MapObjectType.VIRUS;

    lipidsCost = LIPIDS_COST;
    sugarsCost = SUGARS_COST;
    proteinCost = PROTEINS_COST;

    // Temporary implementation for images for the HUD
    //Animation<TextureRegion> walkAnimation = AnimationWrapper.getSpriteSheet(7, 1, 0.2f, "core/assets/virus.png");
    //sprite = new Image(walkAnimation.getKeyFrame(0));
  }

}
