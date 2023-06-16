package cs3500.pa05.controller;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.JournalModel;
import cs3500.pa05.view.GUIView;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Java Decks
 */
public class JournalController implements Controller {
  JournalModel javaJournal;

  GUIView view;
  JournalModel model;

  @FXML
  MenuItem newEventButton;

  @FXML
  VBox sunday;

  @FXML
  AnchorPane mainPane;

  public JournalController(GUIView GUIViewImpl, JournalModel model) {
    this.view = GUIViewImpl;
    this.model = model;
  }

  public void setupButtons() {
    newEventButton.setOnAction(event -> newEventPrompt());
  }

  public void newEventPrompt2() {
    Dialog<String> d = new Dialog<>();
    TextField eventName = new TextField();
    eventName.setPromptText("name");
    d.getDialogPane().getChildren().add(eventName);
    TextField description = new TextField();
    description.setPromptText("description");
    d.getDialogPane().getChildren().add(description);
    d.setContentText("placeholder");
    ButtonType gotIt = new ButtonType("Got it!", ButtonBar.ButtonData.CANCEL_CLOSE);
    d.getDialogPane().getButtonTypes().add(gotIt);
    Window window = d.getDialogPane().getScene().getWindow();
    window.setOnCloseRequest(event -> d.close());
    d.showAndWait();
    sunday.getChildren().clear();
    model.addEvent(
        new Event(Day.MONDAY, eventName.getText(), description.getText(), LocalTime.of(1, 1),
            Duration.of(2, ChronoUnit.HOURS)));
    for (Event e : model.getDaysEvent(Day.MONDAY)) {
      sunday.getChildren().addAll(new Label("name: " + e.getName()),
          new Label("description: " + e.getDescription()));
    }
  }

  public void newEventPrompt() {
    VBox vBox = new VBox();


    Dialog<String> d = new Dialog<>();
    d.setTitle("New Event");

    Label nameLbl = new Label("Name:");
    TextField nameField = new TextField();

    vBox.getChildren().add(nameLbl);
    vBox.getChildren().add(nameField);

    Label descriptionLbl = new Label("Description:");
    TextField descriptionField = new TextField();

    vBox.getChildren().add(descriptionLbl);
    vBox.getChildren().add(descriptionField);


    ChoiceBox<String> dayOptions = new ChoiceBox<>();
    dayOptions.setValue("");
    List<String> optionsList = new ArrayList<>();
    optionsList.add("");
    for (Day day : Day.values()) {
      optionsList.add(day.name());
    }

    dayOptions.setItems(FXCollections.observableArrayList(optionsList));

    Label dayField = new Label("Day:");
    HBox dayBox = new HBox();
    dayBox.prefWidthProperty().bind(vBox.widthProperty());
    dayBox.getChildren().addAll(dayField, dayOptions);
    dayBox.setAlignment(Pos.CENTER_LEFT);
    dayBox.setSpacing(10);


    vBox.getChildren().add(dayBox);


    HBox durationBox = new HBox();

    Label durationLbl = new Label("Duration:");
    Label hoursLbl = new Label("Hours:");

    Label minutesLbl = new Label("Minutes:");

    TextField hoursField = new TextField();
    hoursField.setPrefWidth(30);

    TextField minutesField = new TextField();
    minutesField.setPrefWidth(30);

    vBox.getChildren().add(durationLbl);

    durationBox.getChildren().addAll(hoursLbl, hoursField, minutesLbl, minutesField);
    durationBox.setAlignment(Pos.CENTER_LEFT);

    durationBox.setSpacing(10);

    vBox.getChildren().add(durationBox);

    Button done = new Button("Done!");
    done.prefWidthProperty().bind(vBox.widthProperty());

    vBox.getChildren().add(done);
    done.setOnAction(event -> {

      ((Stage) done.getScene().getWindow()).close();
    });

    vBox.setSpacing(10);
    d.getDialogPane().setContent(vBox);
    d.showAndWait();

  }

  private boolean validateAndAddEvent(String name, String description, String day,
                                      String duration) {
    return true;
    //TODO: Implement
  }

  private boolean validateEvent(String name, String description, Day day, Duration duration) {
    return true;
  }


  private Day constructDayFromString(String d) throws IllegalArgumentException {
    return Day.valueOf(d);
  }

  private Duration constructDurationFromString(String d) {
    return null;
  }


  public void newBujo() {

  }

  public void loadBujo() {

  }

  public void saveBujo() {

  }

  public void newTask() {

  }

  public void newEvent() {

  }

  @Override
  public void run() {
    setupButtons();
  }


}
