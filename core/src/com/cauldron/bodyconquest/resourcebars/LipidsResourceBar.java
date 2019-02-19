package com.cauldron.bodyconquest.resourcebars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.rendering.BodyConquest;

public class LipidsResourceBar extends ResourceBar {

    public LipidsResourceBar() {
        setResourceType(Constants.ResourceType.LIPID);
        setOutline(new TextureRegion(new Texture("core/assets/lipid_outline.png")));
        setInside(new TextureRegion(new Texture("core/assets/droplet.png")));
        //setX(getX() + BodyConquest.V_WIDTH / 20.0f);
    }
}
