package cs3500.pa05.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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

  private Config config;

  List<Entry> testEntries;


  /**
   * sets up BujoWriter tests.
   */
  @BeforeEach
  void setUp() {
    bujoWriter = new BujoWriter();
    config = new Config();
    config.setName("Week Name");
    config.setMaxTasks(5);
    config.setMaxEvents(10);
    testEntries = new ArrayList<>();
    testEntries.add(new Event(Day.WEDNESDAY, "test", "test",
        LocalTime.parse("14:15"), Duration.parse("PT10H10M")));
    testEntries.add(new Task(Day.MONDAY, "test", "test", TaskStatus.INCOMPLETE));
  }

  /**
   * Tests the writeBujo function.
   */
  @Test
  void writeBujoTest() {
    bujoWriter.writeBujo(config, testEntries,
        Path.of("src/test/resources/saveBujoTestOutput.bujo"));
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
  void writeBujoTestEncrypted() {
    config.setPassword("password");
    bujoWriter.writeBujo(config, testEntries,
        Path.of("src/test/resources/saveBujoTestOutputEncrypted.bujo"));
  }
}