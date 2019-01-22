package com.cauldron.bodyconquest.enities;

import java.awt.Rectangle;

public abstract class MapObject {

    // Does x refer to bottom right corner or horizontal centre of object

    // Vectors
    private double x;
    private double y;
    private double dx;
    private double dy;

    //private Dimension dimension;
    private int width;
    private int height;

    private int cwidth;
    private int cheight;


    public MapObject() {

    }

    //public boolean intersects(MapObject obj) {}

    // Using java.awt.Rectangle, gdx Rectangle is available
    public Rectangle getRectangle() {
        return new Rectangle((int) x, (int) y, cwidth, cheight);
    }

    // May need to add assertions depending on what information is available
    // Assertions may happen at a previous stage
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}