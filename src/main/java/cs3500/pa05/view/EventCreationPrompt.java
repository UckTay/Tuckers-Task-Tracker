package cs3500.pa05.view;

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

  public EventCreationPrompt(Consumer<Entry> addEntryToModel, Runnable updateGUI) {
    super(addEntryToModel, updateGUI);

  }

  @Override
  protected void createPrompt() {
    dialog.setTitle("New Event");
    super.createPrompt();
    addTimeElements();
  }

  private void addTimeElements() {
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
    ChoiceBox<String> hoursOptions = new ChoiceBox<>();
    hoursOptions.setItems(FXCollections.observableArrayList(hours));
    ChoiceBox<String> minutesOptions = new ChoiceBox<>();
    minutesOptions.setItems(FXCollections.observableArrayList(minutes));
    startTimeBox.getChildren().addAll(hoursOptions, colon, minutesOptions);
    startTimeBox.setSpacing(10);
    resultBox.getChildren().add(startTimeBox);
    Label durationLbl = new Label("Duration:");
    Label hoursLbl = new Label("Hours:");
    Label minutesLbl = new Label("Minutes:");
    TextField hoursField = new TextField();
    hoursField.setPrefWidth(30);
    TextField minutesField = new TextField();
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
      if (!nameField.getText().equals("")
          && Arrays.stream(Day.values()).toList().contains(Day.valueOf(dayOptions.getValue().toUpperCase()))) {
        ((Stage) doneButton.getScene().getWindow()).close();
        addEntry(addEntryToModel, updateGUI);
      } else {
      }
    });
  }

  @Override
  protected void addEntry(Consumer<Entry> addEntryToModel, Runnable updateGUI) {
    Event event = new Event(Day.valueOf(dayOptions.getValue().toUpperCase()), nameField.getText(),
        descriptionField.getText(),
        LocalTime.of(0, 0), Duration.of(2, ChronoUnit.HOURS));
    addEntryToModel.accept(event);
    updateGUI.run();
  }
}
