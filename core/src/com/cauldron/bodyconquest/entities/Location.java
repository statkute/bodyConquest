package com.cauldron.bodyconquest.entities;

public class Location {

    /*
        Need to store the x and y coordinates of this location on the map for reverse retrieval:
        When trying to get the location of an entity
     */
    private final int x;
    private final int y;
    private LocationType type;
    private Object occupant;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = LocationType.EMPTY;
    }

    public Location(int x, int y, Object occupant) {
        this.x = x;
        this.y = y;
        this.type = LocationType.UNIT;
        this.occupant = occupant;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public String getOccupantTypeName() {
        return occupant.getClass().getName();
    }

    public Object getOccupant() {
        return occupant;
    }

    public void setOccupant(Object occupant) {
        this.occupant = occupant;
    }
}
