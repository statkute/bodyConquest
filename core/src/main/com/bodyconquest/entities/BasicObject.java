package main.com.bodyconquest.entities;
import main.com.bodyconquest.constants.MapObjectType;
import main.com.bodyconquest.constants.PlayerType;

import java.time.Instant;
import java.util.Date;

public class BasicObject {

  private double x;
  private double y;
  private int width;
  private int height;
  private int cwidth;
  private int cheight;
  private double direction;
  private double currentSpeed;
  private double rotation;
  private MapObjectType mapObjectType;
  private boolean wasHit;

  protected long timeAlive;

  public long getTimeAlive() {
    return timeAlive;
  }

  public void setTimeAlive(long timeAlive) {
    this.timeAlive = timeAlive;
  }

  public long getTimeOfDmgTaken() {
    return timeOfDmgTaken;
  }

  public void setTimeOfDmgTaken(long timeOfDmgTaken) {
    this.timeOfDmgTaken = timeOfDmgTaken;
  }

  protected long timeOfDmgTaken;

  private PlayerType playerType;

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

  @SuppressWarnings("unchecked")
  public <T extends Enum & MapObjectType> T getMapObjectType() {
    return mapObjectType.getMapObjectType();
  }

  public double getRotation() {
    return rotation;
  }

  public void setRotation(double rotation) {
    this.rotation = rotation;
  }

  public int getCwidth() {
    return cwidth;
  }

  public void setCwidth(int cwidth) {
    this.cwidth = cwidth;
  }

  public int getCheight() {
    return cheight;
  }

  public void setCheight(int cheight) {
    this.cheight = cheight;
  }

  public boolean getWasHit(){
    return wasHit;
  }

  public void setWasHit(boolean wasHit){
    this.wasHit = wasHit;
  }

  public PlayerType getPlayerType() {
    return playerType;
  }

  public void setPlayerType(PlayerType playerType) {
    this.playerType = playerType;
  }

}
