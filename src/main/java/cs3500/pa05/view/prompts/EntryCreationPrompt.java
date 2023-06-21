package cs3500.pa05.view.prompts;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Entry;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public abstract class EntryCreationPrompt {

  protected VBox resultBox = new VBox();

  protected Dialog<String> dialog = new Dialog<>();

  protected TextField nameField = new TextField();
  protected TextField descriptionField = new TextField();
  protected ChoiceBox<String> dayOptions = new ChoiceBox<>();

  /**
   * Constructs an instance of the Entry Creation Prompt.
   *
   * @param entry the old entry
   * @param addEntryToModel the function that adds the entry to the model
   * @param isUnderLimit the function that checks to see if adding the entry will go over the limit
   * @param updateGUI the GUI Updater
   */
  public EntryCreationPrompt(Entry entry, Consumer<Entry> addEntryToModel, Function<Day, Boolean> isUnderLimit, Runnable updateGUI) {
    createPrompt(entry);
    Button doneButton = new Button("Done!");
    doneButton.prefWidthProperty().bind(resultBox.widthProperty());
    resultBox.getChildren().add(doneButton);
    resultBox.setSpacing(10);
    setDoneButton(doneButton, addEntryToModel, updateGUI, isUnderLimit);
    dialog.getDialogPane().setContent(resultBox);
    dialog.getDialogPane().getScene().getStylesheets()
        .add(getClass().getResource("/NetflixTheme.css").toExternalForm());
    dayOptions.getStylesheets().add(getClass().getResource("/NetflixTheme.css").toExternalForm());
    resultBox.getStylesheets().add(getClass().getResource("/NetflixTheme.css").toExternalForm());
    dialog.showAndWait();
  }

  /**
   * Constructs an instance of the Entry Creation Prompt.
   *
   * @param addEntryToModel the function that adds the entry to the model
   * @param isUnderLimit the function that checks to see if adding the entry will go over the limit
   * @param updateGUI the GUI Updater
   */
  public EntryCreationPrompt(Consumer<Entry> addEntryToModel, Function<Day, Boolean> isUnderLimit, Runnable updateGUI) {
    this(null, addEntryToModel, isUnderLimit, updateGUI);
  }

  /**
   * Creates the dialog prompt.
   *
   * @param entry if an entry is being modified the entry, otherwise null
   */
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
    dayBox.getStylesheets().add(getClass().getResource("/NetflixTheme.css").toExternalForm());
    resultBox.getChildren().add(dayBox);
    Window window = dialog.getDialogPane().getScene().getWindow();
    window.setOnCloseRequest(event -> dialog.close());
  }

  /**
   * Sets the functionality of the done button.
   *
   * @param doneButton the done button
   * @param addEntryToModel the function that adds the entry to the model
   * @param updateGUI updates the GUI
   * @param isUnderLimit the function that checks if adding the task won't go over the limit
   */
  protected abstract void setDoneButton(Button doneButton, Consumer<Entry> addEntryToModel,
                                        Runnable updateGUI, Function<Day, Boolean> isUnderLimit);

  /**
   * Adds the new entry.
   *
   * @param addEntryToModel the function to add the entry to the model
   * @param updateGUI the GUI Updater
   */
  protected abstract void addEntry(Consumer<Entry> addEntryToModel, Runnable updateGUI);

}
