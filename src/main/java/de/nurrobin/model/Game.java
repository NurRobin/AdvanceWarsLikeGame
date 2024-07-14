package de.nurrobin.model;

import de.nurrobin.controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;



public class Game {

    private GameMap map;
    private int turn;
    private int player;

    public Game(GameMap map) {
        this.map = map;
    }

    public GameMap getMap() {
        return map;
    }

    public void advancePlayer() {
        if (player == 1) {
            player = 2;
        } else {
            advanceTurn();
            player = 1;
        }
    }


    public void advanceTurn() {
        turn++;
    }

    public int getTurn() {
        return turn;
    }

    public int getPlayer() {
        return player;
    }
}
