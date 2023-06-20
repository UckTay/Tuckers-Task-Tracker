package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cs3500.pa05.model.Config;

/**
 * Represents a ConfigJson.
 *
 * @param name the name of the week.
 * @param maxEvents the maximum amount of events in a week.
 * @param maxTasks the maximum amount of tasks ina  week.
 */
@JsonPropertyOrder({"name", "maxEvents", "maxTasks"})
public record ConfigJson(
    @JsonProperty("name") String name,
    @JsonProperty("maxEvents") int maxEvents,
    @JsonProperty("maxTasks") int maxTasks
) {

  /**
   * Converts a JsonConfig to a Config.
   *
   * @return the new config
   */
  public Config toConfig() {
    Config config = new Config();
    config.setName(name);
    config.setMaxEvents(maxEvents);
    config.setMaxTasks(maxTasks);
    return config;
  }
}
