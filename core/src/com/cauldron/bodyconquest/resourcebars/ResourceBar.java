package com.cauldron.bodyconquest.resourcebars;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.cauldron.bodyconquest.constants.Assets.ResourceType;
import com.cauldron.bodyconquest.rendering.BodyConquest;

public abstract class ResourceBar extends Actor {

    private float resource;
    private ResourceType resourceType;
    private TextureRegion outline;
    private TextureRegion inside;
    private TextureRegion currentFrame;
    protected Animation<TextureRegion> walkAnimation;
    float stateTime;
    private float elapsedTime;
    private int insideY;
    private String insideTexturePath;

    public ResourceBar(){
//        walkAnimation = AnimationWrapper.getSpriteSheet(4, 1, 0.2f, getInsideTexturePath());
    }

    protected float getResource() {
        return resource;
    }

    protected void setResource(float resource) {
        this.resource = resource;
    }

    protected void setResourceType(ResourceType rt) {
        this.resourceType = rt;
    }


    public TextureRegion getOutline() {
        return outline;
    }

    public void setOutline(TextureRegion outline) {
        this.outline = outline;
    }

    public TextureRegion getInside() {
        return inside;
    }

    public void setInside(TextureRegion inside) {
        this.inside = inside;
    }

    public void setInsideTexturePath(String path){this.insideTexturePath = path;}

    public void setInsideY(int y){
        insideY = y;
    }

    private int getInsideY(){
        return insideY;
    }

    public void updateTime(float t){
        elapsedTime = t;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        //currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        this.setWidth(BodyConquest.V_WIDTH / 20.0f);
        this.setHeight(BodyConquest.V_HEIGHT - 50);currentFrame = getInside();
        //stateTime += elapsedTime;
        //currentFrame = getInside();
        currentFrame = walkAnimation.getKeyFrame(elapsedTime, true);
        this.setY(getInsideY()+50);
        batch.draw(currentFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        currentFrame = getOutline();
        this.setY(50);
        batch.draw(currentFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());


    }

    public String getInsideTexturePath() {
        return insideTexturePath;
    }
}
