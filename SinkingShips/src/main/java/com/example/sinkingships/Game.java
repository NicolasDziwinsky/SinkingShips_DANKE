package com.example.sinkingships;

import com.sun.tools.javac.Main;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Random;

/**
 * //Singleton
 *     //used to share data between scenes
 */
public class Game {
    private static Player Player1;
    private static Player Player2;
    public static AiBrain aiBrain1;
    public static AiBrain aiBrain2;
    public static VBox winningScreen;

    // Standardizes the cell Size, so it can be applied from anywhere
    public static int cellSize = 70;

    private static int turn = 1;
    public static boolean gameOver = false;
    private static Cell lastCellHit;
    private static ArrayList<Cell> cellsHit = new ArrayList<Cell>();
    public static Random rand = new Random();
    static boolean placedRandom = false;
    /**
     *     //used to access the object
     */

    public static Player getPlayer1() {
        return Player1;
    }

    public static Player getPlayer2() {
        return Player2;
    }

    public static void setPlayer1(Player player) {
        Player1 = player;
    }

    public static void setPlayer2(Player player) {
        Player2 = player;
    }

    public static int getTurn(){
        return turn;
    }



    public static boolean HitHappened() {
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

    public static void showShips(Player player, boolean shown){
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

    public static void toggleField(Player player, boolean activate){
        GameBoard gameBoard = player.getGameBoard();

        for(Cell cell:gameBoard.getCells()){
            if(activate){
                cell.fxButton.setDisable(false);
            }else {
                cell.fxButton.setDisable(true);
            }
        }
    }

    public static Object[] randomCell(Object[] cells) {
        int maxValue = cells.length;
        int value = rand.nextInt(maxValue);
        return new Object[]{cells[value], value};
    }

    public static void checkWinningPlayer() {
        if (Player1.checkIfLost()) {
            System.out.println("Player 2 Won");
            gameOver = true;
            setWinningScreen(Player2, Player1);
            //show end screen
        } else if (Player2.checkIfLost()) {
            System.out.println("Player 1 Won");
            gameOver = true;
            setWinningScreen(Player1, Player2);
            //show end screen
        }
    }
    private static void setWinningScreen(Player winningPlayer, Player losingPlayer){
        if(winningScreen != null) {
            // Set up finish screen
            Label playerLabel = (Label) winningScreen.lookup("#WinningScreenPlayer");
            playerLabel.setText(winningPlayer.getName() + " Won");
            Label turnLabel = (Label) winningScreen.lookup("#WinningScreenTurns");
            turnLabel.setText("in " + turn + " turns");

            Thread animationThread = new Thread(() -> {
                try {
                    // Wait a while so the soundbites aren't overlapping with canon shots
                    Thread.sleep(600);
                    winningScreen.visibleProperty().set(true);

                    // Play 'won' or 'lost' soundbite
                    Soundboard sbToPlaySound = new Soundboard();
                    if(winningPlayer.isAI() && losingPlayer.isAI()){
                        sbToPlaySound.playCueWon();
                    } else if(winningPlayer.isAI() && !losingPlayer.isAI()){
                        sbToPlaySound.playCueLost();
                    } else if(!winningPlayer.isAI() && losingPlayer.isAI()){
                        sbToPlaySound.playCueWon();
                    } else if(!winningPlayer.isAI() && !losingPlayer.isAI()){
                        sbToPlaySound.playCueWon();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            animationThread.start();
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
