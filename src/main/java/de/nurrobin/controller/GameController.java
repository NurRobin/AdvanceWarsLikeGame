package de.nurrobin.controller;

import de.nurrobin.model.Game;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class GameController {
    @FXML
    private GridPane gameGrid;

    private Game game;

    public void initialize() {
        game = new Game();
        setupGameGrid();
    }

    private void setupGameGrid() {
        // Initialisiere das Spielfeld basierend auf dem Game-Objekt
        // Hier wird die GUI-Logik implementiert, um die Karte und Einheiten anzuzeigen
    }
}
