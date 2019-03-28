package main.com.bodyconquest.resourcebars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import main.com.bodyconquest.constants.Resource;
import main.com.bodyconquest.handlers.AnimationWrapper;
import main.com.bodyconquest.rendering.BodyConquest;

/**
 * The type Carbs resource bar.
 */
public class CarbsResourceBar extends ResourceBar {

    /**
     * Instantiates a new Carbs resource bar.
     */
    public CarbsResourceBar(){
        setResourceType(Resource.SUGAR);
        setOutline(new TextureRegion(new Texture("core/assets/carbs_outline.png")));
        setInside(new TextureRegion(new Texture("core/assets/carbs_inside.png")));
        setInsideTexturePath("core/assets/carbs_inside.png");
        setX(getX() + (BodyConquest.V_WIDTH / 20.0f));
        walkAnimation = AnimationWrapper.getSpriteSheet(4, 1, 0.2f, getInsideTexturePath());
    }

}
