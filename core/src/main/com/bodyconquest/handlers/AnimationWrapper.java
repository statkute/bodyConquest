package main.com.bodyconquest.handlers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/** The type Animation wrapper. */
public class AnimationWrapper {

  /**
   * Gets sprite sheet.
   *
   * @param frameCols the frame cols
   * @param frameRows the frame rows
   * @param frameDuration the frame duration
   * @param texturePath the texture path
   * @return the sprite sheet
   */
  public static Animation<TextureRegion> getSpriteSheet(
      int frameCols, int frameRows, float frameDuration, String texturePath) {

    Texture walkSheet = new Texture(texturePath);
    TextureRegion[][] tmp =
        TextureRegion.split(
            walkSheet, walkSheet.getWidth() / frameCols, walkSheet.getHeight() / frameRows);
    TextureRegion[] walkFrames = new TextureRegion[(frameCols - 1) * frameRows];
    int index = 0;
    for (int i = 0; i < frameRows; i++) {
      for (int j = 0; j < frameCols - 1; j++) {
        walkFrames[index++] = tmp[i][j];
      }
    }
    return new Animation<>(frameDuration, walkFrames);
  }
}
