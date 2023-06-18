package cs3500.pa05.view;

import cs3500.pa05.controller.Controller;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GUIViewImpl implements GUIView {

  private Runnable updateGUI;

  @Override
  public void showTasks(VBox vBox, List<Task> tasks, EntryGUIContainerFactory factory) {
    VBox box = (VBox) vBox.getChildren().get(1);
    box.getChildren().clear();
    for(Task task: tasks) {
      box.getChildren().add(factory.createContainer(task));
    }
  }

  @Override
  public void showEvents(VBox vBox, List<Event> events, EntryGUIContainerFactory factory) {
    VBox box = (VBox) vBox.getChildren().get(2);
    box.getChildren().clear();
    for(Event event: events) {
      box.getChildren().add(factory.createContainer(event));
    }
  }


  @Override
  public void newTaskPrompt(Consumer<Entry> taskAdder) {
    new TaskCreationPrompt(taskAdder, updateGUI);
  }

  @Override
  public void newEventPrompt(Consumer<Entry> eventAdder) {
    new EventCreationPrompt(eventAdder, updateGUI);
  }



  @Override
  public void showTaskPanel() {


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
      loader.setLocation(getClass().getClassLoader().getResource("test.fxml"));
      loader.setController(controller);
      return loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
