package com.cauldron.bodyconquest.networking.utilities;

import com.cauldron.bodyconquest.entities.BasicObject;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class Serialization {
  public static String serialize(CopyOnWriteArrayList<BasicObject> objects) throws IOException {
    String json = JsonWriter.objectToJson(objects);
    return json;
  }

  public static CopyOnWriteArrayList<BasicObject> deserialize(String json) throws IOException {
    //System.out.println(json.length());
    CopyOnWriteArrayList<BasicObject> objects = (CopyOnWriteArrayList<BasicObject>) JsonReader.jsonToJava(json);
    return objects;
  }
}
