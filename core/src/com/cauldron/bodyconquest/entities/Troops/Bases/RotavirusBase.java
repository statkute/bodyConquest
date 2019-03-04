package com.cauldron.bodyconquest.entities.Troops.Bases;

import com.cauldron.bodyconquest.constants.Assets.*;
import com.cauldron.bodyconquest.constants.BaseType;

public class RotavirusBase extends Base {

  public RotavirusBase(PlayerType pt) {
    super(pt);
    init();
  }

  private void init() {
    this.health = 70;
    this.maxHealth = health;
    this.damage = 10;
    mapObjectType = BaseType.ROTAVIRUS_BASE;
  }

  @Override
  public String getPortraitLocation() {
    return null; // Shouldn't really be spawned
  }
}
