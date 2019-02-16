package com.cauldron.bodyconquest.networking.utilities;

import com.cauldron.bodyconquest.gamestates.EncounterState;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

public class Serialization {
  public static String serialize(EncounterState encounterState) {
    String json = JsonWriter.objectToJson(encounterState);
    System.out.println(json);
    return json;
  }

  public static EncounterState deserialize(String json) {
    EncounterState encounterState = (EncounterState) JsonReader.jsonToJava(json);
    System.out.println(encounterState);
    return encounterState;
  }
}
