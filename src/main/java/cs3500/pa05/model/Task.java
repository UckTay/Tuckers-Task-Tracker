package cs3500.pa05.model;

import cs3500.pa05.model.json.TaskJson;

/**
 * Represents a Task.
 */
public class Task extends Entry {
  private TaskStatus taskStatus;

  /**
   * Creates an instance of Task.
   *
   * @param dayOfTheWeek The day the Task is under
   * @param name The name of the task
   * @param description The description of the task
   * @param status the status of the task
   */
  public Task(Day dayOfTheWeek, String name, String description, TaskStatus status) {
    super(dayOfTheWeek, name, description);
    this.taskStatus = status;
  }

  /**
   * Gets the task's current status.
   *
   * @return the task Status
   */
  public TaskStatus getTaskStatus() {
    return taskStatus;
  }

  /**
   * Updates the tasks' status.
   *
   * @param status The new status of the task
   */
  public void markTask(TaskStatus status) {
    taskStatus = status;
    //TODO: add an observable for updating in weekly overview.
  }

  /**
   * Converts the Task to a TaskJson.
   *
   * @return the new TaskJson
   */
  public TaskJson toJson() {
    return new TaskJson(super.getDayOfTheWeek(), super.getName(), super.getDescription(),
        taskStatus);
  }
}
