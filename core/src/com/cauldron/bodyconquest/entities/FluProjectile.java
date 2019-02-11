package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.graphics.Texture;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;

public class FluProjectile extends Projectile {

  private Texture texture;

  private float xDest;
  private float yDest;

  public FluProjectile(EncounterScreen screen, float x, float y, float xDest, float yDest) {
    super(screen);
    setPosition(x, y);
    this.xDest = xDest;
    this.yDest = yDest;
    init();
  }

  private void init() {
    setSize(20, 20);
    //setBounds();
    damage = 40;
    speed = 200;
    texture = new Texture("core/assets/Default Sprite (Green).png");
    setTargetLocation(xDest, yDest);
  }

  @Override
  public void act(float delta) {
    super.act(delta);

  }
}
