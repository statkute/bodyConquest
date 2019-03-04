package com.cauldron.bodyconquest.constants;

import com.cauldron.bodyconquest.gamestates.MapObjectType;

public enum ProjectileType implements MapObjectType {
  FLU_PROJECTILE;

  @SuppressWarnings("unchecked")
  public <T extends Enum & MapObjectType> T getMapObjectType() {
    return (T) this;
  }
}
