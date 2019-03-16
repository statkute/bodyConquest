package main.com.bodyconquest.constants;

public enum ProjectileType implements MapObjectType {
  FLU_PROJECTILE;

  @SuppressWarnings("unchecked")
  public <T extends Enum & MapObjectType> T getMapObjectType() {
    return (T) this;
  }

  public static boolean isProjectileType(MapObjectType mot) {
    for(ProjectileType pt : values()) {
      if(pt == mot) return true;
    }
    return false;
  }
}
