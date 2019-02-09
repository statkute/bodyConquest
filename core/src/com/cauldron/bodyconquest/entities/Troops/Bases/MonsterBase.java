package com.cauldron.bodyconquest.entities.Troops.Bases;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;
import com.cauldron.bodyconquest.handlers.GifDecoder;

import java.util.ArrayList;

public class MonsterBase extends Base {

    public MonsterBase(EncounterScreen.Lane lane, EncounterScreen.PlayerType pt){
        super(lane, pt);
        init();
    }


    private void init(){
        this.health = 75;
        this.damage = 8;
        this.imageBase =
                GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("core/assets/castle1.gif").read());
    }

}
