package main.com.bodyconquest.entities.abilities;

import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.entities.Spawnable;
import main.com.bodyconquest.gamestates.EncounterState;

/**
 * The type Ability.
 */
public abstract class Ability implements Spawnable {

    /**
     * The Player type.
     */
    protected PlayerType playerType;

    /**
     * The Lane effect.
     */
    protected boolean laneEffect;

    /**
     * Instantiates a new Ability.
     *
     * @param playerType the player type
     */
    public Ability(PlayerType playerType) {
        this.playerType = playerType;
    }

    /**
     * Casts the ability in the game.
     *
     * @param game the game to be casted
     */
    public abstract void cast(EncounterState game);
}
