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
import java.net.URISyntaxException;
import java.util.*;

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
    public void initialize() throws FileNotFoundException, URISyntaxException {
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
            List<Tile> reachableTiles = calculateReachableTiles(selectedUnit);

            // Get the position of the selected unit
            int unitX = selectedUnit.getX();
            int unitY = selectedUnit.getY();

            int movementRadius = selectedUnit.getMovementRadius();
            int mapWidth = game.getMap().getWidth();

            for (Tile tile : reachableTiles) {
                int tileX = tile.getX();
                int tileY = tile.getY();

                // Calculate the position in the gameBoard pane
                int offsetX = tileX - unitX;
                int offsetY = tileY - unitY;

                // Adjust tileStack position to center the movement range around the unit
                int stackIndex = (unitY + offsetY) * mapWidth + (unitX + offsetX);
                if (stackIndex >= 0 && stackIndex < gameBoard.getChildren().size()) {
                    StackPane tileStack = (StackPane) gameBoard.getChildren().get(stackIndex);
                    renderMovementLayer(tile, tileStack);
                }
            }
        }
    }

    private void renderMovementLayer(Tile tile, StackPane tileStack) {
        // Create a semi-transparent overlay to show reachable tiles
        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 255, 0.2);"); // Semi-transparent blue overlay
        overlay.setPrefSize(tileStack.getPrefWidth(), tileStack.getPrefHeight());
        tileStack.getChildren().add(overlay);
    }

    private List<Tile> calculateReachableTiles(Unit unit) {


        List<Tile> reachableTiles = new ArrayList<>();
        int movementRadius = unit.getMovementRadius();
        int startX = unit.getX();
        int startY = unit.getY();

        boolean[][] visited = new boolean[game.getMap().getHeight()][game.getMap().getWidth()];

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY, 0});
        visited[startY][startX] = true;
        logger.logDebug(startX + " x  coordinaten Unit  y "+ startY);

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int distance = current[2];

            //logger.logDebug(x + " x 0  current 1 y "+ y);

            if (distance > movementRadius) continue;

            //logger.logDebug("distance: " + distance + "> movementRadius" + movementRadius);

            reachableTiles.add(game.getMap().getTileAt(y, x));


            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (isValidPosition(newX, newY) && !visited[newY][newX]) {
                    queue.add(new int[]{newX, newY, distance + 1});
                    visited[newY][newX] = false;

                    // Update position of units if needed
                    Unit newUnit = game.getMap().getUnitAt(newY, newX);
                    if (newUnit != null) {
                        newUnit.setX(newX);
                        newUnit.setY(newY);
                    }
                }
            }

        }

        return reachableTiles;

    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < game.getMap().getWidth() &&
                y >= 0 && y < game.getMap().getHeight();
    }

}
