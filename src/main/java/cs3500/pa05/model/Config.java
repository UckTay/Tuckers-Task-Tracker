package cs3500.pa05.model;

import cs3500.pa05.model.json.ConfigJson;

public class Config {
  private String name;
  private int maxEvents = -1;
  private int maxTasks = -1;
  private Day startingDay = Day.SUNDAY;

  public Day getStartingDay() {
    return startingDay;
  }

  public void setStartingDay(Day startingDay) {
    this.startingDay = startingDay;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getMaxEvents() {
    return maxEvents;
  }

  public void setMaxEvents(int maxEvents) {
    this.maxEvents = maxEvents;
  }

  public int getMaxTasks() {
    return maxTasks;
  }

  public void setMaxTasks(int maxTasks) {
    this.maxTasks = maxTasks;
  }

  public ConfigJson toJson() {
    return new ConfigJson(name, maxEvents, maxTasks);
  }
}
