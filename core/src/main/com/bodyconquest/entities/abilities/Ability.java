package main.com.bodyconquest.entities.abilities;

import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.entities.Spawnable;
import main.com.bodyconquest.gamestates.EncounterState;

public abstract class Ability implements Spawnable {

  protected PlayerType playerType;

  protected boolean laneEffect;

  public Ability(PlayerType playerType) {
    this.playerType = playerType;
  }

  public abstract void cast(EncounterState game);

  public abstract String damageAreaPath();
}
