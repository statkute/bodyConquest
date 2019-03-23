package main.com.bodyconquest.constants;

public interface MapObjectType {

  <T extends Enum & MapObjectType> T getMapObjectType();

}
