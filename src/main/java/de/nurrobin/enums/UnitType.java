package de.nurrobin.enums;

public enum UnitType {
    INFANTRY(MovementType.FOOT),
    MECH_INFANTRY(MovementType.BOOTS),
    RECON(MovementType.TIRES),
    TANK(MovementType.TREADS),
    MEDIUM_TANK(MovementType.TREADS),
    APC(MovementType.TREADS),
    ARTILLERY(MovementType.TREADS),
    ROCKETS(MovementType.TREADS),
    ANTI_AIR(MovementType.TREADS),
    MISSILES(MovementType.TREADS),
    FIGHTER(MovementType.AIR),
    BOMBER(MovementType.AIR),
    BATTLE_COPTER(MovementType.AIR),
    TRANSPORT_COPTER(MovementType.AIR),
    BATTLESHIP(MovementType.SEA),
    CRUISER(MovementType.SEA),
    SUBMARINE(MovementType.SEA),
    LANDER(MovementType.LANDER);

    private final MovementType movementType;

    UnitType(MovementType movementType) {
        this.movementType = movementType;
    }

    public MovementType getMovementType() {
        return movementType;
    }
}