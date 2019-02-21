package com.cauldron.bodyconquest.entities.Troops;

import com.cauldron.bodyconquest.constants.Constants.Lane;
import com.cauldron.bodyconquest.constants.Constants.MapObjectType;
import com.cauldron.bodyconquest.constants.Constants.PlayerType;

public class Bacteria extends Troop {

  public static final int MAX_HEALTH = 100;
  public static final double MAX_SPEED = 1;
  public static final long COOLDOWN = 1000;
  public static final int RANGE = 50;
  public static final int DAMAGE = 30;

  public static final int SUGARS_COST = 0;
  public static final int PROTEINS_COST = 15;
  public static final int LIPIDS_COST = 5;

  public Bacteria() {
    super(Lane.BOTTOM, PlayerType.PLAYER_BOTTOM);
    init();
  }

  public Bacteria(PlayerType playerType, Lane lane) {
    super(lane, playerType);
    init();
  }

  private void init() {
    // Dimensions
    setSize(50, 50);
    setCSize(50, 50);

    // Troop Stats
    health = maxHealth = MAX_HEALTH;
    maxSpeed = 1;
    cooldown = 1000; // Milliseconds
    range = 50;
    damage = 30;
    mapObjectType = MapObjectType.BACTERIA;

    lipidsCost = LIPIDS_COST;
    sugarsCost = SUGARS_COST;
    proteinCost = PROTEINS_COST;

    // Temporary implementation for images for the HUD
    //Animation<TextureRegion> walkAnimation = AnimationWrapper.getSpriteSheet(7, 1, 0.2f, "core/assets/bacteria.png");
    //sprite = new Image(walkAnimation.getKeyFrame(0));
  }
}
