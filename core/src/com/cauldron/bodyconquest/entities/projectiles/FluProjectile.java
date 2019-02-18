package com.cauldron.bodyconquest.entities.projectiles;

import com.cauldron.bodyconquest.constants.Constants;

public class FluProjectile extends Projectile {

  private double xDest;
  private double yDest;

  public FluProjectile(int damage, double x, double y, double xDest, double yDest) {
    this.damage = damage;
    setSize(20, 20);
    setPosition(x - (getWidth() / 2), y - (getHeight() / 2));
    this.xDest = xDest;
    this.yDest = yDest;
    init();
  }

  private void init() {
    maxSpeed = 2;
    maxTravelDistance = 200;
    mapObjectType = Constants.MapObjectType.FLUPROJECTILE;

  }

  @Override
  public void update() {
    moveTowards(xDest, yDest);
    super.update();
  }
}
