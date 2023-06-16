package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cs3500.pa05.model.Config;

@JsonPropertyOrder({"name", "maxEvents", "maxTasks"})
public record ConfigJson(
    @JsonProperty("name") String name,
    @JsonProperty("maxEvents") int maxEvents,
    @JsonProperty("maxTasks") int maxTasks
) {
  public Config toConfig() {
    Config config = new Config();
    config.setName(name);
    config.setMaxEvents(maxEvents);
    config.setMaxTasks(maxTasks);
    return config;
  }
}
