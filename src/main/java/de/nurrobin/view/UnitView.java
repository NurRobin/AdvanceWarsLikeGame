package de.nurrobin.view;

import de.nurrobin.model.Unit;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UnitView extends ImageView {
    private Unit unit;

    public UnitView(Unit unit) {
        this.unit = unit;
        setImage(getUnitImage());
    }

    private Image getUnitImage() {
        // Beispielhafte Implementierung zur Rückgabe der Einheiten-Bilder
        switch (unit.getType()) {
            case INFANTRY:
                return new Image("/images/infantry.png");
            case TANK:
                return new Image("/images/tank.png");
            case MOBILE_ARTILLERY:
                return new Image("/images/artillery.png");
            case ANTI_AIR:
                return new Image("/images/anti_air.png");
            case FIGHTER:
                return new Image("/images/fighter.png");
            case BOMBER:
                return new Image("/images/bomber.png");
            case BATTLE_COPTER:
                return new Image("/images/battle_copter.png");
            default:
                return null;
        }
    }
}
