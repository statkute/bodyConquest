package com.cauldron.bodyconquest.game_logic.utils;

import com.badlogic.gdx.Gdx;
import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.gamestates.EncounterState;

import java.util.Random;


//for now the AI only acts as extra units spawned at the losingPlayer base
public class MultiplayerTestAI extends Thread {

    private final int COOLDOWN = 5000;

    private EncounterState game;
    private boolean running;
    private Constants.PlayerType playerType;

    public MultiplayerTestAI(EncounterState game) {
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
    private Constants.PlayerType decidePlayer() {
        Constants.PlayerType losingPlayer;
        Random rand = new Random();
        int player = rand.nextInt(1);

        if (player == 1) {
            losingPlayer = Constants.PlayerType.PLAYER_TOP;
        } else {
            losingPlayer = Constants.PlayerType.PLAYER_BOTTOM;
        }

        return losingPlayer;
    }

    private void summonWave() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.spawnUnit(Constants.UnitType.BACTERIA, Constants.Lane.BOTTOM, playerType);
                game.spawnUnit(Constants.UnitType.BACTERIA, Constants.Lane.MIDDLE, playerType);
                game.spawnUnit(Constants.UnitType.FLU, Constants.Lane.TOP, playerType);
            }
        });
    }
}
