package com.cauldron.bodyconquest.entities;

import java.awt.*;

/*
Contains all the properties and methods that all map objects must have.
It also extends Actor.
*/

public abstract class MapObject {

  // Directions in degrees with up being 0
  private final double UP_DIRECTION = 0;
  private final double DOWN_DIRECTION = 180;
  private final double LEFT_DIRECTION = -90;
  private final double RIGHT_DIRECTION = 90;

  // Current x and y
  private double x;
  private double y;
  // Destination x and y
  private double dx;
  private double dy;
  // Object width and height
  private int width;
  private int height;
  // Collision box width and height
  private int cwidth;
  private int cheight;

  // Movement attributes
  protected double acceleration;
  protected double currentSpeed;
  protected double maxSpeed;
  protected double stopSpeed;
  private double direction;

  // Properties
  protected boolean collidable;

  // States
  protected boolean moving;

  public MapObject() {
    setWidth(0);
    setHeight(0);
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public double getCwidth() {
    return cwidth;
  }

  public double getCheight() {
    return cheight;
  }

  public double distFrom(MapObject object) {
    return distFrom(object.getCentreX(), object.getCentreY());
  }

  public double distFrom(double x, double y) {
    double xDif = this.getCentreX() - x;
    double yDif = this.getCentreY() - y;
    return Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2));
  }

  public double getCentreX() {
    return getX() + (getWidth() / 2);
  }

  public double getCentreY() {
    return getY() + (getHeight() / 2);
  }

  public boolean isCollidable() {
    return collidable;
  }

  public Rectangle getBounds() {
    return new Rectangle((int) x, (int) y, cwidth, cheight);
  }

  public boolean checkCollision(MapObject object) {
    if (object.getBounds().intersects(getBounds())) return true;
    return false;
  }

  public void setDirectionUp() {
    direction = Math.toRadians(UP_DIRECTION);
  }

  public void setDirectionDown() {
    direction = Math.toRadians(DOWN_DIRECTION);
  }

  public void setDirectionLeft() {
    direction = Math.toRadians(LEFT_DIRECTION);
  }

  public void setDirectionRight() {
    direction = Math.toRadians(RIGHT_DIRECTION);
  }

  public void setDirectionUpLeft() {
    direction = Math.toRadians((UP_DIRECTION + LEFT_DIRECTION) / 2);
  }

  public void setDirectionUpRight() {
    direction = Math.toRadians((UP_DIRECTION + RIGHT_DIRECTION) / 2);
  }

  public void setDirectionDownLeft() {
    direction = Math.toRadians((-DOWN_DIRECTION + LEFT_DIRECTION) / 2);
  }

  public void setDirectionDownRight() {
    direction = Math.toRadians((DOWN_DIRECTION + RIGHT_DIRECTION) / 2);
  }

  private void setDirection(double angle) {
    direction = angle;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setCWidth(int cwidth) {
    this.cwidth = cwidth;
  }

  public void setCHeight(int cheight) {
    this.cheight = cheight;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getMaxSpeed() {
    return maxSpeed;
  }

  public void setPosition(double x, double y) {
    setX(x);
    setY(y);
  }

  public void setSize(int width, int height) {
    setWidth(width);
    setHeight(height);
  }

  public void setCSize(int cwidth, int cheight) {
    setCWidth(cwidth);
    setCHeight(cheight);
  }

  public void setMoving(boolean b) {
    moving = b;
  }

  public void moveTowards(MapObject o) {
    moveTowards(o.getCentreX(), o.getCentreY());
  }

  public void moveTowards(double x, double y) {
    double angle = Math.atan((y - this.y) / (x - this.x));
    setDirection(angle);
  }

  public void move() {
    dx = x;
    dy = y;

    if (moving) {
      if (currentSpeed < maxSpeed) currentSpeed += acceleration;
      currentSpeed = Math.min(currentSpeed, maxSpeed);
    } else {
      if (currentSpeed == 0) return;
      currentSpeed -= stopSpeed;
      currentSpeed = Math.max(currentSpeed, 0);
    }
    dx += currentSpeed * Math.sin(direction);
    dy += currentSpeed * Math.cos(direction);

    // This may need to exist outside of move in the future
    checkCollisions();
  }

  public abstract void update();

  // Right now this does nothing but in the future this will check if the object is out of bounds or
  // trying to walk into
  // another wall / unit and stop the x and/or y values from changing
  // This may mean that all move commands for map objects will need to be called, then all check
  // collisions will need to
  // be called after
  public void checkCollisions() {
    x = dx;
    y = dy;
  }

  // The simplified object that is sent to the client
  // This could probably be done better but if it works, it's fine as it is
  // If it does work we could probably make a more efficient implementation
  public BasicObject getBasicObject() {
    BasicObject bo = new BasicObject();
    bo.setX(x);
    bo.setY(y);
    bo.setWidth(width);
    bo.setHeight(height);
    bo.setDirection(direction);
    bo.setCurrentSpeed(currentSpeed);
    return bo;
  }
}
