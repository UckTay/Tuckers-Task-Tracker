package cs3500.pa05.view.prompts;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Creates a Task Prompt.
 */
public class TaskCreationPrompt extends EntryCreationPrompt {

  /**
   * constructs an instance of Task Creation Prompt.
   *
   * @param entry the old entry
   * @param addEntryToModel the function that adds the entry to the model
   * @param isUnderLimit the function that checks if adding the task will go over the limit
   * @param updateGUI updates the GUI
   */
  public TaskCreationPrompt(Entry entry, Consumer<Entry> addEntryToModel, Function<Day, Boolean> isUnderLimit, Runnable updateGUI) {
    super(entry, addEntryToModel, isUnderLimit, updateGUI);
  }

  /**
   * constructs an instance of Task Creation Prompt.
   *
   * @param addEntryToModel the function that adds the entry to the model
   * @param isUnderLimit the function that checks if adding the task will go over the limit
   * @param updateGUI updates the GUI
   */
  public TaskCreationPrompt(Consumer<Entry> addEntryToModel, Function<Day, Boolean> isUnderLimit, Runnable updateGUI) {
    this(null, addEntryToModel, isUnderLimit, updateGUI);
  }

  @Override
  protected void addEntry(Consumer<Entry> addEntryToModel, Runnable updateGUI) {
    addEntryToModel.accept(new Task(Day.valueOf(dayOptions.getValue().toUpperCase()), nameField.getText(),
        descriptionField.getText(),
        TaskStatus.INCOMPLETE));
    updateGUI.run();
  }

  @Override
  protected void createPrompt(Entry task) {
    dialog.setTitle("New Task");
    super.createPrompt(task);
  }

  /**
   * Sets the functionality of the done button.
   *
   * @param doneButton the done button
   * @param addEntryToModel the function that adds the entry to the model
   * @param updateGUI updates the GUI
   * @param isUnderLimit the function that checks if adding the task won't go over the limit
   */
  protected void setDoneButton(Button doneButton, Consumer<Entry> addEntryToModel,
                               Runnable updateGUI, Function<Day, Boolean> isUnderLimit) {
    doneButton.setOnAction(event -> {
      Day day;
      try {
       day = Day.valueOf(dayOptions.getValue().toUpperCase());
      } catch (IllegalArgumentException e) {
        return;
      }

      if (!nameField.getText().equals("") && Arrays.stream(Day.values()).toList()
          .contains(day) && isUnderLimit.apply(day)) {
        ((Stage) doneButton.getScene().getWindow()).close();
        addEntry(addEntryToModel, updateGUI);
      }
    });
  }

}
