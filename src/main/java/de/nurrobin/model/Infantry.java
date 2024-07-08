package de.nurrobin.model;

public class Infantry extends Unit {

    public Infantry(int x, int y) {
        super(x, y);
    }

    @Override
    public void move(int newX, int newY) {
        setX(newX);
        setY(newY);
    }
}
