package de.nurrobin.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameUI {

    @FXML
    private Label roundLabel;

    @FXML
    private Label mapNameLabel;

    @FXML
    private Label unitHealthLabel;

    @FXML
    private Label unitMovementLabel;

    public void updateRound(int round) {
        roundLabel.setText("Round: " + round);
    }

    public void updateMapName(String mapName) {
        mapNameLabel.setText("Map: " + mapName);
    }

    public void updateUnitHealth(int health, int maxHealth) {
        unitHealthLabel.setText("Health: " + health + "/" + maxHealth);
    }

    public void updateUnitMovement(int movement, int maxMovement) {
        unitMovementLabel.setText("Movement: " + movement + "/" + maxMovement);
    }
}
