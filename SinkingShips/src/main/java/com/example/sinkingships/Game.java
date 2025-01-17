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


        if(Player2.isAI() && !Player1.isAI()){
            if (!gameOver) {
                System.out.println("Hit Happened ");
                System.out.println(turn);
                if (turn % 2 != 0) {
                    turn += 1;
                    //Player 1 turn
                    toggleField(Player2, true);
                    toggleField(Player1, false);


                } else{
                    turn += 1;
                    //Player 2 turn
                    Cell[] cells = getPlayer1().getGameBoard().getCells();


                    if (cellsHit.isEmpty()) {
                        cellsHit.addAll(Arrays.asList(cells));
                    }

                    Object randResult[] = randomCell(cellsHit.toArray(new Cell[cellsHit.size()]));

                    Cell randCell = (Cell) randResult[0];
                    int randInt = (int) randResult[1];

                    if(getPlayer1().gameGrid.onPress(randCell, getPlayer1()) == 0){
                        cellsHit.remove(cellsHit.get(randInt));
                        System.out.println("Hit");
                    }else {
                        cellsHit.remove(cellsHit.get(randInt));
                        System.out.println("No Hit");
                    }

                }

                if (Player1.checkIfLost()) {
                    System.out.println("Player 2 Won");
                    gameOver = true;
                    return true;
                } else if (Player2.checkIfLost()) {
                    System.out.println("Player 1 Won");
                    gameOver = true;
                    return true;
                }

                getPlayer1().getGameBoard().outputTextVersion();
                getPlayer2().getGameBoard().outputTextVersion();
                //turn += 1;
                return true;
            }

        }else if(Player1.isAI() && Player2.isAI()){

        }else {
            if (!gameOver) {
                System.out.println("Hit Happened ");
                System.out.println(turn);
                if (turn % 2 != 0) {
                    toggleField(Player2, true);
                    toggleField(Player1, false);
                } else {
                    toggleField(Player1, true);
                    toggleField(Player2, false);
                }

                if (Player1.checkIfLost()) {
                    System.out.println("Player 2 Won");
                    gameOver = true;
                    return true;
                } else if (Player2.checkIfLost()) {
                    System.out.println("Player 1 Won");
                    gameOver = true;
                    return true;
                }

                turn += 1;
                return true;
            }
        }

        return true;
    }

    //funktion
    //rand x
    //rand y


    public int getCoordinate(int maxValue){
        Random rand = new Random();

        return rand.nextInt(maxValue);
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


    //game.getplayerx.getgameboard.getcell(x,y).fxbutton.setopacity()


}
