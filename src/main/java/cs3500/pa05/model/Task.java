package cs3500.pa05.model;

import cs3500.pa05.model.json.EventJson;
import cs3500.pa05.model.json.TaskJson;

public class Task extends Entry {
  private TaskStatus taskStatus;
  public Task(Day dayOfTheWeek, String name, String description, TaskStatus status) {
    super(dayOfTheWeek, name, description);
    this.taskStatus = status;
  }

  public TaskStatus getTaskStatus() {
    return taskStatus;
  }
  public void markTask(TaskStatus status) {
    taskStatus = status;
  }

  public TaskJson toJson() {
    return new TaskJson(super.getDayOfTheWeek(), super.getName(), super.getDescription(), taskStatus);
  }
}
