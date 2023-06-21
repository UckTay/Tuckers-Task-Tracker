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
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

/**
 * Implementation of the GUIView Interface.
 */
public class GUIViewImpl implements GUIView {

  private Runnable updateGUI;
  @Override
  public void showTasks(VBox vBox, List<Task> tasks, EntryGUIContainerFactory factory) {
    VBox box = (VBox) vBox.getChildren().get(1);
    box.prefWidthProperty().bind(vBox.widthProperty());
    box.getChildren().clear();
    for(Task task: tasks) {
      box.getChildren().add(factory.createContainer(task));
    }
  }

  @Override
  public void showEvents(VBox vBox, List<Event> events, EntryGUIContainerFactory factory) {
    VBox box = (VBox) vBox.getChildren().get(2);
    box.prefWidthProperty().bind(vBox.widthProperty());
    box.getChildren().clear();
    for(Event event: events) {
      box.getChildren().add(factory.createContainer(event));
    }
  }


  @Override
  public void newTaskPrompt(Consumer<Entry> taskAdder, Function<Day, Boolean> taskLimitChecker) {
    new TaskCreationPrompt(taskAdder, taskLimitChecker, updateGUI);
  }

  @Override
  public void newTaskPrompt(Task task, Consumer<Entry> taskAdder, Function<Day, Boolean> taskLimitChecker) {
    new TaskCreationPrompt(task, taskAdder, taskLimitChecker,  updateGUI);

  }

  @Override
  public void newEventPrompt(Event event, Consumer<Entry> eventAdder,
                             Function<Day, Boolean> limitChecker) {
    new EventCreationPrompt(event, eventAdder, limitChecker, updateGUI);
  }

  @Override
  public void newEventPrompt(Consumer<Entry> eventAdder, Function<Day, Boolean> eventLimitChecker) {
    new EventCreationPrompt(eventAdder, eventLimitChecker, updateGUI);
  }

  @Override
  public void showSettingsPrompt(Config config) {
    new SettingsPrompt(config);
  }

  @Override
  public void showTaskPanel(VBox vBox, List<Task> tasks) {
    vBox.getChildren().clear();
    for (Task task : tasks) {
      vBox.getChildren().add(new EntryGUIElement(task).getVBox());
    }
  }


  @Override
  public void setGUIUpdater(Runnable updater) {
    this.updateGUI = updater;
  }

  @Override
  public Scene load(Controller controller) {
    this.updateGUI = updateGUI;
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getClassLoader().getResource("view.fxml"));
      loader.setController(controller);
      return loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }

  @Override
  public void showWeekNamePrompt(Config config) {
    new WeekNamePrompt(config);
  }
}
