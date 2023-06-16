package cs3500.pa05.view;

import cs3500.pa05.controller.Controller;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class GUIViewImpl implements GUIView {

  public GUIViewImpl() {
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
  public void showTaskPanel() {

  }

  @Override
  public Scene load(Controller controller) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getClassLoader().getResource("test.fxml"));
      loader.setController(controller);
      return loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
