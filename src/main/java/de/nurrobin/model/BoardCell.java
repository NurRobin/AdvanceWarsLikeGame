package de.nurrobin.model;

import java.util.ArrayList;
import java.util.List;

public class BoardCell {
    private Terrain terrain;
    private List<Unit> units;

    public BoardCell(Terrain terrain) {
        this.terrain = terrain;
        this.units = new ArrayList<>();
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }
}
