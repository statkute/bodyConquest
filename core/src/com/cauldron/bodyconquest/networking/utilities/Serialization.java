package com.cauldron.bodyconquest.networking.utilities;

import com.cauldron.bodyconquest.entities.BasicObject;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A class used for serialization of objects that are sent from the server to the connected clients
 */
public class Serialization {
  /**
   * Serializes an CopyOnWriteArrayList of objects
   *
   * @param objects objects that need to be serialized
   * @return a json String of the serialized objects
   * @throws IOException throws IO exception
   */
  public static String serialize(CopyOnWriteArrayList<BasicObject> objects) throws IOException {
    String json = JsonWriter.objectToJson(objects);
    return json;
  }

  /**
   * Deserializes a json string to an CopyOnWriteArrayList of object
   *
   * @param json a json String that needs to be deserialized
   * @return a deserialized CopyOnWriteArrayList of objects
   * @throws IOException throws IO exception
   */
  public static CopyOnWriteArrayList<BasicObject> deserialize(String json) throws IOException {
    //System.out.println(json.length());
    CopyOnWriteArrayList<BasicObject> objects = (CopyOnWriteArrayList<BasicObject>) JsonReader.jsonToJava(json);
    return objects;
  }
}
