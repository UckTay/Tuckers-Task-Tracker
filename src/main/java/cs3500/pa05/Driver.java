package cs3500.pa05;

import cs3500.pa05.controller.Controller;
import cs3500.pa05.controller.JournalController;
import cs3500.pa05.model.JournalModel;
import cs3500.pa05.view.GuiView;
import cs3500.pa05.view.GuiViewImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The driver of the program.
 */
public class Driver extends Application {
  /**
   * The entry point of the program.
   *
   * @param args program arguments
   */
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
   */
  @Override
  public void start(Stage primaryStage) {
    JournalModel model = new JournalModel();
    GuiView view = new GuiViewImpl();
    Controller controller = new JournalController(view, model);
    Scene scene = view.load(controller);
    primaryStage.setScene(scene);
    primaryStage.show();
    controller.run();
  }
}
