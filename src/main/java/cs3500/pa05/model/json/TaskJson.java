package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;

/**
 * Represents a TaskJson.
 *
 * @param day the day of the task
 * @param name the name of the task
 * @param description the description of the task
 * @param status the status of the task
 */
@JsonPropertyOrder({"day", "name", "description", "taskStatus"})
public record TaskJson(
    @JsonProperty("day") Day day,
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("taskStatus")TaskStatus status
) {

  /**
   * Converts the JsonTask to a Task
   *
   * @return the new task
   */
  public Task toTask() {
    return new Task(day, name, description, status);
  }
}
