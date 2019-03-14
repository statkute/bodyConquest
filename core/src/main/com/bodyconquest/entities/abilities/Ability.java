package main.com.bodyconquest.entities.abilities;

import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.entities.Spawnable;
import main.com.bodyconquest.gamestates.EncounterState;

public abstract class Ability implements Spawnable {

  protected Assets.PlayerType playerType;

  protected boolean laneEffect;

  public Ability(Assets.PlayerType playerType) {
    this.playerType = playerType;
  }

  public abstract void cast(EncounterState game);

  public abstract String damageAreaPath();
}
