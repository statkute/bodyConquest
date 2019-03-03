package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.cauldron.bodyconquest.constants.Organ;
import com.cauldron.bodyconquest.handlers.GifDecoder;

public class Map extends Actor {

  private final double MAP_HEIGHT = 516.0;
  private final double MAP_WIDTH = 516;

  private Texture texture;
  private Animation<TextureRegion> animation;
  private float stateTime;

  // Should have some sort of resource manager and system
  public Map(Organ organ) {
    String texturePath = null;
    if(organ == Organ.LUNGS)  texturePath = "core/assets/map_lungs.gif";
    if(organ == Organ.EYES)   texturePath = "core/assets/map_eyes.gif";
    animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal(texturePath).read());
    stateTime = 0f;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    stateTime += Gdx.graphics.getDeltaTime();
    TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
    batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    super.draw(batch, parentAlpha);
  }

  //  public double getMaxX() {
//    return MAP_WIDTH;
//  }
//
//  public double getMaxY() {
//    return MAP_HEIGHT;
//  }
//
//  public float getTop() {
//    return (float) MAP_HEIGHT;
//  }
//
//  public double getBottom() {
//    return 0;
//  }
//
//  public double getLeft() {
//    return 0;
//  }
//
//  public float getRight() {
//    return (float) MAP_WIDTH;
//  }

  /*
  Here should be a function to set which map image this should have and
  client side should be able to interpret this and choose the correct image to draw
  */

}
