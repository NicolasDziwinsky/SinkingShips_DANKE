package com.example.sinkingships;

public class GameBoard {
    private Cell grid[] = new Cell[100];

    public GameBoard() {
        //Fills the grid array with the corresponding nodes with the correct coordinates
        int x = 1;
        int y = 1;
        int i = 0;

        while (i < 100) {

            grid[i] = new Cell(x, y);

            if (x == 10) {
                x = 1;
                y++;
            } else {
                x++;
            }
            i++;
        }
    }

    //Outputs a Textversion of the Gameboard, with hit and occupied cells visualized
    //Not the best looking code, but it gets the job done
    public void outputTextVersion() {
        String Numbers[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        System.out.println("   A  B  C  D  E  F  G  H  I  J");

        int i = 0;
        int y = 1;

        while (i < 10) {
            String Line = "";
            int counter = 1;

            while (counter < 11) {
                Line += "|"+getCell(counter, y).getStatusSymbol()+"|";
                counter++;
            }

            if (i != 9) {
                getCell(i+1, y).setOccupied();
                getCell(i+1, y).setIsHit();
                System.out.println(Numbers[i] + " " + Line);
            } else {
                System.out.println(Numbers[i] + Line);
            }
            y++;
            i++;
        }

    }

    public Cell getCell(int x, int y) {
        //find the cell with the corresponding Coordinates
        for (Cell c : grid) {
            if (x == c.getX() && y == c.getY()) {
                return c;
            }
        }
        return null;
    }

    public Cell[] getCells() {
        return grid;
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
