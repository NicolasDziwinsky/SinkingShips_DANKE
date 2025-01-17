package com.example.sinkingships;

import javafx.scene.image.ImageView;

/**
 * Currently all cells will be reset
 */
public class Player {

    private String name;
    private boolean isAI;
    private GameBoard gameBoard;
    public GameGrid gameGrid;
    public ImageView gun;

    public Player(String name, boolean isAI, ImageView gun) {
        this.name = name;
        this.isAI = isAI;
        this.gun = gun;
        this.gameBoard = new GameBoard();
    }

    public String getName() {
        return name;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public Boolean isAI() {
        return isAI;
    }

    /**
     * Used for debugging
     */
    public String playerInfo() {
        return this.getName() + " is Ai: " + this.isAI().toString();
    }

    /**
     * Checks if all cells of all ships on the players gameboard are hit, if yes, returns true
     */
    public boolean checkIfLost() {
        for (Ship ship : gameBoard.ShipList) {
            if (!ship.checkIfSunk()) {
                return false;
            }
        }
        return true;
    }

    public void takeTurn(GameBoard OpponentBoard) {
        //i dont know how or where to implement the actual game logic oopsie
    }
}
