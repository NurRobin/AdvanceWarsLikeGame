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
    private String tilesFilePath;
    private int[][] tiles;

    public Map(String mapFileName) throws IOException {
        // Load map data from file in resources/maps/mapFileName.map
        String mapFilePath = "src/main/resources/maps/" + mapFileName;
        loadMap(mapFilePath);
    }

private void loadMap(String mapFileName) throws IOException {
    String content = new String(Files.readAllBytes(Paths.get(mapFileName)), StandardCharsets.UTF_8);
    
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
        this.tilesFilePath = StringUtils.extractStringValue(content, "\"tilesFile\": \"([^\"]+)\"");
        
        loadTiles(this.tilesFilePath);
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

    private int parseTileValue(String tileValue) {
        switch (tileValue) {
            case "0": return 0; // Plains
            case "1": return 1; // Woods
            case "2": return 2; // Mountains
            case "3": return 3; // Sea
            case "4": return 4; // Street
            case "5": return 5; // Bridge
            case "6": return 6; // HQ Neutral
            case "7": return 7; // HQ Orange Star
            case "8": return 8; // HQ Blue Moon
            case "9": return 9; // City Neutral
            case "A": return 10; // City Orange Star
            case "B": return 11; // City Blue Moon
            case "C": return 12; // Base Neutral
            case "D": return 13; // Base Orange Star
            case "E": return 14; // Base Blue Moon
            case "F": return 15; // Airport Neutral
            case "G": return 16; // Airport Orange Star
            case "H": return 17; // Airport Blue Moon
            case "I": return 18; // Port Neutral
            case "J": return 19; // Port Orange Star
            case "K": return 20; // Port Blue Moon
            default: throw new IllegalArgumentException("Unknown tile value: " + tileValue);
        }
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

    public int[][] getTiles() {
        return tiles;
    }

    public Terrain getTerrain(int i, int j) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTerrain'");
    }
}
