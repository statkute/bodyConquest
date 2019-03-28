package main.com.bodyconquest.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The type Sprite batch wrapper.
 */
public class SpriteBatchWrapper extends SpriteBatch {

  private float scaleFactor;

  /**
   * Instantiates a new Sprite batch wrapper.
   *
   * @param scaleFactor the scale factor
   */
  public SpriteBatchWrapper(float scaleFactor) {
    this.scaleFactor = scaleFactor;
  }


}
