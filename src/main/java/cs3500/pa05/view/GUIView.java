package cs3500.pa05.view;

import cs3500.pa05.controller.Controller;
import cs3500.pa05.model.Config;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import java.lang.reflect.Executable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

/**
 * The interface for the gui
 */
public interface GUIView {

  /**
   * Refreshes the tasks with the passed in tasks.
   *
   * @param vbox the container
   * @param tasks the given tasks
   * @param factory The Gui Factory
   */
  void showTasks(VBox vbox, List<Task> tasks, EntryGUIContainerFactory factory);

  /**
   * Refreshes the events with the passed in events.
   *
   * @param vbox the container
   * @param events the given events
   * @param factory The Gui Factory
   */
  void showEvents(VBox vbox, List<Event> events, EntryGUIContainerFactory factory);

  /**
   * Prompts the user to add a new task.
   * 
   * @param taskAdder Function that adds the task to the journal
   * @param limitChecker Function that checks if the creation of a new task surpasses the limit
   */
  void newTaskPrompt(Consumer<Entry> taskAdder, Function<Day, Boolean> limitChecker);

  /**
   * Prompts the user to add a new task with old values already filled.
   * 
   * @param task old task
   * @param taskAdder Function that adds the task to the journal
   * @param limitChecker Function that checks if the creation of a new task surpasses the limit
   */
  void newTaskPrompt(Task task, Consumer<Entry> taskAdder, Function<Day, Boolean> limitChecker);

  /**
   * Prompts the user to add a new event.
   *
   * @param eventAdder Function that adds the event to the journal
   * @param limitChecker Function that checks if the creation of a new event surpasses the limit
   */
  void newEventPrompt(Consumer<Entry> eventAdder, Function<Day, Boolean> limitChecker);

  /**
   * Prompts the user to add a new event with old values already filled.
   *
   * @param event old event
   * @param eventAdder Function that adds the event to the journal
   * @param limitChecker Function that checks if the creation of a new event surpasses the limit
   */
  void newEventPrompt(Event event, Consumer<Entry> eventAdder, Function<Day, Boolean> limitChecker);


  /**
   * Prompts the user with the settings prompt.
   * 
   * @param config the old config
   */
  void showSettingsPrompt(Config config);

  /**
   * Shows the task panel.
   *
   * @param vBox the vbox to add tasks to
   * @param tasks the list of tasks
   */
  void showTaskPanel(VBox vBox, List<Task> tasks);

  /**
   * Sets the gui updater.
   * 
   * @param updater the new gui updater
   */
  void setGUIUpdater(Runnable updater);

  /**
   * Loads the GUI.
   * 
   * @param controller the controller the GUI uses
   * @return the gui
   */
  Scene load(Controller controller);

  void showWeekNamePrompt(Config config);
}
