package main.com.bodyconquest.resourcebars;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import main.com.bodyconquest.constants.Resource;
import main.com.bodyconquest.rendering.BodyConquest;

public abstract class ResourceBar extends Actor {

    private float resource;
    private TextureRegion outline;
    private TextureRegion inside;
    private TextureRegion currentFrame;
    protected Animation<TextureRegion> walkAnimation;
    private float elapsedTime;
    private int insideY;
    private String insideTexturePath;

    public ResourceBar(){
    }

    protected float getResource() {
        return resource;
    }

    protected void setResource(float resource) {
        this.resource = resource;
    }

    protected void setResourceType(Resource rt) {
        Resource resourceType = rt;
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
        // Get current frame of animation for the current stateTime
        this.setWidth(BodyConquest.V_WIDTH / 20.0f);
        this.setHeight(BodyConquest.V_HEIGHT - 50);currentFrame = getInside();

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
