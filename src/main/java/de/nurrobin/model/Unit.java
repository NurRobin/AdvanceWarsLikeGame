package de.nurrobin.model;

import static de.nurrobin.enums.ResourceType.ENTITY;
import static de.nurrobin.enums.UnderlayingResourceType.TEXTURESFILE;
import static de.nurrobin.util.ResourceURLBuilder.buildURL;

import de.nurrobin.enums.MovementType;
import de.nurrobin.enums.UnitType;
import de.nurrobin.persistor.UnitPersistor;
import javafx.scene.image.Image;

import java.util.Map;

public class Unit {
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
    UnitPersistor unitPersistor = new UnitPersistor();

    public Unit(int unitCode) {
        this.unitCode = unitCode;
        this.playerID = determinePlayerID(unitCode);
        this.unitType = UNIT_TYPE_MAP.getOrDefault(unitCode, null);
        if (unitType == null) {
            throw new IllegalArgumentException("Unknown unit value: " + unitCode);
        }
        this.unitImage = loadImage(unitCode, false);
        this.unitImageBig = loadImage(unitCode, true);
        this.unitID = generateUnitID();
        this.movementType = unitType.getMovementType();
        unitPersistor.addUnit(this);
    }

    private int determinePlayerID(int unitCode) {
        if (unitCode < 18) return 1;
        if (unitCode >= 18 && unitCode <= 35) return 2;
        return 0; // No player
    }

    private Image loadImage(int unitCode, boolean isBig) {
        if (unitCode == 36) return null; // No unit image
        int index = unitCode % 18;
        String color = (unitCode < 18) ? "orange" : "blue";
        String size = isBig ? "_big" : "";
        String imageName = IMAGE_NAMES[index];
        return new Image(buildURL(ENTITY, TEXTURESFILE, color + "_" + imageName + size));
    }

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

    public String getUnitID() {
        return unitID;
    }

    public MovementType getMovementType() {
        return movementType;
    }
}
