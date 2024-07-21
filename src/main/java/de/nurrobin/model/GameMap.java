package de.nurrobin.model;

import de.nurrobin.util.Logger;
import de.nurrobin.util.StringUtils;

import static de.nurrobin.enums.ResourceType.*;
import static de.nurrobin.enums.UnderlayingResourceType.*;
import static de.nurrobin.util.ResourceURLBuilder.buildURL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Represents the game map, including its properties, tiles, and units.
 */
public class GameMap {
    private final Logger logger = new Logger(GameMap.class);

    private int players;
    private int width;
    private int height;
    private boolean hq;
    private boolean cities;
    private boolean bases;
    private boolean airports;
    private boolean ports;
    private String imageFile;
    private int[][] tiles;
    private int[][] units;

    
    /**
     * Constructs a GameMap object by loading map data from a specified file.
     *
     * @param mapFileName The name of the map file to load.
     * @throws IOException If an I/O error occurs.
     * @throws URISyntaxException If the syntax of the URI is incorrect.
     */
    public GameMap(String mapFileName) throws IOException, URISyntaxException {
        mapFileName = mapFileName.replace(".map", "");
        String mapFilePath = buildURL(MAP, MAPFILE, mapFileName);
        loadMap(mapFilePath);
    }

    /**
     * Loads map data from the specified file path.
     *
     * @param mapFilePath The file path of the map to load.
     * @throws IOException If an I/O error occurs.
     * @throws URISyntaxException If the syntax of the URI is incorrect.
     */
    private void loadMap(String mapFilePath) throws IOException, URISyntaxException {
        logger.logDebug("Loading map from file: " + mapFilePath);
        String content = Files.readString(Paths.get(GameMap.class.getResource(mapFilePath.toString()).toURI()));

        try {
            this.players = StringUtils.extractIntValue(content, "\"players\": (\\d+),");
            String[] dimensions = StringUtils.extractStringValue(content, "\"size\": \"([\\d x]+)\",").split(" x ");
            this.width = Integer.parseInt(dimensions[0].trim());
            this.height = Integer.parseInt(dimensions[1].trim());

            this.hq = StringUtils.extractBooleanValue(content, "\"HQ\": (true|false),");
            this.cities = StringUtils.extractBooleanValue(content, "\"Cities\": (true|false),");
            this.bases = StringUtils.extractBooleanValue(content, "\"Bases\": (true|false),");
            this.airports = StringUtils.extractBooleanValue(content, "\"Airports\": (true|false),");
            this.ports = StringUtils.extractBooleanValue(content, "\"Ports\": (true|false)");

            this.imageFile = StringUtils.extractStringValue(content, "\"imageFile\": \"([^\"]+)\",");
            String tilesFilePath = StringUtils.extractStringValue(content, "\"tilesFile\": \"([^\"]+)\"");
            String unitsFilePath = StringUtils.extractStringValue(content, "\"unitsFile\": \"([^\"]+)\"");

            loadTiles(tilesFilePath);
            loadUnits(unitsFilePath);

        } catch (Exception e) {
            throw new IOException("Failed to parse map file: " + mapFilePath, e);
        }
    }

