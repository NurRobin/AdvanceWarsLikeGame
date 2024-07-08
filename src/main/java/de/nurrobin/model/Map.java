package de.nurrobin.model;

public class Map {
    private Terrain[][] mapGrid;

    public Map() {
        // Beispiel: Initialisiere eine einfache Karte
        mapGrid = new Terrain[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mapGrid[i][j] = new Terrain(TerrainType.PLAIN);
            }
        }
    }

    public Terrain getTerrain(int x, int y) {
        return mapGrid[x][y];
    }

    public void setTerrain(int x, int y, Terrain terrain) {
        mapGrid[x][y] = terrain;
    }

    public int getWidth() {
        return mapGrid.length;
    }

    public int getHeight() {
        return mapGrid[0].length;
    }
}