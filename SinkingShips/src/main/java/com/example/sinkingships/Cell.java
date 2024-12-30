package com.example.sinkingships;

public class Cell {
    private int x;
    private int y;
    private boolean isOccupied;
    private boolean isHit;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setOccupied() {
        this.isOccupied = true;
    }
    public void setIsHit() {
        this.isHit = true;
    }


    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public boolean isOccupied() {
        return isOccupied;
    }
    public boolean IsHit() {
        return isHit;
    }
}
