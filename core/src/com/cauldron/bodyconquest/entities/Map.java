package com.cauldron.bodyconquest.entities;

public class Map {

  // Should have some sort of resource manager and system
  public Map() {}

  private final double MAP_HEIGHT = 516;
  private final double MAP_WIDTH = 516;

  public double getMaxX() {
    return MAP_WIDTH;
  }

  public double getMaxY() {
    return MAP_HEIGHT;
  }

  public double getTop() {
    return MAP_HEIGHT;
  }

  public double getBottom() {
    return 0;
  }

  public double getLeft() {
    return 0;
  }

  public double getRight() {
    return MAP_WIDTH;
  }

  /*
  Here should be a function to set which map image this should have and
  client side should be able to interpret this and choose the correct image to draw
  */

}
