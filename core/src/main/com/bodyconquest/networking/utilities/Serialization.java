package main.com.bodyconquest.networking.utilities;

import main.com.bodyconquest.entities.BasicObject;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The type Serialization.
 */
public class Serialization {
    /**
     * Serialize string.
     *
     * @param objects the objects
     * @return the string
     * @throws IOException the io exception
     */
    public static String serialize(CopyOnWriteArrayList<BasicObject> objects) throws IOException {
        String json = JsonWriter.objectToJson(objects);
        return json;
    }

    /**
     * Deserialize copy on write array list.
     *
     * @param json the json
     * @return the copy on write array list
     * @throws IOException the io exception
     */
    public static CopyOnWriteArrayList<BasicObject> deserialize(String json) throws IOException {
        //System.out.println(json.length());
        CopyOnWriteArrayList<BasicObject> objects = (CopyOnWriteArrayList<BasicObject>) JsonReader.jsonToJava(json);
        return objects;
    }
}
