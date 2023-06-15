package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name", "maxEvents", "maxTasks"})
public record ConfigJson(
    @JsonProperty("name") String name,
    @JsonProperty("maxEvents") int maxEvents,
    @JsonProperty("maxTasks") int maxTasks
) {
}
