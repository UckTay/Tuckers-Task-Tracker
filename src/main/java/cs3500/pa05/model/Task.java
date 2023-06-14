package cs3500.pa05.model;

public class Task extends Entry {
  private TaskStatus taskStatus = TaskStatus.INCOMPLETE;
  private Long creationTime = creationTime = System.currentTimeMillis() / 1000L;

  public Task(Day dayOfTheWeek, String name, String description) {
    super(dayOfTheWeek, name, description);
  }

  public Task(Day dayOfTheWeek, String name) {
    super(dayOfTheWeek, name);
  }

  public TaskStatus getTaskStatus() {
    return taskStatus;
  }

  public Long getCreationTime() {
    return creationTime;
  }

  public void markTask(TaskStatus status) {
  }

  @Override
  public boolean isTask() {
    return true;
  }
}
