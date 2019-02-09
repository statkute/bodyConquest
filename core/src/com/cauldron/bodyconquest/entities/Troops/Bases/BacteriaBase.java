package com.cauldron.bodyconquest.entities.Troops.Bases;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;
import com.cauldron.bodyconquest.handlers.GifDecoder;

import java.util.ArrayList;

public class BacteriaBase extends Base {

    float stateTime;

    public BacteriaBase(EncounterScreen.PlayerType pt){
        super(pt);
        init();
    }

    @Override
    public void checkAttack(ArrayList<Troop> enemies) {

    }

    private void init(){
        this.health = 100;
        this.damage = 3;
        this.imageBase =
                GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("core/assets/castle1.gif").read());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        currentFrame = imageBase.getKeyFrame(stateTime, true);
        super.draw(batch, parentAlpha);
    }
}
