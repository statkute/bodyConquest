package com.cauldron.bodyconquest.resourcebars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.rendering.BodyConquest;

public class CarbsResourceBar extends ResourceBar {

    public CarbsResourceBar(){
        setResourceType(Constants.ResourceType.CARBS);
        setOutline(new TextureRegion(new Texture("core/assets/carbs_outline.png")));
        setInside(new TextureRegion(new Texture("core/assets/droplet.png")));
        setX(getX()+2*(BodyConquest.V_WIDTH / 20.0f));
    }

}
