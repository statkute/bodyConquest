package main.com.bodyconquest;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Animator implements ApplicationListener {

  // Constant rows and columns of the sprite sheet
  private static final int FRAME_COLS = 6, FRAME_ROWS = 5;
  private static int currentPosition1 = 50;
  private static int currentPosition2 = 100;
  private static int currentPosition3 = 150;
  private static int currentPosition4 = 200;

  // Objects used
  Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
  Texture walkSheet;
  SpriteBatch spriteBatch;

  // A variable for tracking elapsed time for the animation
  float stateTime;

  @Override
  public void create() {

    // Load the sprite sheet as a Texture
    walkSheet = new Texture(Gdx.files.internal("core/assets/sprite-animation4.png.gif"));

    // Use the split utility method to create a 2D array of TextureRegions. This is
    // possible because this sprite sheet contains frames of equal size and they are
    // all aligned.
    TextureRegion[][] tmp =
        TextureRegion.split(
            walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);

    // Place the regions into a 1D array in the correct order, starting from the top
    // left, going across first. The Animation constructor requires a 1D array.
    TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
    int index = 0;
    for (int i = 0; i < FRAME_ROWS; i++) {
      for (int j = 0; j < FRAME_COLS; j++) {
        walkFrames[index++] = tmp[i][j];
      }
    }

    // Initialize the Animation with the frame interval and array of frames
    walkAnimation = new Animation<TextureRegion>(0.025f, walkFrames);

    // Instantiate a SpriteBatch for drawing and reset the elapsed animation
    // time to 0
    spriteBatch = new SpriteBatch();
    stateTime = 0f;
  }

  @Override
  public void resize(int width, int height) {}

  @Override
  public void render() {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
    stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

    // Get current frame of animation for the current stateTime
    TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);

    spriteBatch.begin();
    spriteBatch.draw(currentFrame, currentPosition1, 50); // Draw current frame at (50, 50)
    spriteBatch.draw(currentFrame, currentPosition2, 100); // Draw current frame at (50, 50)
    spriteBatch.draw(currentFrame, currentPosition3, 150); // Draw current frame at (50, 50)
    spriteBatch.draw(currentFrame, currentPosition4, 200); // Draw current frame at (50, 50)
    spriteBatch.end();

    if (currentPosition1 != Gdx.graphics.getWidth() - 80) {
      currentPosition1++;
    }

    if (currentPosition2 != Gdx.graphics.getWidth() - 80) {
      currentPosition2++;
    }

    if (currentPosition3 != Gdx.graphics.getWidth() - 80) {
      currentPosition3++;
    }

    if (currentPosition4 != Gdx.graphics.getWidth() - 80) {
      currentPosition4++;
    }
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void dispose() { // SpriteBatches and Textures must always be disposed
    spriteBatch.dispose();
    walkSheet.dispose();
  }
}
