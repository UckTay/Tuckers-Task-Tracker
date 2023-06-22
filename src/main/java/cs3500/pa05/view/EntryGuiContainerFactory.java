package cs3500.pa05.view;

import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * An Entry GUI Factory.
 */
public class EntryGuiContainerFactory {
  private final Consumer<Entry> moveUp;
  private final Consumer<Entry> moveDown;
  private final Consumer<Entry> takesieBacksie;
  private final Consumer<Event> createEventPrompt;
  private final Consumer<Task> createTaskPrompt;
  private final BiConsumer<Entry, Entry> editElement;
  private final Runnable updateGui;

  /**
   * Constructs an Entry GUI Factory.
   *
   * @param updateGui the GUI Updater
   * @param createEventPrompt the function to create an event prompt
   * @param createTaskPrompt the function to create a task prompt
   * @param moveUp the function to move an entry up the display
   * @param moveDown the function to move an entry down the display
   * @param editElement the function that allows the user to edit an element
   * @param takesieBacksie the function that allows the user to remove an entry
   */
  public EntryGuiContainerFactory(Runnable updateGui, Consumer<Event> createEventPrompt,
                                  Consumer<Task> createTaskPrompt, Consumer<Entry> moveUp,
                                  Consumer<Entry> moveDown, BiConsumer<Entry, Entry> editElement,
                                  Consumer<Entry> takesieBacksie) {
    this.moveUp = moveUp;
    this.moveDown = moveDown;
    this.editElement = editElement;
    this.takesieBacksie = takesieBacksie;
    this.createEventPrompt = createEventPrompt;
    this.createTaskPrompt = createTaskPrompt;
    this.updateGui = updateGui;
  }

  /**
   * Creates a container for a task.
   *
   * @param task the given task
   * @return the container for the task
   */
  public VBox createContainer(Task task) {
    return createContainerHelper(task);
  }

  /**
   * Creates a container for an event.
   *
   * @param event the given event
   * @return the container for the event
   */
  public VBox createContainer(Event event) {
    return createContainerHelper(event);
  }

  /**
   * Creates a container of an entry.
   *
   * @param entry the given entry
   * @param <T> entry type
   * @return the container for the entry
   */
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
      resultBox.getChildren().add(new EntryGuiElement((Task) entry).getVbox());
    } else if (entry instanceof Event) {
      resultBox.getChildren().add(new EntryGuiElement((Event) entry).getVbox());
    } else {
      throw new IllegalStateException();
    }
    resultBox.setBorder(Border.stroke(Color.BLACK));
    resultBox.getStyleClass().add("containerBorder");
    return resultBox;
  }

  /**
   * creates the buttons for an entry.
   *
   * @param entry the given entry
   * @return the HBox containing the buttons
   */
  private HBox createButtons(Entry entry) {
    Button up =  getImageButton("up.png");
    up.setOnAction((event) -> {
      moveUp.accept(entry);
      updateGui.run();
    });
    Button edit = getImageButton("pencil.png");
    edit.setOnAction((event) -> {
      if (entry instanceof Event) {
        createEventPrompt.accept((Event) entry);
      } else if (entry instanceof Task) {
        createTaskPrompt.accept((Task) entry);
      }
    });
    Button trash =  getImageButton("trash.png");
    trash.setOnAction((event) -> {
      takesieBacksie.accept(entry);
      updateGui.run();
    });
    Button down = getImageButton("down.png");
    down.setOnAction((event -> {
      moveDown.accept(entry);
      updateGui.run();
    }));
    HBox resultBox = new HBox();
    resultBox.getChildren().addAll(up, edit, trash, down);
    resultBox.setSpacing(10);
    return resultBox;
  }

  /**
   * Adds an image to a button.
   *
   * @param imagePath the path to the image
   * @return the button with the image
   */
  private Button getImageButton(String imagePath) {
    Button button = new Button();
    ImageView imageView =
        new ImageView(new Image(this.getClass().getResource("/"
            + imagePath).toExternalForm()));
    imageView.fitWidthProperty().set(20);
    imageView.fitHeightProperty().set(20);
    button.setGraphic(imageView);
    return button;
  }
}
