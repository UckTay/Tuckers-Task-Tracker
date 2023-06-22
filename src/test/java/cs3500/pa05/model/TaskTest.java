package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.json.TaskJson;
import org.junit.jupiter.api.Test;

/**
 * Tests the task class.
 */
class TaskTest {
  private final Task testTask = new Task(Day.TUESDAY, "testName2", "testDesc2",
      TaskStatus.COMPLETE);

  /**
   * Tests task status methods.
   */
  @Test
  void testStatus() {
    assertEquals(TaskStatus.COMPLETE, testTask.getTaskStatus());
    testTask.markTask(TaskStatus.INCOMPLETE);
    assertEquals(TaskStatus.INCOMPLETE, testTask.getTaskStatus());
  }

  /**
   * Tests the toJson method.
   */
  @Test
  void toJsonTest() {
    TaskJson task = testTask.toJson();
    assertEquals(task, new TaskJson(Day.TUESDAY, "testName2", "testDesc2",
        TaskStatus.COMPLETE));
  }
}