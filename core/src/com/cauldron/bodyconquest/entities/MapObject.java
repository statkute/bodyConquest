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

  private final double UP_DIRECTION = 0;
  private final double DOWN_DIRECTION = 180;
  private final double LEFT_DIRECTION = -90;
  private final double RIGHT_DIRECTION = 90;

  // private Dimension dimension;
  // Current x and y
  private double x;
  private double y;
  // Destination x and y
  private double dx;
  private double dy;
  private int width;
  private int height;
  private double direction;

  // Movement attributes
  protected double acceleration;
  protected double currentSpeed;
  protected double maxSpeed;
  protected double stopSpeed;
  
  private int cwidth;
  private int cheight;

  public Image sprite;
  protected TextureRegion currentFrame;
  protected Texture texture;

  //public String imagePath;

  protected boolean collidable;

  public EncounterState screen;
  protected boolean moving;

  private Object animation;

  public MapObject() {
    // This might be a mistake
    setWidth(0);
    setHeight(0);
    animation = null;
    //imagePath = "core/assets/Default Sprite(Green).png";
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

  /* maybe int depending on implementation. */
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

  public boolean isCollideable() { return collidable; }

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

  public void setDirectionUpLeft() { direction = Math.toRadians((UP_DIRECTION + LEFT_DIRECTION) / 2); }
  public void setDirectionUpRight() { direction = Math.toRadians((UP_DIRECTION + RIGHT_DIRECTION) / 2); }
  public void setDirectionDownLeft() { direction = Math.toRadians((-DOWN_DIRECTION + LEFT_DIRECTION) / 2); }
  public void setDirectionDownRight() { direction = Math.toRadians((DOWN_DIRECTION + RIGHT_DIRECTION) / 2); }

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

  public double getMaxSpeed() { return maxSpeed; }

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
    dx = x;
    dy = y;

    if (moving) {
      if (currentSpeed < maxSpeed) currentSpeed += acceleration;
      currentSpeed = Math.min(currentSpeed, maxSpeed);
    } else {
      if(currentSpeed == 0) return;
      currentSpeed -= stopSpeed;
      currentSpeed = Math.max(currentSpeed, 0);
    }
    dx += currentSpeed * Math.sin(direction);
    dy += currentSpeed * Math.cos(direction);

    // This may need to exist outside of move in the future
    checkCollisions();
  }

  public void setMoving(boolean b) { moving = b; }

  public BasicObject getBasicObject() {
    BasicObject bo = new BasicObject();
    bo.setX(x);
    bo.setY(y);
    bo.setWidth(width);
    bo.setHeight(height);
    bo.setDirection(direction);
    bo.setCurrentSpeed(currentSpeed);
    bo.setAnimation(animation);
    //bo.setImagePath(imagePath);
    return bo;
  }

  public abstract void update();

  // Right now this does nothing but in the future this will check if the object is out of bounds or trying to walk into
  // another wall / unit and stop the x and/or y values from changing
  // This may mean that all move commands for map objects will need to be called, then all check collisions will need to
  // be called after
  public void checkCollisions() {
    x = dx;
    y = dy;
  }

}
