package cs3500.pa05.view.prompts;

import cs3500.pa05.model.Config;
import cs3500.pa05.model.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

/**
 * Creates a Setting Prompt
 */
public class SettingsPrompt {

  private final VBox resultBox = new VBox();

  private final Dialog<String> dialog = new Dialog<>();

  private final ChoiceBox<String> dayOptions = new ChoiceBox<>();

  private final TextField maxEventsField;
  private final TextField maxTasksField;
  private final TextField passwordField;

  private final Config config;


  /**
   * Constructs an instance of the Settings Prompt.
   *
   * @param config the old configuration
   */
  public SettingsPrompt(Config config) {
    this.config = config;
    dialog.setTitle("Settings");

    setDayOptions();
    maxEventsField = config.getMaxEvents() == -1 ? new TextField() :
        new TextField(String.valueOf(config.getMaxEvents()));
    maxEventsField.setPrefWidth(30);
    Label maxTasksLbl = new Label("Maximum number of Tasks per day:");
    maxTasksField = config.getMaxTasks() == -1 ? new TextField() :
        new TextField(String.valueOf(config.getMaxTasks()));
    maxTasksField.setPrefWidth(30);
    Label maxEventsLbl = new Label("Maximum number of Events per day:");
    resultBox.getChildren().addAll(maxEventsLbl, maxEventsField, maxTasksLbl, maxTasksField);
    Label passwordLbl = new Label("Optional encryption password:");
    passwordField =
        config.getPassword() == null ? new TextField() : new TextField(config.getPassword());
    passwordField.setPrefWidth(30);
    resultBox.getChildren().addAll(passwordLbl, passwordField);
    Window window = dialog.getDialogPane().getScene().getWindow();
    window.setOnCloseRequest(event -> dialog.close());
    Button doneButton = new Button("Done!");
    doneButton.prefWidthProperty().bind(resultBox.widthProperty());
    resultBox.getChildren().add(doneButton);
    resultBox.setSpacing(10);
    setDoneButton(doneButton);
    dialog.getDialogPane().setContent(resultBox);
    dialog.getDialogPane().getStylesheets()
        .add(Objects.requireNonNull(this.getClass().getResource("/NetflixTheme.css"))
            .toExternalForm());
    dialog.showAndWait();
  }

  private void setDayOptions() {
    List<String> optionsList = new ArrayList<>();

    for (Day day : Day.values()) {
      optionsList.add(day.toString());
    }
    dayOptions.setItems(FXCollections.observableArrayList(optionsList));
    dayOptions.setValue(config.getStartingDay().toString());
    Label dayField = new Label("Start Day:");
    HBox dayBox = new HBox();
    dayBox.prefWidthProperty().bind(resultBox.widthProperty());
    dayBox.getChildren().addAll(dayField, dayOptions);
    dayBox.setAlignment(Pos.CENTER_LEFT);
    dayBox.setSpacing(10);
    resultBox.getChildren().add(dayBox);
  }


  /**
   * Sets the done button action.
   *
   * @param doneButton the done button
   */
  private void setDoneButton(Button doneButton) {
    doneButton.setOnAction(event -> {
      if (!passwordField.getText().equals("")) {
        config.setPassword(passwordField.getText());
      }
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
