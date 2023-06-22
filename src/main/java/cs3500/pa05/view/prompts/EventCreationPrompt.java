package cs3500.pa05.view.prompts;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Creates the Event prompt.
 */
public class EventCreationPrompt extends EntryCreationPrompt {
  private ChoiceBox<String> minutesOptions;
  private ChoiceBox<String> hoursOptions;
  private TextField hoursField;
  private TextField minutesField;
  private int durationMinutes;
  private int durationHours;
  private int startMinutes;
  private int startHours;

  /**
   * Constucts an instance of the Event Prompt.
   *
   * @param addEntryToModel the function that adds the entry to the model
   * @param isUnderLimit The function that checks if adding the event goes over the limit
   * @param updategui the GUI updater
   */
  public EventCreationPrompt(Consumer<Entry> addEntryToModel,
                             Function<Day, Boolean> isUnderLimit, Runnable updategui) {
    super(addEntryToModel, isUnderLimit, updategui);
  }

  /**
   * Constucts an instance of the Event Prompt.
   *
   * @param event the old event
   * @param addEntryToModel the function that adds the entry to the model
   * @param isUnderLimit The function that checks if adding the event goes over the limit
   * @param updategui the GUI updater
   */
  public EventCreationPrompt(Event event, Consumer<Entry> addEntryToModel,
                             Function<Day, Boolean> isUnderLimit, Runnable updategui) {
    super(event, addEntryToModel, isUnderLimit, updategui);
  }

  @Override
  protected void createPrompt(Entry event) {
    dialog.setTitle("New Event");
    super.createPrompt(event);
    addTimeElements((Event) event);
  }

  /**
   * Adds the time element to the event
   *
   * @param event the event
   */
  private void addTimeElements(Event event) {
    Label startTimeLbl = new Label("Start Time:");
    resultBox.getChildren().add(startTimeLbl);
    List<String> hours = new ArrayList<>();
    hours.add("");
    for (int i = 0; i < 24; i++) {
      hours.add((i < 10) ? "0" + i : String.valueOf(i));
    }
    List<String> minutes = new ArrayList<>();
    minutes.add("");
    for (int i = 0; i <= 60; i = i + 15) {
      minutes.add((i < 10) ? "0" + i : String.valueOf(i));
    }
    hoursOptions = new ChoiceBox<>();
    hoursOptions.setStyle(".dropdown");
    hoursOptions.setItems(FXCollections.observableArrayList(hours));
    if (event != null) {
      hoursOptions.setValue(String.valueOf(event.getStartTime().getHour()));
    }
    minutesOptions = new ChoiceBox<>();
    hoursOptions.setStyle(".dropdown");
    minutesOptions.setItems(FXCollections.observableArrayList(minutes));
    if (event != null) {
      minutesOptions.setValue(String.valueOf(event.getStartTime().getMinute()));
    }
    HBox startTimeBox = new HBox();
    Label colon = new Label(":");
    startTimeBox.getChildren().addAll(hoursOptions, colon, minutesOptions);
    startTimeBox.setSpacing(10);
    resultBox.getChildren().add(startTimeBox);
    hoursField = event == null ? new TextField() :
        new TextField(String.valueOf(event.getDuration().toHours()));
    hoursField.setPrefWidth(30);
    minutesField = event == null ? new TextField() :
        new TextField(String.valueOf(event.getDuration().toMinutes() % 60));
    minutesField.setPrefWidth(30);
    Label durationLbl = new Label("Duration:");
    Label hoursLbl = new Label("Hours:");
    Label minutesLbl = new Label("Minutes:");
    resultBox.getChildren().add(durationLbl);
    HBox durationBox = new HBox();
    durationBox.getChildren().addAll(hoursLbl, hoursField, minutesLbl, minutesField);
    durationBox.setAlignment(Pos.CENTER_LEFT);
    durationBox.setSpacing(10);
    resultBox.getChildren().add(durationBox);
  }


  @Override
  protected void setDoneButton(Button doneButton, Consumer<Entry> addEntryToModel,
                               Runnable updategui, Function<Day, Boolean> isUnderLimit) {
    doneButton.setOnAction(event -> {

      Day day = Day.valueOf(dayOptions.getValue().toUpperCase());
      try {
        startHours = Integer.parseInt(hoursOptions.getValue());
        startMinutes = Integer.parseInt(minutesOptions.getValue());
        durationMinutes = Integer.parseInt(minutesField.getText());
        durationHours = Integer.parseInt(hoursField.getText());
      } catch (Exception e) {
        return;
      }

      if (!isUnderLimit.apply(day)) {
        System.out.println("Error");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().getStylesheets().add(this.getClass().getResource("/NetflixTheme.css").toExternalForm());
        alert.setTitle("Error");
        alert.setContentText("Too Many Events For The Day");
        alert.showAndWait();
        return;
      }

      if (!nameField.getText().equals("")
          && Arrays.stream(Day.values()).toList()
          .contains(day) && durationMinutes >= 0
          && durationHours >= 0) {
        ((Stage) doneButton.getScene().getWindow()).close();
        addEntry(addEntryToModel, updategui);
      }
    });
  }

  /**
   * Adds the new entry.
   *
   * @param addEntryToModel the function to add the entry to the model
   * @param updategui the GUI Updater
   */
  @Override
  protected void addEntry(Consumer<Entry> addEntryToModel, Runnable updategui) {
    Event event = new Event(Day.valueOf(dayOptions.getValue().toUpperCase()), nameField.getText(),
        descriptionField.getText(),
        LocalTime.of(startHours, startMinutes), Duration.of(durationHours, ChronoUnit.HOURS)
        .plus(Duration.of(durationMinutes, ChronoUnit.MINUTES)));
    addEntryToModel.accept(event);
    updategui.run();
  }
}
