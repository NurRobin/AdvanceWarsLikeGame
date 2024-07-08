package de.nurrobin.controller;

import de.nurrobin.model.Game;
import de.nurrobin.model.Map;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class GameController {

    @FXML
    private Pane gameBoard;

    private Game game;

    @FXML
    public void initialize() {
        final String randomMapName = getRandomMapName();
        try {
            game = new Game(new Map(randomMapName));
            renderGameBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRandomMapName() {
        // Return a random map name from the maps directory that ends with .map
        File mapsDirectory = new File(getClass().getResource("/maps").getFile());
        File[] mapFiles = mapsDirectory.listFiles((dir, name) -> name.endsWith(".map"));
        if (mapFiles != null && mapFiles.length > 0) {
            Random random = new Random();
            int randomIndex = random.nextInt(mapFiles.length);
            return mapFiles[randomIndex].getName();
        } else {
            throw new RuntimeException("No map files found in the maps directory");
        }
    }

    private void renderGameBoard() {
        Map map = game.getMap();
        int tileSize = 32;
        int width = map.getWidth();
        int height = map.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                StackPane tileStack = new StackPane();
                tileStack.setPrefSize(tileSize, tileSize);

                int tile = map.getTiles()[i][j];

                // Layer 0: Background layer
                ImageView background = new ImageView(getTileImage(tile, 0));
                background.setFitWidth(tileSize);
                background.setFitHeight(tileSize);
                tileStack.getChildren().add(background);

                // Layer 1: Object layer
                ImageView object = new ImageView(getTileImage(tile, 1));
                object.setFitWidth(tileSize);
                object.setFitHeight(tileSize);
                tileStack.getChildren().add(object);

                // Layer 2: Interactive layer
                ImageView interactive = new ImageView(getTileImage(tile, 2));
                interactive.setFitWidth(tileSize);
                interactive.setFitHeight(tileSize);
                tileStack.getChildren().add(interactive);

                tileStack.setLayoutX(j * tileSize);
                tileStack.setLayoutY(i * tileSize);
                gameBoard.getChildren().add(tileStack);
            }
        }
    }

    private Image getTileImage(int tile, int layer) {
        String imagePath = "/tiles/" + layer + "/" + tile + ".png";
        try {
            return new Image(new FileInputStream(imagePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
