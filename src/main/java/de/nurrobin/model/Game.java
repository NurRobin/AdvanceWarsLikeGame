package de.nurrobin.model;

import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.nurrobin.util.MapDataLoader;

public class Game {
    private Map map;
    private List<Unit> units;

    public Game() {
        List<String> availableMaps = getAvailableMaps();
        String randomMap = getRandomMap(availableMaps);

        // Use MapDataLoader to load the map
        this.map = MapDataLoader.load(randomMap, 2);
        this.units = Arrays.asList(
                new Unit(UnitType.INFANTRY, 0, 0),
                new Unit(UnitType.TANK, 1, 0)
        );
    }

    private List<String> getAvailableMaps() {
        List<String> availableMaps = new ArrayList<>();
        File mapsFolder = new File("src/main/resources/maps");

        if (mapsFolder.exists() && mapsFolder.isDirectory()) {
            File[] mapFiles = mapsFolder.listFiles();
            if (mapFiles != null) {
                for (File mapFile : mapFiles) {
                    if (mapFile.isFile() && mapFile.getName().endsWith(".map")) {
                        availableMaps.add(mapFile.getName());
                    }
                }
            }
        }

        return availableMaps;
    }

    private String getRandomMap(List<String> availableMaps) {
        Random random = new Random();
        int index = random.nextInt(availableMaps.size());
        return availableMaps.get(index);
    }

    public BoardCell[][] getBoard() {
        int rows = map.getRows();
        int cols = map.getCols();
        BoardCell[][] board = new BoardCell[rows][cols];

        // Initialize the board with empty cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = new BoardCell(map.getTerrainAt(i, j));
            }
        }

        // Place units on the board
        for (Unit unit : units) {
            int x = unit.getX();
            int y = unit.getY();
            board[x][y].addUnit(unit);
        }

        return board;
    }
}