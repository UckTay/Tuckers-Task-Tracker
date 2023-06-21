package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JournalModelTest {
  private JournalModel testModel;

  @BeforeEach
  void setUp() {
    testModel = new JournalModel();
  }

  @Test
  void newWeekTest() {
    testModel.addEvent(new Event(Day.WEDNESDAY, "testName", "testDesc",
        LocalTime.MAX, Duration.ZERO));
    testModel.addTask(new Task(Day.TUESDAY, "testName", "testDesc",
        TaskStatus.INCOMPLETE));
    testModel.newWeek();
    assertEquals(testModel.getAllEvents().size(), 0);
    assertEquals(testModel.getAllTasks().size(), 0);
  }

  @Test
  void loadBujo() {
    testModel.loadBujo(Path.of("src/test/resources/loadBujoTest.bujo"));
    assertEquals(testModel.getAllTasks().get(0).toJson(),
        new Task(Day.MONDAY, "test", "test", TaskStatus.INCOMPLETE).toJson());
    assertEquals(testModel.getAllEvents().get(0).toJson(),
        new Event(Day.WEDNESDAY, "test", "test", LocalTime.parse("14:15"),
            Duration.parse("PT10H10M")).toJson());
    assertEquals(testModel.getConfig().getMaxEvents(), 10);
    assertEquals(testModel.getConfig().getMaxTasks(), 5);
    assertEquals(testModel.getConfig().getName(), "Week Name");
  }

  @Test
  void isBelowTaskLimit() {
    testModel.getConfig().setMaxTasks(1);
    assertTrue(testModel.isBelowTaskLimit(Day.MONDAY));
    testModel.addTask(new Task(Day.MONDAY, "testName", "testDesc",
        TaskStatus.INCOMPLETE));
    testModel.addTask(new Task(Day.MONDAY, "testName", "testDesc",
        TaskStatus.INCOMPLETE));
    assertFalse(testModel.isBelowTaskLimit(Day.MONDAY));
  }

  @Test
  void isBelowEventLimit() {
    testModel.getConfig().setMaxEvents(-1);
    assertTrue(testModel.isBelowEventLimit(Day.WEDNESDAY));
    testModel.getConfig().setMaxEvents(1);
    assertTrue(testModel.isBelowEventLimit(Day.WEDNESDAY));
    testModel.addEvent(new Event(Day.WEDNESDAY, "test", "test", LocalTime.parse("14:15"),
        Duration.parse("PT10H10M")));
    testModel.addEvent(new Event(Day.WEDNESDAY, "test", "test", LocalTime.parse("14:15"),
        Duration.parse("PT10H10M")));
    assertFalse(testModel.isBelowEventLimit(Day.WEDNESDAY));
  }

  @Test
  void saveBujo() {
    testModel.addTask(new Task(Day.MONDAY, "test", "test", TaskStatus.INCOMPLETE));
    testModel.addEvent(new Event(Day.WEDNESDAY, "test", "test",
        LocalTime.parse("14:15"), Duration.parse("PT10H10M")));
    testModel.getConfig().setMaxTasks(5);
    testModel.getConfig().setMaxEvents(10);
    testModel.getConfig().setName("Week Name");
    testModel.saveBujo(Path.of("src/test/resources/saveBujoTestOutput.bujo"));
    String output = null;
    String expected = null;
    try {
      Scanner scanner = new Scanner(Path.of("src/test/resources/saveBujoTestOutput.bujo"));
      output = scanner.nextLine();
      scanner = new Scanner(Path.of("src/test/resources/saveBujoTest.bujo"));
      expected = scanner.nextLine();
    } catch (IOException e) {
      fail();
    }
    assertEquals(output, expected);
  }

  @Test
  void getDaysTasks() {
    Task testTask = new Task(Day.TUESDAY, "testName", "testDesc",
        TaskStatus.INCOMPLETE);
    testModel.addTask(testTask);
    assertEquals(testModel.getDaysTasks(Day.TUESDAY).get(0), testTask);
    assertTrue(testModel.getDaysTasks(Day.WEDNESDAY).isEmpty());
  }

  @Test
  void getDaysEvent() {
    Event testEvent = new Event(Day.TUESDAY, "testName", "testDesc",
        LocalTime.MAX, Duration.ZERO);
    testModel.addEvent(testEvent);
    assertEquals(testModel.getDaysEvent(Day.TUESDAY).get(0), testEvent);
    assertTrue(testModel.getDaysEvent(Day.WEDNESDAY).isEmpty());
  }

  @Test
  void getAllTasks() {
    Task testTask1 = new Task(Day.TUESDAY, "testName", "testDesc",
        TaskStatus.INCOMPLETE);
    Task testTask2 = new Task(Day.MONDAY, "testName", "testDesc",
        TaskStatus.INCOMPLETE);
    testModel.addTask(testTask1);
    testModel.addTask(testTask2);
    assertEquals(testModel.getAllTasks().get(0).toJson(), testTask2.toJson());
    assertEquals(testModel.getAllTasks().get(1).toJson(), testTask1.toJson());
  }

  @Test
  void getAllEvents() {
    Event testEvent1 = new Event(Day.TUESDAY, "testName", "testDesc", LocalTime.MAX,
        Duration.ZERO);
    Event testEvent2 = new Event(Day.MONDAY, "testName", "testDesc", LocalTime.MAX,
        Duration.ZERO);
    testModel.addEvent(testEvent1);
    testModel.addEvent(testEvent2);
    assertEquals(testModel.getAllEvents().get(1).toJson(), testEvent1.toJson());
    assertEquals(testModel.getAllEvents().get(0).toJson(), testEvent2.toJson());
  }

  @Test
  void moveUp() {
    Task testTask1 = new Task(Day.TUESDAY, "testName", "testDesc",
        TaskStatus.INCOMPLETE);
    Task testTask2 = new Task(Day.TUESDAY, "testName", "testDesc2",
        TaskStatus.INCOMPLETE);
    Event testEvent1 = new Event(Day.MONDAY, "testName", "testDesc", LocalTime.MAX,
        Duration.ZERO);
    Event testEvent2 = new Event(Day.MONDAY, "testName", "testDesc", LocalTime.MAX,
        Duration.ZERO);
    testModel.addTask(testTask1);
    testModel.addTask(testTask2);
    testModel.addEvent(testEvent1);
    testModel.addEvent(testEvent2);
    testModel.moveUp(testEvent2);
    List<Event> testList = new ArrayList<>();
    testList.add(testEvent2);
    testList.add(testEvent1);
    assertEquals(testModel.getAllEvents().get(0), testList.get(0));
    List<Task> testList2 = new ArrayList<>();
    testModel.moveUp(testTask2);
    testList2.add(testTask2);
    testList2.add(testTask1);
    assertEquals(testModel.getAllTasks().get(0), testList2.get(0));
  }

  @Test
  void moveDown() {
    Task testTask1 = new Task(Day.TUESDAY, "testName", "testDesc",
        TaskStatus.INCOMPLETE);
    Task testTask2 = new Task(Day.TUESDAY, "testName", "testDesc2",
        TaskStatus.INCOMPLETE);
    Event testEvent1 = new Event(Day.MONDAY, "testName", "testDesc", LocalTime.MAX,
        Duration.ZERO);
    Event testEvent2 = new Event(Day.MONDAY, "testName", "testDesc", LocalTime.MAX,
        Duration.ZERO);
    testModel.addTask(testTask1);
    testModel.addTask(testTask2);
    testModel.addEvent(testEvent1);
    testModel.addEvent(testEvent2);
    testModel.moveDown(testEvent1);
    List<Event> testList = new ArrayList<>();
    testList.add(testEvent2);
    testList.add(testEvent1);
    assertEquals(testModel.getAllEvents().get(0), testList.get(0));
    List<Task> testList2 = new ArrayList<>();
    testModel.moveDown(testTask1);
    testList2.add(testTask2);
    testList2.add(testTask1);
    assertEquals(testModel.getAllTasks().get(0), testList2.get(0));
  }

  @Test
  void takesieBacksie() {
    Event testEvent = new Event(Day.WEDNESDAY, "testName", "testDesc",
        LocalTime.MAX, Duration.ZERO);
    Task testTask = new Task(Day.TUESDAY, "testName", "testDesc",
        TaskStatus.INCOMPLETE);
    testModel.addEvent(testEvent);
    testModel.addTask(testTask);
    testModel.takesieBacksie(testEvent);
    assertEquals(testModel.getAllEvents().size(), 0);
    testModel.takesieBacksie(testTask);
    assertEquals(testModel.getAllTasks().size(), 0);
  }

  @Test
  void getConfig() {
    assertEquals(testModel.getConfig().toJson(), new Config().toJson());
  }

  @Test
  void mindChange() {
    Task testTask = new Task(Day.TUESDAY, "testName", "testDesc",
        TaskStatus.INCOMPLETE);
    Task testTask2 = new Task(Day.TUESDAY, "testName", "testDesc2",
        TaskStatus.INCOMPLETE);
    testModel.addTask(testTask);
    testModel.mindChange(testTask, testTask2);
    assertEquals(testModel.getAllTasks().get(0), testTask2);
  }
}