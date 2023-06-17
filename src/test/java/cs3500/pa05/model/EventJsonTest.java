package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

class EventJsonTest {

  @Test
  public void toJsonTest() {
    assertDoesNotThrow(() -> new Event(Day.MONDAY, "test name", "test description", LocalTime.of(2, 2),
        Duration.of(2, ChronoUnit.HOURS)).toJson());
  }

}