    /**
     * Loads tile data from the specified file path.
     *
     * @param tilesFilePath The file path of the tiles data to load.
     * @throws IOException If an I/O error occurs.
     */
    private void loadTiles(String tilesFilePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(tilesFilePath))) {
            String line;
            int rows = 0;
            while ((line = br.readLine()) != null) {
                rows++;
            }
            this.tiles = new int[rows][];

            try (BufferedReader br2 = new BufferedReader(new FileReader(tilesFilePath))) {
                int y = 0;
                while ((line = br2.readLine()) != null) {
                    String[] tileValues = line.split(" ");
                    this.tiles[y] = new int[tileValues.length];
                    for (int x = 0; x < tileValues.length; x++) {
                        this.tiles[y][x] = parseTileValue(tileValues[x]);
                    }
                    y++;
                }
            }
        }
    }

    /**
     * Loads unit data from the specified file path.
     *
     * @param unitsFilePath The file path of the units data to load.
     * @throws IOException If an I/O error occurs.
     */
    private void loadUnits(String unitsFilePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(unitsFilePath))) {
            String line;
            int rows = 0;
            while ((line = br.readLine()) != null) {
                rows++;
            }
            this.units = new int[rows][];

            try (BufferedReader br2 = new BufferedReader(new FileReader(unitsFilePath))) {
                int y = 0;
                while ((line = br2.readLine()) != null) {
                    String[] unitValues = line.split(" ");
                    this.units[y] = new int[unitValues.length];
                    for (int x = 0; x < unitValues.length; x++) {
                        this.units[y][x] = parseUnitValue(unitValues[x]);
                    }
                    y++;
                }
            }
        }
    }

    private static final Map<String, Integer> UNIT_MAP = Map.ofEntries(
            Map.entry("0", 0), Map.entry("1", 1), Map.entry("2", 2), Map.entry("3", 3), Map.entry("4", 4),
            Map.entry("5", 5), Map.entry("6", 6), Map.entry("7", 7), Map.entry("8", 8), Map.entry("9", 9),
            Map.entry("A", 10), Map.entry("B", 11), Map.entry("C", 12), Map.entry("D", 13), Map.entry("E", 14),
            Map.entry("F", 15), Map.entry("G", 16), Map.entry("H", 17), Map.entry("I", 18), Map.entry("J", 19),
            Map.entry("K", 20), Map.entry("L", 21), Map.entry("M", 22), Map.entry("N", 23), Map.entry("O", 24),
            Map.entry("P", 25), Map.entry("Q", 26), Map.entry("R", 27), Map.entry("S", 28), Map.entry("T", 29),
            Map.entry("U", 30), Map.entry("V", 31), Map.entry("W", 32), Map.entry("X", 33), Map.entry("Y", 34),
            Map.entry("Z", 35), Map.entry("x", 36)
    );

    private static final Map<String, Integer> TILE_MAP = Map.ofEntries(
            Map.entry("0", 0), Map.entry("1", 1), Map.entry("2", 2), Map.entry("3", 3), Map.entry("4", 4),
            Map.entry("5", 5), Map.entry("6", 6), Map.entry("7", 7), Map.entry("8", 8), Map.entry("9", 9),
            Map.entry("A", 10), Map.entry("B", 11), Map.entry("C", 12), Map.entry("D", 13), Map.entry("E", 14),
            Map.entry("F", 15), Map.entry("G", 16), Map.entry("H", 17), Map.entry("I", 18), Map.entry("J", 19),
            Map.entry("K", 20)
    );


    private int parseUnitValue(String unitValue) {
        Integer value = UNIT_MAP.get(unitValue);
        if (value == null) {
            throw new IllegalArgumentException("Unknown unit value: " + unitValue);
        }
        return value;
    }

    private int parseTileValue(String tileValue) {
        Integer value = TILE_MAP.get(tileValue);
        if (value == null) {
            throw new IllegalArgumentException("Unknown tile value: " + tileValue);
        }
        return value;
    }

    /**
     * Gets the number of players in the game.
     *
     * @return The number of players.
     */
    public int getPlayers() {
        return players;
    }

    /**
     * Gets the width of the game map.
     *
     * @return The width of the map.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the game map.
     *
     * @return The height of the map.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Checks if the map has a headquarters (HQ).
     *
     * @return True if the map has an HQ, false otherwise.
     */
    public boolean hasHQ() {
        return hq;
    }

    /**
     * Checks if the map has cities.
     *
     * @return True if the map has cities, false otherwise.
     */
    public boolean hasCities() {
        return cities;
    }

    /**
     * Checks if the map has bases.
     *
     * @return True if the map has bases, false otherwise.
     */
    public boolean hasBases() {
        return bases;
    }

    /**
     * Checks if the map has airports.
     *
     * @return True if the map has airports, false otherwise.
     */
    public boolean hasAirports() {
        return airports;
    }

    /**
     * Checks if the map has ports.
     *
     * @return True if the map has ports, false otherwise.
     */
    public boolean hasPorts() {
        return ports;
    }

    /**
     * Gets the file name of the map's image.
     *
     * @return The file name of the map's image.
     */
    public String getImageFile() {
        return imageFile;
    }

    /**
     * Gets the tile at the specified position.
     *
     * @param y The row index of the tile.
     * @param x The column index of the tile.
     * @return The tile at the specified position.
     */
    public Tile createTileAt(int x, int y, int tileindex) {
        if (y < 0 || y >= tiles.length || x < 0 || x >= tiles[y].length) {
            return null;
        }
        int tileValue = tiles[y][x];
        return new Tile(tileValue, x, y, tileindex);
    }

    /**
    * Gets the unit at the specified position.
    *
    * @param y The row index
    * @param x The column index
    * @return The unit at position i, j
    */
    public Unit createUnitAt(int x, int y, int tileindex) {
        int unitValue = units[y][x];
        if (unitValue != 36) {
            logger.logDebug("Creating unit at position (" + x + ", " + y + ") with value: " + unitValue);
        }
        return new Unit(unitValue, x, y, tileindex);
    }
}
