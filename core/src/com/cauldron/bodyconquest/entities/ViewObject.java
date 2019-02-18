package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.handlers.AnimationWrapper;
import com.badlogic.gdx.Gdx;
import com.cauldron.bodyconquest.handlers.GifDecoder;

public class ViewObject extends Actor {

  private Texture texture;
  private Animation<TextureRegion> walkAnimation;
  float stateTime;
  private TextureRegion[] walkFrames;
  private TextureRegion currentFrame;

  public ViewObject(BasicObject bo, String pathTexture, int frameCols, int frameRows) {
    setX((float)bo.getX());
    setY((float)bo.getY());
    setWidth((float)bo.getWidth());
    setHeight((float)bo.getHeight());
    // Right now all textures are the default buckets
    //texture = new Texture("core/assets/bucket.png");
    //texture = new Texture(pathTexture);
    walkAnimation = AnimationWrapper.getSpriteSheet(frameCols, frameRows, 0.01f, pathTexture);

    stateTime = 0f;
  }

  public ViewObject(BasicObject bo, String pathTexture) {
    setX((float)bo.getX());
    setY((float)bo.getY());
    setWidth((float)bo.getWidth());
    setHeight((float)bo.getHeight());
    walkAnimation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal(pathTexture).read());

    stateTime = 0f;
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    // TO DO will need to fix logic for this
    stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
    // Get current frame of animation for the current stateTime
    currentFrame = walkAnimation.getKeyFrame(stateTime, true);

    Color color = getColor();
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
    batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());

    super.draw(batch, parentAlpha);
  }
}
