package com.cauldron.bodyconquest.entities.Troops.Bases;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.cauldron.bodyconquest.gamestates.EncounterState.Lane;
import com.cauldron.bodyconquest.gamestates.EncounterState.PlayerType;
import com.cauldron.bodyconquest.handlers.GifDecoder;

public class VirusBase extends Base {

    public VirusBase(Lane lane, PlayerType pt){
        super(lane, pt);
        init();
    }

    private void init(){
        this.health = 70;
        this.damage = 10;
//        this.imageBase =
//                GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("core/assets/castle1.gif").read());
    }

}
