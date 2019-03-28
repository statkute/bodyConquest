package main.com.bodyconquest.entities.abilities;

import main.com.bodyconquest.constants.Lane;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.entities.Troops.Bases.Base;
import main.com.bodyconquest.entities.Troops.Troop;
import main.com.bodyconquest.gamestates.EncounterState;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The type Necrosis.
 */
public class Necrosis extends Ability {

    /**
     * The amount of sugar needed to cast this ability.
     */
    public static final int SUGARS_COST = 60;

    /**
     * The amount of sugar needed to cast this ability.
     */
    public static final int PROTEINS_COST = 40;

    /**
     * The amount of sugar needed to cast this ability.
     */
    public static final int LIPIDS_COST = 80;


    private Lane lane;

    private int damage;

    /**
     * Instantiates a new Necrosis.
     */
    public Necrosis() {
        super(PlayerType.PLAYER_TOP);
    }

    /**
     * Instantiates a new Necrosis ability.
     *
     * @param lane       the lane
     * @param playerType the player type
     */
    public Necrosis(Lane lane, PlayerType playerType) {
        super(playerType);
        this.lane = lane; // Should always be Lane.ALL
        this.lane = Lane.ALL;
        init();
    }

    private void init() {
        laneEffect = true;
        damage = 1000000;
    }

    @Override
    public void cast(EncounterState game) {
        CopyOnWriteArrayList<Troop> enemies = game.getEnemyTroops(playerType);
        Base myBase = game.getBase(playerType);

        for (Troop enemy : enemies) {
            if (myBase.distBetween(enemy) <= 300) {
                enemy.hit(damage);
            }
        }
    }

    @Override
    public int getSugarCost() {
        return SUGARS_COST;
    }

    @Override
    public int getLipidCost() {
        return PROTEINS_COST;
    }

    @Override
    public int getProteinCost() {
        return LIPIDS_COST;
    }

    @Override
    public String getPortraitLocation() {
        return "core/assets/necrosis.png";
    }

}
