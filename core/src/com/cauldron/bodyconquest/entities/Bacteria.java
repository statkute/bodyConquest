package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bacteria extends MapObject {

  public Bacteria() {
    setBounds(0, 0, 100, 100);
    speed = 100;
    // Images and Animations
    // sprite = new Image(new Texture("core/assets/Default Sprite (Green).png"));
    // sprite.setColor(Color.BLUE);
    Texture texture = new Texture("core/assets/Default Sprite (Green).png");
    region = new TextureRegion(texture);

    // maybe better to use Rectangle class? instead of Image class (found in Tutorials)

    sprite = new Image(texture);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    Color color = Color.BLUE;
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
    batch.draw(
        region,
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
    moveRight(delta);
  }
}
