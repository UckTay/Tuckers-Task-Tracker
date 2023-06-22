package cs3500.pa05.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.json.BujoJson;
import cs3500.pa05.model.json.EventJson;
import cs3500.pa05.model.json.TaskJson;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a BujoWriter
 */
public class BujoWriter {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  /**
   * Writes to a bujo file
   *
   * @param config the configurations of the week
   * @param entries the entries in a week
   * @param path the path to the file
   */
  public void writeBujo(Config config, List<Entry> entries, Path path) {
    List<TaskJson> tasks = new ArrayList<>();
    List<EventJson> events = new ArrayList<>();
    for (Entry entry : entries) {
      if (entry.getClass() == Task.class) {
        tasks.add(((Task) entry).toJson());
      } else {
        events.add(((Event) entry).toJson());
      }
    }
    try {
      File newFile = new File(path.toUri());
      if (!newFile.exists()) {
        System.out.println("generating new file");
        newFile.createNewFile();
      }
      BujoJson output = new BujoJson(config.toJson(), tasks, events);
      MAPPER.writeValue(newFile, MAPPER.convertValue(output, JsonNode.class));
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }
}
