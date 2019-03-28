package main.com.bodyconquest.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import main.com.bodyconquest.rendering.BodyConquest;

/**
 * The type Unit bar.
 */
public class UnitBar extends Actor {
    private Image unitBar;
    private Texture asdf;
    private TextureRegion uBar;

    /**
     * Instantiates a new Unit bar.
     */
    public UnitBar() {
        unitBar = new Image(new Texture("core/assets/Action Bar v1.png"));
        unitBar.setBounds(0, 0, BodyConquest.V_WIDTH, 250);
        asdf = new Texture("core/assets/Action Bar v1.png");
        uBar = new TextureRegion(asdf);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setY(0);
        setX(0);
        setWidth(BodyConquest.V_WIDTH);
        setHeight(50);
        batch.draw(uBar, getX(), getY(), getWidth(), getHeight());

    }

}
