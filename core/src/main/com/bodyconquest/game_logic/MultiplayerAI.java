package main.com.bodyconquest.game_logic;

import com.badlogic.gdx.Gdx;
import main.com.bodyconquest.constants.Lane;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.constants.Resource;
import main.com.bodyconquest.constants.UnitType;
import main.com.bodyconquest.entities.Troops.Troop;
import main.com.bodyconquest.entities.resources.Resources;
import main.com.bodyconquest.gamestates.EncounterState;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

//for now the AI only acts as extra units spawned at the losingPlayer base

/**
 * Multiplayer AI class that acts as the immune system in multi player mode
 * It uses the decideLoser() method to find which disease poses the least amount of threat each time it acts
 * If the players are fairly similar in strength, it attacks both on their least defended lane to cause the most damage
 * Instead, if one player is clearly losing to the other, the AI aids the losing side and attacks the other player
 * on its most defended lane to protect the losing player
 * <p>
 * In order to decide the losing player, an ASI (Absolute Strength Index) is calculated for each player;
 * This index takes into account:
 * 1. The strength of its troops on the screen
 * 2. The remaining base health
 * 3. The stockpile of resources
 * <p>
 * To take into account all these things without making the formula too complicated, a heuristic is employed:
 * <p>
 * The strength of a unit is calculated as damage*(health/100)/(cooldown/1000)
 * 1 strength point = 1 point in the ASI
 * <p>
 * 10 base health point = 2 points in the ASI
 * <p>
 * 5 resources (of any type) = 1 point is the ASI
 * <p>
 * Note: As the multi player AI is not subject to the same resource constraints as the normal players,
 * it will calculate the number of units it spawns in each wave dependent on the case it is in:
 * <p>
 * 1. When it attacks both players, it will spawn one unit for each 300 ASI points of a player
 * 2. When it attacks only one player, it will try and balance the game by spawning one unit (whose strength is 45)
 * for each 100 points multiple of the difference between the two ASIs (minimum 1 unit). Example:
 * topASI = 250
 * botASI = 120
 * <p>
 * topASI - botASI = 130; 130/100 = 1 => the AI spawns 2 units against the top player
 */
public class MultiplayerAI extends Thread {

    private final int COOLDOWN = 10000;

    private EncounterState game;
    private boolean running;

    private double topASI;
    private double botASI;

    public MultiplayerAI(EncounterState game) {
        this.game = game;
        running = true;
        topASI = 0.0;
        botASI = 0.0;
    }

    @Override
    public void run() {
        super.run();
        long time;
        long lastWave = System.currentTimeMillis();
        while (running) {
            time = System.currentTimeMillis();
            if (time > (lastWave + COOLDOWN)) {
                int winningPlayer = decideWinner();
                if (winningPlayer == 0) {
                    attackBoth();
                } else if (winningPlayer == 1) {
                    defendPlayer(PlayerType.PLAYER_TOP);
                } else {
                    defendPlayer(PlayerType.PLAYER_BOTTOM);
                }
                lastWave = time;
            }
        }
    }

    public void stopRunning() {
        running = false;
    }

    //decides whom the AI sides with -- the losingPlayer

    /**
     * It looks at the players' units strength, base health and current stockpile of resources
     * and computes an Absolute Strength Index (ASI);
     *
     * @return 0 if the two players' ASIs are similar; 1 if the bottom player's ASI is bigger; 2 is the top player's ASI is
     */
    private int decideWinner() {
        int winningPlayer = 0;
        topASI = 0.0;
        botASI = 0.0;

        //compute top player's ASI

        CopyOnWriteArrayList<Troop> topUnits = null;
        topUnits = game.getTroops(PlayerType.PLAYER_TOP);

        double strength = 0.0;
        for (Troop troop : topUnits) {
            strength = (double) troop.getDamage() * ((double) troop.getHealth() / 100) / ((double) troop.getCooldown() / 1000);
            topASI += strength;
        }

        int topBaseHealth = game.getBase(PlayerType.PLAYER_TOP).getHealth();
        topASI += (double) topBaseHealth / 5;

        Resources topResources = game.getResources(PlayerType.PLAYER_TOP);
        int totalTopResources = topResources.getLipids() + topResources.getProteins() + topResources.getSugars();

        topASI += (double) totalTopResources / 5;

        //compute bottom player's ASI

        CopyOnWriteArrayList<Troop> botUnits = null;
        botUnits = game.getTroops(PlayerType.PLAYER_BOTTOM);

        strength = 0.0;
        for (Troop troop : topUnits) {
            strength = (double) troop.getDamage() * ((double) troop.getHealth() / 100) / ((double) troop.getCooldown() / 1000);
            botASI += strength;
        }

        int botBaseHealth = game.getBase(PlayerType.PLAYER_BOTTOM).getHealth();

        botASI += (double) botBaseHealth / 5;

        Resources botResources = game.getResources(PlayerType.PLAYER_BOTTOM);
        int totalBotResources = botResources.getLipids() + botResources.getProteins() + botResources.getSugars();

        botASI += (double) totalBotResources / 5;

        //compare the two

        System.out.println("botASI = " + botASI);
        System.out.println("topASI = " + topASI);

        if (botASI > 1.2 * topASI) {
            winningPlayer = 1;
            System.out.println("winning player is BOTTOM");
        } else if (topASI > 1.2 * botASI) {
            winningPlayer = 2;
            System.out.println("winning player is TOP");
        }

        // else return 0
        return winningPlayer;
    }

