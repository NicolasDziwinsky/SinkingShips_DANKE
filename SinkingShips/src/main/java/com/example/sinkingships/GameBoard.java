package com.example.sinkingships;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {
    //contains all Cells of the Grid
    private Cell grid[] = new Cell[100];
    //Contains all Ships of the Field
    public List<Ship> ShipList = new ArrayList<Ship>();
    public PlacementGrid placementGrid;
    public Ship[] placementShips =
            {new Ship(2, true), new Ship(3, true), new Ship(4, true), new Ship(5, true)};
    public int shipCounter = 0;


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


    /**
     * Outputs a Textversion of the Gameboard, with hit and occupied cells visualized.
     * Not the best looking code, but it gets the job done
     */
    public void outputTextVersion() {
        String Numbers[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        System.out.println("   A  B  C  D  E  F  G  H  I  J");

        int i = 0;
        int y = 1;

        while (i < 10) {
            String Line = "";
            int counter = 1;

            while (counter < 11) {
                Line += "|" + getCell(counter, y).getStatusSymbol() + "|";
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

    /**
     * return the cell with the corresponding Coordinates
     */
    public Cell getCell(int x, int y) {
        for (Cell c : grid) {
            if (x == c.getX() && y == c.getY()) {
                return c;
            }
        }
        return null;
    }

    /**
     * returns the complete cell array
     */
    public Cell[] getCells() {
        return grid;
    }


    /**
     * //place Ship object at target Cell, direction is defined in Ship Object
     * //return true is placement was successful, false if placement would not work (out of bounds, another ship in the way)
     * <p>
     * //If placement was successful, add all nodes that the ship would occupy, and add them to the ships node list
     * //Horizontal: Left to right
     * //Vertical: Top to Bottom
     */
    public boolean placeShip(Ship ship, Cell targetCell) {

        int X = targetCell.getX();
        int Y = targetCell.getY();
        int i = 0;

        //pre-checks if target cells are valid for placement
        while (i < ship.getLength()) {
            //Horizontal
            if (ship.getOrrientation() == true) {
                if (getCell(X, Y) == null || getCell(X, Y).isOccupied()) {
                    return false;
                }
                X++;
            }
            //Vertical
            if (ship.getOrrientation() == false) {

                if (getCell(X, Y) == null || getCell(X, Y).isOccupied()) {
                    return false;
                }
                Y++;
            }
            i++;
        }
        //resets variables
        X = targetCell.getX();
        Y = targetCell.getY();

        i = 0;
        while (i < ship.getLength()) {
            //Horizontal
            if (ship.getOrrientation() == true) {
                getCell(X, Y).image = new ImageView();
                getCell(X, Y).image.setImage(ship.shipImageParts.get(i));
                getCell(X, Y).image.setFitHeight(80);
                getCell(X, Y).image.setFitWidth(80);
                placementGrid.gridPane.add(getCell(X, Y).image, X - 1, Y - 1);
                ship.coordinates.add(getCell(X, Y));
                X++;
            }
            //Vertical
            if (ship.getOrrientation() == false) {
                getCell(X, Y).image = new ImageView();
                getCell(X, Y).image.setImage(ship.shipImageParts.get(i));
                getCell(X, Y).image.setFitHeight(80);
                getCell(X, Y).image.setFitWidth(80);
                getCell(X, Y).image.rotateProperty().set(90);
                placementGrid.gridPane.add(getCell(X, Y).image, X - 1, Y - 1);
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

    public boolean placeNextShip(Cell targetCell) {
        boolean returnMessage = placeShip(placementShips[shipCounter], targetCell);
        if (returnMessage) {
            shipCounter += 1;
            if (shipCounter > 3) {
                placementGrid.shipPreview.imageProperty().set(null);
            }
            placementGrid.setShipPreview(placementShips[shipCounter].shipImage);
        }
        return returnMessage;
    }

    public void placeShipsRandomly() {
        Random rand = new Random();
        shipCounter = 4;
        placementGrid.shipPreview.imageProperty().set(null);
        for (Ship ship : placementShips) {
            boolean positionSet = false;
            while (!positionSet) {
                int X = rand.nextInt(1, 11);
                int Y = rand.nextInt(1, 11);
                int Z = rand.nextInt(2);
                if (Z == 1) {
                    for (Ship turnShip : placementShips) {
                        turnShip.turnShip();
                    }
                }

                if (placeShip(ship, getCell(X, Y))) {
                    positionSet = true;
                }
                ;

            }
        }


    }
}
