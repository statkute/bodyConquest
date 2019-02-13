package com.cauldron.bodyconquest.resourcebars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cauldron.bodyconquest.constants.Constants;

public class ProteinResourceBar extends ResourceBar {

    public ProteinResourceBar(){
        setResourceType(Constants.resourceType.PROTEIN);
        setOutline(new TextureRegion(new Texture("core/assets/protein_outline.png")));
        setInside(new TextureRegion(new Texture("core/assets/droplet.png")));
    }


}
