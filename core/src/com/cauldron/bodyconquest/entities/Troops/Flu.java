package com.cauldron.bodyconquest.entities.Troops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.entities.FluProjectile;
import com.cauldron.bodyconquest.entities.Troops.Bases.BacteriaBase;
import com.cauldron.bodyconquest.gamestates.EncounterState;
import com.cauldron.bodyconquest.gamestates.EncounterState.Lane;
import com.cauldron.bodyconquest.gamestates.EncounterState.PlayerType;
import com.cauldron.bodyconquest.handlers.AnimationWrapper;

public class Flu extends Troop {

  private float stateTime;
  private Animation<TextureRegion> walkAnimation;

  private EncounterState map;
  private PlayerType playerType;

  public Flu() {
    super(Lane.BOT);
    init();
    playerType = PlayerType.BOT_PLAYER;
  }

  /*
  Each moving unit could be given a queue of checkpoints to reach
  and then one left at the enemy base it would be within range and attack
  */
  public Flu(EncounterState map, PlayerType playerType, Lane lane) {
    super(lane);
    this.playerType = playerType;
    this.map = map;
    init();
  }

  private void init() {
    // Dimensions
    setSize(50, 50);

    // Troop Stats
    health = maxHealth = 70;
    speed = 80;
    attackable = true;
    moving = true;
    attacking = false;
    cooldown = 1300; // Milliseconds
    lastAttack = 0;
    range = 200;
    damage = 40;

    walkAnimation = AnimationWrapper.getSpriteSheet(7, 1, 0.2f, "core/assets/flu.png");

    stateTime = 0f;

    // maybe better to use Rectangle class? instead of Image class (found in Tutorials)
    sprite = new Image(walkAnimation.getKeyFrame(0));
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {

    stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
    // Get current frame of animation for the current stateTime
    currentFrame = walkAnimation.getKeyFrame(stateTime, true);

    super.draw(batch, parentAlpha);
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

        // Turn values are too hard coded, have turn points sent in the EncounterState that this can access so every
        // Troop conforms to the same turn location
        // And the unit should turn when the centre of the unit has passed the respective turn point
        if (lane == Lane.BOT) {
          if (getX() > Constants.BOT_TURNPOINT_X) {
            moveLeft(delta);
          } else {
            moveUp(delta);
          }
        } else if (lane == Lane.MID) {
          moveLeft(delta / 2);
          moveUp(delta / 2);
        } else if (lane == Lane.TOP) {
          if (getY() < Constants.TOP_TURNPOINT_Y) {
            moveUp(delta);
          } else {
            moveLeft(delta);
          }
        }
      } else if (playerType == PlayerType.TOP_PLAYER) {
        if (lane == Lane.BOT) {
          if (getCentreY() > Constants.BOT_TURNPOINT_Y) {
            moveDown(delta);
          } else {
            moveRight(delta);
          }
        } else if (lane == Lane.MID){
          moveRight(delta / 2);
          moveDown(delta / 2);
        } else if (lane == Lane.TOP) {
          if (getX() < Constants.TOP_TURNPOINT_X) {
            moveRight(delta);
            //System.out.println("move right");
          } else {
            moveDown(delta);
          }
          //moveRight(delta);
        }
      }
    }
  }

  @Override
  public void attack(Troop troop) {
    if (troop != null && troop.isAttackable() /*&& inRange(troop)*/) {
      if(troop.getClass() == BacteriaBase.class)System.out.println("HERE");
      setMoving(false);
      long time = System.currentTimeMillis();
      if (!attacking && (time > lastAttack + cooldown)) {
        FluProjectile proj = new FluProjectile(map, getCentreX(), getCentreY(), troop.getCentreX(), troop.getCentreY());
        map.addProjectile(proj, playerType);
        lastAttack = time;
      }
    } else {
      if (!moving) setMoving(true);
    }

  }

}
