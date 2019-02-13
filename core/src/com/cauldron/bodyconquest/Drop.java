package com.cauldron.bodyconquest;

import java.sql.Time;
import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.TimeUtils;

public class Drop extends Game {

  public SpriteBatch batch;
  public BitmapFont font;

  @Override
  public void create() {

    batch = new SpriteBatch();
    font = new BitmapFont();
    this.setScreen(new MainMenuScreen(this));
  }

  @Override
  public void resize(int width, int height) {}

  @Override
  public void render() {

    super.render();
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void dispose() { // naive resources which are not handled by Java garbage collector
    batch.dispose();
    font.dispose();
  }
}