    /**
     * @param playerType   the player whose weakest/strongest lane is to be returned (BOTTOM | TOP)
     * @param mostDefended if true, then the function returns the lane with strongest units in it; else, the least defended
     * @return The lane that corresponds to the values of the parameters passed
     */
    private Lane getAttackingLane(PlayerType playerType, boolean mostDefended) {
        Lane lane = Lane.BOTTOM;

        CopyOnWriteArrayList<Troop> units = null;
        units = game.getTroops(playerType);

        double strengthTop = 0.0;
        double strengthMid = 0.0;
        double strengthBot = 0.0;

        double strength = 0.0;
        for (Troop troop : units) {
            strength = (double) troop.getDamage() * ((double) troop.getHealth() / 100) / ((double) troop.getCooldown() / 1000);
            switch (troop.getLane()) {
                case TOP:
                    strengthTop += strength;
                    break;
                case BOTTOM:
                    strengthBot += strength;
                    break;
                default:
                    strengthMid += strength;
            }
        }

        if (!mostDefended) {
            if (strengthTop <= strengthMid && strengthTop <= strengthBot) {
                lane = Lane.TOP;
            } else if (strengthMid <= strengthBot && strengthMid <= strengthTop) {
                //lane = Lane.MIDDLE;
            } else {
                lane = Lane.BOTTOM;
            }
        } else {
            if (strengthTop >= strengthMid && strengthTop >= strengthBot) {
                lane = Lane.TOP;
            } else if (strengthMid >= strengthBot && strengthMid >= strengthTop) {
                //lane = Lane.MIDDLE;
            } else {
                lane = Lane.BOTTOM;
            }
        }

        return lane;
    }

    /**
     * Based on the details given about this class, this method is called when both players are to be attacked
     */
    private void attackBoth() {
        System.out.println("attacked both");
        Lane undefendedTop = getAttackingLane(PlayerType.PLAYER_TOP, false);

        Lane undefendedBot;

        //undefendedBot = getAttackingLane(PlayerType.PLAYER_BOTTOM, false);

        if (undefendedTop == Lane.TOP) {
            undefendedBot = Lane.BOTTOM;
        } else {
            undefendedBot = Lane.TOP;
        }

        int multiplierTop = (int) topASI / 300;
        if (multiplierTop <= 1) multiplierTop = 1;

        //System.out.println("multiplier top: " + multiplierTop);
        //summon all units against top on its undefended lane
        for (int i = 0; i < multiplierTop; i++) {
            summonUnit(undefendedTop, PlayerType.PLAYER_BOTTOM);
        }

        int multiplierBot = (int) botASI / 300;
        if (multiplierBot <= 1) multiplierBot = 1;

        //System.out.println("multiplier bot: " + multiplierBot);

        //summon all units against bot on its undefended lane
        for (int i = 0; i < multiplierBot; i++) {
            summonUnit(undefendedBot, PlayerType.PLAYER_TOP);
        }
    }

    /**
     * Spawn units to defend the weaker player in the game, based on the difference between the players' ASIs
     *
     * @param playerType The player to be defended
     */
    private void defendPlayer(PlayerType playerType) {

        //System.out.println("attacked player");

        double relativeASI = Math.abs(topASI - botASI);

        int multiplier = (int) relativeASI / 100;
        if (multiplier <= 1) multiplier = 1;

        Lane defendedLane;

        if (playerType == PlayerType.PLAYER_BOTTOM) {
            defendedLane = getAttackingLane(PlayerType.PLAYER_TOP, true);
        } else {
            defendedLane = getAttackingLane(PlayerType.PLAYER_BOTTOM, true);
        }


        //summon all units to protect the designated player on the other's most defended one
        for (int i = 0; i < multiplier; i++) {
            summonUnit(defendedLane, playerType);
        }
    }

    /**
     * Method to spawn an AI unit - A WhiteCell
     *
     * @param lane       the lane in which the unit is spawned
     * @param playerType the player with which the unit allies itself
     */
    private void summonUnit(Lane lane, PlayerType playerType) {
        System.out.println("summoned a new AI on lane " + lane + " for player " + playerType);
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.spawnUnit(UnitType.WHITE_CELL, lane, playerType, true);
            }
        });
    }
}
