package cs3500.pa05.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.json.BujoJson;
import cs3500.pa05.model.json.ConfigJson;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a BujoReader
 */
public class BujoReader {
  private final List<Entry> entryList = new ArrayList<>();
  private static final ObjectMapper MAPPER = new ObjectMapper();

  /**
   * reads a given bujo file
   *
   * @param path the path to the bujo file
   * @return the configurations of the week
   */
  public Config readBujo(Path path) {
    JsonNode fileContents;
    try {
      fileContents = MAPPER.readTree(path.toFile());
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid file path");
    }
    MAPPER.convertValue(fileContents, BujoJson.class);
    Config config = MAPPER.convertValue(fileContents.get("config"), ConfigJson.class).toConfig();
    BujoJson entryGetter = MAPPER.convertValue(fileContents, BujoJson.class);
    entryList.addAll(entryGetter.generateTasks());
    entryList.addAll(entryGetter.generateEvents());
    return config;
  }

  /**
   * Gets the entries in a given bujo file.
   *
   * @param entryType the kind of entry
   * @return a list of that entry type
   * @param <T> the type of entry
   */
  public <T> List<T> getEntry(Class<T> entryType) {
    List<T> list = new ArrayList<>();
    for (Entry entry : entryList) {
      if (entryType.isInstance(entry)) {
        list.add(entryType.cast(entry));
      }
    }
    return list;
  }
}
