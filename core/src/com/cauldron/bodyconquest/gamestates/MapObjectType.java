package com.cauldron.bodyconquest.gamestates;

public interface MapObjectType {

  <T extends Enum & MapObjectType> T getMapObjectType();


}
