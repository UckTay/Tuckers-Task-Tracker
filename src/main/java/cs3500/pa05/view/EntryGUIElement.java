package cs3500.pa05.view;

import cs3500.pa05.controller.CustomGUIEvent;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EntryGUIElement {
  VBox resultBox = new VBox();

  private void addBasicInfo(Entry e) {
    resultBox.getChildren().add(new Label(e.getName()));
    if (e.getDescription() != null && !e.getDescription().equals("")) {
      Label description = new Label(e.getDescription());
      description.setWrapText(true);
      description.prefWidthProperty().bind(resultBox.widthProperty());
      resultBox.getChildren().add(description);
    }
  }

  public EntryGUIElement(Task task) {
    addBasicInfo(task);
    CheckBox status = new CheckBox();
    boolean isComplete = task.getTaskStatus().equals(TaskStatus.COMPLETE);
    status.setSelected(isComplete);
    status.setOnAction((event) -> {
      task.markTask(
          status.isSelected() ? TaskStatus.COMPLETE : TaskStatus.INCOMPLETE);
      status.fireEvent(new CustomGUIEvent());
    });
    HBox statusBox = new HBox();
    statusBox.getChildren().add(new Label("Status:"));
    statusBox.getChildren().add(status);
    statusBox.setAlignment(Pos.CENTER_LEFT);
    statusBox.setSpacing(10);
    resultBox.getChildren().add(statusBox);
  }

  public EntryGUIElement(Event event) {
    addBasicInfo(event);
    resultBox.getChildren().add(new Label(event.getStartTime().toString()));
    resultBox.getChildren().add(new Label(event.getDuration().toString()));
  }

  public VBox getVBox() {
    return resultBox;
  }
}
