package de.nurrobin.controller;

import de.nurrobin.enums.MovementType;
import de.nurrobin.enums.TerrainType;
import de.nurrobin.model.Game;
import de.nurrobin.model.GameMap;
import de.nurrobin.model.Tile;
import de.nurrobin.model.Unit;
import de.nurrobin.util.Logger;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class GameController {
    private List<int[]> directions = Arrays.asList(
        new int[]{-1, 0}, // Up
        new int[]{1, 0},  // Down
        new int[]{0, -1}, // Left
        new int[]{0, 1}   // Right
    );

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
        // This Layer is meant for semi-transparent tiles to show where the player can
        // move a clicked unit but until then it's an empty layer

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
            // Clear existing movement overlays
            clearMovementOverlays();
    
            List<int[]> reachableTiles = calculateReachableTiles(selectedUnit);
            
            int mapWidth = game.getMap().getWidth();
    
            for (int[] tileCoords : reachableTiles) {
                int tileX = tileCoords[0];
                int tileY = tileCoords[1];
    
                // Calculate the position in the gameBoard pane
                int index = tileY * mapWidth + tileX;
                StackPane tileStack = (StackPane) gameBoard.getChildren().get(index);
                renderMovementLayer(game.getMap().getTileAt(tileX, tileY), tileStack);
            }
        }
    }

    private void clearMovementOverlays() {
        for (Node node : gameBoard.getChildren()) {
            if (node instanceof StackPane) {
                StackPane stackPane = (StackPane) node;
                List<Node> overlaysToRemove = new ArrayList<>();
                for (Node child : stackPane.getChildren()) {
                    if (child instanceof Pane && child.getStyle().contains("rgba(0, 0, 255, 0.2)")) {
                        overlaysToRemove.add(child);
                    }
                }
                stackPane.getChildren().removeAll(overlaysToRemove);
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

    public List<int[]> calculateReachableTiles(Unit unit) {
        int startX = unit.getX();
        int startY = unit.getY();
        int mapWidth = game.getMap().getWidth();
        int mapHeight = game.getMap().getHeight();
        boolean[][] visited = new boolean[mapWidth][mapHeight];
        List<int[]> reachableTiles = new ArrayList<>();
        Queue<int[]> queue = new LinkedList<>();
        int movementPoints = unit.getUnitType().getMovementRadius();
        queue.add(new int[]{startX, startY, movementPoints});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int remainingMovementPoints = current[2];

            if (remainingMovementPoints < 0 || visited[x][y]) {
                continue;
            }

            visited[x][y] = true;
            reachableTiles.add(new int[]{x, y});

            for (int[] direction : directions) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (isValidTile(newX, newY) && !visited[newX][newY]) {
                    int cost = getMovementCost(unit, newX, newY);
                    if (remainingMovementPoints - cost >= 0) {
                        queue.add(new int[]{newX, newY, remainingMovementPoints - cost});
                    }
                }
            }
        }

        return reachableTiles;
    }

    private boolean isValidTile(int x, int y) {
        // Checks if the tile is within map bounds
        int mapWidth = game.getMap().getWidth();
        int mapHeight = game.getMap().getHeight();
        return x >= 0 && x < mapWidth && y >= 0 && y < mapHeight;
    }

    private int getMovementCost(Unit unit, int x, int y) {
        // Calculates the movement cost based on the terrain type
        TerrainType terrainType = game.getMap().getTileAt(x, y).getTerrain().getType();
        MovementType movementType = unit.getMovementType();
        return unit.getMovementCostForTerrainType(terrainType, movementType);
    }
}