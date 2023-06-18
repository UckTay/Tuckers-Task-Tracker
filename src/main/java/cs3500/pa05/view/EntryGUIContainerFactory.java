package cs3500.pa05.view;

import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import java.util.function.Consumer;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class EntryGUIContainerFactory {

  Consumer<Entry> moveUp, moveDown, editElement, takesieBacksie;

  Runnable updateGUI;

  public EntryGUIContainerFactory(Runnable updateGUI, Consumer<Entry> moveUp, Consumer<Entry> moveDown, Consumer<Entry> editElement, Consumer<Entry> takesieBacksie) {
    this.moveUp = moveUp;
    this.moveDown = moveDown;
    this.editElement = editElement;
    this.takesieBacksie = takesieBacksie;
    this.updateGUI = updateGUI;
  }

  public VBox createContainer(Task task) {
    VBox resultBox = new VBox();
    resultBox.getChildren().add(createButtons(task));
    resultBox.getChildren().add(new EntryGUIElement(task).getVBox());
    resultBox.setBorder(Border.stroke(Color.BLACK));
    return resultBox;
  }

  public VBox createContainer(Event event) {
    VBox resultBox = new VBox();
    resultBox.getChildren().add(createButtons(event));
    resultBox.getChildren().add(new EntryGUIElement(event).getVBox());
    resultBox.setBorder(Border.stroke(Color.BLACK));
    return resultBox;
  }

  private HBox createButtons(Entry entry) {
    HBox resultBox = new HBox();
    Button up = new Button("⬆\uFE0F");
    up.setOnAction((event) -> {
      moveUp.accept(entry);
      updateGUI.run();
    });
    Button edit = new Button("✏\uFE0F");
    edit.setOnAction((event) -> {
      editElement.accept(entry);
      updateGUI.run();
    });
    Button trash = new Button("\uD83D\uDDD1\uFE0F");
    trash.setOnAction((event -> {
      takesieBacksie.accept(entry);
      updateGUI.run();
    }));
    Button down = new Button("⬇\uFE0F");
    down.setOnAction((event -> {
      moveDown.accept(entry);
      updateGUI.run();
    }));
    resultBox.getChildren().addAll(up, edit, trash, down);
    return resultBox;
  }




}
