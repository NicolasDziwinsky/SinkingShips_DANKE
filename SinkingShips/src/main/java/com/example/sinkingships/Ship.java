package com.example.sinkingships;

import javafx.scene.image.Image;

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
    public Image shipImage;
    public List<Image> shipImageParts = new ArrayList<Image>();

    public Ship(int length, boolean orientation) {
        this.length = length;
        this.orientation = orientation;

        switch (length) {
            case 2:
                shipImage = new Image(String.valueOf(getClass().getResource("/img/ships/ship_2a.png")));
                shipImageParts.add(new Image(String.valueOf(getClass().getResource("/img/ships/ship_2a_part1.png"))));
                shipImageParts.add(new Image(String.valueOf(getClass().getResource("/img/ships/ship_2a_part2.png"))));
                break;
            case 3:
                shipImage = new Image(String.valueOf(getClass().getResource("/img/ships/ship_3a.png")));
                shipImageParts.add(new Image(String.valueOf(getClass().getResource("/img/ships/ship_3a_part1.png"))));
                shipImageParts.add(new Image(String.valueOf(getClass().getResource("/img/ships/ship_3a_part2.png"))));
                shipImageParts.add(new Image(String.valueOf(getClass().getResource("/img/ships/ship_3a_part3.png"))));
                break;
            case 4:
                shipImage = new Image(String.valueOf(getClass().getResource("/img/ships/ship_4a.png")));
                shipImageParts.add(new Image(String.valueOf(getClass().getResource("/img/ships/ship_4a_part1.png"))));
                shipImageParts.add(new Image(String.valueOf(getClass().getResource("/img/ships/ship_4a_part2.png"))));
                shipImageParts.add(new Image(String.valueOf(getClass().getResource("/img/ships/ship_4a_part3.png"))));
                shipImageParts.add(new Image(String.valueOf(getClass().getResource("/img/ships/ship_4a_part4.png"))));
                break;
            case 5:
                shipImage = new Image(String.valueOf(getClass().getResource("/img/ships/ship_5a.png")));
                shipImageParts.add(new Image(String.valueOf(getClass().getResource("/img/ships/ship_5a_part1.png"))));
                shipImageParts.add(new Image(String.valueOf(getClass().getResource("/img/ships/ship_5a_part2.png"))));
                shipImageParts.add(new Image(String.valueOf(getClass().getResource("/img/ships/ship_5a_part3.png"))));
                shipImageParts.add(new Image(String.valueOf(getClass().getResource("/img/ships/ship_5a_part4.png"))));
                shipImageParts.add(new Image(String.valueOf(getClass().getResource("/img/ships/ship_5a_part5.png"))));
        }
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

    public void turnShip() {
        if (orientation) {
            orientation = false;
        } else {
            orientation = true;
        }
        System.out.println(orientation);
    }

    public List<Cell> getCoordinates(){
        return coordinates;
    }
}
