package de.nurrobin.model;

import java.util.Map;

import de.nurrobin.enums.MovementType;
import de.nurrobin.enums.ResourceType;
import de.nurrobin.enums.TerrainType;
import javafx.scene.image.Image;

import static de.nurrobin.enums.ResourceType.*;
import static de.nurrobin.enums.UnderlayingResourceType.*;
import static de.nurrobin.util.ResourceURLBuilder.buildURL;

/**
 * Represents a tile on the game map, including its terrain, objects, and other properties.
 */
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
        Map.entry(1, "wood"),
        Map.entry(2, "mountain"),
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
    private int x;
    private int y;
    
    /**
     * Constructs a Tile with a specific tile ID. Initializes the tile's terrain, background image,
     * object image (if any), defense bonus, and movement costs based on the tile ID.
     *
     * @param tileID The unique identifier for the tile, determining its terrain and object.
     */
    public Tile(int tileID) {
        this.tileID = tileID;
        this.hasObject = OBJECT_IMAGE_MAP.containsKey(tileID);
        this.backgroundImage = initBackgroundImage(tileID);
        this.terrain = new Terrain(TERRAIN_TYPE_MAP.getOrDefault(tileID, TerrainType.PLAINS));
        this.objectImage = hasObject ? new Image(buildURL(getResourceType(tileID), TEXTURESFILE, OBJECT_IMAGE_MAP.get(tileID))) : null;
        this.defenseBonus = this.terrain.getType().getDefenseBonus();
        this.movementCosts = this.terrain.getType().getMovementCosts();
    }

    /**
     * Initializes the background image for the tile based on its ID.
     *
     * @param tileID The tile ID used to determine the background image.
     * @return The initialized background image for the tile.
     */
    private Image initBackgroundImage(int tileID) {
        String backgroundName = (tileID == 3 || tileID == 5) ? "sea" : "plain";
        return new Image(buildURL(TILE, TEXTURESFILE, backgroundName));
    }

    /**
     * Determines the resource type for the tile based on its ID.
     *
     * @param tileID The tile ID used to determine the resource type.
     * @return The resource type of the tile.
     */
    private ResourceType getResourceType(int tileID) {
        if (tileID >= 6 && tileID <= 20) {
            return BUILDING;
        }
        return TILE;
    }

    /**
     * Gets the tile ID.
     *
     * @return The tile ID.
     */
    public int getTileID() {
        return tileID;
    }

    
    /**
     * Gets the terrain of the tile.
     *
     * @return The terrain of the tile.
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     * Checks if the tile has an object.
     *
     * @return True if the tile has an object, false otherwise.
     */
    public boolean hasObject() {
        return hasObject;
    }

    /**
     * Gets the background image of the tile.
     *
     * @return The background image of the tile.
     */
    public Image getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * Gets the object image of the tile, if any.
     *
     * @return The object image of the tile, or null if there is no object.
     */
    public Image getObjectImage() {
        return objectImage;
    }

    /**
     * Gets the defense bonus provided by the tile.
     *
     * @return The defense bonus.
     */
    public int getDefenseBonus() {
        return defenseBonus;
    }

    /**
     * Gets the movement costs for moving through the tile, based on movement type.
     *
     * @return A map of movement types to their respective movement costs.
     */
    public Map<MovementType, Integer> getMovementCosts() {
        return movementCosts;
    }

    /**
     * Gets the x-coordinate of the tile on the game map.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the tile on the game map.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

}
