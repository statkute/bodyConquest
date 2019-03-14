package main.com.bodyconquest.entities.projectiles;

import main.com.bodyconquest.constants.ProjectileType;

public class FluProjectile extends Projectile {

  private double xDest;
  private double yDest;

  public FluProjectile(int damage, double x, double y, double xDest, double yDest) {
    super();
    xInit = x;
    yInit = y;
    this.damage = damage;
    setSize(60, 60);
    setPosition(x - (getWidth() / 2), y - (getHeight() / 2));
    this.xDest = xDest;
    this.yDest = yDest;
    setCSize(45, 15);
    init();
  }

  private void init() {
    maxSpeed = 1;
    maxTravelDistance = 200;
    mapObjectType = ProjectileType.FLU_PROJECTILE;
    piercing = false;
    moveTowards(xDest, yDest);

  }

  @Override
  public void update() {
    super.update();
  }
}
