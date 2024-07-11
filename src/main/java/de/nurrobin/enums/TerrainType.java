package de.nurrobin.enums;

import java.util.Map;

public enum TerrainType {
    PLAINS(1,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 2,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    WOODS(2, Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 2, MovementType.TIRES, 3,
            MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    MOUNTAINS(4,
            Map.of(MovementType.FOOT, 2, MovementType.BOOTS, 1, MovementType.TREADS, -1, MovementType.TIRES, -1,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    SEA(0, Map.of(MovementType.FOOT, -1, MovementType.BOOTS, -1, MovementType.TREADS, -1, MovementType.TIRES, -1,
            MovementType.AIR, 1, MovementType.SEA, 1, MovementType.LANDER, 1)),
    STREET(0,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    BRIDGE(0,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    HQ_NEUTRAL(4,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    HQ_ORANGE(4,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    HQ_BLUE(4,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    CITY_NEUTRAL(3,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    CITY_ORANGE(3,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    CITY_BLUE(3,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    BASE_NEUTRAL(3,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    BASE_ORANGE(3,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    BASE_BLUE(3,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    AIRPORT_NEUTRAL(3,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    AIRPORT_ORANGE(3,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    AIRPORT_BLUE(3,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, -1, MovementType.LANDER, -1)),
    PORT_NEUTRAL(3,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, 1, MovementType.LANDER, 1)),
    PORT_ORANGE(3,
            Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
                    MovementType.AIR, 1, MovementType.SEA, 1, MovementType.LANDER, 1)),
    PORT_BLUE(3, Map.of(MovementType.FOOT, 1, MovementType.BOOTS, 1, MovementType.TREADS, 1, MovementType.TIRES, 1,
            MovementType.AIR, 1, MovementType.SEA, 1, MovementType.LANDER, 1));

    private final int defenseBonus;
    private Map<MovementType, Integer> movementCosts;

    TerrainType(int defenseBonus, Map<MovementType, Integer> movementCosts) {
        this.defenseBonus = defenseBonus;
        this.movementCosts = movementCosts;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }

    public Map<MovementType, Integer> getMovementCosts() {
        return movementCosts;
    }
}