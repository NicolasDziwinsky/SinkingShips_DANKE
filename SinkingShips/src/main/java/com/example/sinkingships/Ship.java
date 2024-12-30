package com.example.sinkingships;

import java.util.List;

public class Ship {
    enum direction {
        horizontal,
        vertical
    }

    private List<Cell> coordinates;
    private int length;
    private direction orientation;
    private boolean sunk = false;

    public Ship(int length) {
        this.length = length;
        this.orientation = direction.horizontal;
    }

    public boolean isSunk() {
        //check all Cells in list, if all are hit, it is sunk
        return true;
    }

    public List<Cell> getCoordinates(){
        return coordinates;
    }
}
