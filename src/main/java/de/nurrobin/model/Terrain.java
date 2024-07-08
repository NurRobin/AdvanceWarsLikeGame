package de.nurrobin.model;

public class Terrain {
    private TerrainType type;

    public Terrain(TerrainType type) {
        this.type = type;
    }

    public TerrainType getType() {
        return type;
    }
}
