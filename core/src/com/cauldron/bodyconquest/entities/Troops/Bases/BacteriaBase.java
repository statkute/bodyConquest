package com.cauldron.bodyconquest.entities.Troops.Bases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.gamestates.EncounterState;
import com.cauldron.bodyconquest.handlers.GifDecoder;

public class BacteriaBase extends Base {

    private float stateTime;

    public BacteriaBase(EncounterState.Lane lane, EncounterState.PlayerType pt){
        super(lane, pt);
        init();
    }

    private void init(){
        this.health = 100;
        this.damage = 3;
//        this.imageBase =
//                GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("core/assets/castle1.gif").read());
        this.range = 130;
    }

//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
//
//        // Get current frame of animation for the current stateTime
//        currentFrame = imageBase.getKeyFrame(stateTime, true);
//        super.draw(batch, parentAlpha);
//    }

    @Override
    public void attack(Troop troop) {
        super.attack(troop);
    }

    @Override
    public void update() {

    }
}
