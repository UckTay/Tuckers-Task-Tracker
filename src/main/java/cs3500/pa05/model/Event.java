package cs3500.pa05.model;

import cs3500.pa05.model.json.EventJson;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event extends Entry {

  LocalTime startTime;
  Duration duration;

  public Event(Day dayOfTheWeek, String name, String description, LocalTime startTime, Duration duration) {
    super(dayOfTheWeek, name, description);
    this.startTime = startTime;
    this.duration = duration;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public Duration getDuration() {
    return duration;
  }

  public EventJson toJson() {
    return new EventJson(super.getDayOfTheWeek(), super.getName(), super.getDescription(), startTime, duration);
  }

}
