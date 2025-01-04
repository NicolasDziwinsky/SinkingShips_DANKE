package com.example.sinkingships;

import javafx.scene.control.Button;
public class Cell {
    private int x;
    private int y;
    private boolean isOccupied;
    private boolean isHit;
    public Button fxButton;

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

    //Used for the Text version of the Gameboard
    public String getStatusSymbol() {
        if (isOccupied && isHit) {
            return "X";
        }
        if (isOccupied) {
            return "O";
        }
        if (isHit) {
            return "/";
        }
        return " ";
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
