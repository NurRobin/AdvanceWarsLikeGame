package de.nurrobin.model;

import java.util.List;

public class Game {
    private GameMap map;
    private List<Unit> player1Units;
    private List<Unit> player2Units;

    public Game(GameMap map) {
        this.map = map;
        initializeUnits();
    }

    private void initializeUnits() {
        //TODO: Initialize units for both players at predefined positions

    }

    public GameMap getMap() {
        return map;
    }

    public List<Unit> getPlayer1Units() {
        return player1Units;
    }

    public List<Unit> getPlayer2Units() {
        return player2Units;
    }

    public Unit[] getUnits() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUnitAt'");
    }
}
