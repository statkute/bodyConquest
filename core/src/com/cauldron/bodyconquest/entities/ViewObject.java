package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ViewObject extends Actor {

  private Texture texture;

  public ViewObject(BasicObject bo) {
    setX((float)bo.getX());
    setY((float)bo.getY());
    setWidth((float)bo.getWidth());
    setHeight((float)bo.getHeight());
    texture = new Texture("core/assets/Default Sprite (Green).png");
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    Color color = getColor();
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
    batch.draw(texture, getX(), getY(), getWidth(), getHeight());

  }
}
