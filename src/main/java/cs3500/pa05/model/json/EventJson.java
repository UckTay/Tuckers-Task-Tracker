package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.TaskStatus;
import java.time.Duration;
import java.time.LocalTime;

@JsonPropertyOrder({"day", "name", "description", "startTime", "duration"})
public record EventJson(
    @JsonProperty("day") Day day,
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("startTime") LocalTime startTime,
    @JsonProperty("duration") Duration duration
    ) {

}