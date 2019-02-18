package com.cauldron.bodyconquest.game_logic;

import com.cauldron.bodyconquest.entities.BasicObject;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Communicator {

  private CopyOnWriteArrayList<BasicObject> objects;

  public Communicator() {
    objects = new CopyOnWriteArrayList<BasicObject>();
  }

  public CopyOnWriteArrayList<BasicObject> getAllObjects() {
    return objects;
  }

  public void populateObjectList(CopyOnWriteArrayList<BasicObject> os) {
    this.objects = os;
  }

}
