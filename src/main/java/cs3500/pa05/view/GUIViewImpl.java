package cs3500.pa05.view;

import cs3500.pa05.controller.Controller;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;
import java.io.IOException;
import java.lang.reflect.Executable;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

public class GUIViewImpl implements GUIView {

  private Runnable updateGUI;

  @Override
  public void showTasks(VBox vBox, List<Task> tasks) {
    VBox box = (VBox) vBox.getChildren().get(1);
    box.getChildren().clear();
    for(Task task: tasks) {
      box.getChildren().add(createEntryElement(task));
    }
  }

  @Override
  public void showEvents(VBox vBox, List<Event> events) {
    VBox box = (VBox) vBox.getChildren().get(2);
    box.getChildren().clear();
    for(Event event: events) {
      box.getChildren().add(createEntryElement(event));
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

  private VBox entryContainerFactory() {
    VBox container = new VBox();
    HBox buttons = new HBox();

    Button up = new Button("⬆\uFE0F");
    Button edit = new Button("✏\uFE0F");
    Button trash = new Button("\uD83D\uDDD1\uFE0F");
    Button down = new Button("⬇\uFE0F");
    buttons.getChildren().addAll(up, edit, trash, down);
    buttons.spacingProperty()
        .bind(buttons.widthProperty().subtract((up.widthProperty().multiply(4).add(16))).divide(3));
    container.getChildren().add(buttons);
    container.setBorder(Border.stroke(Color.BLACK));
    return container;
  }

  private VBox createEntryElement(Entry e) {
    VBox entryElement = entryContainerFactory();
    entryElement.getChildren().add(new Label(e.getName()));
    if(e.getDescription() != null && !e.getDescription().equals("")) {
      entryElement.getChildren().add(new Label(e.getDescription()));
    }
    return entryElement;
  }

  private VBox createEntryElement(Event event) {
    VBox entryElement = createEntryElement((Entry) event);
    entryElement.getChildren().add(new Label(event.getStartTime().toString()));
    entryElement.getChildren().add(new Label(event.getDuration().toString()));
    return entryElement;
  }

  private VBox createEntryElement(Task task) {
    VBox entryElement = createEntryElement((Entry) task);
    CheckBox status = new CheckBox();
    boolean isComplete = task.getTaskStatus().equals(TaskStatus.COMPLETE);
    status.setSelected(isComplete);
    entryElement.getChildren().add(new Label("Status:"));
    entryElement.getChildren().add(status);
    return entryElement;
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
