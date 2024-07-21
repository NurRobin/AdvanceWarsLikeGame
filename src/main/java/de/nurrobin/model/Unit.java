package de.nurrobin.model;

import de.nurrobin.enums.MovementType;
import de.nurrobin.enums.TerrainType;
import de.nurrobin.enums.UnitType;
import de.nurrobin.util.Logger;
import de.nurrobin.persistor.UnitPersistor;
import javafx.scene.image.Image;

import java.util.Map;

import static de.nurrobin.enums.ResourceType.ENTITY;
import static de.nurrobin.enums.UnderlayingResourceType.TEXTURESFILE;
import static de.nurrobin.util.ResourceURLBuilder.buildURL;

/**
 * Represents a unit in the game, including its type, player ownership, and graphical representation.
 */
public class Unit {
    Logger logger = new Logger(Unit.class);
    private static final Map<Integer, UnitType> UNIT_TYPE_MAP = Map.ofEntries(
            Map.entry(0, UnitType.INFANTRY),
            Map.entry(1, UnitType.MECH_INFANTRY),
            Map.entry(2, UnitType.RECON),
            Map.entry(3, UnitType.TANK),
            Map.entry(4, UnitType.MEDIUM_TANK),
            Map.entry(5, UnitType.APC),
            Map.entry(6, UnitType.ARTILLERY),
            Map.entry(7, UnitType.ROCKETS),
            Map.entry(8, UnitType.ANTI_AIR),
            Map.entry(9, UnitType.MISSILES),
            Map.entry(10, UnitType.FIGHTER),
            Map.entry(11, UnitType.BOMBER),
            Map.entry(12, UnitType.BATTLE_COPTER),
            Map.entry(13, UnitType.TRANSPORT_COPTER),
            Map.entry(14, UnitType.BATTLESHIP),
            Map.entry(15, UnitType.CRUISER),
            Map.entry(16, UnitType.LANDER),
            Map.entry(17, UnitType.SUBMARINE),
            Map.entry(18, UnitType.INFANTRY),
            Map.entry(19, UnitType.MECH_INFANTRY),
            Map.entry(20, UnitType.RECON),
            Map.entry(21, UnitType.TANK),
            Map.entry(22, UnitType.MEDIUM_TANK),
            Map.entry(23, UnitType.APC),
            Map.entry(24, UnitType.ARTILLERY),
            Map.entry(25, UnitType.ROCKETS),
            Map.entry(26, UnitType.ANTI_AIR),
            Map.entry(27, UnitType.MISSILES),
            Map.entry(28, UnitType.FIGHTER),
            Map.entry(29, UnitType.BOMBER),
            Map.entry(30, UnitType.BATTLE_COPTER),
            Map.entry(31, UnitType.TRANSPORT_COPTER),
            Map.entry(32, UnitType.BATTLESHIP),
            Map.entry(33, UnitType.CRUISER),
            Map.entry(34, UnitType.LANDER),
            Map.entry(35, UnitType.SUBMARINE)
    );

    private static final String[] IMAGE_NAMES = {
            "infantry", "mech", "recon", "tank", "midtank", "apc", "artillery",
            "rocket", "antiair", "missile", "fighter", "bomber", "bcopter",
            "tcopter", "bship", "cruiser", "lander", "sub"
    };

    private final int unitCode;
    private final String unitID;
    private final UnitType unitType;
    private final MovementType movementType;
    private final int playerID;
    private final Image unitImage;
    private final Image unitImageBig;
    private final int movementRadius;
    private int x;
    private int y;
    private int index;

    private int health;
    private int maxHealth;
    private int movementPoints;
    private int maxMovementPoints;
    private int attackPower;
    private int defensePower;

    UnitPersistor unitPersistor = UnitPersistor.getInstance();

    /**
     * Constructs a Unit with a specific unit code and position. Initializes the unit's type,
     * player ID, images, movement type, and movement radius. Adds the unit to the persistor.
     *
     * @param unitCode The code representing the unit type and player ownership.
     * @param x The x-coordinate of the unit's position.
     * @param y The y-coordinate of the unit's position.
     */
    public Unit(int unitCode, int x, int y, int index) {
        this.unitCode = unitCode;
        this.x = x;
        this.y = y;
        this.playerID = determinePlayerID(unitCode);
        this.unitType = UNIT_TYPE_MAP.getOrDefault(unitCode, null);
        if (unitType == null && unitCode != 36) {
            throw new IllegalArgumentException("Unknown unit value: " + unitCode);
        }
        if (unitCode == 36) {
            this.unitImage = null;
            this.unitImageBig = null;
            this.unitID = null;
            this.movementType = null;
            this.movementRadius = 0;
            return;
        }
        this.unitImage = loadImage(unitCode, false);
        this.unitImageBig = loadImage(unitCode, true);
        this.unitID = generateUnitID();
        this.movementType = unitType.getMovementType();
        this.movementRadius = unitType.getMovementRadius();
        this.index = index;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.maxMovementPoints = unitType.getMovementRadius();
        this.movementPoints = maxMovementPoints;

        unitPersistor.addUnit(this);
    }

