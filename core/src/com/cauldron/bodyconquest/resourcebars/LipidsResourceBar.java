package com.cauldron.bodyconquest.resourcebars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.handlers.AnimationWrapper;
import com.cauldron.bodyconquest.rendering.BodyConquest;

public class LipidsResourceBar extends ResourceBar {

    public LipidsResourceBar() {
        setResourceType(Assets.ResourceType.LIPID);
        setOutline(new TextureRegion(new Texture("core/assets/lipid_outline.png")));
        setInside(new TextureRegion(new Texture("core/assets/lipids_inside.png")));
        setInsideTexturePath("core/assets/lipids_inside.png");
        setX(getX() + BodyConquest.V_WIDTH / 20.0f);
        walkAnimation = AnimationWrapper.getSpriteSheet(4, 1, 0.2f, getInsideTexturePath());
    }
}
