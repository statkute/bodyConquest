package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.cauldron.bodyconquest.rendering.BodyConquest;

import java.util.ArrayList;

public class SpawnArea extends Actor {

  public ArrayList<MapObject> enemiesToSpawn;

  public SpawnArea() {
    enemiesToSpawn = new ArrayList<MapObject>();
    setBounds(0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT / 2);
  }
}
