package de.nurrobin.model;

import de.nurrobin.interfaces.MapData;

public class Map implements MapData {
    private int playerCount;
    private String mapSize;
    private String imageFile;

    public Map(int playerCount, String mapSize) {
        this.playerCount = playerCount;
        this.mapSize = mapSize;
    }

    @Override
    public void loadMapData(String mapData) {
        
    }

    @Override
    public int getPlayerCount() {
        return playerCount;
    }

    @Override
    public String getMapSize() {
        return mapSize;
    }

    @Override
    public boolean hasProperty(String property) {
        // Implement this method
        return false;
    }

    @Override
    public int getPlayerCities(String playerName) {
        // Implement this method
        return 0;
    }

    @Override
    public int getPlayerBases(String playerName) {
        // Implement this method
        return 0;
    }

    @Override
    public int getPlayerAirports(String playerName) {
        // Implement this method
        return 0;
    }

    @Override
    public int getPlayerPorts(String playerName) {
        // Implement this method
        return 0;
    }

    @Override
    public String getImageFile() {
        return imageFile;
    }
}