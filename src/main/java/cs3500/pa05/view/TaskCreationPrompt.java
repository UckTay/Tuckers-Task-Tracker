package cs3500.pa05.view;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;
import java.util.function.Consumer;

public class TaskCreationPrompt extends EntryCreationPrompt {
  public TaskCreationPrompt(Consumer<Entry> addEntryToModel, Runnable updateGUI) {
    super(addEntryToModel, updateGUI);
  }

  @Override
  protected void addEntry(Consumer<Entry> addEntryToModel, Runnable updateGUI) {
    addEntryToModel.accept(new Task(Day.valueOf(dayOptions.getValue().toUpperCase()), nameField.getText(),
        descriptionField.getText(),
        TaskStatus.INCOMPLETE));
    updateGUI.run();
  }

  @Override
  protected void createPrompt() {
    dialog.setTitle("New Task");
    super.createPrompt();
  }

}
