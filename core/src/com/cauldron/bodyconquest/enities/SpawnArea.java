package com.cauldron.bodyconquest.enities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.cauldron.bodyconquest.rendering.BodyConquest;

import java.awt.*;
import java.util.ArrayList;

public class SpawnArea extends Actor {

    public ArrayList<MapObject> enemiesToSpawn;

    private Image sprite;

    public SpawnArea() {
        enemiesToSpawn = new ArrayList<MapObject>();
        //setBounds(0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);

    }


}
