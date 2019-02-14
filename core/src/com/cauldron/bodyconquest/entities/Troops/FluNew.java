//package com.cauldron.bodyconquest.entities.Troops;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.cauldron.bodyconquest.gamestates.EncounterScreen;
//import com.cauldron.bodyconquest.gamestates.EncounterScreen.Lane;
//import com.cauldron.bodyconquest.gamestates.EncounterScreen.PlayerType;
//import com.cauldron.bodyconquest.handlers.AnimationWrapper;
//
//public class FluNew extends Troop {
//
//  float stateTime;
//  private TextureRegion[] walkFrames;
//  private Animation<TextureRegion> walkAnimation;
//
//  private EncounterScreen map;
//  private PlayerType playerType;
//  // Some above head health bar?
//  // private UnitHealthBar healthBar;
//
//  private Rectangle collisionBox; // + Sprite for now at least
//
//  public FluNew() {
//    super(Lane.BOT);
//    init();
//    playerType = PlayerType.BOT_PLAYER;
//  }
//
//
//  /*
//  Each moving unit could be given a queue of checkpoints to reach
//  and then one left at the enemy base it would be within range and attack
//  */
//  public FluNew(EncounterScreen map, PlayerType playerType, Lane lane) {
//    super(lane);
//    this.playerType = playerType;
//    this.map = map;
//    init();
//  }
//  private void init(){
//    // Dimensions
//    setSize(50, 50);
//
//    // Troop Stats
//    health = maxHealth = 100;
//    speed = 100;
//    attackable = true;
//    moving = true;
//    attacking = false;
//    // attackCooldown = 20;
//    cooldown = 1000; // Milliseconds
//    lastAttack = 0;
//    range = 50;
//    damage = 30;
//
//    walkAnimation = AnimationWrapper.getSpriteSheet(7, 1, 0.2f, "core/assets/flu.png");
//
//    stateTime = 0f;
//
//
//
//    // maybe better to use Rectangle class? instead of Image class (found in Tutorials)
//    sprite = new Image(walkAnimation.getKeyFrame(0));
//  }
//
//  @Override
//  public void draw(Batch batch, float parentAlpha) {
//    stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
//
//    // Get current frame of animation for the current stateTime
//    currentFrame = walkAnimation.getKeyFrame(stateTime, true);
//
//    super.draw(batch, parentAlpha);
//  }
//
//  @Override
//  public void act(float delta) {
//    super.act(delta);
//
//    if (moving) {
//      if (playerType == PlayerType.BOT_PLAYER) {
//        /*if (lane == Lane.BOT) {
//          System.out.println(getX());
//          *//*System.out.println(
//              "X: "
//                  + getX()
//                  + "\tCentre X: "
//                  + getCentreX()
//                  + "\tOrigin X: "
//                  + getOriginX()
//                  + "\tScale X: "
//                  + getScaleX());*//*
//          //if (getCentreX() > map.getBotTurnPointX()) {
//          if (getX() > 150) {
//            moveLeft(delta);
//          } else {
//            moveUp(delta);
//          }
//        }*/
//
//        // Turn values are too hard coded, have turn points sent in the EncounterScreen that this can access so every
//        // Troop conforms to the same turn location
//        // And the unit should turn when the centre of the unit has passed the respective turn point
//        if (lane == Lane.BOT) {
//          if (getX() > 150) {
//            moveLeft(delta);
//          } else {
//            moveUp(delta);
//          }
//        } else if (lane == Lane.MID) {
//          moveLeft(delta / 2);
//          moveUp(delta / 2);
//        } else if (lane == Lane.TOP) {
//          if (getY() < 550) {
//            moveUp(delta);
//          } else {
//            moveLeft(delta);
//          }
//        }
//      } else if (playerType == PlayerType.TOP_PLAYER) {
//        if (lane == Lane.BOT) {
//          if (getCentreY() > map.getBotTurnPointY()) {
//            moveDown(delta);
//          } else {
//            moveRight(delta);
//          }
//        }
//      }
//    }
//  }
//
//
//}
