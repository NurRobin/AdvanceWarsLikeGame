package de.nurrobin.view;

import de.nurrobin.model.Game;
import de.nurrobin.model.Terrain;
import de.nurrobin.model.Unit;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameView {
    private Game game;
    private GridPane grid;

    public GameView(Game game, GridPane grid) {
        this.game = game;
        this.grid = grid;
        initializeGrid();
    }

    private void initializeGrid() {
        // Initialisiere das GridPane basierend auf der Karte und den Einheiten
        for (int i = 0; i < game.getMap().getWidth(); i++) {
            for (int j = 0; j < game.getMap().getHeight(); j++) {
                Terrain terrain = game.getMap().getTerrain(i, j);
                ImageView terrainView = new ImageView(getTerrainImage(terrain));
                grid.add(terrainView, i, j);
            }
        }

        for (Unit unit : game.getUnits()) {
            ImageView unitView = new ImageView(getUnitImage(unit));
            grid.add(unitView, unit.getX(), unit.getY());
        }
    }

    private Image getTerrainImage(Terrain terrain) {
        // Beispielhafte Implementierung zur Rückgabe der Terrain-Bilder
        switch (terrain.getType()) {
            case PLAINS:
                return new Image("/tiles/plain.png");
            case WOODS:
                return new Image("/tiles/wood.png");
            case MOUNTAINS:
                return new Image("/tiles/mountain.png");
            case SEA:
                return new Image("/tiles/sea.png");
            default:
                return null;
        }
    }

    private Image getUnitImage(Unit unit) {
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