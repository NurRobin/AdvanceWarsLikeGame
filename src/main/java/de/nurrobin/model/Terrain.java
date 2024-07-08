package de.nurrobin.model;

public class Terrain {
    private TerrainType type;
    private String propertyOwner;

    public Terrain(TerrainType type) {
        this.type = type;
    }
    
    public TerrainType getType() {
        return type;
    }

    public void setType(TerrainType type) {
        this.type = type;
    }

    public String getPropertyOwner() {
        return propertyOwner;
    }

    public void setPropertyOwner(String propertyOwner) {
        this.propertyOwner = propertyOwner;
    }

    public boolean hasPropertyOwner() {
        return propertyOwner != null;
    }

    public void clearPropertyOwner() {
        propertyOwner = null;
    }
}
