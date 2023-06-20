package cs3500.pa05.controller;

import cs3500.pa05.model.Config;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.JournalModel;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;
import cs3500.pa05.view.EntryGUIContainerFactory;
import cs3500.pa05.view.GUIView;
import java.time.Duration;
import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

//TODO: rename days in gui when order is changed
//TODO: limit creation according to the limit
//TODO: Add task queue
/**
 * Java Decks
 */
public class JournalController implements Controller{
  JournalModel javaJournal;

  GUIView view;
  JournalModel model;

  Config config;

  @FXML
  private MenuItem newEventButton;

  @FXML
  private MenuItem newTaskButton;

  @FXML
  private VBox day0;

  @FXML
  private VBox day1;

  @FXML
  private VBox day2;

  @FXML
  private VBox day3;

  @FXML
  private VBox day4;

  @FXML
  private VBox day5;

  @FXML
  private VBox day6;

  @FXML
  private AnchorPane mainPane;
  private Window fileWindow;

  @FXML
  private MenuItem saveButton;

  @FXML
  private MenuItem openButton;

  @FXML
  private MenuItem newWeekButton;

  @FXML
  private Label totalEvents;

  @FXML
  private Label totalTasks;

  @FXML
  private Label totalComplete;

  @FXML
  private Label weekName;

  @FXML
  private Button newEventView;

  @FXML
  private Button newTaskView;

  @FXML
  private Button settingsButton;

  private final EventHandler<CustomGUIEvent> updateGUIHandler = updateEvent -> {
    updateGUI();
  };

  public JournalController(GUIView GUIViewImpl, JournalModel model) {
    this.view = GUIViewImpl;
    this.model = model;
    this.config = model.getConfig();
    view.setGUIUpdater(this::updateGUI);
  }

  private void setupButtons() {
    EventHandler<ActionEvent> handleNewEvent = event -> view.newEventPrompt(newEvent -> model.addEvent(
        (Event) newEvent));
    EventHandler<ActionEvent> handleNewTask = event -> view.newTaskPrompt(newTask -> model.addTask((Task) newTask));
    newEventButton.setOnAction(handleNewEvent);
    newTaskButton.setOnAction(handleNewTask);
    saveButton.setOnAction(event -> saveBujo());
    openButton.setOnAction(event -> loadBujo());
    newWeekButton.setOnAction(event -> newBujo());
    newEventView.setOnAction(event -> view.newEventPrompt(newEvent -> model.addEvent(
        (Event) newEvent)));
    newTaskView.setOnAction(
        event -> view.newTaskPrompt(newTask -> model.addTask((Task) newTask)));
    settingsButton.setOnAction(event -> {
      view.showSettingsPrompt(config);
      updateGUI();
    });
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
        },
        entry -> {
          model.moveDown(entry);
        },
        (oldEntry, newEntry) -> {
          model.mindChange(oldEntry, newEntry);
        },
        entry -> {
          model.takesieBacksie(entry);
        }
    );
    for (Day day : Day.values()) {
      view.showEvents(dayToVBox(day), model.getDaysEvent(day), factory);
      view.showTasks(dayToVBox(day), model.getDaysTasks(day), factory);
    }
    updateWeeklyOverview();
  }

  private VBox dayToVBox(Day day) {
    List<Day> unorderedList = List.of(Day.values());
    List<Day> orderedList = new ArrayList<>();
    int startingIndex = unorderedList.indexOf(config.getStartingDay());
    for (int i = 0; i < unorderedList.size(); i++) {
      orderedList.add(unorderedList.get((startingIndex + i) % 7));
    }
    List<VBox> boxes = List.of(day0, day1, day2, day3, day4, day5, day6);
    return boxes.get(orderedList.indexOf(day));
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

  private void updateWeeklyOverview() {
    List<Event> events = model.getAllEvents();
    List<Task> tasks = model.getAllTasks();
    int totalEventsInt = events.size();
    int totalTasksInt = tasks.size();
    double count = 0;
    for (Task task : tasks) {
      if (task.getTaskStatus() == TaskStatus.COMPLETE) {
        count++;
      }
    }
    double percentTasksDouble = 100 * ((tasks.size() == 0) ? 1 : count / tasks.size());
    totalEvents.setText(String.valueOf(totalEventsInt));
    totalTasks.setText(String.valueOf(totalTasksInt));
    totalComplete.setText(percentTasksDouble + "%");
  }

  @Override
  public void run() {
    setupButtons();
    mainPane.addEventHandler(CustomGUIEvent.UPDATE_GUI_EVENT, updateGUIHandler);
  }


}
