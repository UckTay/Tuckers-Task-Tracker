package cs3500.pa05.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

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
  public void setUp() {
    reader = new BujoReader();
  }

  /**
   * tests the readBujo function.
   */
  @Test
  public void readBujoTest() {
    reader.readBujo(Path.of("src/test/resources/loadBujoTest.bujo"));
    assertEquals(reader.getEntry(Task.class).get(0).toJson(), new Task(Day.MONDAY, "test",
        "test", TaskStatus.INCOMPLETE).toJson());
    assertEquals(reader.getEntry(Task.class).get(0).getDayOfTheWeek().toString(), "Monday");
    assertEquals(reader.getEntry(Event.class).get(0).toJson(), new Event(Day.WEDNESDAY,
        "test", "test", LocalTime.parse("14:15"),
        Duration.parse("PT10H10M")).toJson());
  }

  /**
   * Tests reading an encrypted bujo file.
   */
  @Test
  public void readBujoTestEncrypted() {
    reader.readBujo(Path.of("src/test/resources/loadBujoTestEncrypted.bujo"), "password");
    assertEquals(reader.getEntry(Task.class).get(0).toJson(), new Task(Day.MONDAY, "test",
        "test", TaskStatus.INCOMPLETE).toJson());
    assertEquals(reader.getEntry(Task.class).get(0).getDayOfTheWeek().toString(), "Monday");
    assertEquals(reader.getEntry(Event.class).get(0).toJson(), new Event(Day.WEDNESDAY,
        "test", "test", LocalTime.parse("14:15"),
        Duration.parse("PT10H10M")).toJson());
  }
}