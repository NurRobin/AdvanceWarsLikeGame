package de.nurrobin.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameUI {

    @FXML
    private Label roundLabel;

    @FXML
    private Label playerLabel;

    @FXML
    private Label mapNameLabel;

    @FXML
    private Label unitNameLabel;

    @FXML
    private ImageView unitImageView;

    @FXML
    private Label unitHealthLabel;

    @FXML
    private Label unitMovementLabel;

    @FXML
    private Label actionLabel;

    public void updateRound(int round) {
        roundLabel.setText("Round: " + round);
    }

    public void updatePlayer(int player) {
        playerLabel.setText("Player: " + player);
    }

    public void updateMapName(String mapName) {
        mapNameLabel.setText("Map: " + mapName);
    }

    public void updateUnitName(String unitName) {
        unitNameLabel.setText("Unit: " + unitName);
    }

    public void updateUnitImage(String unitImage) {
        unitImageView.setImage(new Image(unitImage));
    }

    public void updateUnitHealth(int health, int maxHealth) {
        unitHealthLabel.setText("Health: " + health + "/" + maxHealth);
    }

    public void updateUnitMovement(int movement, int maxMovement) {
        unitMovementLabel.setText("Movement: " + movement + "/" + maxMovement);
    }

    public void updateAction(String action) {
        actionLabel.setText(action);
    }
}
