package de.nurrobin.model;

import java.util.Arrays;
import java.util.List;

public class Game {
    private Map map;
    private List<Unit> units;

    public Game() {
        // Initialisiere das Spiel mit einer Standardkarte und Einheiten
        this.map = new Map();
        this.units = Arrays.asList(
                new Unit(UnitType.INFANTRY, 0, 0),
                new Unit(UnitType.TANK, 1, 0)
                // Weitere Einheiten hinzufügen
        );
    }

    public Map getMap() {
        return map;
    }

    public List<Unit> getUnits() {
        return units;
    }
}