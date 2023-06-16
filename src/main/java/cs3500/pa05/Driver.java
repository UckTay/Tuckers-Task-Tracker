package cs3500.pa05;

import cs3500.pa05.controller.Controller;
import cs3500.pa05.controller.JournalController;
import cs3500.pa05.model.BujoWriter;
import cs3500.pa05.model.JournalModel;
import cs3500.pa05.view.GUIView;
import cs3500.pa05.view.GUIViewImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application {
  public static void main(String[] args) {
    launch();
  }

  /**
   * The main entry point for all JavaFX applications.
   * The start method is called after the init method has returned,
   * and after the system is ready for the application to begin running.
   *
   * <p>
   * NOTE: This method is called on the JavaFX Application Thread.
   * </p>
   *
   * @param primaryStage the primary stage for this application, onto which
   *                     the application scene can be set.
   *                     Applications may create other stages, if needed, but they will not be
   *                     primary stages.
   * @throws Exception if something goes wrong
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    JournalModel model = new JournalModel();
    GUIView view = new GUIViewImpl();
    Controller controller = new JournalController(view, model);
    primaryStage.setScene(view.load(controller));
    controller.run();
    primaryStage.show();
  }
}
