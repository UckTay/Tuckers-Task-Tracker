package cs3500.pa05.model;

import cs3500.pa05.model.json.EventJson;
import java.time.Duration;
import java.time.LocalTime;

/**
 * Represents an Event
 */
public class Event extends Entry {

  private final LocalTime startTime;
  private final Duration duration;

  /**
   * Constructs an instance of the event.
   *
   * @param dayOfTheWeek the day of the event
   * @param name the name of the event
   * @param description the description of the event
   * @param startTime the time the event starts
   * @param duration how long the event is
   */
  public Event(Day dayOfTheWeek, String name, String description,
               LocalTime startTime, Duration duration) {
    super(dayOfTheWeek, name, description);
    this.startTime = startTime;
    this.duration = duration;
  }

  /**
   * Gets the start time.
   *
   * @return the start time
   */
  public LocalTime getStartTime() {
    return startTime;
  }

  /**
   * Gets the duration.
   *
   * @return the duration of the event
   */
  public Duration getDuration() {
    return duration;
  }

  /**
   * Converts the Event to Json.
   *
   * @return the new Json
   */
  public EventJson toJson() {
    return new EventJson(super.getDayOfTheWeek(), super.getName(), super.getDescription(),
        startTime.toString(), duration.toString());
  }

}
