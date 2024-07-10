package de.nurrobin.controller;

import de.nurrobin.model.Game;
import de.nurrobin.model.GameMap;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GameController {

    private final Logger logger = new Logger(GameController.class);
    private final Random random = new Random();

    private int tileCount;
    private int objectCount;
    private int unitCount;

    private Unit selectedUnit;

    @FXML
    private Pane gameBoard;

    private Game game;

    @FXML
    public void initialize() throws FileNotFoundException {
        final String randomMapName = getRandomMapName();
        logger.logInfo("Loading map: " + randomMapName);
        try {
            game = new Game(new GameMap(randomMapName));
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
        GameMap map = game.getMap();
        int tileSize = 32;
        int width = map.getWidth();
        int height = map.getHeight();

        renderLoop(height, width, tileSize, map);
        logger.logDebug("Rendered " + tileCount + " tiles, " + objectCount + " objects and " + unitCount + " units");
    }

    private void renderLoop(int height, int width, int tileSize, GameMap map) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                StackPane tileStack = new StackPane();
                tileStack.setPrefSize(tileSize, tileSize);

                Tile tile = map.getTileAt(i, j);
                Unit unit = map.getUnitAt(i, j);

                // Layer 0: Background layer -> only plains or sea tiles
                renderBackgroundLayer(tile, tileSize, tileStack);

                // Layer 1: Object layer
                renderObjectLayer(tile, tileSize, tileStack);

                // Layer 2: Movement layer -> pathfinding
                readyMovementLayer(tile, tileSize, tileStack);

                // Layer 3: Interactive layer -> units
                renderSpriteLayer(unit, tileSize, tileStack);

                tileStack.setLayoutX(j * tileSize);
                tileStack.setLayoutY(i * tileSize);
                gameBoard.getChildren().add(tileStack);
            }
        }
    }

    private void readyMovementLayer(Tile tile, int tileSize, StackPane tileStack) {
        // This Layer is meant for semi-transparent tiles to show where the player can move a clicked unit but until then it's an empty layer
        
    }

    private void renderBackgroundLayer(Tile tile, int tileSize, StackPane tileStack) {
        ImageView background = new ImageView(tile.getBackgroundImage());
        tileCount++;
        background.setFitWidth(tileSize);
        background.setFitHeight(tileSize);
        tileStack.getChildren().add(background);
    }

    private void renderObjectLayer(Tile tile, int tileSize, StackPane tileStack) {
        if (tile.hasObject()) {
            ImageView object = new ImageView(tile.getObjectImage());
            objectCount++;
            object.setFitWidth(tileSize);
            object.setFitHeight(tileSize);
            tileStack.getChildren().add(object);
        }
    }

    private void renderSpriteLayer(Unit unit, int tileSize, StackPane tileStack) {
        if (unit.getUnitCode() != 36) {
            ImageView unitImage = new ImageView(unit.getImage());
            unitCount++;
            unitImage.setFitWidth(tileSize);
            unitImage.setFitHeight(tileSize);
            tileStack.getChildren().add(unitImage);
            // Event-Handler für das Klicken auf Einheiten
            unitImage.setOnMouseClicked(event -> onUnitClicked(unit));
        }
    }

    private void onUnitClicked(Unit unit) {
        logger.logDebug("Unit clicked: " + unit.getUnitID());
        selectedUnit = unit;
        visualizeMovementOptions();
    }

    private void visualizeMovementOptions() {
        if (selectedUnit != null) {
            // Calculate reachable tiles based on the unit's position and movement ability
            List<Tile> reachableTiles = calculateReachableTiles(selectedUnit);
            for (Tile tile : reachableTiles) {
                //TODO: Implement logic to visualize reachable tiles
            }
        }
    }

    private List<Tile> calculateReachableTiles(Unit unit) {
        // Implement logic to calculate reachable tiles based on the unit's position and movement radius
        // This is a placeholder implementation
        return new ArrayList<>();
    }
}
