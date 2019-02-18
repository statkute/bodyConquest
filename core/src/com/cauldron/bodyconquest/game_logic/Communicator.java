package com.cauldron.bodyconquest.game_logic;

import com.cauldron.bodyconquest.entities.BasicObject;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Communicator {

  private CopyOnWriteArrayList<BasicObject> objects;
  private int bottomHealthPercentage;
  private int topHealthPercentage;

  public Communicator() {
    objects = new CopyOnWriteArrayList<BasicObject>();
  }

  public CopyOnWriteArrayList<BasicObject> getAllObjects() {
    return objects;
  }

  public void populateObjectList(CopyOnWriteArrayList<BasicObject> os) {
    this.objects = os;
  }

  public void setBottomHealthPercentage(int health) {
    bottomHealthPercentage = health;
  }

  public void setTopHealthPercentage(int health) {
    topHealthPercentage = health;
  }

  public int getBottomHealthPercentage() {
    return bottomHealthPercentage;
  }

  public int getTopHealthPercentage() {
    return topHealthPercentage;
  }
}
