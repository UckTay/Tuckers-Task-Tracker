package cs3500.pa05.view;

import cs3500.pa05.controller.Controller;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import java.util.List;
import javafx.fxml.FXMLLoader;

public class GUIViewImpl implements GUIView {

  FXMLLoader loader;
  public GUIViewImpl(Controller controller) {
  }

  @Override
  public void showTasks(Day day, List<Task> tasks) {

  }

  @Override
  public void showEvents(Day day, List<Event> events) {

  }

  @Override
  public void newTaskPrompt() {

  }

  @Override
  public void newEventPrompt() {

  }

  @Override
  public void load() {

  }
}
