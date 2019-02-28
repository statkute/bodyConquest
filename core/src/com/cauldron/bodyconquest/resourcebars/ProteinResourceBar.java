package com.cauldron.bodyconquest.resourcebars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.handlers.AnimationWrapper;

public class ProteinResourceBar extends ResourceBar {

    public ProteinResourceBar(){
        setResourceType(Constants.ResourceType.PROTEIN);
        setOutline(new TextureRegion(new Texture("core/assets/protein_outline.png")));
        setInside(new TextureRegion(new Texture("core/assets/protein_inside.png")));
        setInsideTexturePath("core/assets/protein_inside.png");
        walkAnimation = AnimationWrapper.getSpriteSheet(4, 1, 0.2f, getInsideTexturePath());
    }

}