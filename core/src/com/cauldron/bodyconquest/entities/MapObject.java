package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/*
Contains all the properties and methods that all map objects must have.
It also extends Actor.
*/


public abstract class MapObject extends Actor {

    //public static final MapObject emptyUnit = new MapObject();

    //private Dimension dimension;
    private float cwidth;
    private float cheight;

    public Image sprite;
    protected TextureRegion region;

    protected float speed;

    public MapObject() { }

    public float getCwidth() { return cwidth; }
    public float getCheight() { return cheight; }

    public void moveUp(float delta) { setY(getY() - (delta * speed)); }
    public void moveDown(float delta) { setY(getY() - (delta * speed)); }
    public void moveLeft(float delta) { setX(getX() - (delta * speed)); }
    public void moveRight(float delta) {
        setX(getX() + (delta * speed));
    }

}