    /**
     * Determines the player ID based on the unit code.
     *
     * @param unitCode The code representing the unit type and player ownership.
     * @return The player ID associated with the unit.
     */
    private int determinePlayerID(int unitCode) {
        if (unitCode < 18) return 1;
        if (unitCode >= 18 && unitCode <= 35) return 2;
        return 0;
    }

    /**
     * Loads the image for the unit based on its code and whether a big image is requested.
     *
     * @param unitCode The code representing the unit type.
     * @param isBig Indicates whether the big version of the image should be loaded.
     * @return The loaded image for the unit.
     */
    private Image loadImage(int unitCode, boolean isBig) {
        if (unitCode == 36) return null;
        int index = unitCode % 18;
        String color = (unitCode < 18) ? "orange" : "blue";
        String size = isBig ? "_big" : "";
        String imageName = IMAGE_NAMES[index];
        return new Image(buildURL(ENTITY, TEXTURESFILE, color + "_" + imageName + size));
    }

    /**
     * Generates a unique unit ID using the unit type and current time.
     *
     * @return A unique unit ID.
     */
    private String generateUnitID() {
        String unitIDString = unitType + "_" + System.currentTimeMillis();
        while (!unitPersistor.isUnitIDAvailable(unitIDString)) {
            unitIDString = unitType + "_" + System.currentTimeMillis();
        }
        return unitIDString;
    }

    public int getUnitCode() {
        return unitCode;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public int getPlayerID() {
        return playerID;
    }

    public Image getImage() {
        return unitImage;
    }

    public Image getBigImage() {
        return unitImageBig;
    }

    public boolean isOrange() {
        return playerID == 1;
    }

    public boolean isBlue() {
        return playerID == 2;
    }

    public String getTeamColor() {
        return isOrange() ? "orange" : "blue";
    }

    public String getUnitID() {
        return unitID;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public int getMovementRadius(){
        return movementRadius;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        unitPersistor.updateUnit(this);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        unitPersistor.updateUnit(this);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        unitPersistor.updateUnit(this);
    }

        public int getHealth() {
            return health;
        }
    
        public void setHealth(int health) {
            this.health = health;
        }
    
        public int getMaxHealth() {
            return maxHealth;
        }
    
        public void setMaxHealth(int maxHealth) {
            this.maxHealth = maxHealth;
        }
    
        public int getMovementPoints() {
            return movementPoints;
        }
    
        public void setMovementPoints(int movementPoints) {
            this.movementPoints = movementPoints;
        }
    
        public int getMaxMovementPoints() {
            return maxMovementPoints;
        }
    
        public void setMaxMovementPoints(int maxMovementPoints) {
            this.maxMovementPoints = maxMovementPoints;
        }
    
        public int getAttackPower() {
            return attackPower;
        }
    
        public void setAttackPower(int attackPower) {
            this.attackPower = attackPower;
        }

        public int getDefensePower() {
            return defensePower;
        }
    
        public void setDefensePower(int defensePower) {
            this.defensePower = defensePower;
        }
    
        public void resetMovementPoints() {
            this.movementPoints = maxMovementPoints;
        }
    
        public void join(Unit other) {
            if (this.canJoinWith(other)) {
                this.health = Math.min(this.health + other.health, this.maxHealth);
            }
        }
    
        public boolean canJoinWith(Unit other) {
            return this.unitType.equals(other.unitType);
        }
    
        
        /**
         * Calculates the movement cost for the unit to move over a specified terrain type.
         *
         * @param terrainType The type of terrain the unit wants to move over.
         * @param movementType The movement type of the unit.
         * @return The movement cost for the unit to move over the specified terrain type.
         */
        public int getMovementCostForTerrainType(TerrainType terrainType, MovementType movementType) {
            Map<MovementType, Integer> movementCosts = terrainType.getMovementCosts();
            Integer cost = movementCosts.get(movementType);
            if (cost != null && cost != -1) {
                return cost;
            } else {
                return Integer.MAX_VALUE;
            }
        }
    }
    