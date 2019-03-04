package com.cauldron.bodyconquest.resourcebars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.handlers.AnimationWrapper;
import com.cauldron.bodyconquest.rendering.BodyConquest;

public class CarbsResourceBar extends ResourceBar {

    public CarbsResourceBar(){
        setResourceType(Assets.ResourceType.CARBS);
        setOutline(new TextureRegion(new Texture("core/assets/carbs_outline.png")));
        setInside(new TextureRegion(new Texture("core/assets/carbs_inside.png")));
        setInsideTexturePath("core/assets/carbs_inside.png");
        setX(getX()+2*(super.encounterScreenWidth / 20.0f));
        walkAnimation = AnimationWrapper.getSpriteSheet(4, 1, 0.2f, getInsideTexturePath());
    }

}
