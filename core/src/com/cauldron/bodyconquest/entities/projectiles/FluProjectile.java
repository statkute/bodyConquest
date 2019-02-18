package com.cauldron.bodyconquest.entities.projectiles;

import com.cauldron.bodyconquest.constants.Constants;

public class FluProjectile extends Projectile {

  private double xDest;
  private double yDest;

  public FluProjectile(int damage, double x, double y, double xDest, double yDest) {
    super();
    xInit = x;
    yInit = y;
    this.damage = damage;
    setSize(60, 60);
    setPosition(x + getWidth(), y + getHeight());
    this.xDest = xDest;
    this.yDest = yDest;
    setCSize(20, 20);
    init();
  }

  private void init() {
    maxSpeed = 1;
    maxTravelDistance = 200;
    mapObjectType = Constants.MapObjectType.FLUPROJECTILE;
    piercing = false;
    moveTowards(xDest, yDest);

  }

  @Override
  public void update() {
    super.update();
  }
}
