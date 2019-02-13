package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cauldron.bodyconquest.gamestates.EncounterState;
import com.cauldron.bodyconquest.handlers.AnimationWrapper;

public class FluProjectile extends Projectile {

  private float xDest;
  private float yDest;
  private Animation<TextureRegion> walkAnimation;
  private float stateTime;

  public FluProjectile(EncounterState screen, float x, float y, float xDest, float yDest) {
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

    walkAnimation = AnimationWrapper.getSpriteSheet(5, 1, 0.15f, "core/assets/projectile_with_trail.png");
    stateTime = 0f;
    sprite = new Image(walkAnimation.getKeyFrame(0));
  }

  @Override
  public void act(float delta) {
    super.act(delta);

  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

    // Get current frame of animation for the current stateTime
    //currentFrame = walkAnimation.getKeyFrame(stateTime, true);
    //this.setWidth(70);
    //this.setHeight(70);
    super.draw(batch, parentAlpha);
  }
}
