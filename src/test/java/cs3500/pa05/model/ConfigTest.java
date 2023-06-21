package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa05.model.json.ConfigJson;
import org.junit.jupiter.api.Test;

/**
 * Tests the config class
 */
class ConfigTest {
  private Config config = new Config();

  /**
   * Tests startingDay functions
   */
  @Test
  void startingDayTest() {
    config.setStartingDay(Day.MONDAY);
    assertEquals(config.getStartingDay(), Day.MONDAY);
  }

  /**
   * Tests name functions
   */
  @Test
  void nameTest() {
    config.setName("test");
    assertEquals(config.getName(), "test");
  }

  @Test
  void maxEventsTest() {
    config.setMaxEvents(10);
    assertEquals(config.getMaxEvents(), 10);
  }

  @Test
  void maxTasksTest() {
    config.setMaxTasks(10);
    assertEquals(config.getMaxTasks(), 10);
  }


  @Test
  void toJson() {
    config.setStartingDay(Day.MONDAY);
    config.setName("test");
    config.setMaxEvents(10);
    config.setMaxTasks(10);
    ConfigJson testJson = config.toJson();
    assertEquals(testJson.toString(),
        new ConfigJson(Day.MONDAY, "test", 10, 10).toString());
  }
}