package cs3500.pa05.controller;

import cs3500.pa05.model.Config;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.JournalModel;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;
import cs3500.pa05.view.EntryGUIContainerFactory;
import cs3500.pa05.view.GUIView;
import java.nio.file.Path;
import java.time.Duration;
import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;

//TODO: rename days in gui when order is changed
//TODO: limit creation according to the limit
//TODO: Add task queue

/**
 * Represents the controller for the java journal.
 */
public class JournalController implements Controller {
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

  @FXML
  private MediaView intro;

  @FXML
  private Pane pane;

  private final EventHandler<CustomGUIEvent> updateGUIHandler = updateEvent -> {
    updateGUI();
  };

  /**
   * Construsts and instance of the controller.
   *
   * @param GUIViewImpl the GUI
   * @param model       the model
   */
  public JournalController(GUIView GUIViewImpl, JournalModel model) {
    this.view = GUIViewImpl;
    this.model = model;
    this.config = model.getConfig();
    view.setGUIUpdater(this::updateGUI);
  }

  /**
   * Sets up the buttons for use.
   */
  private void setupButtons() {
    EventHandler<ActionEvent> handleNewEvent = event -> {
      view.newEventPrompt(newEvent -> model.addEvent(
          (Event) newEvent), day -> model.isBelowEventLimit(day));
    };
    EventHandler<ActionEvent> handleNewTask =
        event -> view.newTaskPrompt(newTask -> model.addTask((Task) newTask),
            day -> model.isBelowTaskLimit(day));
    newEventButton.setOnAction(handleNewEvent);
    newTaskButton.setOnAction(handleNewTask);
    saveButton.setOnAction(event -> saveBujo());
    openButton.setOnAction(event -> loadBujo());
    newWeekButton.setOnAction(event -> newBujo());
    newEventView.setOnAction(handleNewEvent);
    newTaskView.setOnAction(handleNewTask);
    settingsButton.setOnAction(event -> {
      view.showSettingsPrompt(config);
      updateGUI();
    });

  }

  /**
   * Constructs a day from a string.
   *
   * @param d the string
   * @return the new day
   * @throws IllegalArgumentException the string isn't a day
   */
  private Day constructDayFromString(String d) throws IllegalArgumentException {
    return Day.valueOf(d);
  }

  /**
   * Constructs a duration from a string.
   *
   * @param d the duration as a string
   * @return the new duration
   */
  private Duration constructDurationFromString(String d) {
    return null;
  }

  /**
   * Updates the GUI.
   */
  private void updateGUI() {
    EntryGUIContainerFactory factory = new EntryGUIContainerFactory(
        this::updateGUI,
        event -> view.newEventPrompt(event, newEvent -> model.mindChange(event,
            (Event) newEvent), day -> model.isBelowTaskLimit(day)),
        task -> {
          view.newTaskPrompt(task, newTask -> model.mindChange(task, (Task) newTask),
              day -> model.isBelowTaskLimit(day));
        },
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
      Label label = ((Label) dayToVBox(day).getChildren().get(0));
      label.setText(day.toString());
      if (LocalDate.now().get(ChronoField.DAY_OF_WEEK) == day.ordinal()) {
        label.setUnderline(true);
      } else {
        label.setUnderline(false);
      }
      view.showEvents(dayToVBox(day), model.getDaysEvent(day), factory);
      view.showTasks(dayToVBox(day), model.getDaysTasks(day), factory);
    }
    updateWeeklyOverview();
  }

  /**
   * Gets the vbox of a given day.
   *
   * @param day the given day
   * @return the VBox
   */
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

  /**
   * Creates a new week.
   */
  private void newBujo() {
    model.newWeek();
    updateGUI();
  }

  /**
   * Loads a given week from a Bujo file
   */
  private void loadBujo() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    File selectedFile = fileChooser.showOpenDialog(fileWindow);
    model.loadBujo(selectedFile.toPath());
    updateGUI();
  }

  /**
   * Saves the week to a bujo file.
   */
  private void saveBujo() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Bujo Files", "*.bujo"));
    File selectedFile = fileChooser.showSaveDialog(fileWindow);
    model.saveBujo(selectedFile.toPath());
  }

  /**
   * Updates the weekly overview.
   */
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
    playSplashScene();
  }

  private void runAfterSplashScene() {
    setupButtons();
    mainPane.addEventHandler(CustomGUIEvent.UPDATE_GUI_EVENT, updateGUIHandler);
    updateGUI();
  }

  private void playSplashScene() {
    intro.setVisible(true);
//    intro.setFitHeight(Double.MAX_VALUE);
//    intro.setFitWidth(Double.MAX_VALUE);
    Media media = new Media(Path.of("src/main/resources/JournalAnimation3.mp4").toUri().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    intro.setMediaPlayer(mediaPlayer);
    mediaPlayer.setAutoPlay(true);
//    mediaPlayer.play();
    mediaPlayer.setOnEndOfMedia(() -> {
      intro.setVisible(false);
      runAfterSplashScene();
      pane.setVisible(false);

    });


  }

}

