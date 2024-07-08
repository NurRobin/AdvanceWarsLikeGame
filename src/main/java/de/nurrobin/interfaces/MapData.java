package de.nurrobin.interfaces;

public interface MapData {
    /**
     * Gets the number of players in the map.
     * @return the number of players.
     */
    int getPlayerCount();

    /**
     * Gets the size of the map.
     * @return the size of the map as a String.
     */
    String getMapSize();

    /**
     * Checks if a specific property exists in the map.
     * @param property the property to check for.
     * @return true if the property exists, false otherwise.
     */
    boolean hasProperty(String property);

    /**
     * Gets the number of cities a player has.
     * @param playerName the name of the player.
     * @return the number of cities.
     */
    int getPlayerCities(String playerName);

    /**
     * Gets the number of bases a player has.
     * @param playerName the name of the player.
     * @return the number of bases.
     */
    int getPlayerBases(String playerName);

    /**
     * Gets the number of airports a player has.
     * @param playerName the name of the player.
     * @return the number of airports.
     */
    int getPlayerAirports(String playerName);

    /**
     * Gets the number of ports a player has.
     * @param playerName the name of the player.
     * @return the number of ports.
     */
    int getPlayerPorts(String playerName);

    /**
     * Gets the file path to the map's image.
     * @return the file path of the map's image.
     */
    String getImageFile();

    /**
     * Gets the file path to the map's tiles.
     * @return the file path of the map's tiles.
     */
    String getTilesFile();
}