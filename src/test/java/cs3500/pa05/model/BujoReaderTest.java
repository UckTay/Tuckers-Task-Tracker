package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the BujoReader.
 */
class BujoReaderTest {
  private BujoReader reader;

  /**
   * Sets up bujo reader tests.
   */
  @BeforeEach
  void setUp() {
    reader = new BujoReader();
    reader.readBujo(Path.of("src/test/resources/loadBujoTest.bujo"));
  }

  /**
   * tests the readBujo function.
   */
  @Test
  void readBujoTest() {
    assertEquals(reader.getEntry(Task.class).get(0).toJson(), new Task(Day.MONDAY, "test",
        "test", TaskStatus.INCOMPLETE).toJson());
    assertEquals(reader.getEntry(Task.class).get(0).getDayOfTheWeek().toString(), "Monday");
    assertEquals(reader.getEntry(Event.class).get(0).toJson(), new Event(Day.WEDNESDAY, "test", "test",
        LocalTime.parse("14:15"), Duration.parse("PT10H10M")).toJson());
  }
}