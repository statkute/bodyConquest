package main.com.bodyconquest.resourcebars;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import main.com.bodyconquest.constants.Resource;
import main.com.bodyconquest.rendering.BodyConquest;

/**
 * The type Resource bar.
 */
public abstract class ResourceBar extends Actor {

    private float resource;
    private TextureRegion outline;
    private TextureRegion inside;
    private TextureRegion currentFrame;
    /**
     * The Walk animation.
     */
    protected Animation<TextureRegion> walkAnimation;
    private float elapsedTime;
    private int insideY;
    private String insideTexturePath;

    /**
     * Instantiates a new Resource bar.
     */
    public ResourceBar(){
    }

    /**
     * Gets resource.
     *
     * @return the resource
     */
    protected float getResource() {
        return resource;
    }

    /**
     * Sets resource.
     *
     * @param resource the resource
     */
    protected void setResource(float resource) {
        this.resource = resource;
    }

    /**
     * Sets resource type.
     *
     * @param rt the rt
     */
    protected void setResourceType(Resource rt) {
        Resource resourceType = rt;
    }


    /**
     * Gets outline.
     *
     * @return the outline
     */
    public TextureRegion getOutline() {
        return outline;
    }

    /**
     * Sets outline.
     *
     * @param outline the outline
     */
    public void setOutline(TextureRegion outline) {
        this.outline = outline;
    }

    /**
     * Gets inside.
     *
     * @return the inside
     */
    public TextureRegion getInside() {
        return inside;
    }

    /**
     * Sets inside.
     *
     * @param inside the inside
     */
    public void setInside(TextureRegion inside) {
        this.inside = inside;
    }

    /**
     * Set inside texture path.
     *
     * @param path the path
     */
    public void setInsideTexturePath(String path){this.insideTexturePath = path;}

    /**
     * Set inside y.
     *
     * @param y the y
     */
    public void setInsideY(int y){
        insideY = y;
    }

    private int getInsideY(){
        return insideY;
    }

    /**
     * Update time.
     *
     * @param t the t
     */
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

    /**
     * Gets inside texture path.
     *
     * @return the inside texture path
     */
    public String getInsideTexturePath() {
        return insideTexturePath;
    }
}
