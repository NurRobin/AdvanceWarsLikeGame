package de.nurrobin.persistor;

import java.util.ArrayList;
import java.util.List;

import de.nurrobin.model.Unit;

public class UnitPersistor {
    // Save which units are on the map
    private List<Unit> units = new ArrayList<>();

    public UnitPersistor() {
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    public void clearUnits() {
        units.clear();
    }

    public boolean isUnitIDAvailable(String unitID) {
        return units.stream().noneMatch(unit -> unit.getUnitID().equals(unitID));
    }
}
