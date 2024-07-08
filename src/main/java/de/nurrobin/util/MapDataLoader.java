package de.nurrobin.util;

import de.nurrobin.interfaces.MapData;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import de.nurrobin.model.Map;

public class MapDataLoader {

    public static Map load(String mapName, int playerCount) {
        String path = Paths.get("src", "main", "resources", "maps", mapName + ".map").toString();
        StringBuilder contentBuilder = new StringBuilder();
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Or handle the error as appropriate
        }
        
        String mapDataString = contentBuilder.toString();
        
        Map map = new Map(2, mapDataString); // Adjust constructor as needed
        map.loadMapData(mapDataString); // Use the implemented method to parse and load data
        return map;
    }
}