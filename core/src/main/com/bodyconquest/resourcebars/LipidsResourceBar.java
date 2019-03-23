package main.com.bodyconquest.resourcebars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import main.com.bodyconquest.constants.Resource;
import main.com.bodyconquest.handlers.AnimationWrapper;
import main.com.bodyconquest.rendering.BodyConquest;

public class LipidsResourceBar extends ResourceBar {

    public LipidsResourceBar() {
        setResourceType(Resource.LIPID);
        setOutline(new TextureRegion(new Texture("core/assets/lipid_outline.png")));
        setInside(new TextureRegion(new Texture("core/assets/lipids_inside.png")));
        setInsideTexturePath("core/assets/lipids_inside.png");
        setX(getX() + BodyConquest.V_WIDTH / 20.0f);
        walkAnimation = AnimationWrapper.getSpriteSheet(4, 1, 0.2f, getInsideTexturePath());
    }
}
