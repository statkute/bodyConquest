package com.cauldron.bodyconquest.entities.Troops;

import com.cauldron.bodyconquest.constants.Lane;
import com.cauldron.bodyconquest.constants.PlayerType;
import com.cauldron.bodyconquest.entities.projectiles.FluProjectile;
import com.cauldron.bodyconquest.gamestates.EncounterState;

public class Flu extends Troop {

  private EncounterState map;

  public Flu() {
    super(Lane.BOTTOM, PlayerType.PLAYER_BOTTOM);
    init();
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

    // Troop Stats
    health = maxHealth = 70;
    maxSpeed = 0.8;
    attackable = true;
    moving = true;
    attacking = false;
    cooldown = 1300; // Milliseconds
    lastAttack = 0;
    range = 200;
    damage = 40;

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


}
