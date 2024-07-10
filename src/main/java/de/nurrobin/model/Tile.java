package de.nurrobin.model;

import java.util.Map;

import de.nurrobin.enums.MovementType;
import de.nurrobin.enums.TerrainType;
import javafx.scene.image.Image;

public class Tile {
    private final int tileID;
    private Terrain terrain;
    private final boolean hasObject;
    private Image backgroundImage;
    private Image objectImage;

    private final int defenseBonus;
    private final Map<MovementType, Integer> movementCosts;


    public Tile(int tileID) {
        this.tileID = tileID;
        this.hasObject = (tileID != 0 && tileID != 3);
        initBackgroundImage(tileID);
        initTerrain(tileID);
        initObjectImage(tileID);
        this.defenseBonus = this.terrain.getType().getDefenseBonus();
        this.movementCosts = this.terrain.getType().getMovementCosts();
        }

    private void initObjectImage(int tileID) {
        if (hasObject) {
            this.objectImage = switch (tileID) {
                case 1 -> new Image("/tiles/textures/wood.png");
                case 2 -> new Image("/tiles/textures/mountain.png");
                case 4, 5 -> new Image("/tiles/textures/road.png");
                case 7 -> new Image("/buildings/textures/HQ-Orange.png");
                case 8 -> new Image("/buildings/textures/HQ-Blue.png");
                case 9,10,11 -> new Image("/buildings/textures/City-Neutral.png");
//                case 10 -> new Image("/buildings/textures/City-Orange.png");
//                case 11 -> new Image("/buildings/textures/City-Blue.png");
                case 12 -> new Image("/buildings/textures/Base-Neutral.png");
                case 13 -> new Image("/buildings/textures/Base-Orange.png");
                case 14 -> new Image("/buildings/textures/Base-Blue.png");
                case 15,16,17 -> new Image("/buildings/textures/Airport-Neutral.png");
//                case 16 -> new Image("/buildings/textures/Airport-Orange.png");
//                case 17 -> new Image("/buildings/textures/Airport-Blue.png");
                case 18,19,20 -> new Image("/buildings/textures/Port-Neutral.png");
//                case 19 -> new Image("/buildings/textures/Port-Orange.png");
//                case 20 -> new Image("/buildings/textures/Port-Blue.png");
                default -> throw new IllegalArgumentException("Unknown tile value: " + tileID);
            };
            }
    }

    private void initTerrain(int tileID) {
        this.terrain = switch (tileID) {
            case 0 -> new Terrain(TerrainType.PLAINS);
            case 1 -> new Terrain(TerrainType.WOODS);
            case 2 -> new Terrain(TerrainType.MOUNTAINS);
            case 3 -> new Terrain(TerrainType.SEA);
            case 4, 5 -> new Terrain(TerrainType.STREET);
            case 6 -> new Terrain(TerrainType.HQ_NEUTRAL);
            case 7 -> new Terrain(TerrainType.HQ_ORANGE);
            case 8 -> new Terrain(TerrainType.HQ_BLUE);
            case 9 -> new Terrain(TerrainType.CITY_NEUTRAL);
            case 10 -> new Terrain(TerrainType.CITY_ORANGE);
            case 11 -> new Terrain(TerrainType.CITY_BLUE);
            case 12 -> new Terrain(TerrainType.BASE_NEUTRAL);
            case 13 -> new Terrain(TerrainType.BASE_ORANGE);
            case 14 -> new Terrain(TerrainType.BASE_BLUE);
            case 15 -> new Terrain(TerrainType.AIRPORT_NEUTRAL);
            case 16 -> new Terrain(TerrainType.AIRPORT_ORANGE);
            case 17 -> new Terrain(TerrainType.AIRPORT_BLUE);
            case 18 -> new Terrain(TerrainType.PORT_NEUTRAL);
            case 19 -> new Terrain(TerrainType.PORT_ORANGE);
            case 20 -> new Terrain(TerrainType.PORT_BLUE);
            default -> throw new IllegalArgumentException("Unknown tile value: " + tileID);
        };
    }

    private void initBackgroundImage(int tileID) {
        if (tileID != 3 && tileID != 5) {
            this.backgroundImage = new Image("/tiles/textures/plain.png");
        } else {
            this.backgroundImage = new Image("/tiles/textures/sea.png");
        }
    }

    public int getTileID() {
        return tileID;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public boolean hasObject() {
        return hasObject;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public Image getObjectImage() {
        return objectImage;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }

    public Map<MovementType, Integer> getMovementCosts() {
        return movementCosts;
    }
}