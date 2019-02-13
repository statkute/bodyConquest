package com.cauldron.bodyconquest.entities;

import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.gamestates.EncounterState;

import java.util.ArrayList;

public abstract class Projectile extends MapObject {

  protected float speed;
  protected int damage;
  protected float maxTravelDistance;
  protected float distanceTraveled;

  protected boolean hit;
  protected boolean piercing;
  protected ArrayList<Troop> hitTroops;

  protected boolean remove;
  protected EncounterState screen;

  private double angle;

  public Projectile(EncounterState screen) {
    this.screen = screen;
    // Maybe a better way of doing this
    hitTroops = new ArrayList<Troop>();
  }

  public void hit(Troop troop) {
    if (remove) return;
    if (piercing) {
      if (hitTroops.contains(troop)) return;
      hitTroops.add(troop);
    }
    troop.hit(damage);
    setRemove();
    // Make sure this immediately removes projectile of necessary and
    // doesn't hit enemies multiple unless intended to do so
  }

  public void setRemove() {
    remove = true;
    remove();
  }

  public boolean getRemove() {
    return remove;
  }

  public void setTargetLocation(float x, float y) {
    double relX = x - this.getCentreX();
    double relY = y - this.getCentreY();
    setAngle(Math.atan(relY / relX));
  }

  @Override
  public void act(float delta) {
    super.act(delta);

    if(remove) {
      remove();
      return;
    }

    float dx, dy;
    dx = speed * delta * (float) Math.cos(getAngle());
    dy = speed * delta * (float) Math.sin(getAngle());

    setPosition(getX() - dx, getY() - dy);
  }

  public double getAngle() {
    return angle;
  }

  public void setAngle(double angle) {
    this.angle = angle % 360;
  }

  public void checkHit(ArrayList<Troop> enemies) {
    for(Troop enemy : enemies) {
      if(getBounds().overlaps(enemy.getBounds())) {
        hit(enemy);
      }
    }
  }

}
