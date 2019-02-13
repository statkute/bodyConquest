package com.cauldron.bodyconquest.entities;

public class Map {

  // Should have some sort of resource manager and system
  public Map() {

  }

  private final int MAP_HEIGHT = 516;
  private final int MAP_WIDTH = 516;

  public int getMaxX() {
    return MAP_WIDTH;
  }

  public int getMaxY() {
    return MAP_HEIGHT;
  }
  /*
  Here should be a function to set which map image this should have and
  client side should be able to interpret this and choose the correct image to draw
  */

}
