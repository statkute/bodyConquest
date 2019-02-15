package com.cauldron.bodyconquest.entities;

public class BasicObject {

  private Object animation;
  private double x;
  private double y;
  private int width;
  private int height;
  private double direction;
  private double currentSpeed;
  private String imagePath;

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

  public void setAnimation(Object animation) {
    this.animation = animation;
  }

  public Object getAnimation() {
    return animation;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public String getImagePath() {
    return imagePath;
  }
}