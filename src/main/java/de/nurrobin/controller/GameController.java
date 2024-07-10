package de.nurrobin.controller;

import de.nurrobin.model.Game;
import de.nurrobin.model.Map;
import de.nurrobin.model.Tile;
import de.nurrobin.model.Unit;
import de.nurrobin.util.Logger;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
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

    private int tileCount;
    private int objectCount;
    private int unitCount;

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

                Tile tile = map.getTileAt(i, j);
                Unit unit = map.getUnitAt(i, j);

                // Layer 0: Background layer -> only plains or sea tiles
                ImageView background = new ImageView(tile.getBackgroundImage());
                tileCount++;
                background.setFitWidth(tileSize);
                background.setFitHeight(tileSize);
                tileStack.getChildren().add(background);

                // Layer 1: Object layer
                if (tile.hasObject()) {
                    ImageView object = new ImageView(tile.getObjectImage());
                    objectCount++;
                    object.setFitWidth(tileSize);
                    object.setFitHeight(tileSize);
                    tileStack.getChildren().add(object);
                }

                // Layer 2: Interactive layer -> units
                if (unit.getUnitID() != 36) {
                    ImageView unitImage = new ImageView(unit.getImage());
                    unitCount++;
                    unitImage.setFitWidth(tileSize);
                    unitImage.setFitHeight(tileSize);
                    tileStack.getChildren().add(unitImage);
                }

                tileStack.setLayoutX(j * tileSize);
                tileStack.setLayoutY(i * tileSize);
                gameBoard.getChildren().add(tileStack);
            }
        }
        logger.logDebug("Rendered " + tileCount + " tiles, " + objectCount + " objects and " + unitCount + " units");
    }

}
