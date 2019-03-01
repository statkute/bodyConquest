package com.cauldron.bodyconquest.game_logic;

import com.badlogic.gdx.Gdx;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.gamestates.EncounterState;

import java.util.Random;


//for now the AI only acts as extra units spawned at the losingPlayer base
public class MultiplayerTestAI extends Thread {

    private final int COOLDOWN = 5000;

    private EncounterState game;
    private boolean running;
    private Assets.PlayerType playerType;

    public MultiplayerTestAI(EncounterState game) {
        this.game = game;
        running = true;
    }

    @Override
    public void run() {
        super.run();
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
    private Assets.PlayerType decidePlayer() {
        Assets.PlayerType losingPlayer;
        Random rand = new Random();
        int player = rand.nextInt(2);

        if (player == 1) {
            losingPlayer = Assets.PlayerType.PLAYER_TOP;
        } else {
            losingPlayer = Assets.PlayerType.PLAYER_BOTTOM;
        }

        return losingPlayer;
    }

    private void summonWave() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.spawnUnit(Assets.UnitType.BACTERIA, Assets.Lane.BOTTOM, playerType);
                game.spawnUnit(Assets.UnitType.BACTERIA, Assets.Lane.MIDDLE, playerType);
                game.spawnUnit(Assets.UnitType.FLU, Assets.Lane.TOP, playerType);
            }
        });
    }
}
