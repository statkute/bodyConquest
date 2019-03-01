package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.handlers.AnimationWrapper;
import com.cauldron.bodyconquest.handlers.GifDecoder;

public class ViewObject extends Actor {

  private Texture texture;
  private Animation<TextureRegion> walkAnimation;
  float stateTime;
  private TextureRegion[] walkFrames;
  private TextureRegion currentFrame;
  private float elapsedTime;

  public ViewObject(BasicObject bo, String pathTexture, int frameCols, int frameRows,float elapsedTime) {
    // Right now all textures are the default buckets
    //texture = new Texture("core/assets/bucket.png");
    //texture = new Texture(pathTexture);
    this.elapsedTime = elapsedTime;
    walkAnimation = AnimationWrapper.getSpriteSheet(frameCols, frameRows, 0.2f, pathTexture);
    if(bo.getMapObjectType() == Assets.MapObjectType.FLUPROJECTILE){
      setRotation((float)bo.getRotation());
    }
    setX((float)bo.getX());
    setY((float)bo.getY());
    setWidth((float)bo.getWidth());
    setHeight((float)bo.getHeight());
    stateTime = 0f;
  }

  public ViewObject(BasicObject bo, String pathTexture, float elapsedTime) {
    walkAnimation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal(pathTexture).read());
    this.elapsedTime = elapsedTime;
    if(bo.getMapObjectType() == Assets.MapObjectType.FLUPROJECTILE){
      setRotation((float)bo.getRotation());
    }
    stateTime = 0f;
    setX((float)bo.getX());
    setY((float)bo.getY());
    setWidth((float)bo.getWidth());
    setHeight((float)bo.getHeight());
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    // TO DO will need to fix logic for this
    stateTime += elapsedTime; // Accumulate elapsed animation time
    // Get current frame of animation for the current stateTime
    //System.out.println(System.currentTimeMillis());
    currentFrame = walkAnimation.getKeyFrame(stateTime, true);

    Color color = getColor();
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
    //batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    batch.draw(currentFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

    super.draw(batch, parentAlpha);
  }
}
