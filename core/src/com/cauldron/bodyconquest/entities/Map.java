package com.cauldron.bodyconquest.entities;

public class Map {

    private Location[][] map;

    /*
    Map should have a fixed size
     */
    private final int MAP_HEIGHT = 50;
    private final int MAP_LENGTH = 50;

    public Map() {
        for (int i = 0; i < MAP_LENGTH; i++) {
            for (int j = 0; j < MAP_HEIGHT; j++) {
                this.map[i][j] = new Location(i, j);
            }
        }
    }

    public Location[][] getMap() {
        return map;
    }

    /*
       Gotta come back to this, define what is accessible
    */
    public boolean isAccessible(Location l) {
        return l.getType() == LocationType.EMPTY;
    }

    public Object getObject(int x, int y) {
        return map[x][y].getOccupant();
    }

    public Object getObjectType(int x, int y) {
        return map[x][y].getOccupantTypeName();
    }

    public int getMaxX() {
        return MAP_LENGTH;
    }

    public int getMaxY() {
        return MAP_HEIGHT;
    }
}
