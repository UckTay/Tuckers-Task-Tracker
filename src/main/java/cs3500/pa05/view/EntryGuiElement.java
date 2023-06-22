package cs3500.pa05.view;

import cs3500.pa05.controller.CustomGuiEvent;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;
import java.time.Duration;
import java.time.LocalTime;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Represents the visual display of an entry.
 */
public class EntryGuiElement {
  VBox resultBox = new VBox();

  /**
   * Constructs an instance of EntryGUIElement.
   *
   * @param task the task to display
   */
  public EntryGuiElement(Task task) {
    addBasicInfo(task);
    CheckBox status = new CheckBox();
    boolean isComplete = task.getTaskStatus().equals(TaskStatus.COMPLETE);
    status.setSelected(isComplete);
    status.setOnAction((event) -> {
      task.markTask(
          status.isSelected() ? TaskStatus.COMPLETE : TaskStatus.INCOMPLETE);
      status.fireEvent(new CustomGuiEvent());
    });
    HBox statusBox = new HBox();
    statusBox.getChildren().add(new Label("Status:"));
    statusBox.getChildren().add(status);
    statusBox.setAlignment(Pos.CENTER_LEFT);
    statusBox.setSpacing(10);
    resultBox.getChildren().add(statusBox);
  }

  /**
   * Constructs an instance of EntryGUIElement.
   *
   * @param event the task to display
   */
  public EntryGuiElement(Event event) {
    addBasicInfo(event);
    resultBox.getChildren().add(new Label("Start Time: " + event.getStartTime().toString()));
    String s = event.getDuration().toString();
    resultBox.getChildren().add(new Label("Duration: " + s.substring(2, s.indexOf('H')) + " Hours " +
        s.substring(s.indexOf('H') + 1, s.indexOf('M')) + " Minutes"));
  }

  /**
   * Adds the info of the Entry to the view.
   *
   * @param e the entry being added
   */
  private void addBasicInfo(Entry e) {
    Label nameLabel = new Label(e.getName());
    nameLabel.setUnderline(true);
    resultBox.getChildren().add(nameLabel);
    if (e.getDescription() != null && !e.getDescription().equals("")) {
      Label description = new Label(e.getDescription());
      description.setWrapText(true);
      description.prefWidthProperty().bind(resultBox.widthProperty());
      resultBox.getChildren().add(description);
    }
  }

  /**
   * Gets the VBox that contains the elements.
   *
   * @return the info container
   */
  public VBox getVbox() {
    return resultBox;
  }
}
