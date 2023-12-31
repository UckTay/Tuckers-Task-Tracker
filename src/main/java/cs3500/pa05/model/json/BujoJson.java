package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a BujoJson.
 *
 * @param name the configuration of the week
 * @param taskList the tasks of the week
 * @param eventList the events of the week
 */
@JsonPropertyOrder({"config", "taskList", "eventList"})

public record BujoJson(
    @JsonProperty("config") ConfigJson name,
    @JsonProperty("taskList") List<TaskJson> taskList,
    @JsonProperty("eventList") List<EventJson> eventList
) {

  /**
   * Generates tasks from the TaskJson.
   *
   * @return a list of Task
   */
  public List<Task> generateTasks() {
    List<Task> tasks = new ArrayList<>();
    for (TaskJson task : taskList) {
      tasks.add(task.toTask());
    }
    return tasks;
  }

  /**
   * Generates events from the EventJson.
   *
   * @return a list of events
   */
  public List<Event> generateEvents() {
    List<Event> events = new ArrayList<>();
    for (EventJson event : eventList) {
      events.add(event.toEvent());
    }
    return events;
  }
}
