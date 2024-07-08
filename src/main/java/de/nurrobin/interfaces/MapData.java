package de.nurrobin.interfaces;

public interface MapData {
    void loadMapData(String mapData);
    int getPlayerCount();
    String getMapSize();
    boolean hasProperty(String property);
    int getPlayerCities(String playerName);
    int getPlayerBases(String playerName);
    int getPlayerAirports(String playerName);
    int getPlayerPorts(String playerName);
    String getImageFile();
}