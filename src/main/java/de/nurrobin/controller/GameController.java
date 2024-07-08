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
        gameGrid.getChildren().clear();
        gameGrid.getColumnConstraints().clear();
        gameGrid.getRowConstraints().clear();

        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard()[i].length; j++) {
                gameGrid.add(game.getBoard()[i][j].getButton(), j, i);
            }
        }

        gameGrid.setGridLinesVisible(true);

        gameGrid.setPrefSize(400, 400);

        gameGrid.setHgap(5);
        gameGrid.setVgap(5);

        gameGrid.setStyle("-fx-background-color: #000000");
    }
}
