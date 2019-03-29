package main.com.bodyconquest.constants;

/**
 * The enum Projectile type.
 */
public enum ProjectileType implements MapObjectType {
    /**
     * Virus projectile projectile type.
     */
    VIRUS_PROJECTILE,
    /**
     * Fungus projectile projectile type.
     */
    FUNGUS_PROJECTILE;

    @SuppressWarnings("unchecked")
    public <T extends Enum & MapObjectType> T getMapObjectType() {
        return (T) this;
    }

    /**
     * Is projectile type boolean.
     *
     * @param mot the MapObjectType
     * @return true if it is a projectile, false otherwise
     */
    public static boolean isProjectileType(MapObjectType mot) {
        for (ProjectileType pt : values()) {
            if (pt == mot) return true;
        }
        return false;
    }
}
