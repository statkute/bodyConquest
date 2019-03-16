package main.com.bodyconquest.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import main.com.bodyconquest.rendering.BodyConquest;

public class UnitBar extends Actor {
    private Image unitBar;
    private Texture asdf;
    private TextureRegion uBar;

    public UnitBar() {
        unitBar = new Image(new Texture("core/assets/Action Bar v1.png"));
        unitBar.setBounds(0, 0, BodyConquest.V_WIDTH, 250);
        asdf = new Texture("core/assets/Action Bar v1.png");
        uBar = new TextureRegion(asdf);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        //stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
//
//        // Get current frame of animation for the current stateTime
//        //currentFrame = walkAnimation.getKeyFrame(stateTime, true);
//        this.setWidth(BodyConquest.V_WIDTH / 20.0f);
//        this.setHeight(BodyConquest.V_HEIGHT - 50);currentFrame = getInside();
//        //stateTime += elapsedTime;
//        //currentFrame = getInside();
//        currentFrame = walkAnimation.getKeyFrame(elapsedTime, true);
//        this.setY(getInsideY());
//        batch.draw(currentFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            setY(0);
            setX(0);
            setWidth(BodyConquest.V_WIDTH);
            setHeight(50);
//        currentFrame = getOutline();
//        this.setY(50);
          //batch.draw(unitBar, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            batch.draw(uBar, getX(), getY(), getWidth(), getHeight());

    }

}
