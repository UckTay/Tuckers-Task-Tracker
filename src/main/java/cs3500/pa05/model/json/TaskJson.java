package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;

@JsonPropertyOrder({"day", "name", "description", "taskStatus"})
public record TaskJson(
    @JsonProperty("day") Day day,
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("taskStatus")TaskStatus status
    ) {

    public Task toTask() {
        return new Task(day, name, description, status);
    }

}
