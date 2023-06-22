package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.json.EventJson;
import java.time.Duration;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

/**
 * Tests the Event class.
 */
class EventTest {
  private final Event testEvent = new Event(Day.MONDAY, "testName", "testDesc",
      LocalTime.MAX, Duration.ZERO);

  /**
   * tests the startTime.
   */
  @Test
  void startTimeTest() {
    assertEquals(LocalTime.MAX, testEvent.getStartTime());
  }

  /**
   * Tests the duration
   */
  @Test
  void durationTest() {
    assertEquals(Duration.ZERO, testEvent.getDuration());
  }

  /**
   * Tests the toJson method.
   */
  @Test
  void toJsonTest() {
    EventJson testJson = testEvent.toJson();
    assertEquals(testJson, new EventJson(Day.MONDAY, "testName", "testDesc",
        LocalTime.MAX.toString(), Duration.ZERO.toString()));
  }
}