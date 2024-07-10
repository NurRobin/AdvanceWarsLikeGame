package de.nurrobin.model;

import de.nurrobin.enums.MovementType;
import de.nurrobin.enums.UnitType;
import de.nurrobin.persistor.UnitPersistor;
import javafx.scene.image.Image;

public class Unit {
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
        this.playerID = unitCode < 17 ? 1 : (unitCode >= 18 && unitCode <= 35) ? 2 : 0; // 0 for no player
        this.unitType = switch (unitCode) {
            case 0, 18 -> UnitType.INFANTRY;
            case 1, 19 -> UnitType.MECH_INFANTRY;
            case 2, 20 -> UnitType.RECON;
            case 3, 21 -> UnitType.TANK;
            case 4, 22 -> UnitType.MEDIUM_TANK;
            case 5, 23 -> UnitType.APC;
            case 6, 24 -> UnitType.ARTILLERY;
            case 7, 25 -> UnitType.ROCKETS;
            case 8, 26 -> UnitType.ANTI_AIR;
            case 9, 27 -> UnitType.MISSILES;
            case 10, 28 -> UnitType.FIGHTER;
            case 11, 29 -> UnitType.BOMBER;
            case 12, 30 -> UnitType.BATTLE_COPTER;
            case 13, 31 -> UnitType.TRANSPORT_COPTER;
            case 14, 32 -> UnitType.BATTLESHIP;
            case 15, 33 -> UnitType.CRUISER;
            case 16, 34 -> UnitType.LANDER;
            case 17, 35 -> UnitType.SUBMARINE;
            case 36 -> null; // No unit
            default -> throw new IllegalArgumentException("Unknown unit value: " + unitCode);
        };
        this.unitImage = switch (unitCode) {
            case 0 -> new Image("/entities/sprites/orange_infantry.png");
            case 1 -> new Image("/entities/sprites/orange_mech.png");
            case 2 -> new Image("/entities/sprites/orange_recon.png");
            case 3 -> new Image("/entities/sprites/orange_tank.png");
            case 4 -> new Image("/entities/sprites/orange_midtank.png");
            case 5 -> new Image("/entities/sprites/orange_apc.png");
            case 6 -> new Image("/entities/sprites/orange_artillery.png");
            case 7 -> new Image("/entities/sprites/orange_rocket.png");
            case 8 -> new Image("/entities/sprites/orange_antiair.png");
            case 9 -> new Image("/entities/sprites/orange_missile.png");
            case 10 -> new Image("/entities/sprites/orange_fighter.png");
            case 11 -> new Image("/entities/sprites/orange_bomber.png");
            case 12 -> new Image("/entities/sprites/orange_bcopter.png");
            case 13 -> new Image("/entities/sprites/orange_tcopter.png");
            case 14 -> new Image("/entities/sprites/orange_bship.png");
            case 15 -> new Image("/entities/sprites/orange_cruiser.png");
            case 16 -> new Image("/entities/sprites/orange_lander.png");
            case 17 -> new Image("/entities/sprites/orange_sub.png");
            case 18 -> new Image("/entities/sprites/blue_infantry.png");
            case 19 -> new Image("/entities/sprites/blue_mech.png");
            case 20 -> new Image("/entities/sprites/blue_recon.png");
            case 21 -> new Image("/entities/sprites/blue_tank.png");
            case 22 -> new Image("/entities/sprites/blue_midtank.png");
            case 23 -> new Image("/entities/sprites/blue_apc.png");
            case 24 -> new Image("/entities/sprites/blue_artillery.png");
            case 25 -> new Image("/entities/sprites/blue_rocket.png");
            case 26 -> new Image("/entities/sprites/blue_antiair.png");
            case 27 -> new Image("/entities/sprites/blue_missile.png");
            case 28 -> new Image("/entities/sprites/blue_fighter.png");
            case 29 -> new Image("/entities/sprites/blue_bomber.png");
            case 30 -> new Image("/entities/sprites/blue_bcopter.png");
            case 31 -> new Image("/entities/sprites/blue_tcopter.png");
            case 32 -> new Image("/entities/sprites/blue_bship.png");
            case 33 -> new Image("/entities/sprites/blue_cruiser.png");
            case 34 -> new Image("/entities/sprites/blue_lander.png");
            case 35 -> new Image("/entities/sprites/blue_sub.png");
            case 36 -> null; // No unit image
            default -> throw new IllegalArgumentException("Unknown unit value: " + unitCode);
        };
        this.unitImageBig = switch (unitCode) {
            case 0 -> new Image("/entities/sprites/orange_infantry_big.png");
            case 1 -> new Image("/entities/sprites/orange_mech_big.png");
            case 2 -> new Image("/entities/sprites/orange_recon_big.png");
            case 3 -> new Image("/entities/sprites/orange_tank_big.png");
            case 4 -> new Image("/entities/sprites/orange_midtank_big.png");
            case 5 -> new Image("/entities/sprites/orange_apc_big.png");
            case 6 -> new Image("/entities/sprites/orange_artillery_big.png");
            case 7 -> new Image("/entities/sprites/orange_rocket_big.png");
            case 8 -> new Image("/entities/sprites/orange_antiair_big.png");
            case 9 -> new Image("/entities/sprites/orange_missile_big.png");
            case 10 -> new Image("/entities/sprites/orange_fighter_big.png");
            case 11 -> new Image("/entities/sprites/orange_bomber_big.png");
            case 12 -> new Image("/entities/sprites/orange_bcopter_big.png");
            case 13 -> new Image("/entities/sprites/orange_tcopter_big.png");
            case 14 -> new Image("/entities/sprites/orange_bship_big.png");
            case 15 -> new Image("/entities/sprites/orange_cruiser_big.png");
            case 16 -> new Image("/entities/sprites/orange_lander_big.png");
            case 17 -> new Image("/entities/sprites/orange_sub_big.png");
            case 18 -> new Image("/entities/sprites/blue_infantry_big.png");
            case 19 -> new Image("/entities/sprites/blue_mech_big.png");
            case 20 -> new Image("/entities/sprites/blue_recon_big.png");
            case 21 -> new Image("/entities/sprites/blue_tank_big.png");
            case 22 -> new Image("/entities/sprites/blue_midtank_big.png");
            case 23 -> new Image("/entities/sprites/blue_apc_big.png");
            case 24 -> new Image("/entities/sprites/blue_artillery_big.png");
            case 25 -> new Image("/entities/sprites/blue_rocket_big.png");
            case 26 -> new Image("/entities/sprites/blue_antiair_big.png");
            case 27 -> new Image("/entities/sprites/blue_missile_big.png");
            case 28 -> new Image("/entities/sprites/blue_fighter_big.png");
            case 29 -> new Image("/entities/sprites/blue_bomber_big.png");
            case 30 -> new Image("/entities/sprites/blue_bcopter_big.png");
            case 31 -> new Image("/entities/sprites/blue_tcopter_big.png");
            case 32 -> new Image("/entities/sprites/blue_bship_big.png");
            case 33 -> new Image("/entities/sprites/blue_cruiser_big.png");
            case 34 -> new Image("/entities/sprites/blue_lander_big.png");
            case 35 -> new Image("/entities/sprites/blue_sub_big.png");
            case 36 -> null; // No big unit image
            default -> throw new IllegalArgumentException("Unknown unit value: " + unitCode);
        };
        this.unitID = generateUnitID();
        this.movementType = unitType.getMovementType();
        unitPersistor.addUnit(this);
    }

    private String generateUnitID() {
        // Generate a random String for the unit ID
        String UnitIDString = unitType + "_" + System.currentTimeMillis();
        while (!unitPersistor.isUnitIDAvailable(UnitIDString)) {
            UnitIDString = unitType + "_" + System.currentTimeMillis();
        }
        return UnitIDString;
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
