package com.cauldron.bodyconquest.entities.Troops;

import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.constants.Assets.Lane;
import com.cauldron.bodyconquest.constants.Assets.PlayerType;
import com.cauldron.bodyconquest.entities.projectiles.FluProjectile;
import com.cauldron.bodyconquest.gamestates.EncounterState;

public class Flu extends Troop {

  public static final int SUGARS_COST = 80;
  public static final int PROTEINS_COST = 30;
  public static final int LIPIDS_COST = 20;

  private EncounterState map;

  public Flu() {
    super(Lane.BOTTOM, PlayerType.PLAYER_BOTTOM);
    init();
    killingPoints = 7;
  }

  /*
  Each moving unit could be given a queue of checkpoints to reach
  and then one left at the enemy base it would be within range and attack
  */
  public Flu(EncounterState map, PlayerType playerType, Lane lane) {
    super(lane, playerType);
    this.map = map;
    init();
  }

  private void init() {
    // Dimensions
    setSize(50, 50);
    setCSize(50, 50);
    // Troop Stats
    health = maxHealth = 70;
    maxSpeed = 2;
    attackable = true;
    moving = true;
    attacking = false;
    cooldown = 3000; // Milliseconds
    lastAttack = 0;
    range = 200;
    damage = 40;

    mapObjectType = Assets.UnitType.FLU;

    lipidsCost = LIPIDS_COST;
    sugarsCost = SUGARS_COST;
    proteinCost = PROTEINS_COST;

    // Temporary implementation for images for the HUD
    //Animation<TextureRegion> walkAnimation = AnimationWrapper.getSpriteSheet(7, 1, 0.2f, "core/assets/flu.png");
    //sprite = new Image(walkAnimation.getKeyFrame(0));
  }

  @Override
  public void attack(Troop troop) {
    if (troop != null && troop.isAttackable()) {
      setMoving(false);
      long time = System.currentTimeMillis();
      if (!attacking && (time > lastAttack + cooldown)) {
        FluProjectile proj = new FluProjectile(getDamage(), getCentreX(), getCentreY(), troop.getCentreX(), troop.getCentreY());
        map.addProjectile(proj, playerType);
        lastAttack = time;
      }
    } else {
      if (!moving) setMoving(true);
    }
  }


  @Override
  public String getPortraitLocation() {
    return "core/assets/flu_button.png";
  }
}
