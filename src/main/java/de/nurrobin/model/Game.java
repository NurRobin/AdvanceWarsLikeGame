package de.nurrobin.model;

public class Game {

    private GameMap map;
    private int turn = 1;
    private int player;

    public Game(GameMap map) {
        this.map = map;
        this.player = 1;
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
