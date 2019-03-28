package main.com.bodyconquest.constants;

/**
 * The interface Map object type.
 */
public interface MapObjectType {

    /**
     * Gets the map object type.
     *
     * @param <T> the type parameter
     * @return the map object type
     */
    <T extends Enum & MapObjectType> T getMapObjectType();

}
