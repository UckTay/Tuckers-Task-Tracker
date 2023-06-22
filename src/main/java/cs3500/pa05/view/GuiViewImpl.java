package cs3500.pa05.view;

import cs3500.pa05.controller.Controller;
import cs3500.pa05.model.Config;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.view.prompts.EventCreationPrompt;
import cs3500.pa05.view.prompts.SettingsPrompt;
import cs3500.pa05.view.prompts.TaskCreationPrompt;
import cs3500.pa05.view.prompts.WeekNamePrompt;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Implementation of the GUIView Interface.
 */
public class GuiViewImpl implements GuiView {

  private Runnable updategui;

  @Override
  public void showTasks(VBox vbox, List<Task> tasks, EntryGuiContainerFactory factory) {
    VBox box = (VBox) vbox.getChildren().get(1);
    box.prefWidthProperty().bind(vbox.widthProperty());
    box.getChildren().clear();
    if (tasks.size() > 0) {
      box.getChildren().add(new Label("Tasks:"));
    }
    for (Task task : tasks) {
      box.getChildren().add(factory.createContainer(task));
    }
  }

  @Override
  public void showEvents(VBox vbox, List<Event> events, EntryGuiContainerFactory factory) {
    VBox box = (VBox) vbox.getChildren().get(2);
    box.prefWidthProperty().bind(vbox.widthProperty());
    box.getChildren().clear();
    if (events.size() > 0) {
      box.getChildren().add(new Label("Events:"));
    }
    for (Event event : events) {
      box.getChildren().add(factory.createContainer(event));
    }
  }


  @Override
  public void newTaskPrompt(Consumer<Entry> taskAdder, Function<Day, Boolean> taskLimitChecker) {
    new TaskCreationPrompt(taskAdder, taskLimitChecker, updategui);
  }

  @Override
  public void newTaskPrompt(Task task, Consumer<Entry> taskAdder,
                            Function<Day, Boolean> taskLimitChecker) {
    new TaskCreationPrompt(task, taskAdder, taskLimitChecker, updategui);

  }

  @Override
  public void newEventPrompt(Event event, Consumer<Entry> eventAdder,
                             Function<Day, Boolean> limitChecker) {
    new EventCreationPrompt(event, eventAdder, limitChecker, updategui);
  }

  @Override
  public void newEventPrompt(Consumer<Entry> eventAdder, Function<Day, Boolean> eventLimitChecker) {
    new EventCreationPrompt(eventAdder, eventLimitChecker, updategui);
  }

  @Override
  public void showSettingsPrompt(Config config) {
    new SettingsPrompt(config);
  }

  @Override
  public void showTaskPanel(VBox vbox, List<Task> tasks) {
    vbox.getChildren().clear();
    for (Task task : tasks) {
      vbox.getChildren().add(new EntryGuiElement(task).getVbox());
    }
    vbox.setBorder(Border.stroke(Color.BLACK));
    vbox.getStylesheets().add(
        Objects.requireNonNull(this.getClass().getResource("/NetflixTheme.css")).toExternalForm());
    vbox.getStyleClass().add("containerBorder");
    vbox.setSpacing(15);

  }

  @Override
  public void setGuiUpdater(Runnable updater) {
    this.updategui = updater;
  }

  @Override
  public Scene load(Controller controller) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getClassLoader().getResource("view.fxml"));
      loader.setController(controller);
      Scene scene = loader.load();
      return scene;
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.", exc);
    }
  }

  @Override
  public void showWeekNamePrompt(Config config) {
    new WeekNamePrompt(config);
  }
}
