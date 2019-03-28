package main.com.bodyconquest.resourcebars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import main.com.bodyconquest.constants.Resource;
import main.com.bodyconquest.handlers.AnimationWrapper;

/**
 * The type Protein resource bar.
 */
public class ProteinResourceBar extends ResourceBar {

    /**
     * Instantiates a new Protein resource bar.
     */
    public ProteinResourceBar(){
        setResourceType(Resource.PROTEIN);
        setOutline(new TextureRegion(new Texture("core/assets/protein_outline.png")));
        setInside(new TextureRegion(new Texture("core/assets/protein_inside.png")));
        setInsideTexturePath("core/assets/protein_inside.png");
        walkAnimation = AnimationWrapper.getSpriteSheet(4, 1, 0.2f, getInsideTexturePath());
    }

}
