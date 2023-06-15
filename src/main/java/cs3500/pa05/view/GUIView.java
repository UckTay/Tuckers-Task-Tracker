package cs3500.pa05.view;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import java.util.List;
//TODO: add the task panel thing
public interface GUIView {
  void showTasks(Day day, List<Task> tasks);
  void showEvents(Day day, List<Event> events);
  void newTaskPrompt();
  void newEventPrompt();

  void showTaskPanel();

  void load();

}
