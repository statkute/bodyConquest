package com.cauldron.bodyconquest.gamestates;

import com.badlogic.gdx.Gdx;
import com.cauldron.bodyconquest.entities.Troops.Troop.*;
import com.cauldron.bodyconquest.gamestates.EncounterScreen.*;

import java.util.Random;


//for now the AI only acts as extra units spawned at the losingPlayer base
public class MultiplayerTestAI extends Thread {

    private final int COOLDOWN = 5000;

    private EncounterScreen game;
    private boolean running;
    private PlayerType playerType;

    public MultiplayerTestAI(EncounterScreen game) {
        this.game = game;
        running = true;
    }

    @Override
    public void run() {
        long time;
        long lastWave = 0;
        while (running) {
            time = System.currentTimeMillis();
            if (time > (lastWave + COOLDOWN)) {
                this.playerType = decidePlayer();
                summonWave();
                lastWave = time;
            }
        }
    }

    //decides whom the AI sides with -- the losingPlayer
    private PlayerType decidePlayer() {
        PlayerType losingPlayer;
        Random rand = new Random();
        int player = rand.nextInt(1);

        if (player == 1) {
            losingPlayer = PlayerType.TOP_PLAYER;
        } else {
            losingPlayer = PlayerType.BOT_PLAYER;
        }

        return losingPlayer;
    }

    private void summonWave() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.spawnUnit(UnitType.BACTERIA, Lane.BOT, playerType);
                game.spawnUnit(UnitType.BACTERIA, Lane.MID, playerType);
                game.spawnUnit(UnitType.FLU, Lane.TOP, playerType);
            }
        });
    }
}
