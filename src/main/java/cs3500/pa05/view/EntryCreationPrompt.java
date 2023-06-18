package cs3500.pa05.view;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public abstract class EntryCreationPrompt {

  protected VBox resultBox = new VBox();

  protected Dialog<String> dialog = new Dialog<>();

  private Button doneButton = new Button("Done!");

  protected TextField nameField = new TextField();
  protected TextField descriptionField = new TextField();
  protected ChoiceBox<String> dayOptions = new ChoiceBox<>();


  public EntryCreationPrompt(Entry entry, Consumer<Entry> addEntryToModel, Runnable updateGUI) {
    createPrompt(entry);
    doneButton.prefWidthProperty().bind(resultBox.widthProperty());
    resultBox.getChildren().add(doneButton);
    resultBox.setSpacing(10);
    setDoneButton(doneButton, addEntryToModel, updateGUI);
    dialog.getDialogPane().setContent(resultBox);
    dialog.showAndWait();
  }

  public EntryCreationPrompt(Consumer<Entry> addEntryToModel, Runnable updateGUI) {
    this(null, addEntryToModel, updateGUI);
  }

  protected void createPrompt(Entry entry) {
    Label nameLbl = new Label("Name:");
    resultBox.getChildren().add(nameLbl);
    if (entry != null) {
      nameField = new TextField(entry.getName());
    }
    resultBox.getChildren().add(nameField);
    Label descriptionLbl = new Label("Description:");
    resultBox.getChildren().add(descriptionLbl);
    if (entry != null) {
      descriptionField = new TextField(entry.getDescription());
    }
    resultBox.getChildren().add(descriptionField);
    dayOptions.setValue("");
    List<String> optionsList = new ArrayList<>();
    optionsList.add("");
    for (Day day : Day.values()) {
      optionsList.add(day.toString());
    }
    dayOptions.setItems(FXCollections.observableArrayList(optionsList));
    if (entry != null) {
      dayOptions.setValue(entry.getDayOfTheWeek().toString());
    }
    Label dayField = new Label("Day:");
    HBox dayBox = new HBox();
    dayBox.prefWidthProperty().bind(resultBox.widthProperty());
    dayBox.getChildren().addAll(dayField, dayOptions);
    dayBox.setAlignment(Pos.CENTER_LEFT);
    dayBox.setSpacing(10);
    resultBox.getChildren().add(dayBox);
    Window window = dialog.getDialogPane().getScene().getWindow();
    window.setOnCloseRequest(event -> dialog.close());
  }

  protected void setDoneButton(Button doneButton, Consumer<Entry> addEntryToModel,
                               Runnable updateGUI) {
    doneButton.setOnAction(event -> {
      if (!nameField.getText().equals("") && Arrays.stream(Day.values()).toList()
          .contains(Day.valueOf(dayOptions.getValue().toUpperCase()))) {
        ((Stage) doneButton.getScene().getWindow()).close();
        addEntry(addEntryToModel, updateGUI);
      }
    });
  }

  protected abstract void addEntry(Consumer<Entry> addEntryToModel, Runnable updateGUI);

}
