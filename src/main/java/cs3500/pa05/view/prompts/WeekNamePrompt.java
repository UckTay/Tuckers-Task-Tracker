package cs3500.pa05.view.prompts;

import cs3500.pa05.model.Config;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class WeekNamePrompt {

  private VBox resultBox = new VBox();

  private Dialog<String> dialog = new Dialog<>();

  private TextField weekNameField;

  private Config config;

  public WeekNamePrompt(Config config) {
    this.config = config;
    dialog.setTitle("Set Week Name");

    Label weekNameLbl = new Label("Enter Week Name:");
    weekNameField = config.getWeekNameString() == null ? new TextField() :
        new TextField(config.getWeekNameString());
    resultBox.getChildren().addAll(weekNameLbl, weekNameField);
    Window window = dialog.getDialogPane().getScene().getWindow();
    window.setOnCloseRequest(event -> dialog.close());
    Button doneButton = new Button("Done!");
    doneButton.prefWidthProperty().bind(resultBox.widthProperty());
    resultBox.setSpacing(10);
    setDoneButton(doneButton);
    dialog.getDialogPane().setContent(resultBox);
    dialog.getDialogPane().getStylesheets()
        .add(this.getClass().getResource("/NetflixTheme.css").toExternalForm());
  }

  private void setDoneButton(Button doneButton) {
    doneButton.setOnAction(event -> {
      if(weekNameField.getText() != null) {
        config.setName(weekNameField.getText());
      }
    });
  }


}
