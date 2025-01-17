package com.example.sinkingships;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Cell {
    private int x;
    private int y;
    private boolean isOccupied;
    private boolean isHit;
    public Button fxButton;
    public ImageView image;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setOccupied() {
        this.isOccupied = true;
    }
    public boolean setIsHit() {
        if (isHit == true) {
            return false;
        } else {
            isHit = true;
            return true;
        }
    }

    /**
     *Used for the Text version of the Gameboard
     */
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
