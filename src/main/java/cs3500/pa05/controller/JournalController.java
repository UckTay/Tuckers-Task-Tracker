package cs3500.pa05.controller;

import cs3500.pa05.model.Config;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.JournalModel;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;
import cs3500.pa05.view.EntryGuiContainerFactory;
import cs3500.pa05.view.GuiView;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
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
  GuiView view;
  JournalModel model;
  Config config;

  boolean isTaskPanelVisible = false;

  @FXML
  private Scene scene;
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
  private Button taskPanelButton;

  @FXML
  private MediaView intro;

  @FXML
  private Pane pane;

  @FXML
  private VBox taskPanel;

  @FXML
  private MenuItem openTemplateButton;

  @FXML
  private MenuItem settingsMenuBar;

  @FXML
  private CheckMenuItem taskPanelMenuButton;

  private final EventHandler<CustomGuiEvent> updateGuiHandler = updateEvent -> updateGui();

  /**
   * Construsts and instance of the controller.
   *
   * @param guiViewImpl the GUI
   * @param model       the model
   */
  public JournalController(GuiView guiViewImpl, JournalModel model) {
    this.view = guiViewImpl;
    this.model = model;
    this.config = model.getConfig();
    view.setGuiUpdater(this::updateGui);
  }

  /**
   * Sets up the buttons for use.
   */
  private void setupButtons() {
    EventHandler<ActionEvent> handleNewEvent =
        event -> view.newEventPrompt(newEvent -> model.addEvent(
            (Event) newEvent), day -> model.isBelowEventLimit(day));
    EventHandler<ActionEvent> handleNewTask =
        event -> view.newTaskPrompt(newTask -> model.addTask((Task) newTask),
            day -> model.isBelowTaskLimit(day));
    EventHandler<ActionEvent> showSettingsPrompt = event -> {
      view.showSettingsPrompt(config);
      updateGui();
    };
    newEventButton.setOnAction(handleNewEvent);
    newTaskButton.setOnAction(handleNewTask);
    settingsMenuBar.setOnAction(showSettingsPrompt);
    settingsButton.setOnAction(showSettingsPrompt);
    saveButton.setOnAction(event -> saveBujo());
    openButton.setOnAction(event -> loadBujo());
    openTemplateButton.setOnAction(event -> loadBujoAsTemplate());
    newWeekButton.setOnAction(event -> newBujo());
    newEventView.setOnAction(handleNewEvent);
    newTaskView.setOnAction(handleNewTask);
    taskPanelMenuButton.setOnAction(event -> taskQueueHandler());
    taskPanelButton.setOnAction(event -> taskQueueHandler());
    weekName.setOnMouseClicked(event -> {
      view.showWeekNamePrompt(config);
      updateGui();
    });

  }

  /**
   * Updates the GUI.
   */
  private void updateGui() {
    EntryGuiContainerFactory factory = new EntryGuiContainerFactory(
        this::updateGui,
        event -> view.newEventPrompt(event, newEvent -> model.mindChange(event,
            (Event) newEvent), day -> model.isBelowTaskLimit(day)),
        task -> view.newTaskPrompt(task, newTask -> model.mindChange(task, (Task) newTask),
            day -> model.isBelowTaskLimit(day)),
        entry -> model.moveUp(entry),
        entry -> model.moveDown(entry),
        (oldEntry, newEntry) -> model.mindChange(oldEntry, newEntry),
        entry -> model.takesieBacksie(entry));
    for (Day day : Day.values()) {
      Label label = ((Label) dayToVbox(day).getChildren().get(0));
      label.setText(day.toString());
      if (LocalDate.now().get(ChronoField.DAY_OF_WEEK) == day.ordinal()) {
        label.setUnderline(true);
      } else {
        label.setUnderline(false);
      }
      view.showEvents(dayToVbox(day), model.getDaysEvent(day), factory);
      view.showTasks(dayToVbox(day), model.getDaysTasks(day), factory);
    }
    if (isTaskPanelVisible) {
      taskPanel.setVisible(true);
      taskPanel.setManaged(true);
      view.showTaskPanel(taskPanel, model.getAllTasks());
    } else {
      taskPanel.setVisible(false);
      taskPanel.setManaged(false);
    }
    updateWeeklyOverview();
    if (config.getName() != null) {
      weekName.setText(config.getName());
      weekName.setPrefWidth(500);
      weekName.setAlignment(Pos.CENTER);
    } else {
      weekName.setText("Click To Enter Name");
    }
  }

  /**
   * Handles the Task Queue menu.
   */
  private void taskQueueHandler() {
    isTaskPanelVisible = !isTaskPanelVisible;
    if (isTaskPanelVisible) {
      taskPanelButton.setText("Close Task Queue");
      taskPanelMenuButton.setSelected(true);
    } else {
      taskPanelButton.setText("Open Task Queue");
      taskPanelMenuButton.setSelected(false);
    }
    updateGui();
  }

  /**
   * Gets the vbox of a given day.
   *
   * @param day the given day
   * @return the VBox
   */
  private VBox dayToVbox(Day day) {
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
    config = model.getConfig();
    updateGui();
  }

  /**
   * Loads a given week from a Bujo file
   */
  private void loadBujo() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    File selectedFile = fileChooser.showOpenDialog(fileWindow);
    try (Reader reader = Files.newBufferedReader(selectedFile.toPath())) {
      reader.read();
      model.loadBujo(selectedFile.toPath());
      config = model.getConfig();
    } catch (IOException ex) {
      boolean firstTime = true;
      String password = "";
      do {
        try {
          if (!firstTime) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.getDialogPane().getStylesheets()
                .add(this.getClass().getResource("/NetflixTheme.css").toExternalForm());
            a.setContentText("Error Decrypting File");
            a.showAndWait();
          }
          firstTime = false;
          TextInputDialog dialog = new TextInputDialog();
          dialog.setTitle("Password Prompt");
          dialog.setGraphic(null);
          dialog.setHeaderText(null);
          dialog.setContentText("Enter Password:");
          dialog.getDialogPane().getStylesheets()
              .add(Objects.requireNonNull(this.getClass().getResource("/NetflixTheme.css"))
                  .toExternalForm());
          dialog.showAndWait();
          password = dialog.getResult();
          model.loadBujo(selectedFile.toPath(), password);
        } catch (Exception ignored) {
          //Exception intentionally ignored
        }
      } while (model.getConfig() == null);
      config = model.getConfig();
      config.setPassword(password);
    }
    updateGui();
  }

  /**
   * Loads a Bujo File as a Template.
   */
  private void loadBujoAsTemplate() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    File selectedFile = fileChooser.showOpenDialog(fileWindow);
    try {
      model.loadBujoTemplate(selectedFile.toPath());
      config = model.getConfig();
      view.showWeekNamePrompt(config);
    } catch (Exception e) {
      Alert a = new Alert(Alert.AlertType.ERROR);
      a.setContentText("Error Reading File");
      a.showAndWait();
    }

    updateGui();
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
    String percentTasksString = String.format("%3.2f%%", percentTasksDouble);
    totalComplete.setText(percentTasksString);
  }

  @Override
  public void run() {
    playSplashScene();
  }

  /**
   * Runs the code after the splash screen
   */
  private void runAfterSplashScene() {
    setupButtons();
    scene.addEventHandler(CustomGuiEvent.UPDATE_GUI_EVENT, updateGuiHandler);
    updateGui();
  }

  /**
   * Plays the splash screen.
   */
  private void playSplashScene() {
    intro.setVisible(true);
    pane.setVisible(true);
    Media media = new Media(
        Objects.requireNonNull(getClass().getClassLoader().getResource("JournalAnimation3.mp4"))
            .toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    intro.setMediaPlayer(mediaPlayer);
    mediaPlayer.setAutoPlay(true);
    mediaPlayer.setOnEndOfMedia(() -> {
      intro.setVisible(false);
      pane.setVisible(false);
      runAfterSplashScene();
    });
    mediaPlayer.setOnError(() -> {
      intro.setVisible(false);
      pane.setVisible(false);
      runAfterSplashScene();
    });
  }
}

