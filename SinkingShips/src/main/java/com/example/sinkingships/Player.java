package com.example.sinkingships;

public class Player {

    private String name;
    private boolean isAI;
    private GameBoard gameBoard;

    public Player(String name, boolean isAI, GameBoard gameBoard) {
        this.name = name;
        this.isAI = isAI;
        this.gameBoard = gameBoard;
    }

    public String getName() {
        return name;
    }
    public Boolean isAI() {
        return isAI;
    }
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void takeTurn(GameBoard OpponentBoard) {
        //i dont know how or where to implement the actual game logic oopsie
    }
}
