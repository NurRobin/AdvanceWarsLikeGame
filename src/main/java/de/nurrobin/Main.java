package de.nurrobin;

import de.nurrobin.util.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class for the Advance Wars-like game application. It initializes the JavaFX application.
 */
public class Main extends Application {
    private final Logger logger = new Logger(Main.class);

    /**
     * Starts the JavaFX application by loading the main view from an FXML file and setting up the primary stage.
     *
     * @param primaryStage The primary stage for this application, onto which the application scene can be set.
     * @throws Exception if there is any issue loading the FXML file.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.logDebug("Loading FXML file...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        Scene scene = new Scene(loader.load()); // Set scene dimensions
        logger.logDebug("Starting application...");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Advance Wars-like Game");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * The main entry point for all JavaFX applications. The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * @param args The command line arguments passed to the application. Not used in this application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
