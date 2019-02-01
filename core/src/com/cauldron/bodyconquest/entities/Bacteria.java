package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bacteria extends Unit {


    float stateTime;
    TextureRegion[] walkFrames;
    Animation<TextureRegion> walkAnimation;

    public Bacteria() {
        setSize(50, 50);
        speed = 100;
        // Images and Animations
        //sprite = new Image(new Texture("core/assets/Default Sprite (Green).png"));
        //sprite.setColor(Color.BLUE);
        Texture texture = new Texture("core/assets/bacteria.png");
        region = new TextureRegion(texture);

        /////////////////

        Texture walkSheet = new Texture("core/assets/bacteria.png");
        int FRAME_COLS = 7, FRAME_ROWS = 1;
        TextureRegion[][] tmp =
                TextureRegion.split(
                        walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);
        walkFrames = new TextureRegion[(FRAME_COLS - 1) * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS - 1; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation<TextureRegion>(0.2f, walkFrames);

        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0
        SpriteBatch spriteBatch = new SpriteBatch();
        stateTime = 0f;


        //////////////////////////

    // maybe better to use Rectangle class? instead of Image class (found in Tutorials)

    sprite = new Image(texture);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    Color color = getColor();
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

      stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

      // Get current frame of animation for the current stateTime
      TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);

    batch.draw(
        currentFrame,
        getX(),
        getY(),
        getOriginX(),
        getOriginY(),
        getWidth(),
        getHeight(),
        getScaleX(),
        getScaleY(),
        getRotation());
  }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getX() > 150){
            moveLeft(delta);
        } else {
            moveUp(delta);
        }

        System.out.println(getX());
    }
}
