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
        String tilesFilePath = StringUtils.extractStringValue(content, "\"tilesFile\": \"([^\"]+)\"");
        
        loadTiles(tilesFilePath);
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

    public int[][] getTiles() {
        return tiles;
    }

    public Terrain getTerrain(int i, int j) {
        // Return the terrain at position i, j by getting the tile value from the tiles array and creating a new Terrain object with the correct TerrainType
        int tileValue = tiles[i][j];
        return switch (tileValue) {
            case 0 -> new Terrain(TerrainType.PLAINS);
            case 1 -> new Terrain(TerrainType.WOODS);
            case 2 -> new Terrain(TerrainType.MOUNTAINS);
            case 3 -> new Terrain(TerrainType.SEA);
            case 4, 5 -> new Terrain(TerrainType.STREET);
            case 6, 8, 7 -> new Terrain(TerrainType.HQ);
            case 9, 11, 10 -> new Terrain(TerrainType.CITY);
            case 12, 14, 13 -> new Terrain(TerrainType.BASE);
            case 15, 17, 16 -> new Terrain(TerrainType.AIRPORT);
            case 18, 20, 19 -> new Terrain(TerrainType.PORT);
            default -> throw new IllegalArgumentException("Unknown tile value: " + tileValue);
        };
    }
}
