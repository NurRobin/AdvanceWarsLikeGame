package de.nurrobin.model;

public class Unit {
    private UnitType type;
    private int x;
    private int y;

    public Unit(UnitType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public UnitType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
}
