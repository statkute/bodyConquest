package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;
import com.cauldron.bodyconquest.gamestates.EncounterScreen.PlayerType;

public class Base extends Troop {

  private Texture texture;
  public Base(EncounterScreen screen, PlayerType playerType) {
    this.screen = screen;
    this.playerType = playerType;
    init();
  }

  private void init() {
    setSize(150, 150);
    if (playerType == PlayerType.BOT_PLAYER) {
      setPosition(screen.getMap().getRight() - getWidth(), screen.getMap().getY());
       texture = new Texture("core/assets/Base (Green).png");
    } else if (playerType == PlayerType.TOP_PLAYER) {
      setPosition(screen.getMap().getX(), screen.getMap().getTop() - getHeight());
      texture = new Texture("core/assets/Base (Yellow).png");
    }

    maxHealth = health = 800;
    attackable = true;
    sprite = new Image(texture);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    Color color = getColor();
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
    batch.draw(texture, getX(), getY(), getWidth(), getHeight());
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }
}
