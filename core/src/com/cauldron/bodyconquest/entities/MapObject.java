package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cauldron.bodyconquest.gamestates.EncounterState;

import java.awt.*;

/*
Contains all the properties and methods that all map objects must have.
It also extends Actor.
*/

public abstract class MapObject {

  private final double UP_DIRECTION = 90;
  private final double DOWN_DIRECTION = -90;
  private final double LEFT_DIRECTION = 180;
  private final double RIGHT_DIRECTION = 0;

  // private Dimension dimension;
  private double x;
  private double y;
  private double dx;
  private double dy;
  private int width;
  private int height;
  private double direction;

  // Movement attributes
  protected double acceleration;
  protected double deceleration;
  protected double currentSpeed;
  protected double maxSpeed;
  protected double stopSpeed;
  
  private int cwidth;
  private int cheight;

  private Rectangle bounds;

  public Image sprite;
  protected TextureRegion currentFrame;
  protected Texture texture;

  protected double speed;
  protected boolean collideable;

  private double destx;
  private double desty;

  public EncounterState screen;
  private boolean moving;
  private boolean stoping;

  public MapObject() {
    // This might be a mistake
    setWidth(0);
    setHeight(0);
    bounds = new Rectangle();
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) { this.y = y; }

  public double getCwidth() {
    return cwidth;
  }

  public double getCheight() {
    return cheight;
  }

//  public void moveUp(double delta) {
//    setY(getY() + (delta * speed));
//  }
//
//  public void moveDown(double delta) {
//    setY(getY() - (delta * speed));
//  }
//
//  public void moveLeft(double delta) {
//    setX(getX() - speed);
//  }
//
//  public void moveRight(double delta) {
//    setX(getX() + speed);
//  }

  /* maybe int depending on implementation. */
  public double distFrom(MapObject object) {
    return distFrom(object.getCentreX(), object.getCentreY());
  }

  public double distFrom(double x, double y) {
    double xDif = (double) this.getCentreX() - (double) x;
    double yDif = (double) this.getCentreY() - (double) y;
    // System.out.println((double) Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2)));
    return (double) Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2));
  }

  public double getCentreX() {
    return getX() + (getWidth() / 2);
  }

  public double getCentreY() {
    return getY() + (getHeight() / 2);
  }

  public boolean isCollideable() { return collideable; }

  public Rectangle getBounds()
  {
    return new Rectangle((int)x, (int)y, cwidth, cheight);
  }

  public boolean checkCollision(MapObject object){
    if(object.getBounds().intersects(getBounds())) return true;
    return false;
  }

//  @Override
//  public void draw(Batch batch, double parentAlpha) {
//    Color color = getColor();
//    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
//    batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
//  }

  public void setDirectionUp() { direction = Math.toRadians(UP_DIRECTION); }
  public void setDirectionDown() { direction = Math.toRadians(DOWN_DIRECTION); }
  public void setDirectionLeft() { direction = Math.toRadians(LEFT_DIRECTION); }
  public void setDirectionRight() { direction = Math.toRadians(RIGHT_DIRECTION); }
  private void setDirection(double angle) {
    // Takes in radians
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

  public void moveTowards(MapObject o) {
    moveTowards(o.getCentreX(), o.getCentreY());
  }

  public void moveTowards(double x, double y) {
    double angle = Math.atan((y - this.y) / (x - this.x));
    setDirection(angle);
  }

  public void move() {
    if (moving) {
      if (currentSpeed < maxSpeed) currentSpeed += acceleration;
      currentSpeed = Math.min(currentSpeed, maxSpeed);
    } else {
      if(currentSpeed == 0) return;
      currentSpeed -= stopSpeed;
      currentSpeed = Math.max(currentSpeed, 0);
    }
    dx += currentSpeed * Math.cos(direction);
    dy += currentSpeed * Math.sin(direction);
  }

}
