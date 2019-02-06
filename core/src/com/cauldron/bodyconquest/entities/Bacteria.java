package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;
import com.cauldron.bodyconquest.gamestates.EncounterScreen.*;

import java.util.ArrayList;

public class Bacteria extends Unit {

  private EncounterScreen map;
  private PlayerType playerType;
  private Lane lane;
  // Some above head health bar?
  // private UnitHealthBar healthBar;

  // States
  // Maybe should be in the unit class
  //private boolean moving;
  //private boolean attacking;

  private Rectangle collisionBox; // + Sprite for now at least

  /*
  Each moving unit could be given a queue of checkpoints to reach
  and then one left at the enemy base it would be within range and attack
  */
  public Bacteria(EncounterScreen map, PlayerType playerType, Lane lane) {
    this.playerType = playerType;
    this.lane = lane;
    this.map = map;

    // Dimensions
    setSize(50, 50);

    // Images and Animations
    // sprite = new Image(new Texture("core/assets/Default Sprite (Green).png"));
    // sprite.setColor(Color.BLUE);
    Texture texture = new Texture("core/assets/Default Sprite (Green).png");
    region = new TextureRegion(texture);

    // Unit Stats
    health = maxHealth = 100;
    speed = 100;
    attackable = true;
    moving = true;
    attacking = false;
    //attackCooldown = 20;
    cooldown = 1000; // Milliseconds
    lastAttack = 0;
    range = 100;
    damage = 30;

    // maybe better to use Rectangle class? instead of Image class (found in Tutorials)
    sprite = new Image(texture);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    Color color = getColor();
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
   /* batch.draw(
        region,
        getX(),
        getY(),
        getOriginX(),
        getOriginY(),
        getWidth(),
        getHeight(),
        getScaleX(),
        getScaleY(),
        getRotation());*/
    batch.draw(region, getX(), getY());
  }

  @Override
  public void act(float delta) {
    super.act(delta);

    if (moving) {
      if (playerType == PlayerType.BOT_PLAYER) {
        if (lane == Lane.BOT) {
          System.out.println("X: " + getX() + "\tCentre X: " + getCentreX() + "\tOrigin X: " + getOriginX() + "\tScale X: " + getScaleX());
          if (getCentreX() > map.getBotTurnPointX()) {
            moveLeft(delta);
          } else {
            moveUp(delta);
          }
        } /*else if (lane == Lane.MID) {

        }*/
      } else if (playerType == PlayerType.TOP_PLAYER) {
        if (lane.equals(Lane.BOT)) {
          if (getCentreY() > map.getBotTurnPointY()) {
            moveDown(delta);
          } else {
            moveRight(delta);
          }
        }
      }
    }
  }

  private void attack(Unit unit) {
    unit.hit(damage);
  }

  public void checkAttack(ArrayList<Unit> enemies) {
    Unit closestEnemy = null;
    for (Unit enemy : enemies) {
      if (closestEnemy == null) closestEnemy = enemy;

        // Attack closest enemy
        closestEnemy = distFrom(enemy) < distFrom(closestEnemy) ? enemy : closestEnemy;

    }

    if (closestEnemy != null && closestEnemy.isAttackable() && inRange(closestEnemy)) {
      setMoving(false);
      long time = System.currentTimeMillis();
      if (!attacking && (time > lastAttack + cooldown)) {
        attack(closestEnemy);
        lastAttack = time;
      }
    } else {
      if(!moving) setMoving(true);
    }

  }

  //public Rectangle getCollisionBox() { return collisionBox; }
}
