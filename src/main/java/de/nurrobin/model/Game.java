package de.nurrobin.model;

public class Game {
    private GameMap map;

    public Game(GameMap map) {
        this.map = map;
    }

    public GameMap getMap() {
        return map;
    }
}
