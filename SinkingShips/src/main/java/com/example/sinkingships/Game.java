package com.example.sinkingships;

import java.util.ArrayList;
import java.util.Arrays;
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
                    //Player 1 turn
                    toggleField(Player2, true);
                    toggleField(Player1, false);

                } else {
                    //Player 2 turn
                    System.out.println("Hallo");
                    int randInt = getCoordinate();
                    Cell[] cells = getPlayer1().getGameBoard().getCells();
                    boolean validFound = false;

                    if(!cellsHit.isEmpty() ){
                        while (!validFound){
                            randInt = getCoordinate();
                            for(Cell cell:cellsHit){
                                if(Arrays.asList(cells).contains(cell)){
                                    validFound = true;
                                }
                            }
                        }
                    }


                    int value = getPlayer1().gameGrid.onPress(cells[randInt].getX(), cells[randInt].getY(), getPlayer1());


                    if(value == 0){
                        cellsHit.add(cells[randInt]);

                    }else if(value == 1) {
                        cellsHit.add(cells[randInt]);
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

                turn += 1;
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

    public void AiTurn(){
        //make hit at random cell
        //if cell is already hit AiTurn()
        GameBoard gameBoard = getPlayer1().getGameBoard();
        Cell[] cell = gameBoard.getCells();
        int coordinate = getCoordinate();


        if(cell[coordinate].IsHit()){
            AiTurn();
            return;
        }else {
            if(cell[coordinate].isOccupied()){

            }else {
                //make turn normally
            }
        }

        //if cell is valid mark cell as hit
        //if cell was occupied
          //make next guess to be adjacent
    }

    public int getCoordinate(){
        Random rand = new Random();

        return rand.nextInt(100);
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


    //game.getplayerx.getgameboard.getcell(x,y).fxbutton.setopacity()


}
