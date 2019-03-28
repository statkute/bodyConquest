package main.com.bodyconquest.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import main.com.bodyconquest.constants.Organ;
import main.com.bodyconquest.handlers.AnimationWrapper;

/**
 * The type Map.
 */
public class Map extends Actor {

  private final double MAP_HEIGHT = 516.0;
  private final double MAP_WIDTH = 516.0;

  private float stateTime;
  private int frameCols;
  private int frameRows;
  private float frameRate;
  private Animation<TextureRegion> walkAnimation;
  private int points;

  /**
   * Instantiates a new Map.
   *
   * @param organ the organ whose map is to be instantiated
   */
// Should have some sort of resource manager and system
  public Map(Organ organ) {
    String texturePath = null;
    if(organ == Organ.LUNGS){
      texturePath = "core/assets/map_lungs_ss.png";
      frameCols = 4;
      frameRows = 5;
      frameRate = 30f;
      points =30;
    }
    if(organ == Organ.EYES)  {
      texturePath = "core/assets/map_eyes_ss.png";
      frameCols = 2;
      frameRows = 11;
      frameRate = 60f;
      points =20;
    }
    if(organ == Organ.HEART)  {
      texturePath = "core/assets/map_heart_ss.png";
      frameCols = 11;
      frameRows = 1;
      frameRate = 40f;
      points = 30;
    }
    if(organ == Organ.TEETH)  {
      texturePath = "core/assets/map_teeth_ss.png";
      frameCols = 5;
      frameRows = 5;
      frameRate = 47f;
      points = 10;
    }
    if(organ == Organ.BRAIN)  {
      texturePath = "core/assets/map_brain_ss.png";
      frameCols = 4;
      frameRows = 5;
      frameRate = 60f;
      points = 40;
    }

    if(organ == Organ.INTESTINES)  {
      texturePath = "core/assets/map_brain_ss.png";
      frameCols = 4;
      frameRows = 5;
      frameRate = 60f;
      points = 20;
    }
    //animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal(texturePath).read());
    walkAnimation = AnimationWrapper.getSpriteSheet(frameCols, frameRows, frameRate, texturePath);
    stateTime = 0f;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
//    stateTime += Gdx.graphics.getDeltaTime();
//    TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
    stateTime += 10f; // Accumulate elapsed animation time
    // Get current frame of animation for the current stateTime

    TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);

    batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    super.draw(batch, parentAlpha);
  }

  public float getRight() {
    return (float) MAP_WIDTH;
  }

  /**
   * Get points.
   *
   * @return the points
   */
  public int getPoints(){
    return points;
  }

  /*
  Here should be a function to set which map image this should have and
  client side should be able to interpret this and choose the correct image to draw
  */

}
