package com.cauldron.bodyconquest.enities;

public class Map {
  private SquareLocation[][] map;

  public Map(int length_x, int height_y) {
      for (int i = 0; i  < length_x; i ++){
          for (int j = 0; j < height_y; j ++){
              map[i][j] = new SquareLocation();
          }
      }
  }

    public SquareLocation[][] getMap() {
        return map;
    }

    public boolean isEmpty (int x, int y){
      return map[x][y].getIsEmpty();
    }

    public Object getObject (int x, int y){
        return map[x][y].getOccupant();
    }

    public Object getObjectType (int x, int y){
        return map[x][y].getOccupantTypeName();
    }


}
