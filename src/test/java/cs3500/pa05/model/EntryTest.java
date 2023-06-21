package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Entry abstract class.
 */
class EntryTest {
  private Entry testEvent;
  private Entry testTask;

  /**
   * Sets up tests for the Entry abstract class.
   */
  @BeforeEach
  void setUp() {
    testEvent = new Event(Day.MONDAY, "testName", "testDesc",
        LocalTime.MAX, Duration.ZERO);
    testTask = new Task(Day.TUESDAY, "testName2", "testDesc2", TaskStatus.COMPLETE);
  }

  /**
   * Tests the getDayOfTheWeek method.
   */
  @Test
  void getDayOfTheWeekTest() {
    assertEquals(Day.MONDAY, testEvent.getDayOfTheWeek());
    assertEquals(Day.TUESDAY, testTask.getDayOfTheWeek());
  }

  /**
   * tests the getName method
   */
  @Test
  void getNameTest() {
    assertEquals("testName", testEvent.getName());
    assertEquals("testName2", testTask.getName());
  }

  /**
   * Tests the getDescription method.
   */
  @Test
  void getDescriptionTest() {
    assertEquals("testDesc", testEvent.getDescription());
    assertEquals("testDesc2", testTask.getDescription());
  }

  /**
   * tests the getIndex method.
   */
  @Test
  void getIndexTest() {
    assertEquals(0, testEvent.getIndex());
    assertEquals(0, testTask.getIndex());
  }
}