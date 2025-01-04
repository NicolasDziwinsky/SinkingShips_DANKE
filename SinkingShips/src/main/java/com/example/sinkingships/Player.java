package com.example.sinkingships;

public class Player {

    private String name;
    private boolean isAI;
    private GameBoard gameBoard;
    public GameGrid gameGrid;

    public Player(String name, boolean isAI) {
        this.name = name;
        this.isAI = isAI;
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

    //Just used for Debugging
    public String playerInfo() {
        return this.getName() + " is Ai: " + this.isAI().toString();
    }

    //Only checks Cells that are contained in ships
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
