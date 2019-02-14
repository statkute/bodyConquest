package com.cauldron.bodyconquest.entities;

public class FluProjectile extends Projectile {

  private double xDest;
  private double yDest;

  public FluProjectile(double x, double y, double xDest, double yDest) {
    setSize(20, 20);
    setPosition(x - (getWidth() / 2), y - (getHeight() / 2));
    this.xDest = xDest;
    this.yDest = yDest;
    init();
  }

  private void init() {
    damage = 40;
    maxSpeed = 2;
    maxTravelDistance = 100;
  }

  @Override
  public void update() {
    moveTowards(xDest, yDest);
    super.update();
  }
}
