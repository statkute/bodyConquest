package main.com.bodyconquest.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteBatchWrapper extends SpriteBatch {

  private float scaleFactor;

  public SpriteBatchWrapper(float scaleFactor) {
    this.scaleFactor = scaleFactor;
  }
}
