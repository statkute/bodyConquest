package main.com.bodyconquest.resourcebars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.handlers.AnimationWrapper;
import main.com.bodyconquest.rendering.BodyConquest;

public class CarbsResourceBar extends ResourceBar {

    public CarbsResourceBar(){
        setResourceType(Assets.ResourceType.CARBS);
        setOutline(new TextureRegion(new Texture("core/assets/carbs_outline.png")));
        setInside(new TextureRegion(new Texture("core/assets/carbs_inside.png")));
        setInsideTexturePath("core/assets/carbs_inside.png");
        setX(getX()+2*(BodyConquest.V_WIDTH / 20.0f));
        walkAnimation = AnimationWrapper.getSpriteSheet(4, 1, 0.2f, getInsideTexturePath());
    }

}
