package main.com.bodyconquest.entities.Troops.Bases;

import main.com.bodyconquest.constants.BaseType;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.PlayerType;

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
