package com.example.sinkingships;

public class GameBoard {
    private Cell grid[];

    public Cell getCell(int x, int y) {
        //find the cell with the corresponding Coordinates
        for (Cell c : grid) {
            if (x == c.getX() && y == c.getY()) {
                return c;
            }
        }
        return null;
    }

    public boolean placeShip(Ship ship, Cell targetCell) {
        //place Ship object at target Cell, direction is defined in Ship Object
        //return true is placement was successful, false if placement would not work (out of bounds, another ship in the way)

        //If placement was successful, add all nodes that the ship would occupy, and add them to the ships node list
        return true;
    }

    public boolean markHit(Cell targetCell) {
        //if target was a occupied space,(which should be defined in the target Cell) mark the node as hit then return true
        if (targetCell.isOccupied() == true) {
            targetCell.setIsHit();
            return true;
        } else {
            return false;
        }
    }

    //Stupid, because you can just "getCell().isHit(), i will leave it tho
    public boolean isHit(int x, int y) {
        Cell cell = getCell(x, y);

        if (cell.IsHit()) {
            return true;
        } else {
            return false;
        }
    }



}
