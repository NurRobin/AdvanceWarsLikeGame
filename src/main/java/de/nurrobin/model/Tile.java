package de.nurrobin.model;

import java.util.Map;

import de.nurrobin.enums.MovementType;
import de.nurrobin.enums.ResourceType;
import de.nurrobin.enums.TerrainType;
import javafx.scene.image.Image;

import static de.nurrobin.enums.ResourceType.*;
import static de.nurrobin.enums.UnderlayingResourceType.*;
import static de.nurrobin.util.ResourceURLBuilder.buildURL;

public class Tile {
    private static final Map<Integer, TerrainType> TERRAIN_TYPE_MAP = Map.ofEntries(
        Map.entry(0, TerrainType.PLAINS),
        Map.entry(1, TerrainType.WOODS),
        Map.entry(2, TerrainType.MOUNTAINS),
        Map.entry(3, TerrainType.SEA),
        Map.entry(4, TerrainType.STREET),
        Map.entry(5, TerrainType.STREET),
        Map.entry(6, TerrainType.HQ_NEUTRAL),
        Map.entry(7, TerrainType.HQ_ORANGE),
        Map.entry(8, TerrainType.HQ_BLUE),
        Map.entry(9, TerrainType.CITY_NEUTRAL),
        Map.entry(10, TerrainType.CITY_ORANGE),
        Map.entry(11, TerrainType.CITY_BLUE),
        Map.entry(12, TerrainType.BASE_NEUTRAL),
        Map.entry(13, TerrainType.BASE_ORANGE),
        Map.entry(14, TerrainType.BASE_BLUE),
        Map.entry(15, TerrainType.AIRPORT_NEUTRAL),
        Map.entry(16, TerrainType.AIRPORT_ORANGE),
        Map.entry(17, TerrainType.AIRPORT_BLUE),
        Map.entry(18, TerrainType.PORT_NEUTRAL),
        Map.entry(19, TerrainType.PORT_ORANGE),
        Map.entry(20, TerrainType.PORT_BLUE)
    );

    private static final Map<Integer, String> OBJECT_IMAGE_MAP = Map.ofEntries(
        Map.entry(1, "woods"),
        Map.entry(2, "mountains"),
        Map.entry(4, "road"),
        Map.entry(5, "road"),
        Map.entry(7, "HQ-Orange"),
        Map.entry(8, "HQ-Blue"),
        Map.entry(9, "City-Neutral"),
        Map.entry(10, "City-Neutral"),
        Map.entry(11, "City-Neutral"),
        Map.entry(12, "Base-Neutral"),
        Map.entry(13, "Base-Orange"),
        Map.entry(14, "Base-Blue"),
        Map.entry(15, "Airport-Neutral"),
        Map.entry(16, "Airport-Neutral"),
        Map.entry(17, "Airport-Neutral"),
        Map.entry(18, "Port-Neutral"),
        Map.entry(19, "Port-Neutral"),
        Map.entry(20, "Port-Neutral")
    );

    private final int tileID;
    private final Terrain terrain;
    private final boolean hasObject;
    private final Image backgroundImage;
    private final Image objectImage;

    private final int defenseBonus;
    private final Map<MovementType, Integer> movementCosts;

    public Tile(int tileID) {
        this.tileID = tileID;
        this.hasObject = OBJECT_IMAGE_MAP.containsKey(tileID);
        this.backgroundImage = initBackgroundImage(tileID);
        this.terrain = new Terrain(TERRAIN_TYPE_MAP.getOrDefault(tileID, TerrainType.PLAINS));
        this.objectImage = hasObject ? new Image(buildURL(getResourceType(tileID), TEXTURESFILE, OBJECT_IMAGE_MAP.get(tileID))) : null;
        this.defenseBonus = this.terrain.getType().getDefenseBonus();
        this.movementCosts = this.terrain.getType().getMovementCosts();
    }

    private Image initBackgroundImage(int tileID) {
        String backgroundName = (tileID == 3 || tileID == 5) ? "sea" : "plain";
        return new Image(buildURL(TILE, TEXTURESFILE, backgroundName));
    }

    private ResourceType getResourceType(int tileID) {
        if (tileID >= 9 && tileID <= 20) {
            return BUILDING;
        }
        return TILE;
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
