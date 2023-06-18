package cs3500.pa05.view;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Entry;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskStatus;
import java.util.function.Consumer;

public class TaskCreationPrompt extends EntryCreationPrompt {
  public TaskCreationPrompt(Entry entry, Consumer<Entry> addEntryToModel, Runnable updateGUI) {
    super(entry, addEntryToModel, updateGUI);
  }

  public TaskCreationPrompt(Consumer<Entry> addEntryToModel, Runnable updateGUI) {
    this(null, addEntryToModel, updateGUI);
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

}
