package com.cauldron.bodyconquest.entities.abilities;

import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.entities.Spawnable;
import com.cauldron.bodyconquest.gamestates.EncounterState;

public abstract class Ability implements Spawnable {

  protected Assets.PlayerType playerType;

  protected boolean laneEffect;

  public Ability(Assets.PlayerType playerType) {
    this.playerType = playerType;
  }

  public abstract void cast(EncounterState game);

  public abstract String damageAreaPath();
}
