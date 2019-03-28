package main.com.bodyconquest.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import main.com.bodyconquest.constants.*;
import main.com.bodyconquest.screens.EncounterScreen;

/** The type View object. */
public class ViewObject extends Actor {

  private Texture texture;
  private Animation<TextureRegion> walkAnimation;
  /** The State time. */
  float stateTime;

  private TextureRegion[] walkFrames;
  private TextureRegion currentFrame;
  private float elapsedTime;
  private BasicObject bo;
  private PlayerType playerType;

  /**
   * Instantiates a new View object.
   *
   * @param bo the bo
   * @param elapsedTime the elapsed time
   * @param playerType the player type
   * @param walkAnimation the walk animation
   */
  public ViewObject(
      BasicObject bo,
      float elapsedTime,
      PlayerType playerType,
      Animation<TextureRegion> walkAnimation) {

    // Right now all textures are the default buckets
    this.bo = bo;
    this.elapsedTime = elapsedTime;
    this.walkAnimation = walkAnimation;

    if (ProjectileType.isProjectileType(bo.getMapObjectType())) {
      setRotation((float) bo.getRotation());
    }

    setX((float) bo.getX());
    setY((float) bo.getY());
    setWidth((float) bo.getWidth());
    setHeight((float) bo.getHeight());
    setOrigin(getWidth() / 2, getHeight() / 2);
    stateTime = 0f;
    this.playerType = playerType;
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    if (this.bo.getMapObjectType() == BaseType.INFLUENZA_BASE
        || this.bo.getMapObjectType() == BaseType.MEASLES_BASE
        || this.bo.getMapObjectType() == BaseType.ROTAVIRUS_BASE) {
      preDraw();
      commonDraw(batch, parentAlpha);
      postDraw(batch);
    } else if (this.bo.getWasHit()
        && (bo.getMapObjectType() == UnitType.BACTERIA
            || bo.getMapObjectType() == UnitType.FUNGUS
            || bo.getMapObjectType() == UnitType.VIRUS)) {
      preDrawObject();
      commonDraw(batch, parentAlpha);
      postDraw(batch);
    } else {
      preDrawObject();
      commonDraw(batch, parentAlpha);
      postDraw(batch);
    }
  }

  /**
   * Post draw.
   *
   * @param batch the batch
   */
  public void postDraw(Batch batch) {
    batch.setColor(1, 1, 1, 1);
  }

  /** Pre draw object. */
  public void preDrawObject() {
    long timeAlive = bo.getTimeAlive();
    ;
    long timeOfDmgTaken = bo.getTimeOfDmgTaken();
    long timeElapsed = timeAlive - timeOfDmgTaken;

    if (timeElapsed < EncounterScreen.BLINK_TIME_AFTER_DMG_BACTERIAS) {
      float t = timeElapsed / EncounterScreen.BLINK_TIME_AFTER_DMG_BACTERIAS;
      t = t * t;
      setColor(1, 0, 0, t);
    }
  }

  /** Pre draw. */
  public void preDraw() {
    if (((playerType == PlayerType.PLAYER_TOP || playerType == PlayerType.AI)
            && bo.getY() == Assets.baseTopY)
        || (playerType == PlayerType.PLAYER_BOTTOM
            && EncounterScreen.gameType == GameType.SINGLE_PLAYER
            && bo.getY() == Assets.baseTopY)) {

      if (EncounterScreen.getTimeAlive()
          < EncounterScreen.getTimeOfDmgTakenTop() + EncounterScreen.BLINK_TIME_AFTER_DMG) {
        float t =
            (EncounterScreen.getTimeAlive() - EncounterScreen.getTimeOfDmgTakenTop())
                / EncounterScreen.BLINK_TIME_AFTER_DMG;
        t = t * t;
        setColor(1, 0, 0, t);
      }
    }
    if (playerType == PlayerType.PLAYER_BOTTOM && bo.getY() == Assets.baseBottomY) {
      if (EncounterScreen.getTimeAlive()
          < EncounterScreen.getTimeOfDmgTakenBottom() + EncounterScreen.BLINK_TIME_AFTER_DMG) {
        float t =
            (EncounterScreen.getTimeAlive() - EncounterScreen.getTimeOfDmgTakenBottom())
                / EncounterScreen.BLINK_TIME_AFTER_DMG;
        t = t * t;
        setColor(1, 0, 0, t);
      }
    }

    // new if
    if (playerType == PlayerType.PLAYER_BOTTOM && bo.getY() == Assets.baseTopY) {
      if (EncounterScreen.getTimeAlive()
          < EncounterScreen.getTimeOfDmgTakenTop() + EncounterScreen.BLINK_TIME_AFTER_DMG) {
        float t =
            (EncounterScreen.getTimeAlive() - EncounterScreen.getTimeOfDmgTakenTop())
                / EncounterScreen.BLINK_TIME_AFTER_DMG;
        t = t * t;
        setColor(1, 0, 0, t);
      }
    }

    if (playerType == PlayerType.PLAYER_TOP && bo.getY() == Assets.baseBottomY) {
      if (EncounterScreen.getTimeAlive()
          < EncounterScreen.getTimeOfDmgTakenBottom() + EncounterScreen.BLINK_TIME_AFTER_DMG) {
        float t =
            (EncounterScreen.getTimeAlive() - EncounterScreen.getTimeOfDmgTakenBottom())
                / EncounterScreen.BLINK_TIME_AFTER_DMG;
        t = t * t;
        setColor(1, 0, 0, t);
      }
    }
  }

  /**
   * Common draw.
   *
   * @param batch the batch
   * @param parentAlpha the parent alpha
   */
  public void commonDraw(Batch batch, float parentAlpha) {

    stateTime += elapsedTime; // Accumulate elapsed animation time
    currentFrame = walkAnimation.getKeyFrame(stateTime, true);
    Color color = getColor();
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
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

    super.draw(batch, parentAlpha);
  }

  /**
   * Gets map object type.
   *
   * @return the map object type
   */
  public MapObjectType getMapObjectType() {
    return bo.getMapObjectType();
  }

  /**
   * Gets walk animation.
   *
   * @return the walk animation
   */
  public Animation<TextureRegion> getWalkAnimation() {
    return walkAnimation;
  }

  /**
   * Gets key.
   *
   * @return the key
   */
  public String getKey() {
    Enum mot = bo.getMapObjectType();
    return mot.name() + bo.getPlayerType().getEncoded();
  }
}
