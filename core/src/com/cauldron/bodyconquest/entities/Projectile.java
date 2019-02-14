package com.cauldron.bodyconquest.entities;

import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.gamestates.EncounterState;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Projectile extends MapObject {

  protected int damage;
  protected float maxTravelDistance;
  protected float distanceTraveled;

  protected boolean hit;
  protected boolean piercing;
  protected ArrayList<Troop> hitTroops;

  protected boolean remove;
  protected EncounterState screen;

  public Projectile(EncounterState screen) {
    this.screen = screen;
    // Maybe a better way of doing this
    hitTroops = new ArrayList<Troop>();
    this.collidable = true;
    initDefault();
  }

  private void initDefault() {
    // Maxed out because I don't want to do fine tuning right now
    stopSpeed = 100000;
    acceleration = 100000;
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
    //remove();
  }

  public boolean getRemove() {
    return remove;
  }

  public void update() {
    move();
  }
//
//  @Override
//  public void act(float delta) {
//    super.act(delta);
//
////    if(remove) {
////      remove();
////      return;
////    }
//
//    float dx, dy;
//    dx = speed * delta * (float) Math.cos(getAngle());
//    dy = speed * delta * (float) Math.sin(getAngle());
//
//    setPosition(getX() - dx, getY() - dy);
//  }
//
//  public double getAngle() {
//    return angle;
//  }
//
//  public void setAngle(double angle) {
//    this.angle = angle % 360;
//  }

  public void checkHit(CopyOnWriteArrayList<Troop> enemies) {
    for(Troop enemy : enemies) {
      if(getBounds().intersects(enemy.getBounds())) {
        hit(enemy);
      }
    }
  }

}
