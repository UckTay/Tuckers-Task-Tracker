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

/**
 * Tests the BujoWriter class.
 */
class BujoWriterTest {

  private BujoWriter bujoWriter;

  /**
   * sets up BujoWriter tests.
   */
  @BeforeEach
  void setUp() {
    bujoWriter = new BujoWriter();
    Config config = new Config();
    config.setName("Week Name");
    config.setMaxTasks(5);
    config.setMaxEvents(10);
    List<Entry> testEntries = new ArrayList<>();
    testEntries.add(new Event(Day.WEDNESDAY, "test", "test",
        LocalTime.parse("14:15"), Duration.parse("PT10H10M")));
    testEntries.add(new Task(Day.MONDAY, "test", "test", TaskStatus.INCOMPLETE));
    bujoWriter.writeBujo(config, testEntries,
        Path.of("src/test/resources/saveBujoTestOutput.bujo"));
  }

  /**
   * Tests the writeBujo function.
   */
  @Test
  void writeBujoTest() {
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

  /**
   * Tests the exception in writeBujo.
   */
  @Test
  void writeBujoCases() {
    assertThrows(IllegalArgumentException.class, () -> bujoWriter.writeBujo(new Config(), new ArrayList<>(),
        Path.of("noFIlePath/NOTEXISTS")));
  }
}