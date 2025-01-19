package com.example.sinkingships;

import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Random;

/**
 * //Singleton
 *     //used to share data between scenes
 */
public class Game {
    static private Player Player1;
    static private Player Player2;
    static public AiBrain aiBrain1;
    static public AiBrain aiBrain2;
    static public StackPane winningScreen;

    // Standardizes the cell Size, so it can be applied from anywhere
    static public int cellSize = 70;

    static private int turn = 1;
    static public boolean gameOver = false;
    static private Cell lastCellHit;
    static private ArrayList<Cell> cellsHit = new ArrayList<Cell>();
    static public Random rand = new Random();
    static boolean placedRandom = false;
    /**
     *     //used to access the object
     */

    static public Player getPlayer1() {
        return Player1;
    }

    static public Player getPlayer2() {
        return Player2;
    }

    static public void setPlayer1(Player player) {
        Player1 = player;
    }

    static public void setPlayer2(Player player) {
        Player2 = player;
    }

    static public int getTurn(){
        return turn;
    }



    static public boolean HitHappened() {
        //0 = occupied
        //1 = nicht occupied aber valid
        //2 = nicht valid

            //Player vs Ai
        if(Player2.isAI() && !Player1.isAI() && !gameOver){
            System.out.println(turn);
            checkWinningPlayer();
            if (turn % 2 != 0) {
                turn += 1;
                //Player 1 turn
                System.out.println("Player 1 Turn");
                toggleField(Player2, true);
                toggleField(Player1, false);

            } else {
                turn += 1;
                //Player 2 turn
                System.out.println("Player 2 Turn");
                aiBrain2.hitCell(true);
            }

            return true;

            //Ai vs Ai
        }else if(Player1.isAI() && Player2.isAI() && !gameOver){
            System.out.println(turn);
            System.out.println("Checking if won");
            checkWinningPlayer();

            if (turn % 2 != 0) {
                turn += 1;
                //Player 1 turn
                aiBrain1.hitCell(true);

            } else {
                turn += 1;
                //Player 2 turn

                aiBrain2.hitCell(true);
            }
            return true;

            //Player vs Player
        }else if (!gameOver) {
            checkWinningPlayer();

            System.out.println("Hit Happened ");
            System.out.println(turn);
            checkWinningPlayer();
            if (turn % 2 != 0) {
                toggleField(Player2, true);
                toggleField(Player1, false);
            } else {
                toggleField(Player1, true);
                toggleField(Player2, false);
            }


            turn += 1;
            return true;
        }
        return true;
    }

    static public void showShips(Player player, boolean shown){
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

    static public void toggleField(Player player, boolean activate){
        GameBoard gameBoard = player.getGameBoard();

        for(Cell cell:gameBoard.getCells()){
            if(activate){
                cell.fxButton.setDisable(false);
            }else {
                cell.fxButton.setDisable(true);
            }
        }
    }

    static public Object[] randomCell(Object[] cells) {
        int maxValue = cells.length;
        int value = rand.nextInt(maxValue);
        Object[] returnObjects = new Object[]{cells[value], value};
        return returnObjects;
    }

    static public void checkWinningPlayer() {
        if (Player1.checkIfLost()) {
            System.out.println("Player 2 Won");
            gameOver = true;
            winningScreen.visibleProperty().set(true);
            //show end screen
        } else if (Player2.checkIfLost()) {
            System.out.println("Player 1 Won");
            gameOver = true;
            winningScreen.visibleProperty().set(true);
            //show end screen
        }
    }

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

    //shows screen(Player)

}
