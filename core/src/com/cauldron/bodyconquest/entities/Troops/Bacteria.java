package com.cauldron.bodyconquest.entities.Troops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;
import com.cauldron.bodyconquest.gamestates.EncounterScreen.*;

import java.util.ArrayList;

public class Bacteria extends Troop {

  float stateTime;
  private TextureRegion[] walkFrames;
  private Animation<TextureRegion> walkAnimation;

  private EncounterScreen map;
  private PlayerType playerType;
  private Lane lane;
  // Some above head health bar?
  // private UnitHealthBar healthBar;

  private Rectangle collisionBox; // + Sprite for now at least

  public Bacteria() {
    super();
    setup();
    playerType = PlayerType.BOT_PLAYER;
    lane = Lane.BOT;
  }
  /*
  Each moving unit could be given a queue of checkpoints to reach
  and then one left at the enemy base it would be within range and attack
  */
  public Bacteria(EncounterScreen map, PlayerType playerType, Lane lane) {
    this.playerType = playerType;
    this.lane = lane;
    this.map = map;
    setup();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    // We can put this in troop if all Troops will act in a similar way
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

    if (moving) {
      if (playerType == PlayerType.BOT_PLAYER) {
        /*if (lane == Lane.BOT) {
          System.out.println(getX());
          *//*System.out.println(
              "X: "
                  + getX()
                  + "\tCentre X: "
                  + getCentreX()
                  + "\tOrigin X: "
                  + getOriginX()
                  + "\tScale X: "
                  + getScaleX());*//*
          //if (getCentreX() > map.getBotTurnPointX()) {
          if (getX() > 150) {
            moveLeft(delta);
          } else {
            moveUp(delta);
          }
        }*/

        // Turn values are too hard coded, have turn points sent in the EncounterScreen that this can access so every
        // Troop conforms to the same turn location
        // And the unit should turn when the centre of the unit has passed the respective turn point
        if (lane == Lane.BOT) {
          if (getX() > 150) {
            moveLeft(delta);
          } else {
            moveUp(delta);
          }
        } else if (lane == Lane.MID) {
          moveLeft(delta / 2);
          moveUp(delta / 2);
        } else if (lane == Lane.TOP) {
          if (getY() < 550) {
            moveUp(delta);
          } else {
            moveLeft(delta);
          }
        }
      } else if (playerType == PlayerType.TOP_PLAYER) {
        if (lane == Lane.BOT) {
          if (getCentreY() > map.getBotTurnPointY()) {
            moveDown(delta);
          } else {
            moveRight(delta);
          }
        }
      }
    }
  }

  private void attack(Troop troop) {
    troop.hit(damage);
  }

  public void checkAttack(ArrayList<Troop> enemies) {
    Troop closestEnemy = null;
    for (Troop enemy : enemies) {
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
      if (!moving) setMoving(true);
    }
  }

  // public Rectangle getCollisionBox() { return collisionBox; }

  private void setup(){
    // Dimensions
    setSize(50, 50);

    // Troop Stats
    health = maxHealth = 100;
    speed = 100;
    attackable = true;
    moving = true;
    attacking = false;
    // attackCooldown = 20;
    cooldown = 1000; // Milliseconds
    lastAttack = 0;
    range = 100;
    damage = 30;

    // Images and Animations
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
    sprite = new Image(walkFrames[0].getTexture());
  }
}
