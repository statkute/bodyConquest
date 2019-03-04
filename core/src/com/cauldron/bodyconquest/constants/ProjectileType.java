package com.cauldron.bodyconquest.constants;

public enum ProjectileType implements MapObjectType {
  FLU_PROJECTILE;

  @SuppressWarnings("unchecked")
  public <T extends Enum & MapObjectType> T getMapObjectType() {
    return (T) this;
  }
}
