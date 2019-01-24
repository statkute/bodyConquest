package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {

    private Rectangle space;
    private String type;
    private int health;
    private Texture texture;

    public Entity(String type, int health){
        this.type = type;
        this.health = health;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
