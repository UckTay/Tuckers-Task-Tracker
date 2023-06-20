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
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EventCreationPrompt extends EntryCreationPrompt {

  private ChoiceBox<String> minutesOptions;
  private ChoiceBox<String> hoursOptions;
  private TextField hoursField;
  private TextField minutesField;
  private int durationMinutes;
  private int durationHours;
  private int startMinutes;
  private int startHours;

  public EventCreationPrompt(Consumer<Entry> addEntryToModel, Runnable updateGUI) {
    super(addEntryToModel, updateGUI);
  }

  public EventCreationPrompt(Event event, Consumer<Entry> addEntryToModel, Runnable updateGUI) {
    super(event, addEntryToModel, updateGUI);
  }

  @Override
  protected void createPrompt(Entry event) {
    dialog.setTitle("New Event");
    super.createPrompt(event);
    addTimeElements((Event) event);
  }

  private void addTimeElements(Event event) {
    HBox durationBox = new HBox();

    Label startTimeLbl = new Label("Start Time:");
    Label colon = new Label(":");
    HBox startTimeBox = new HBox();
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
    hoursOptions.setItems(FXCollections.observableArrayList(hours));
    if (event != null) {
      hoursOptions.setValue(String.valueOf(event.getStartTime().getHour()));
    }
    minutesOptions = new ChoiceBox<>();
    minutesOptions.setItems(FXCollections.observableArrayList(minutes));
    if (event != null) {
      minutesOptions.setValue(String.valueOf(event.getStartTime().getMinute()));
    }
    startTimeBox.getChildren().addAll(hoursOptions, colon, minutesOptions);
    startTimeBox.setSpacing(10);
    resultBox.getChildren().add(startTimeBox);
    Label durationLbl = new Label("Duration:");
    Label hoursLbl = new Label("Hours:");
    Label minutesLbl = new Label("Minutes:");
    hoursField = event == null ? new TextField() : new TextField(String.valueOf(event.getDuration().toHours()));
    hoursField.setPrefWidth(30);
    minutesField = event == null ? new TextField() : new TextField(String.valueOf(event.getDuration().toMinutes() % 60));
    minutesField.setPrefWidth(30);
    resultBox.getChildren().add(durationLbl);
    durationBox.getChildren().addAll(hoursLbl, hoursField, minutesLbl, minutesField);
    durationBox.setAlignment(Pos.CENTER_LEFT);
    durationBox.setSpacing(10);
    resultBox.getChildren().add(durationBox);
  }


  @Override
  protected void setDoneButton(Button doneButton, Consumer<Entry> addEntryToModel,
                               Runnable updateGUI) {
    doneButton.setOnAction(event -> {


      try {
        startHours = Integer.parseInt(hoursOptions.getValue());
        startMinutes = Integer.parseInt(minutesOptions.getValue());
        durationMinutes = Integer.parseInt(minutesField.getText());
        durationHours = Integer.parseInt(hoursField.getText());
      } catch (Exception e) {
        return;
      }
      if (!nameField.getText().equals("")
          && Arrays.stream(Day.values()).toList()
          .contains(Day.valueOf(dayOptions.getValue().toUpperCase())) && durationMinutes >= 0 &&
          durationHours >= 0) {
        ((Stage) doneButton.getScene().getWindow()).close();
        addEntry(addEntryToModel, updateGUI);
      }
    });
  }

  @Override
  protected void addEntry(Consumer<Entry> addEntryToModel, Runnable updateGUI) {
    Event event = new Event(Day.valueOf(dayOptions.getValue().toUpperCase()), nameField.getText(),
        descriptionField.getText(),
        LocalTime.of(startHours, startMinutes), Duration.of(durationHours, ChronoUnit.HOURS)
        .plus(Duration.of(durationMinutes, ChronoUnit.MINUTES)));
    addEntryToModel.accept(event);
    updateGUI.run();
  }
}
