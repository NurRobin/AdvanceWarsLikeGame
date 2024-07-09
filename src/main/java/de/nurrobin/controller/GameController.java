package de.nurrobin.controller;

import de.nurrobin.model.Game;
import de.nurrobin.model.Map;
import de.nurrobin.util.Logger;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class GameController {

    private final Logger logger = new Logger(GameController.class);
    private final Random random = new Random();

    @FXML
    private Pane gameBoard;

    private Game game;

    @FXML
    public void initialize() throws FileNotFoundException {
        final String randomMapName = getRandomMapName();
        logger.logInfo("Loading map: " + randomMapName);
        try {
            game = new Game(new Map(randomMapName));
            renderGameBoard();
        } catch (IOException e) {
            logger.logException(e);
        }
    }

    private String getRandomMapName() throws FileNotFoundException {
        File mapsDirectory = new File(Objects.requireNonNull(getClass().getResource("/maps")).getFile());
        File[] mapFiles = mapsDirectory.listFiles((dir, name) -> name.endsWith(".map"));
        if (mapFiles != null && mapFiles.length > 0) {
            int randomIndex = random.nextInt(mapFiles.length);
            return mapFiles[randomIndex].getName();
        } else {
            throw new FileNotFoundException("No map files found in the maps directory");
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

                // Layer 0: Background layer -> only plains or sea tiles
                ImageView background = new ImageView(getTileImage(tile, 0));
                background.setFitWidth(tileSize);
                background.setFitHeight(tileSize);
                tileStack.getChildren().add(background);

                // Layer 1: Object layer
                ImageView object = new ImageView(getTileImage(tile, 1));
                object.setFitWidth(tileSize);
                object.setFitHeight(tileSize);
                tileStack.getChildren().add(object);

                // Layer 2: Interactive layer -> units
                //TODO: Add logic to render units on the interactive layer

                tileStack.setLayoutX(j * tileSize);
                tileStack.setLayoutY(i * tileSize);
                gameBoard.getChildren().add(tileStack);
            }
        }
    }

    private Image getTileImage(int tile, int layer) {
        // Switch statement to return the correct image based on the tile and layer
        switch (layer) {
            case 0:
                if (tile != 3 && tile != 5) {
                    return new Image("/tiles/textures/plain.png");
                } else {
                    return new Image("/tiles/textures/sea.png");
                }
            case 1:
                return switch (tile) {
                    case 0 -> new Image("/tiles/textures/plain.png");
                    case 1 -> new Image("/tiles/textures/wood.png");
                    case 2 -> new Image("/tiles/textures/mountain.png");
                    case 3 -> new Image("/tiles/textures/sea.png");
                    case 4, 5 -> new Image("/tiles/textures/road.png");
                    case 7 -> new Image("/buildings/textures/HQ-Orange.png");
                    case 8 -> new Image("/buildings/textures/HQ-Blue.png");
                    case 9 -> new Image("/buildings/textures/City-Neutral.png");
                    case 10 ->
                        //TODO: Return the correct image for City Orange Star
                            new Image("/buildings/textures/City-Neutral.png");
                    case 11 ->
                        //TODO: Return the correct image for City Blue Moon
                            new Image("/buildings/textures/City-Neutral.png");
                    case 12 -> new Image("/buildings/textures/Base-Neutral.png");
                    case 13 -> new Image("/buildings/textures/Base-Orange.png");
                    case 14 -> new Image("/buildings/textures/Base-Blue.png");
                    case 15 -> new Image("/buildings/textures/Airport-Neutral.png");
                    case 16 ->
                        //TODO: Return the correct image for Airport Orange Star when we have the texture for Airport Blue
                            new Image("/buildings/textures/Airport-Neutral.png");
                    //return new Image("/buildings/textures/Airport-Orange.png");
                    case 17 ->
                        //TODO: Return the correct image for Airport Blue Moon
                            new Image("/buildings/textures/Airport-Neutral.png");
                    //return new Image("/buildings/textures/Airport-Blue.png");
                    case 18 -> new Image("/buildings/textures/Port-Neutral.png");
                    case 19 ->
                        //TODO: Return the correct image for Port Orange Star
                            new Image("/buildings/textures/Port-Neutral.png");
                    case 20 ->
                        //TODO: Return the correct image for Port Blue Moon
                            new Image("/buildings/textures/Port-Neutral.png");
                    default -> throw new IllegalStateException("Unexpected tile value: " + tile);
                };
            default:
                throw new IllegalArgumentException("Unknown or unsopported layer: " + layer);
        }
    }
}
