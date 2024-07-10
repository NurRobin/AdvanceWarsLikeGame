package de.nurrobin.model;

import de.nurrobin.util.Logger;
import de.nurrobin.util.StringUtils;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    public GameMap(String mapFileName) throws IOException {
        // Load map data from file in resources/maps/mapFileName.map
        String mapFilePath = "src/main/resources/maps/" + mapFileName;
        loadMap(mapFilePath);
    }

private void loadMap(String mapFileName) throws IOException {
    logger.logDebug("Loading map from file: " + mapFileName);
    String content = Files.readString(Paths.get(mapFileName));
    
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
        throw new IOException("Failed to parse map file: " + mapFileName, e);
    }
}

    private void loadTiles(String tilesFilePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(tilesFilePath))) {
            String line;
            int rows = 0;
            while ((line = br.readLine()) != null) {
                rows++;
            }
            this.tiles = new int[rows][];

            try (BufferedReader br2 = new BufferedReader(new FileReader(tilesFilePath))) {
                int i = 0;
                while ((line = br2.readLine()) != null) {
                    String[] tileValues = line.split(" ");
                    this.tiles[i] = new int[tileValues.length];
                    for (int j = 0; j < tileValues.length; j++) {
                        this.tiles[i][j] = parseTileValue(tileValues[j]);
                    }
                    i++;
                }
            }
        }
    }

    private void loadUnits(String unitsFilePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(unitsFilePath))) {
            String line;
            int rows = 0;
            while ((line = br.readLine()) != null) {
                rows++;
            }
            this.units = new int[rows][];

            try (BufferedReader br2 = new BufferedReader(new FileReader(unitsFilePath))) {
                int i = 0;
                while ((line = br2.readLine()) != null) {
                    String[] unitValues = line.split(" ");
                    this.units[i] = new int[unitValues.length];
                    for (int j = 0; j < unitValues.length; j++) {
                        this.units[i][j] = parseUnitValue(unitValues[j]);
                    }
                    i++;
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
    

    public int getPlayers() {
        return players;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean hasHQ() {
        return hq;
    }

    public boolean hasCities() {
        return cities;
    }

    public boolean hasBases() {
        return bases;
    }

    public boolean hasAirports() {
        return airports;
    }

    public boolean hasPorts() {
        return ports;
    }

    public String getImageFile() {
        return imageFile;
    }

    public Tile getTileAt(int i, int j) {
        // Return a new Tile object with the correct background and object images based on the terrain at position i, j
        int tileValue = tiles[i][j];
        return new Tile(tileValue);
    }

    public Unit getUnitAt(int i, int j) {
        // Return a new Unit object with the correct image based on the unit at position i, j
        int unitValue = units[i][j];
        return new Unit(unitValue);
    }
}
