package main.com.bodyconquest.entities.projectiles;

import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.entities.MapObject;
import main.com.bodyconquest.entities.Troops.Troop;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Projectile extends MapObject {

  protected int damage;

  protected double maxTravelDistance;
  protected double distanceTraveled;

  protected double xInit;
  protected double yInit;

  protected boolean remove;

  public Projectile() {
    super();
    // Maybe a better way of doing this
    this.collidable = true;
    initDefault();
  }

  private void initDefault() {
    // Maxed out because I don't want to do fine tuning right now
    stopSpeed = 100000;
    acceleration = 100000;
    //mapObjectType = Assets.UnitType.VIRUS;
    distanceTraveled = 0;
    moving = true;
    collidable = true;
    remove = false;
    playerType = PlayerType.AI;
  }

  public void hit(Troop troop) {
    if (remove) return;
    troop.hit(damage);
    setRemove();
  }

  public void setRemove() {
    remove = true;
  }

  public boolean getRemove() {
    return remove;
  }

  public void update() {
    if(distanceTraveled >= maxTravelDistance) setRemove();
    move();
    distanceTraveled = distFrom(xInit, yInit);
  }

  public void checkHit(CopyOnWriteArrayList<Troop> enemies) {
    for(Troop enemy : enemies) {
      if(checkCollision(enemy)/*getBounds().intersects(enemy.getBounds())*/) {
        System.out.println("Wanting to hit");
        hit(enemy);
      }
    }
  }

  @Override
  public void move() {
    super.move();
    setX(dx);
    setY(dy);
  }

  @Override
  public Shape getBounds() {
    Shape originalRect = super.getBounds();
    AffineTransform trans = AffineTransform.getRotateInstance(getDirection() + Math.PI, getX() + ((getWidth() - getCwidth()) / 2), getY() + ((getHeight() - getCheight()) / 2));
    return trans.createTransformedShape(originalRect);
  }
}
