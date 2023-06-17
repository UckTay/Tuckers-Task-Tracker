package cs3500.pa05.view;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
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

public abstract class EntryCreationPrompt {

  protected VBox resultBox = new VBox();

  protected Dialog<String> dialog = new Dialog<>();

  private Button doneButton = new Button("Done!");

  protected TextField nameField = new TextField();
  protected TextField descriptionField = new TextField();
  protected ChoiceBox<String> dayOptions = new ChoiceBox<>();


  public EntryCreationPrompt(Consumer<Entry> addEntryToModel, Runnable updateGUI) {
    createPrompt();
    doneButton.prefWidthProperty().bind(resultBox.widthProperty());
    resultBox.getChildren().add(doneButton);
    resultBox.setSpacing(10);
    setDoneButton(doneButton, addEntryToModel, updateGUI);
    dialog.getDialogPane().setContent(resultBox);
    dialog.showAndWait();
  }

  protected void createPrompt() {
    Label nameLbl = new Label("Name:");
    resultBox.getChildren().add(nameLbl);
    resultBox.getChildren().add(nameField);
    Label descriptionLbl = new Label("Description:");
    resultBox.getChildren().add(descriptionLbl);
    resultBox.getChildren().add(descriptionField);
    dayOptions.setValue("");
    List<String> optionsList = new ArrayList<>();
    optionsList.add("");
    for (Day day : Day.values()) {
      optionsList.add(day.name());
    }
    dayOptions.setItems(FXCollections.observableArrayList(optionsList));
    Label dayField = new Label("Day:");
    HBox dayBox = new HBox();
    dayBox.prefWidthProperty().bind(resultBox.widthProperty());
    dayBox.getChildren().addAll(dayField, dayOptions);
    dayBox.setAlignment(Pos.CENTER_LEFT);
    dayBox.setSpacing(10);
    resultBox.getChildren().add(dayBox);
    Window window = dialog.getDialogPane().getScene().getWindow();
    window.setOnCloseRequest(event -> dialog.close());
  }

  protected void setDoneButton(Button doneButton, Consumer<Entry> addEntryToModel,
                               Runnable updateGUI) {
    doneButton.setOnAction(event -> {
      if (!nameField.getText().equals("") && Arrays.stream(Day.values()).toList()
          .contains(Day.valueOf(dayOptions.getValue().toUpperCase()))) {
        ((Stage) doneButton.getScene().getWindow()).close();
        addEntry(addEntryToModel, updateGUI);
      }
    });
  }

  protected abstract void addEntry(Consumer<Entry> addEntryToModel, Runnable updateGUI);

//   Dialog<String> dialog = new Dialog<>();
//    dialog.setTitle("New Task");


//    addDoneButton(dialog, vBox);
//    Task task = new Task(Day.valueOf(dayOptions.getValue().toUpperCase()), nameField.getText(),
//        descriptionField.getText(),
//        TaskStatus.INCOMPLETE);
//    taskAdder.accept(task);
//    updateGUI.run();
//  }

//  @Override
//  public void newEventPrompt(Consumer<Event> eventAdder) {
//    VBox vBox = new VBox();
//    Dialog<String> d = new Dialog<>();
//    d.setTitle("New Event");
//
//    Label nameLbl = new Label("Name:");
//    TextField nameField = new TextField();
//    vBox.getChildren().add(nameLbl);
//    vBox.getChildren().add(nameField);
//
//    Label descriptionLbl = new Label("Description:");
//    TextField descriptionField = new TextField();
//    vBox.getChildren().add(descriptionLbl);
//    vBox.getChildren().add(descriptionField);
//
//    ChoiceBox<String> dayOptions = new ChoiceBox<>();
//    dayOptions.setValue("");
//    List<String> optionsList = new ArrayList<>();
//    optionsList.add("");
//    for (Day day : Day.values()) {
//      optionsList.add(day.name().substring(0, 1) + day.name().substring(1).toLowerCase());
//    }
//    dayOptions.setItems(FXCollections.observableArrayList(optionsList));
//    Label dayField = new Label("Day:");
//    HBox dayBox = new HBox();
//    dayBox.prefWidthProperty().bind(vBox.widthProperty());
//    dayBox.getChildren().addAll(dayField, dayOptions);
//    dayBox.setAlignment(Pos.CENTER_LEFT);
//    dayBox.setSpacing(10);
//    vBox.getChildren().add(dayBox);
//    HBox durationBox = new HBox();
//
//    Label startTimeLbl = new Label("Start Time:");
//    Label colon = new Label(":");
//    HBox startTimeBox = new HBox();
//    vBox.getChildren().add(startTimeLbl);
//    List<String> hours = new ArrayList<>();
//    hours.add("");
//    for (int i = 0; i < 24; i++) {
//      hours.add((i < 10) ? "0" + i : String.valueOf(i));
//    }
//    List<String> minutes = new ArrayList<>();
//    minutes.add("");
//    for (int i = 0; i <= 60; i = i + 15) {
//      minutes.add((i < 10) ? "0" + i : String.valueOf(i));
//    }
//    ChoiceBox<String> hoursOptions = new ChoiceBox<>();
//    hoursOptions.setItems(FXCollections.observableArrayList(hours));
//    ChoiceBox<String> minutesOptions = new ChoiceBox<>();
//    minutesOptions.setItems(FXCollections.observableArrayList(minutes));
//    startTimeBox.getChildren().addAll(hoursOptions, colon, minutesOptions);
//    startTimeBox.setSpacing(10);
//    vBox.getChildren().add(startTimeBox);
//    Label durationLbl = new Label("Duration:");
//    Label hoursLbl = new Label("Hours:");
//    Label minutesLbl = new Label("Minutes:");
//    TextField hoursField = new TextField();
//    hoursField.setPrefWidth(30);
//    TextField minutesField = new TextField();
//    minutesField.setPrefWidth(30);
//    vBox.getChildren().add(durationLbl);
//    durationBox.getChildren().addAll(hoursLbl, hoursField, minutesLbl, minutesField);
//    durationBox.setAlignment(Pos.CENTER_LEFT);
//    durationBox.setSpacing(10);
//    vBox.getChildren().add(durationBox);
//    addDoneButton(d, vBox);
//    Event event = new Event(Day.valueOf(dayOptions.getValue().toUpperCase()), nameField.getText(),
//        descriptionField.getText(),
//        LocalTime.of(0, 0), Duration.of(2, ChronoUnit.HOURS));
//    eventAdder.accept(event);
//    updateGUI.run();

//  private void addDoneButton(Dialog d, VBox vBox) {
//    Button done = new Button("Done!");
//    done.prefWidthProperty().bind(vBox.widthProperty());
//    vBox.getChildren().add(done);
//    done.setOnAction(event -> {
//      ((Stage) done.getScene().getWindow()).close();
//    });
//
//  }

}
