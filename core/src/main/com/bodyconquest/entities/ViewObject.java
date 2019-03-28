package main.com.bodyconquest.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import main.com.bodyconquest.handlers.GifDecoder;
import main.com.bodyconquest.screens.EncounterScreen;
import main.com.bodyconquest.constants.*;

public class ViewObject extends Actor {

    private Texture texture;
    private Animation<TextureRegion> walkAnimation;
    float stateTime;
    private TextureRegion[] walkFrames;
    private TextureRegion currentFrame;
    private float elapsedTime;
    private BasicObject bo;
    private PlayerType playerType;

  // Constructor for spritesheet (default time between frames of 0.2f)
//  public ViewObject(BasicObject bo, String pathTexture, int frameCols, int frameRows, float elapsedTime, PlayerType playerType) {
//    this.bo = bo;
//    this.elapsedTime = elapsedTime;
//    walkAnimation = AnimationWrapper.getSpriteSheet(frameCols, frameRows, 0.2f, pathTexture);
//    if(bo.getMapObjectType().getClass().getSuperclass() == ProjectileType.class){
//      setRotation((float)bo.getRotation());
//    }
//    setX((float)bo.getX());
//    setY((float)bo.getY());
//    setWidth((float)bo.getWidth());
//    setHeight((float)bo.getHeight());
//    stateTime = 0f;
//    this.playerType = playerType;
//  }

