package main.com.bodyconquest.entities.projectiles;

import main.com.bodyconquest.constants.ProjectileType;

/**
 * The Virus projectile class.
 */
public class VirusProjectile extends Projectile {

    private double xDest;
    private double yDest;

    /**
     * Instantiates a new Virus projectile.
     *
     * @param damage the damage dealt
     * @param x      the x
     * @param y      the y
     * @param xDest  the x dest
     * @param yDest  the y dest
     */
    public VirusProjectile(int damage, double x, double y, double xDest, double yDest) {
        super();
        xInit = x;
        yInit = y;
        this.damage = damage;
        setSize(60, 60);
        setPosition(x - (getWidth() / 2.0), y - (getHeight() / 2.0));
        this.xDest = xDest;
        this.yDest = yDest;
        setCSize(45, 15);
        init();
    }

    private void init() {
        maxSpeed = 2.5;
        maxTravelDistance = 200;
        mapObjectType = ProjectileType.VIRUS_PROJECTILE;
        moveTowards(xDest, yDest);
    }

    @Override
    public void update() {
        super.update();
    }
}
