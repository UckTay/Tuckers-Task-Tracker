package cs3500.pa05.view;

import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.view.prompts.EventCreationPrompt;
import cs3500.pa05.view.prompts.TaskCreationPrompt;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class EntryGUIContainerFactory {

  Consumer<Entry> moveUp, moveDown, takesieBacksie;

  BiConsumer<Entry, Entry> editElement;

  Runnable updateGUI;

  public EntryGUIContainerFactory(Runnable updateGUI, Consumer<Entry> moveUp,
                                  Consumer<Entry> moveDown, BiConsumer<Entry, Entry> editElement,
                                  Consumer<Entry> takesieBacksie) {
    this.moveUp = moveUp;
    this.moveDown = moveDown;
    this.editElement = editElement;
    this.takesieBacksie = takesieBacksie;
    this.updateGUI = updateGUI;
  }

  public VBox createContainer(Task task) {
    return createContainerHelper(task);
  }

  public VBox createContainer(Event event) {
    return createContainerHelper(event);
  }

  private <T extends Entry> VBox createContainerHelper(T entry) {
    VBox resultBox = new VBox();
    HBox buttonBox = createButtons(entry);
    buttonBox.setVisible(false);
    buttonBox.setManaged(false);
    resultBox.setOnMouseEntered((event -> {
      buttonBox.setVisible(true);
      buttonBox.setManaged(true);
    }));
    resultBox.setOnMouseExited((event -> {
      buttonBox.setVisible(false);
      buttonBox.setManaged(false);
    }));
    resultBox.getChildren().add(buttonBox);
    if (entry instanceof Task) {
      resultBox.getChildren().add(new EntryGUIElement((Task) entry).getVBox());
    } else if (entry instanceof Event) {
      resultBox.getChildren().add(new EntryGUIElement((Event) entry).getVBox());
    } else {
      throw new IllegalStateException();
    }
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
      if (entry instanceof Event) {
        new EventCreationPrompt((Event) entry, newEntry -> editElement.accept(entry, newEntry),
            updateGUI);
      } else if (entry instanceof Task) {
        new TaskCreationPrompt((Task) entry, newEntry -> editElement.accept(entry, newEntry),
            updateGUI);
      }
    });
    Button trash = new Button("\uD83D\uDDD1\uFE0F");
    trash.setOnAction((event) -> {
      takesieBacksie.accept(entry);
      updateGUI.run();
    });
    Button down = new Button("⬇\uFE0F");
    down.setOnAction((event -> {
      moveDown.accept(entry);
      updateGUI.run();
    }));
    resultBox.getChildren().addAll(up, edit, trash, down);
    resultBox.setSpacing(10);
    return resultBox;
  }


}
