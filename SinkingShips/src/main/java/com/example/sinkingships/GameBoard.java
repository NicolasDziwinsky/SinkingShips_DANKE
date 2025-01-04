package com.example.sinkingships;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    //contains all Cells of the Grid
    private Cell grid[] = new Cell[100];
    //Contains all Ships of the Field
    public List<Ship> ShipList = new ArrayList<Ship>();
    public PlacementGrid placementGrid;

    //Fills the grid array with the corresponding nodes with the correct coordinates
    public GameBoard() {

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
                System.out.println(Numbers[i] + " " + Line);
            } else {
                System.out.println(Numbers[i] + Line);
            }
            y++;
            i++;
        }

    }

    //find the cell with the corresponding Coordinates
    public Cell getCell(int x, int y) {
        for (Cell c : grid) {
            if (x == c.getX() && y == c.getY()) {
                return c;
            }
        }
        return null;
    }

    //returns the complete cell array
    public Cell[] getCells() {
        return grid;
    }

    public boolean placeShip(Ship ship, Cell targetCell) {
        //place Ship object at target Cell, direction is defined in Ship Object
        //return true is placement was successful, false if placement would not work (out of bounds, another ship in the way)

        //If placement was successful, add all nodes that the ship would occupy, and add them to the ships node list
        //Horizontal: Left to right
        //Vertical: Top to Bottom


        int X = targetCell.getX();
        int Y = targetCell.getY();

        int i = 0;
        while (i < ship.getLength()) {
            //Horizontal
            if (ship.getOrrientation() == true) {
                ship.coordinates.add(getCell(X, Y));
                X++;
            }
            //Vertical
            if (ship.getOrrientation() == false) {
                ship.coordinates.add(getCell(X, Y));
                Y++;
            }
            i++;

        }

        for (Cell c : ship.coordinates) {
            c.setOccupied();
        }

        ShipList.add(ship);
        return true;
    }





}
