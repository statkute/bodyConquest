package com.cauldron.bodyconquest.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteBatchWrapper extends SpriteBatch {

  private float scaleFactor;

  public SpriteBatchWrapper(float scaleFactor) {
    this.scaleFactor = scaleFactor;
  }
}
