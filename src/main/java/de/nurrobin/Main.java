package de.nurrobin;

import de.nurrobin.util.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private final Logger logger = new Logger(Main.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.logDebug("Loading FXML file...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        logger.logDebug("Starting application...");
        primaryStage.setTitle("Advance Wars-like Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
