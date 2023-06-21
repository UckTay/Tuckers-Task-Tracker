package cs3500.pa05.view.prompts;

import cs3500.pa05.model.Config;
import cs3500.pa05.model.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SettingsPrompt {

  private VBox resultBox = new VBox();

  private Dialog<String> dialog = new Dialog<>();

  private ChoiceBox<String> dayOptions = new ChoiceBox<>();

  private TextField maxEventsField;
  private TextField maxTasksField;

  private Config config;


  public SettingsPrompt(Config config) {
    this.config = config;
    dialog.setTitle("Settings");

    List<String> optionsList = new ArrayList<>();

    for (Day day : Day.values()) {
      optionsList.add(day.toString());
    }
    dayOptions.setItems(FXCollections.observableArrayList(optionsList));
    dayOptions.setValue(config.getStartingDay().toString());
    Label dayField = new Label("Day:");
    HBox dayBox = new HBox();
    dayBox.prefWidthProperty().bind(resultBox.widthProperty());
    dayBox.getChildren().addAll(dayField, dayOptions);
    dayBox.setAlignment(Pos.CENTER_LEFT);
    dayBox.setSpacing(10);
    resultBox.getChildren().add(dayBox);
    Label maxEventsLbl = new Label("Maximum number of Events per day:");
    maxEventsField = config.getMaxEvents() == -1 ? new TextField() : new TextField(String.valueOf(config.getMaxEvents()));
    maxEventsField.setPrefWidth(30);
    Label maxTasksLbl = new Label("Maximum number of Tasks per day:");
    maxTasksField = config.getMaxTasks() == -1 ? new TextField() : new TextField(String.valueOf(config.getMaxTasks()));
    maxTasksField.setPrefWidth(30);
    resultBox.getChildren().addAll(maxEventsLbl, maxEventsField, maxTasksLbl, maxTasksField);
    Window window = dialog.getDialogPane().getScene().getWindow();
    window.setOnCloseRequest(event -> dialog.close());
    Button doneButton = new Button("Done!");
    doneButton.prefWidthProperty().bind(resultBox.widthProperty());
    resultBox.getChildren().add(doneButton);
    resultBox.setSpacing(10);
    setDoneButton(doneButton);
    dialog.getDialogPane().setContent(resultBox);
    dialog.showAndWait();
  }
  private void setDoneButton(Button doneButton) {
    doneButton.setOnAction(event -> {
      Day day = Day.valueOf(dayOptions.getValue().toUpperCase());
      if (Arrays.stream(Day.values()).toList()
          .contains(day)) {
        config.setStartingDay(day);
      }
      try {
        config.setMaxEvents(Integer.parseInt(maxEventsField.getText()));
      } catch (Exception ignored) {
        //Intentionally ignored
      }
      try {
        config.setMaxTasks(Integer.parseInt(maxTasksField.getText()));
      } catch (Exception ignored) {
        //Intentionally ignored
      }
      ((Stage) doneButton.getScene().getWindow()).close();
      System.out.println("tasks:" + config.getMaxTasks());
      System.out.println("events:" + config.getMaxEvents());
    });


  }
}
