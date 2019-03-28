package main.com.bodyconquest.game_logic;

import com.badlogic.gdx.Gdx;
import main.com.bodyconquest.constants.Lane;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.constants.UnitType;
import main.com.bodyconquest.gamestates.EncounterState;

import java.util.Random;

//for now the AI only acts as extra units spawned at the losingPlayer base
public class MultiplayerTestAI extends Thread {

    private final int COOLDOWN = 5000;

    private EncounterState game;
    private boolean running;
    private PlayerType playerType;

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
    private PlayerType decidePlayer() {
        PlayerType losingPlayer;
        Random rand = new Random();
        int player = rand.nextInt(2);

        if (player == 1) {
            losingPlayer = PlayerType.PLAYER_TOP;
        } else {
            losingPlayer = PlayerType.PLAYER_BOTTOM;
        }

        return losingPlayer;
    }

    private void summonWave() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.spawnUnit(UnitType.BACTERIA, Lane.BOTTOM, playerType, true);
                game.spawnUnit(UnitType.BACTERIA, Lane.MIDDLE, playerType, true);
                game.spawnUnit(UnitType.VIRUS, Lane.TOP, playerType, true);
            }
        });
    }
}
