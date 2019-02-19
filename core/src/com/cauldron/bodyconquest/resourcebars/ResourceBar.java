package com.cauldron.bodyconquest.resourcebars;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cauldron.bodyconquest.constants.Constants.ResourceType;
import com.cauldron.bodyconquest.entities.MapObject;
import com.cauldron.bodyconquest.rendering.BodyConquest;

public abstract class ResourceBar extends Actor {

    private float resource;
    private ResourceType resourceType;
    private TextureRegion outline;
    private TextureRegion inside;
    private TextureRegion currentFrame;
    private int insideY;

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

    public void setInsideY(int y){
        insideY = y;
    }

    private int getInsideY(){
        return insideY;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        //currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        this.setWidth(BodyConquest.V_WIDTH / 20.0f);
        this.setHeight(BodyConquest.V_HEIGHT - 50);currentFrame = getInside();
        currentFrame = getInside();
        this.setY(getInsideY());
        batch.draw(currentFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        currentFrame = getOutline();
        this.setY(50);
        batch.draw(currentFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());


    }
}
