package com.cauldron.bodyconquest.entities.Troops.Bases;


import com.cauldron.bodyconquest.constants.Lane;
import com.cauldron.bodyconquest.constants.PlayerType;

public class VirusBase extends Base {

  public VirusBase(Lane lane, PlayerType pt) {
    super(lane, pt);
    init();
  }

  private void init() {
    this.health = 70;
    this.damage = 10;
  }
}
