package com.cauldron.bodyconquest.entities.Troops.Bases;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;
import com.cauldron.bodyconquest.handlers.GifDecoder;

import java.util.ArrayList;

public class VirusBase extends Base {

    public VirusBase(EncounterScreen.PlayerType pt){
        super(pt);
        init();
    }

    @Override
    public void checkAttack(ArrayList<Troop> enemies) {

    }

    private void init(){
        this.health = 70;
        this.damage = 10;
        this.imageBase =
                GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("core/assets/castle1.gif").read());
    }

}
