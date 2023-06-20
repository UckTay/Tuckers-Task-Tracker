package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.TaskStatus;
import java.time.Duration;
import java.time.LocalTime;

/**
 * Represents a EventJson.
 *
 * @param day the day of the event
 * @param name the name of the event
 * @param description the description of the event
 * @param startTime the start time of the event
 * @param duration the duration of the event
 */
@JsonPropertyOrder({"day", "name", "description", "startTime", "duration"})
public record EventJson(
    @JsonProperty("day") Day day,
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("startTime") String startTime,
    @JsonProperty("duration") String duration
    ) {

    /**
     * Converts the EventJson to an event.
     *
     * @return the new event
     */
    public Event toEvent() {
        return new Event(day, name, description, LocalTime.parse(startTime), Duration.parse(duration));
    }

}