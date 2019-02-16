package com.cauldron.bodyconquest.networking.utilities;

import com.cauldron.bodyconquest.gamestates.EncounterState;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

import java.io.IOException;

public class Serialization {
  public static String serialize(EncounterState encounterState) throws IOException {
    String json = JsonWriter.objectToJson(encounterState);
    System.out.println(json);
    return json;
  }

  public static EncounterState deserialize(String json) throws IOException {
    EncounterState encounterState = (EncounterState) JsonReader.jsonToJava(json);
    System.out.println(encounterState);
    return encounterState;
  }
}
