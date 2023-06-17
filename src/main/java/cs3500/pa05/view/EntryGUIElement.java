//package cs3500.pa05.view;
//
//import cs3500.pa05.model.Entry;
//import cs3500.pa05.model.Event;
//import cs3500.pa05.model.Task;
//import cs3500.pa05.model.TaskStatus;
//import javafx.scene.control.CheckBox;
//import javafx.scene.control.Label;
//import javafx.scene.layout.VBox;
//
//public class EntryGUIElement {
//
//  VBox resultBox = new VBox();
//
//  public EntryGUIElement() {
//
//  }
//
//  private VBox createEntryElement(Entry e) {
//    //VBox entryElement = entryContainerFactory();
//    entryElement.getChildren().add(new Label(e.getName()));
//    if(e.getDescription() != null && !e.getDescription().equals("")) {
//      entryElement.getChildren().add(new Label(e.getDescription()));
//    }
//    return entryElement;
//  }
//
//  private VBox createEntryElement(Event event) {
//    VBox entryElement = createEntryElement((Entry) event);
//    entryElement.getChildren().add(new Label(event.getStartTime().toString()));
//    entryElement.getChildren().add(new Label(event.getDuration().toString()));
//    return entryElement;
//  }
//
//  private VBox createEntryElement(Task task) {
//    VBox entryElement = createEntryElement((Entry) task);
//    CheckBox status = new CheckBox();
//    boolean isComplete = task.getTaskStatus().equals(TaskStatus.COMPLETE);
//    status.setSelected(isComplete);
//    entryElement.getChildren().add(new Label("Status:"));
//    entryElement.getChildren().add(status);
//    return entryElement;
//  }
//}
