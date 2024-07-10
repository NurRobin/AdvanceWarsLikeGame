package de.nurrobin.model;

import de.nurrobin.util.StringUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Map {
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

    public Map(String mapFileName) throws IOException {
        // Load map data from file in resources/maps/mapFileName.map
        String mapFilePath = "src/main/resources/maps/" + mapFileName;
        loadMap(mapFilePath);
    }

private void loadMap(String mapFileName) throws IOException {
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

    private int parseUnitValue(String unitValue) {
        return switch (unitValue) {
            case "0" -> 0; // Orange Infantry
            case "1" -> 1; // Orange Mech Infantry
            case "2" -> 2; // Orange Recon
            case "3" -> 3; // Orange Tank
            case "4" -> 4; // Orange Medium Tank
            case "5" -> 5; // Orange APC
            case "6" -> 6; // Orange Artillery
            case "7" -> 7; // Orange Rockets
            case "8" -> 8; // Orange Anti-Air
            case "9" -> 9; // Orange Missiles
            case "A" -> 10; // Orange Fighter
            case "B" -> 11; // Orange Bomber
            case "C" -> 12; // Orange Battle Copter
            case "D" -> 13; // Orange Transport Copter
            case "E" -> 14; // Orange Battleship
            case "F" -> 15; // Orange Cruiser
            case "G" -> 16; // Orange Lander
            case "H" -> 17; // Orange Submarine
            case "I" -> 18; // Blue Infantry
            case "J" -> 19; // Blue Mech Infantry
            case "K" -> 20; // Blue Recon
            case "L" -> 21; // Blue Tank
            case "M" -> 22; // Blue Medium Tank
            case "N" -> 23; // Blue APC
            case "O" -> 24; // Blue Artillery
            case "P" -> 25; // Blue Rockets
            case "Q" -> 26; // Blue Anti-Air
            case "R" -> 27; // Blue Missiles
            case "S" -> 28; // Blue Fighter
            case "T" -> 29; // Blue Bomber
            case "U" -> 30; // Blue Battle Copter
            case "V" -> 31; // Blue Transport Copter
            case "W" -> 32; // Blue Battleship
            case "X" -> 33; // Blue Cruiser
            case "Y" -> 34; // Blue Lander
            case "Z" -> 35; // Blue Submarine
            case "x" -> 36; // No unit
            default -> throw new IllegalArgumentException("Unknown unit value: " + unitValue);
        };
    }

    private int parseTileValue(String tileValue) {
        return switch (tileValue) {
            case "0" -> 0; // Plains
            case "1" -> 1; // Woods
            case "2" -> 2; // Mountains
            case "3" -> 3; // Sea
            case "4" -> 4; // Street
            case "5" -> 5; // Bridge
            case "6" -> 6; // HQ Neutral
            case "7" -> 7; // HQ Orange Star
            case "8" -> 8; // HQ Blue Moon
            case "9" -> 9; // City Neutral
            case "A" -> 10; // City Orange Star
            case "B" -> 11; // City Blue Moon
            case "C" -> 12; // Base Neutral
            case "D" -> 13; // Base Orange Star
            case "E" -> 14; // Base Blue Moon
            case "F" -> 15; // Airport Neutral
            case "G" -> 16; // Airport Orange Star
            case "H" -> 17; // Airport Blue Moon
            case "I" -> 18; // Port Neutral
            case "J" -> 19; // Port Orange Star
            case "K" -> 20; // Port Blue Moon
            default -> throw new IllegalArgumentException("Unknown tile value: " + tileValue);
        };
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
