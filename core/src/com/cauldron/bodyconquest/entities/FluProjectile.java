package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;

public class FluProjectile extends Projectile {

  private float xDest;
  private float yDest;

  public FluProjectile(EncounterScreen screen, float x, float y, float xDest, float yDest) {
    super(screen);
    setSize(20, 20);
    setPosition(x - (getWidth() / 2) , y - (getHeight() / 2));
    this.xDest = xDest;
    this.yDest = yDest;
    init();
  }

  private void init() {
    damage = 40;
    speed = 200;
    currentFrame = new TextureRegion(new Texture("core/assets/Default Sprite (Green).png"));
    setTargetLocation(xDest, yDest);
  }

  @Override
  public void act(float delta) {
    super.act(delta);

  }
}
