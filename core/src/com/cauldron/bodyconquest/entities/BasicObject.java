package com.cauldron.bodyconquest.entities;

import com.cauldron.bodyconquest.constants.Assets.*;

public class BasicObject {

  private double x;
  private double y;
  private int width;
  private int height;
  private double direction;
  private double currentSpeed;
  private double rotation;
  private MapObjectType mapObjectType;

  public void setX(double x) {
    this.x = x;
  }

  public double getX() {
    return x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public double getY() {
    return y;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getWidth() {
    return width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getHeight() {
    return height;
  }

  public void setDirection(double direction) {
    this.direction = direction;
  }

  public double getDirection() {
    return direction;
  }

  public void setCurrentSpeed(double currentSpeed) {
    this.currentSpeed = currentSpeed;
  }

  public double getCurrentSpeed() {
    return currentSpeed;
  }

  public void setMapObjectType(MapObjectType ut) {
    this.mapObjectType = ut;
  }

  public MapObjectType getMapObjectType() {
    return mapObjectType;
  }

  public double getRotation() {
    return rotation;
  }

  public void setRotation(double rotation) {
    this.rotation = rotation;
  }
}
