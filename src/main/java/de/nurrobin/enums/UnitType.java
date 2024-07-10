package de.nurrobin.enums;

public enum UnitType {
    INFANTRY(MovementType.FOOT, 3),
    MECH_INFANTRY(MovementType.BOOTS, 2),
    RECON(MovementType.TIRES, 8),
    TANK(MovementType.TREADS, 6),
    MEDIUM_TANK(MovementType.TREADS, 5),
    APC(MovementType.TREADS, 6),
    ARTILLERY(MovementType.TREADS, 5),
    ROCKETS(MovementType.TREADS, 5),
    ANTI_AIR(MovementType.TREADS, 6),
    MISSILES(MovementType.TREADS, 4),
    FIGHTER(MovementType.AIR, 9),
    BOMBER(MovementType.AIR, 7),
    BATTLE_COPTER(MovementType.AIR, 6),
    TRANSPORT_COPTER(MovementType.AIR, 6),
    BATTLESHIP(MovementType.SEA, 5),
    CRUISER(MovementType.SEA, 6),
    SUBMARINE(MovementType.SEA, 5),
    LANDER(MovementType.LANDER, 6);

    private final MovementType movementType;
    private final int movementRadius;

    UnitType(MovementType movementType, int movementRadius) {
        this.movementType = movementType;
        this.movementRadius = movementRadius;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public int getMovementRadius() {
        return movementRadius;
    }
}
