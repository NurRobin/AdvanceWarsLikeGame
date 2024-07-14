package de.nurrobin.controller;

import de.nurrobin.enums.MovementType;
import de.nurrobin.enums.SelectedOrder;
import de.nurrobin.enums.TerrainType;
import de.nurrobin.model.Game;
import de.nurrobin.model.GameMap;
import de.nurrobin.model.Tile;
import de.nurrobin.model.Unit;
import de.nurrobin.persistor.TilePersistor;
import de.nurrobin.persistor.UnitPersistor;
import de.nurrobin.util.Logger;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
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

    private int tileindex = 0;
    private int tileCount = 0;
    private int objectCount;
    private int unitCount;

    private SelectedOrder selectedOrder;

    private Unit selectedUnit;

    @FXML
    private Pane gameBoard;

    @FXML
    private Label roundLabel;

    @FXML
    private Label mapNameLabel;

    @FXML
    private Label unitHealthLabel;

    @FXML
    private Label unitMovementLabel;

    @FXML
    private Button endTurnButton;

    private int currentRound = 1;

    private Game game;

    TilePersistor tilepersistor = TilePersistor.getInstance();
    UnitPersistor unitPersistor = UnitPersistor.getInstance();

    /**
     * Initializes the game controller and loads a random map to start the game.
     * This method is automatically called after the FXML fields have been injected.
     *
     * @throws FileNotFoundException If the maps directory does not exist or is empty.
     * @throws URISyntaxException If the URI syntax is incorrect.
     */
    @FXML
    public void initialize() throws FileNotFoundException, URISyntaxException {
        final String randomMapName = getRandomMapName();
        logger.logInfo("Loading map: " + randomMapName);
        try {
            game = new Game(new GameMap(randomMapName));
            initGameBoard();
            updateRoundLabel();
        } catch (IOException e) {
            logger.logException(e);
        }
    }

    /**
     * Updates the round label to reflect the current round.
     */
    private void updateRoundLabel() {
        roundLabel.setText("Round: " + currentRound);
    }

    /**
     * Ends the current turn and starts a new round.
     */
    @FXML
    private void endTurn() {
        currentRound++;
        updateRoundLabel();
        // Weitere Logik für den Rundenwechsel
        onTurnEnd();
    }

    private void onTurnEnd() {
        // Aktualisieren Sie die Spielzustände und Einheiten für die neue Runde
        // Beispiel: Reset der Bewegungspunkte der Einheiten
        for (Unit unit : unitPersistor.getUnits()) {
            unit.resetMovementPoints();
        }
        updateGameBoard();
    }

    /**
     * Selects a random map name from the available map files in the /maps directory.
     *
     * @return The name of the randomly selected map file.
     * @throws FileNotFoundException If no map files are found in the maps directory.
     */
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

    /**
     * Renders the game board by creating visual representations of tiles, objects, and units.
     */
    private void initGameBoard() {
        GameMap map = game.getMap();
        int tileSize = 32;
        int width = map.getWidth();
        int height = map.getHeight();

        renderLoop(height, width, tileSize, map);
        logger.logInfo("Rendered " + tileCount + " tiles, " + objectCount + " objects and " + unitCount + " units");
        logger.logDebug("UnitPersistor size: " + unitPersistor.getUnits().size());
        logger.logDebug("TilePersistor size: " + tilepersistor.getTiles().size());
    }

    /**
     * The main loop for rendering the game board. It iterates through each tile and renders
     * the background, objects, movement options, and units.
     *
     * @param height The height of the game map in tiles.
     * @param width The width of the game map in tiles.
     * @param tileSize The size of each tile in pixels.
     * @param map The game map to render.
     */
    private void renderLoop(int height, int width, int tileSize, GameMap map) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                StackPane tileStack = new StackPane();
                tileStack.setPrefSize(tileSize, tileSize);

                Tile tile = map.createTileAt(x, y, tileindex);
                Unit unit = map.createUnitAt(x, y, tileindex);
                tileindex++;

                // Layer 0: Background layer -> only plains or sea tiles
                renderBackgroundLayer(tile, tileSize, tileStack);

                // Layer 1: Object layer
                renderObjectLayer(tile, tileSize, tileStack);

                // Layer 3: Interactive layer -> units
                renderSpriteLayer(unit, tileSize, tileStack);

                tileStack.setLayoutX(x * tileSize);
                tileStack.setLayoutY(y * tileSize);
                gameBoard.getChildren().add(tileStack);
            }
        }
    }

    /**
     * Renders the background layer of a tile. This includes the base terrain of the tile.
     *
     * @param tile The tile to render the background for.
     * @param tileSize The size of the tile in pixels.
     * @param tileStack The stack pane representing the current tile.
     */
    private void renderBackgroundLayer(Tile tile, int tileSize, StackPane tileStack) {
        ImageView background = new ImageView(tile.getBackgroundImage());
        background.setFitWidth(tileSize);
        background.setFitHeight(tileSize);
        tileCount++;
        tileStack.getChildren().add(background);
    }

    /**
     * Renders the object layer of a tile. This includes any objects that are present on the tile,
     * such as trees or buildings.
     *
     * @param tile The tile to render objects for.
     * @param tileSize The size of the tile in pixels.
     * @param tileStack The stack pane representing the current tile.
     */
    private void renderObjectLayer(Tile tile, int tileSize, StackPane tileStack) {
        if (tile.hasObject()) {
            ImageView object = new ImageView(tile.getObjectImage());
            object.setFitWidth(tileSize);
            object.setFitHeight(tileSize);
            objectCount++;
            tileStack.getChildren().add(object);
        }
    }

    /**
     * Renders the sprite layer of a tile, which includes units. This method also sets up
     * event handlers for clicking on units.
     *
     * @param unit The unit to render.
     * @param tileSize The size of the tile in pixels.
     * @param tileStack The stack pane representing the current tile.
     */
    private void renderSpriteLayer(Unit unit, int tileSize, StackPane tileStack) {
        if (unit.getUnitCode() != 36) {
            ImageView unitImage = new ImageView(unit.getImage());
            unitImage.setFitWidth(tileSize);
            unitImage.setFitHeight(tileSize);
            tileStack.getChildren().add(unitImage);
            unitCount++;
            // Event-Handler für das Klicken auf Einheiten
            unitImage.setOnMouseClicked(event -> onUnitClicked(unit));
        }
    }

    /**
     * Handles the event when a unit is clicked. Sets the selected unit and visualizes
     * movement options for that unit.
     *
     * @param unit The unit that was clicked.
     */
    private void onUnitClicked(Unit unit) {
        logger.logDebug("Unit clicked: " + unit.getUnitID());
        selectedUnit = unit;
        updateUnitInfo();
        visualizeMovementOptions();
    }

    /**
     * Updates the unit info labels to reflect the currently selected unit.
     */
    private void updateUnitInfo() {
        if (selectedUnit != null) {
            unitHealthLabel.setText("Health: " + selectedUnit.getHealth() + "/" + selectedUnit.getMaxHealth());
            unitMovementLabel.setText("Movement: " + selectedUnit.getMovementPoints() + "/" + selectedUnit.getMaxMovementPoints());
        }
    }

    /**
     * Visualizes the movement options for the selected unit by highlighting reachable tiles.
     */
    private void visualizeMovementOptions() {
        if (selectedUnit != null) {
            // Clear existing movement overlays
            clearMovementOverlays();
    
            List<int[]> reachableTiles = calculateReachableTiles(selectedUnit);
    
            for (int[] tileCoords : reachableTiles) {
                int tileX = tileCoords[0];
                int tileY = tileCoords[1];
                
                
                if (tilepersistor.getTileAtPosition(tileX, tileY) == null) {
                    continue;
                }

                // Calculate the position in the gameBoard pane
                Tile tile = tilepersistor.getTileAtPosition(tileX, tileY);
                int index = tile.getIndex();
                StackPane tileStack = (StackPane) gameBoard.getChildren().get(index);
                renderMovementLayer(tilepersistor.getTileAtPosition(tileX, tileY), tileStack);
            }
        }
    }

    /**
     * Clears any existing movement overlays from the game board.
     */
    private void clearMovementOverlays() {
        for (Node node : gameBoard.getChildren()) {
            if (node instanceof StackPane) {
                StackPane stackPane = (StackPane) node;
                List<Node> overlaysToRemove = new ArrayList<>();
                for (Node child : stackPane.getChildren()) {
                    if (child instanceof Pane && (child.getStyle().contains("rgba(0, 0, 255, 0.3)") || child.getStyle().contains("rgba(255, 0, 0, 0.3)"))) {
                        overlaysToRemove.add(child);
                    }
                }
                stackPane.getChildren().removeAll(overlaysToRemove);
            }
        }
    }

    /**
     * Renders a semi-transparent overlay on a tile to indicate it is within the movement range
     * of the selected unit.
     *
     * @param tile The tile to render the movement overlay for.
     * @param tileStack The stack pane representing the current tile.
     */
    private void renderMovementLayer(Tile tile, StackPane tileStack) {
        // Create a semi-transparent overlay to show reachable tiles
        Pane overlay = new Pane();
        Boolean doesTileHaveFriendlyUnit = doesTileHaveFriendlyUnit(tile.getX(), tile.getY());
        Boolean hasUnit = doesTileHaveUnit(tile.getX(), tile.getY());
        Boolean isOwnTile = selectedUnit.getX() == tile.getX() && selectedUnit.getY() == tile.getY();
        if (hasUnit && !doesTileHaveFriendlyUnit && !isOwnTile) {
            overlay.setStyle("-fx-background-color: rgba(255, 0, 0, 0.3);");
        }
        if (!hasUnit && !doesTileHaveFriendlyUnit && !isOwnTile) {
            overlay.setStyle("-fx-background-color: rgba(0, 0, 255, 0.3);");
        }
        overlay.setPrefSize(tileStack.getPrefWidth(), tileStack.getPrefHeight());
        overlay.setOnMouseClicked(event -> moveSelectedUnitToTile(tile));
        tileStack.getChildren().add(overlay);
    }

    private void moveSelectedUnitToTile(Tile tile) {
        // if (selectedOrder != SelectedOrder.MOVE) {
        //     return;
        // }
        if (selectedUnit == null) {
            return;
        }
        if (doesTileHaveUnit(tile.getX(), tile.getY())) {
            return;
        }
        Unit unit = selectedUnit;
        int moveToX = tile.getX();
        int moveToY = tile.getY();
        int moveToIndex = tile.getIndex();
        logger.logDebug("Moving unit " + unit.getUnitID() + " to tile " + moveToX + ", " + moveToY);
        unit.setX(moveToX);
        unit.setY(moveToY);
        unit.setIndex(moveToIndex);
        selectedUnit = null;
        clearMovementOverlays();
        updateGameBoard();
    }

    private void updateGameBoard() {
        // First clear the existing layers
        for (Node node : gameBoard.getChildren()) {
            if (node instanceof StackPane) {
                StackPane stackPane = (StackPane) node;
                List<Node> spritesToRemove = new ArrayList<>();
                for (Node child : stackPane.getChildren()) {
                    if (child instanceof ImageView) {
                        spritesToRemove.add(child);
                    }
                }
                stackPane.getChildren().removeAll(spritesToRemove);
            }
        }
        // Then re-render all the layers
        // Background and object layers
        for (Tile tile : tilepersistor.getTiles()) {
            int index = tile.getIndex();
            StackPane tileStack = (StackPane) gameBoard.getChildren().get(index);
            renderBackgroundLayer(tile, 32, tileStack);
            renderObjectLayer(tile, 32, tileStack);
        }

        for (Unit unit : unitPersistor.getUnits()) {
            if (unit.getUnitCode() != 36) {
                int index = unit.getIndex();
                StackPane tileStack = (StackPane) gameBoard.getChildren().get(index);
                renderSpriteLayer(unit, 32, tileStack);
            }
        }
    }

    /**
     * Calculates the tiles that are reachable by the selected unit based on its movement points
     * and the terrain of the tiles.
     *
     * @param unit The unit for which to calculate reachable tiles.
     * @return A list of coordinates (int arrays) of the reachable tiles.
     */
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

                if (isValidTile(newX, newY) && !visited[newX][newY] && !doesTileHaveFriendlyUnit(newX, newY)) {
                    int cost = getMovementCost(unit, newX, newY);
                    if (remainingMovementPoints - cost >= 0) {
                        queue.add(new int[]{newX, newY, remainingMovementPoints - cost});
                    }
                }
            }
        }

        return reachableTiles;
    }

    private boolean doesTileHaveFriendlyUnit(int x, int y) {
        Unit unit = unitPersistor.getUnitAtPosition(x, y);
        if (unit == null) {
            return false;
        } else {
            Boolean sameTeam = unitPersistor.isOnSameTeam(selectedUnit, unit);
            return sameTeam;
        }
    }

    private boolean doesTileHaveUnit(int x, int y) {
        return unitPersistor.getUnitAtPosition(x, y) != null;
    }

    /**
     * Checks if a given tile coordinate is valid and within the bounds of the game map.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @return True if the tile is within bounds, false otherwise.
     */
    private boolean isValidTile(int x, int y) {
        // Checks if the tile is within map bounds
        int mapWidth = game.getMap().getWidth();
        int mapHeight = game.getMap().getHeight();
        return x >= 0 && x < mapWidth && y >= 0 && y < mapHeight;
    }

    /**
     * Calculates the movement cost for a unit to move onto a tile based on the unit's movement
     * type and the terrain type of the tile.
     *
     * @param unit The unit moving onto the tile.
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @return The movement cost for the unit to move onto the tile.
     */
    private int getMovementCost(Unit unit, int x, int y) {
        // Calculates the movement cost based on the terrain type
        TerrainType terrainType = tilepersistor.getTileAtPosition(x, y).getTerrain().getType();
        if (terrainType == null) {
            return 0;
        }
        MovementType movementType = unit.getMovementType();
        return unit.getMovementCostForTerrainType(terrainType, movementType);
    }
}
