package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.awt.Rectangle;

public abstract class MapObject extends Actor {

    //public static final MapObject emptyUnit = new MapObject();

    // Does x refer to bottom right corner or horizontal centre of object

    // Vectors
    //protected double x;
    //private double y;
    //private double dx;
    //private double dy;
    protected float speed;

    //private Dimension dimension;
    private int width;
    private int height;

    private int cwidth;
    private int cheight;

    public Image sprite;
    protected TextureRegion region;

    public MapObject() {}

   public void moveRight(float delta) {
       setX(getX() + (delta * speed));
   }

}