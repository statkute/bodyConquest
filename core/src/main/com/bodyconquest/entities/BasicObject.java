package main.com.bodyconquest.entities;

import main.com.bodyconquest.constants.MapObjectType;
import main.com.bodyconquest.constants.PlayerType;

import java.time.Instant;
import java.util.Date;

/**
 * The type Basic object.
 */
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

    /**
     * The Time alive.
     */
    protected long timeAlive;

    /**
     * Gets time alive.
     *
     * @return the time alive
     */
    public long getTimeAlive() {
        return timeAlive;
    }

    /**
     * Sets time alive.
     *
     * @param timeAlive the time alive
     */
    public void setTimeAlive(long timeAlive) {
        this.timeAlive = timeAlive;
    }

    /**
     * Gets time of dmg taken.
     *
     * @return the time of dmg taken
     */
    public long getTimeOfDmgTaken() {
        return timeOfDmgTaken;
    }

    /**
     * Sets time of dmg taken.
     *
     * @param timeOfDmgTaken the time of dmg taken
     */
    public void setTimeOfDmgTaken(long timeOfDmgTaken) {
        this.timeOfDmgTaken = timeOfDmgTaken;
    }

    /**
     * The Time of dmg taken.
     */
    protected long timeOfDmgTaken;

    private PlayerType playerType;

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets direction.
     *
     * @param direction the direction
     */
    public void setDirection(double direction) {
        this.direction = direction;
    }

    /**
     * Gets direction.
     *
     * @return the direction
     */
    public double getDirection() {
        return direction;
    }

    /**
     * Sets current speed.
     *
     * @param currentSpeed the current speed
     */
    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    /**
     * Gets current speed.
     *
     * @return the current speed
     */
    public double getCurrentSpeed() {
        return currentSpeed;
    }

    /**
     * Sets map object type.
     *
     * @param ut the ut
     */
    public void setMapObjectType(MapObjectType ut) {
        this.mapObjectType = ut;
    }

    /**
     * Gets map object type.
     *
     * @param <T> the type parameter
     * @return the map object type
     */
    @SuppressWarnings("unchecked")
    public <T extends Enum & MapObjectType> T getMapObjectType() {
        return mapObjectType.getMapObjectType();
    }

    /**
     * Gets rotation.
     *
     * @return the rotation
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * Sets rotation.
     *
     * @param rotation the rotation
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    /**
     * Gets cwidth.
     *
     * @return the cwidth
     */
    public int getCwidth() {
        return cwidth;
    }

    /**
     * Sets cwidth.
     *
     * @param cwidth the cwidth
     */
    public void setCwidth(int cwidth) {
        this.cwidth = cwidth;
    }

    /**
     * Gets cheight.
     *
     * @return the cheight
     */
    public int getCheight() {
        return cheight;
    }

    /**
     * Sets cheight.
     *
     * @param cheight the cheight
     */
    public void setCheight(int cheight) {
        this.cheight = cheight;
    }

    /**
     * Method that checks whether the BasicObject was hit
     *
     * @return true if it was hit; false otherwise
     */
    public boolean getWasHit() {
        return wasHit;
    }

    /**
     * Method that sets the current state of the BasicObject as hit or not hit
     *
     * @param wasHit true if it was hit; false otherwise
     */
    public void setWasHit(boolean wasHit) {
        this.wasHit = wasHit;
    }

    /**
     * Gets player type.
     *
     * @return the player type
     */
    public PlayerType getPlayerType() {
        return playerType;
    }

    /**
     * Sets player type.
     *
     * @param playerType the player type
     */
    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

}
