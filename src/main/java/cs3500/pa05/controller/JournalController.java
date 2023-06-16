package cs3500.pa05.controller;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.JournalModel;
import cs3500.pa05.view.GUIView;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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

  public void newEventPrompt() {
    Dialog<String> d = new Dialog<>();
    Window window = d.getDialogPane().getScene().getWindow();
    window.setOnCloseRequest(event -> d.close());
    TextField eventName = new TextField();
    eventName.setPromptText("name");
    d.getDialogPane().getChildren().add(eventName);
    TextField description = new TextField();
    description.setPromptText("description");
    d.getDialogPane().getChildren().add(description);
    d.setContentText("placeholder");

    d.getDialogPane().getButtonTypes().add(new ButtonType("Got it!", ButtonBar.ButtonData.CANCEL_CLOSE));
    d.showAndWait();
    sunday.getChildren().clear();
    model.addEvent(new Event(Day.MONDAY, eventName.getText(), description.getText(), LocalTime.of(1, 1), Duration.of(2, ChronoUnit.HOURS)));
    for (Event e : model.getDaysEvent(Day.MONDAY)) {
      sunday.getChildren().addAll(new Label("name: " + e.getName()), new Label("description: " + e.getDescription()));
    }
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
