package cs3500.pa05.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.json.TaskJson;
import java.nio.file.Path;
import java.util.List;

public class BujoWriter {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  public void writeBujo(List<Entry> entries, Path path) {
    TaskJson json = new TaskJson(Day.MONDAY, "testName", "test description", TaskStatus.COMPLETE);
    JsonNode node = MAPPER.convertValue(json, JsonNode.class);
    Task task = MAPPER.convertValue(node, TaskJson.class).toTask();
    node = MAPPER.convertValue(task.toJson(), JsonNode.class);
    System.out.println(node);
  }
}