    public ViewObject(BasicObject bo, float elapsedTime, PlayerType playerType, Animation<TextureRegion> walkAnimation) {

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

//  // Constructor for spritesheet with defined framerate
//  public ViewObject(BasicObject bo, String pathTexture, int frameCols, int frameRows,float elapsedTime, int frameRate,PlayerType playerType) {
//    // Right now all textures are the default buckets
//    //texture = new Texture("core/assets/bucket.png");
//    //texture = new Texture(pathTexture);
//    this.bo = bo;
//    this.elapsedTime = elapsedTime;
//    walkAnimation = AnimationWrapper.getSpriteSheet(frameCols, frameRows, frameRate, pathTexture);
//    if(bo.getMapObjectType().getClass().getSuperclass() == ProjectileType.class){
//      setRotation((float)bo.getRotation());
//    }
//    setX((float)bo.getX());
//    setY((float)bo.getY());
//    setOrigin(getWidth()/2, getHeight()/2);
//    setWidth((float)bo.getWidth());
//    setHeight((float)bo.getHeight());
//    stateTime = 0f;
//    this.playerType = playerType;
//  }

    // Constructor for gif images
    public ViewObject(BasicObject bo, String pathTexture, float elapsedTime, PlayerType playerType) {
        this.bo = bo;
        walkAnimation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal(pathTexture).read());
        this.elapsedTime = elapsedTime;
        if (ProjectileType.isProjectileType(bo.getMapObjectType())) {
            setRotation((float) bo.getRotation());
        }
        stateTime = 0f;
        setX((float) bo.getX());
        setY((float) bo.getY());
        setWidth((float) bo.getWidth());
        setHeight((float) bo.getHeight());
        this.playerType = playerType;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.bo.getMapObjectType() == BaseType.INFLUENZA_BASE || this.bo.getMapObjectType() == BaseType.MEASLES_BASE || this.bo.getMapObjectType() == BaseType.ROTAVIRUS_BASE) {
            preDraw();
            commonDraw(batch, parentAlpha);
            postDraw(batch);
        } else if (this.bo.getWasHit() && (bo.getMapObjectType() == UnitType.BACTERIA || bo.getMapObjectType() == UnitType.FUNGUS || bo.getMapObjectType() == UnitType.VIRUS)) {
            preDrawObject();
            commonDraw(batch, parentAlpha);
            postDraw(batch);
        } else {
            preDrawObject();
            commonDraw(batch, parentAlpha);
            postDraw(batch);
        }

    }

    public void postDraw(Batch batch) {
        batch.setColor(1, 1, 1, 1);

    }

    public void preDrawObject() {
        long timeAlive = bo.getTimeAlive();
        long timeOfDmgTaken = bo.getTimeOfDmgTaken();
        long timeElapsed = timeAlive - timeOfDmgTaken;

        if (timeElapsed < EncounterScreen.BLINK_TIME_AFTER_DMG_BACTERIAS) {
            float t = timeElapsed / EncounterScreen.BLINK_TIME_AFTER_DMG_BACTERIAS;
            t = t * t;
            setColor(1, 0, 0, t);
        }

    }

    public void preDraw() {
        if (((playerType == PlayerType.PLAYER_TOP || playerType == PlayerType.AI) && bo.getY() == Assets.baseTopY) || (playerType == PlayerType.PLAYER_BOTTOM && EncounterScreen.gameType == GameType.SINGLE_PLAYER && bo.getY() == Assets.baseTopY)) {

            if (EncounterScreen.getTimeAlive() < EncounterScreen.getTimeOfDmgTakenTop() + EncounterScreen.BLINK_TIME_AFTER_DMG) {
                float t = (EncounterScreen.getTimeAlive() - EncounterScreen.getTimeOfDmgTakenTop()) / EncounterScreen.BLINK_TIME_AFTER_DMG;
                t = t * t;
                setColor(1, 0, 0, t);
            }
        }
        if (playerType == PlayerType.PLAYER_BOTTOM && bo.getY() == Assets.baseBottomY) {
            if (EncounterScreen.getTimeAlive() < EncounterScreen.getTimeOfDmgTakenBottom() + EncounterScreen.BLINK_TIME_AFTER_DMG) {
                float t = (EncounterScreen.getTimeAlive() - EncounterScreen.getTimeOfDmgTakenBottom()) / EncounterScreen.BLINK_TIME_AFTER_DMG;
                t = t * t;
                setColor(1, 0, 0, t);
            }

        }

        // new if
        if (playerType == PlayerType.PLAYER_BOTTOM && bo.getY() == Assets.baseTopY) {
            if (EncounterScreen.getTimeAlive() < EncounterScreen.getTimeOfDmgTakenTop() + EncounterScreen.BLINK_TIME_AFTER_DMG) {
                float t = (EncounterScreen.getTimeAlive() - EncounterScreen.getTimeOfDmgTakenTop()) / EncounterScreen.BLINK_TIME_AFTER_DMG;
                t = t * t;
                setColor(1, 0, 0, t);
            }

        }

        if (playerType == PlayerType.PLAYER_TOP && bo.getY() == Assets.baseBottomY) {
            if (EncounterScreen.getTimeAlive() < EncounterScreen.getTimeOfDmgTakenBottom() + EncounterScreen.BLINK_TIME_AFTER_DMG) {
                float t = (EncounterScreen.getTimeAlive() - EncounterScreen.getTimeOfDmgTakenBottom()) / EncounterScreen.BLINK_TIME_AFTER_DMG;
                t = t * t;
                setColor(1, 0, 0, t);
            }

        }

    }

    public void commonDraw(Batch batch, float parentAlpha) {

        stateTime += elapsedTime; // Accumulate elapsed animation time
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(currentFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        //AffineTransform trans = AffineTransform.getRotateInstance(getDirection() + Math.PI, getX() + ((getWidth() - getCwidth()) / 2), getY() + ((getHeight() - getCheight()) / 2)

//    ShapeRenderer shapeRenderer = new ShapeRenderer();
//
//      shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
//
//    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//    shapeRenderer.setColor(Color.GREEN);
//    shapeRenderer.rect((float)bo.getX() + ((bo.getWidth() - bo.getCwidth()) / 2), (float)bo.getY() + ((bo.getHeight() - bo.getCheight()) / 2), bo.getCwidth(), bo.getCheight());
//    shapeRenderer.end();
        //setColor(1,1,1,1);
        super.draw(batch, parentAlpha);
    }

    public MapObjectType getMapObjectType() {
        return bo.getMapObjectType();
    }

  public Animation<TextureRegion> getWalkAnimation() { return walkAnimation; }

  public String getKey() {
    Enum mot = bo.getMapObjectType();
    return mot.name() + bo.getPlayerType().getEncoded();
  }

}
