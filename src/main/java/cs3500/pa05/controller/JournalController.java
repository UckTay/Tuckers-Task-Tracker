package cs3500.pa05.controller;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.JournalModel;
import cs3500.pa05.model.Task;
import cs3500.pa05.view.EntryGUIContainerFactory;
import cs3500.pa05.view.GUIView;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
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
  MenuItem newTaskButton;

  @FXML
  VBox day0;

  @FXML
  VBox day1;

  @FXML
  VBox day2;

  @FXML
  VBox day3;

  @FXML
  VBox day4;

  @FXML
  VBox day5;

  @FXML
  VBox day6;

  @FXML
  AnchorPane mainPane;
  Window fileWindow;

  @FXML
  MenuItem saveButton;

  @FXML
  MenuItem openButton;

  @FXML
  MenuItem newWeekButton;

  @FXML
  Label totalEvents;

  @FXML
  Label totalTasks;

  @FXML
  Label totalCompleted;

  public JournalController(GUIView GUIViewImpl, JournalModel model) {
    this.view = GUIViewImpl;
    this.model = model;
    view.setGUIUpdater(this::updateGUI);
  }

  private void setupButtons() {
    newEventButton.setOnAction(event -> view.newEventPrompt(newEvent -> model.addEvent(
        (Event) newEvent)));
    newTaskButton.setOnAction(event -> view.newTaskPrompt(newTask -> model.addTask((Task) newTask)));
    saveButton.setOnAction(event -> saveBujo());
    openButton.setOnAction(event -> loadBujo());
    newWeekButton.setOnAction(event -> newBujo());
  }

  private Day constructDayFromString(String d) throws IllegalArgumentException {
    return Day.valueOf(d);
  }

  private Duration constructDurationFromString(String d) {
    return null;
  }

  private void updateGUI() {
    EntryGUIContainerFactory factory = new EntryGUIContainerFactory(
        this::updateGUI,
        entry -> {
          model.moveUp(entry);
          updateGUI();
        },
        entry -> {
          model.moveDown(entry);
          updateGUI();
        },
        entry -> model.mindChange(entry, this::updateGUI),
        entry -> {
          model.takesieBacksie(entry);
          updateGUI();
        }
        );
    for (Day day : Day.values()) {
      view.showEvents(dayToVBox(day), model.getDaysEvent(day), factory);
      view.showTasks(dayToVBox(day), model.getDaysTasks(day), factory);
    }
  }

  private VBox dayToVBox(Day day) {
    return switch (day.ordinal()) {
      case 0 -> day0;
      case 1 -> day1;
      case 2 -> day2;
      case 3 -> day3;
      case 4 -> day4;
      case 5 -> day5;
      case 6 -> day6;
      default -> throw new IllegalStateException("Unexpected value: " + day.ordinal());
    };
  }

  private void newBujo() {
    model.newWeek();
    updateGUI();
  }

  private void loadBujo() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    File selectedFile = fileChooser.showOpenDialog(fileWindow);
    model.loadBujo(selectedFile.toPath());
    updateGUI();
  }

  private void saveBujo() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Bujo Files", "*.bujo"));
    File selectedFile = fileChooser.showSaveDialog(fileWindow);
    model.saveBujo(selectedFile.toPath());
  }

  @Override
  public void run() {
    setupButtons();
  }




}
