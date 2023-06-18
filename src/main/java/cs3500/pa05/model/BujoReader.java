package cs3500.pa05.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.json.BujoJson;
import cs3500.pa05.model.json.ConfigJson;
import cs3500.pa05.model.json.TaskJson;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BujoReader {
  List<Entry> entryList = new ArrayList<>();
  private static final ObjectMapper MAPPER = new ObjectMapper();


  public Config readBujo(Path path) {
    JsonNode fileContents;
    try {
      fileContents = MAPPER.readTree(path.toFile());
      MAPPER.convertValue(fileContents, BujoJson.class);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid file path");
    }
    Config config = MAPPER.convertValue(fileContents.get("config"), ConfigJson.class).toConfig();
    BujoJson entryGetter = MAPPER.convertValue(fileContents, BujoJson.class);
    entryList.addAll(entryGetter.generateTasks());
    entryList.addAll(entryGetter.generateEvents());
    return config;
  }

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
