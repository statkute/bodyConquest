package com.cauldron.bodyconquest.entities.Troops.Bases;

import com.cauldron.bodyconquest.constants.Constants.*;

public class VirusBase extends Base {

  public VirusBase(Lane lane, PlayerType pt) {
    super(lane, pt);
    init();
  }

  private void init() {
    this.health = 70;
    this.damage = 10;
    mapObjectType = MapObjectType.VIRUS_BASE;
  }
}
