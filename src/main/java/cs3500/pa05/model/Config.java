package cs3500.pa05.model;

import cs3500.pa05.model.json.ConfigJson;

/**
 * Represents the configuration of the week
 */
public class Config {
  private String name;
  private int maxEvents = -1;
  private int maxTasks = -1;
  private Day startingDay = Day.SUNDAY;

  private String weekNameString = null;

  /**
   * Gets the starting day of the week.
   *
   * @return the starting day
   */
  public Day getStartingDay() {
    return startingDay;
  }

  /**
   * Sets the starting day of the week.
   *
   * @param startingDay the new starting day.
   */
  public void setStartingDay(Day startingDay) {
    this.startingDay = startingDay;
  }

  /**
   * Gets the name of the week.
   *
   * @return the name of the week
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the week.
   *
   * @param name the new name of the week
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the maximum of events.
   *
   * @return the max number of events.
   */
  public int getMaxEvents() {
    return maxEvents;
  }

  /**
   * sets the maximum amount of events.
   *
   * @param maxEvents the new max amount of events
   */
  public void setMaxEvents(int maxEvents) {
    this.maxEvents = maxEvents;
  }

  /**
   * Gets the maximum of tasks.
   *
   * @return the max number of tasks.
   */
  public int getMaxTasks() {
    return maxTasks;
  }

  /**
   * Sets the maximum amount of tasks.
   *
   * @param maxTasks the new max amount of tasks
   */
  public void setMaxTasks(int maxTasks) {
    this.maxTasks = maxTasks;
  }

  /**
   * Gets the name of the week.
   *
   * @return the name of the week.
   */
  public String getWeekNameString() {
    return weekNameString;
  }

  /**
   * Sets the name of the week.
   */
  public void setWeekNameString(String weekNameString) {
    this.weekNameString = weekNameString;
  }

  /**
   * Converts the configurations into a ConfigJson.
   *
   * @return the new ConfigJson
   */
  public ConfigJson toJson() {
    return new ConfigJson(startingDay, name, maxEvents, maxTasks);
  }
}
