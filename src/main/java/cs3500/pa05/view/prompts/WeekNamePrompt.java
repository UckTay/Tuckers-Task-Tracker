package cs3500.pa05.view.prompts;

import cs3500.pa05.model.Config;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * The prompt for the week name.
 */
public class WeekNamePrompt {

  private final Dialog<String> dialog = new Dialog<>();

  private final TextField weekNameField;

  private final Config config;

  /**
   * Constructs the prompt for Week Name.
   *
   * @param config the configuration containing the week name
   */
  public WeekNamePrompt(Config config) {
    this.config = config;
    dialog.setTitle("Set Week Name");

    weekNameField = config.getName() == null ? new TextField() :
        new TextField(config.getName());
    Window window = dialog.getDialogPane().getScene().getWindow();
    window.setOnCloseRequest(event -> dialog.close());
    Button doneButton = new Button("Done!");
    VBox resultBox = new VBox();
    doneButton.prefWidthProperty().bind(resultBox.widthProperty());
    resultBox.setSpacing(10);
    setDoneButton(doneButton);
    dialog.getDialogPane().setContent(resultBox);
    Label weekNameLbl = new Label("Enter Week Name:");
    dialog.getDialogPane().getStylesheets()
        .add(this.getClass().getResource("/NetflixTheme.css").toExternalForm());
    resultBox.getChildren().addAll(weekNameLbl, weekNameField, doneButton);
    dialog.showAndWait();

  }

  /**
   * Sets the done button.
   *
   * @param doneButton the done button
   */
  private void setDoneButton(Button doneButton) {
    doneButton.setOnAction(event -> {
      if (weekNameField.getText() != null) {
        config.setName(weekNameField.getText());
      }
      ((Stage) doneButton.getScene().getWindow()).close();
    });
  }


}
