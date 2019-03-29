package main.com.bodyconquest.entities.projectiles;

import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.entities.MapObject;
import main.com.bodyconquest.entities.Troops.Troop;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The type Projectile.
 */
public abstract class Projectile extends MapObject {

    /**
     * The Damage it deals.
     */
    protected int damage;

    /**
     * The Max travel distance.
     */
    protected double maxTravelDistance;
    /**
     * The Distance traveled.
     */
    protected double distanceTraveled;

    /**
     * The X initial coordinate.
     */
    protected double xInit;
    /**
     * The Y initial coordinate.
     */
    protected double yInit;

    /**
     * The Remove flag.
     */
    protected boolean remove;

    /**
     * Instantiates a new Projectile.
     */
    public Projectile() {
        super();
        // Maybe a better way of doing this
        this.collidable = true;
        initDefault();
    }

    private void initDefault() {
        // Maxed out because I don't want to do fine tuning right now
        stopSpeed = 100000;
        acceleration = 100000;
        //mapObjectType = Assets.UnitType.VIRUS;
        distanceTraveled = 0;
        moving = true;
        collidable = true;
        remove = false;
        playerType = PlayerType.AI;
    }

    /**
     * This method is called when the projectile touches a {@link Troop}
     *
     * @param troop the troop
     */
    public void hit(Troop troop) {
        if (remove) return;
        troop.hit(damage);
        setRemove();
    }

    /**
     * Sets remove to true in order to delete the projectile form the map.
     */
    public void setRemove() {
        remove = true;
    }

    /**
     * Gets value of the remove flag.
     *
     * @return the remove
     */
    public boolean getRemove() {
        return remove;
    }

    public void update() {
        if (distanceTraveled >= maxTravelDistance) setRemove();
        move();
        distanceTraveled = distFrom(xInit, yInit);
    }

    /**
     * Check whether the projectile hits a {@link Troop}.
     *
     * @param enemies the enemies list
     */
    public void checkHit(CopyOnWriteArrayList<Troop> enemies) {
        for (Troop enemy : enemies) {
            if (checkCollision(enemy)) {
                hit(enemy);
            }
        }
    }

    @Override
    public void move() {
        super.move();
        setX(dx);
        setY(dy);
    }

    @Override
    public Shape getBounds() {
        Shape originalRect = super.getBounds();
        AffineTransform trans = AffineTransform.getRotateInstance(getDirection() + Math.PI, getX() + ((getWidth() - getCwidth()) / 2), getY() + ((getHeight() - getCheight()) / 2));
        return trans.createTransformedShape(originalRect);
    }
}
