package com.example.sinkingships;

import java.util.ArrayList;
import java.util.List;

public class Ship {


    public List<Cell> coordinates = new ArrayList<Cell>();

    private int length;
    /**
     * True: Horizontal
     * False: Vertical
     */
    private boolean orientation;
    private boolean sunk = false;

    public Ship(int length, boolean orientation) {
        this.length = length;
        this.orientation = orientation;
    }

    /**
     * Used to place Ships on the Field
     * Prefered Version is the PlaceShip Function in the GameBoard option
     */
    public boolean setCoordinates(Cell startLocation, GameBoard gameBoard) {
        //Horizontal: Left to right
        //Vertical: Top to Bottom
        int X = startLocation.getX();
        int Y = startLocation.getY();

        int i = 0;
        while (i < length) {
            //Horizontal
            if (orientation == true) {
                coordinates.add(gameBoard.getCell(X, Y));
                X++;
            }
            //Vertical
            if (orientation == false) {
                coordinates.add(gameBoard.getCell(X, Y));
                Y++;
            }
            i++;

        }

        for (Cell c : coordinates) {
            c.setOccupied();
        }
        return true;
    }
    /**
     * If all Nodes are Hit, then returns true
     */
    public boolean checkIfSunk() {
        for (Cell c : this.coordinates) {
            if (!c.IsHit()) {
                return false;
            }
        }
        this.sunk = true;
        return true;
    }

    public int getLength() {
        return length;
    }

    public boolean getOrrientation() {
        return orientation;
    }

    public List<Cell> getCoordinates(){
        return coordinates;
    }
}
