package main.com.bodyconquest.entities.projectiles;

import main.com.bodyconquest.constants.ProjectileType;

public class FungusProjectile extends Projectile {

  private double xDest;
  private double yDest;

  public FungusProjectile(int damage, double x, double y, double xDest, double yDest) {
    super();
    xInit = x;
    yInit = y;
    this.damage = damage;
    setSize(80, 80);
    setPosition(x - (getWidth() / 2.0), y - (getHeight() / 2.0));
    this.xDest = xDest;
    this.yDest = yDest;
    setCSize(60, 20);
    init();
  }

  private void init() {
    maxSpeed = 2.0;
    maxTravelDistance = 200;
    mapObjectType = ProjectileType.FUNGUS_PROJECTILE;
    moveTowards(xDest, yDest);
  }

  @Override
  public void update() {
    super.update();
  }
}
