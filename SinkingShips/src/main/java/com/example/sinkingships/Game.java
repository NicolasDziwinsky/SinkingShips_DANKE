package com.example.sinkingships;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * //Singleton
 *     //used to share data between scenes
 */
public class Game {

    private static final Game game = new Game();


    private Player Player1;
    private Player Player2;
    public AiBrain aiBrain1;
    public AiBrain aiBrain2;
    private int turn = 1;
    public boolean gameOver = false;
    private Cell lastCellHit;
    private ArrayList<Cell> cellsHit = new ArrayList<Cell>();
    public Random rand = new Random();
    /**
     *     //used to access the object
     */
    public static Game returnGame() {
        return game;  
    }

    public Player getPlayer1() {
        return Player1;
    }

    public Player getPlayer2() {
        return Player2;
    }

    public void setPlayer1(Player player) {
        Player1 = player;
    }

    public void setPlayer2(Player player) {
        Player2 = player;
    }

    public int getTurn(){
        return this.turn;
    }




    public boolean HitHappened() {
        //0 = occupied
        //1 = nicht occupied aber valid
        //2 = nicht valid

            //Player vs Ai
        if(Player2.isAI() && !Player1.isAI() && !gameOver){
            System.out.println(turn);

            if (turn % 2 != 0) {
                turn += 1;
                //Player 1 turn
                toggleField(Player2, true);
                toggleField(Player1, false);

            } else {
                turn += 1;
                //Player 2 turn
                aiBrain2.hitCell();
            }

            if (checkWinningPlayer() == Player1) {
                System.out.println("Player1 Won");
            } else if (checkWinningPlayer() == Player2) {
                System.out.println("Player2 Won");
            }
            return true;

            //Ai vs Ai
        }else if(Player1.isAI() && Player2.isAI() && !gameOver){
            System.out.println(turn);

            if (turn % 2 != 0) {
                turn += 1;
                //Player 1 turn
                aiBrain1.hitCell();

            } else {
                turn += 1;
                //Player 2 turn
                aiBrain2.hitCell();
            }

            if (checkWinningPlayer() == Player1) {
                System.out.println("Player1 Won");
            } else if (checkWinningPlayer() == Player2) {
                System.out.println("Player2 Won");
            }
            return true;

            //Player vs Player
        }else if (!gameOver) {

            System.out.println("Hit Happened ");
            System.out.println(turn);
            if (turn % 2 != 0) {
                toggleField(Player2, true);
                toggleField(Player1, false);
            } else {
                toggleField(Player1, true);
                toggleField(Player2, false);
            }

            if (checkWinningPlayer() == Player1) {
                System.out.println("Player1 Won");
            } else if (checkWinningPlayer() == Player2) {
                System.out.println("Player2 Won");
            }

            turn += 1;
            return true;
        }
        return true;
    }

    public void showShips(Player player, boolean shown){
        GameBoard gameBoard = player.getGameBoard();

        for(Ship ship:gameBoard.ShipList){
            for(Cell cell:ship.coordinates){
                if(shown){
                    cell.fxButton.setOpacity(100);
                }else {
                    cell.fxButton.setOpacity(0);
                }

            }
        }

    }

    public void toggleField(Player player, boolean activate){
        GameBoard gameBoard = player.getGameBoard();

        for(Cell cell:gameBoard.getCells()){
            if(activate){
                cell.fxButton.setDisable(false);
            }else {
                cell.fxButton.setDisable(true);
            }
        }
    }

    public Object[] randomCell(Object[] cells) {
        int maxValue = cells.length;
        int value = rand.nextInt(maxValue);
        Object[] returnObjects = new Object[]{cells[value], value};
        return returnObjects;
    }

    public Player checkWinningPlayer() {
        if (Player1.checkIfLost()) {
            System.out.println("Player 2 Won");
            gameOver = true;
            return Player2;
        } else if (Player2.checkIfLost()) {
            System.out.println("Player 1 Won");
            gameOver = true;
            return Player1;
        }
        return null;
    }


}
