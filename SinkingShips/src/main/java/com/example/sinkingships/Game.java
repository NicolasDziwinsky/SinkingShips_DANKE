package com.example.sinkingships;

import javafx.scene.control.Button;
/**
 * //Singleton
 *     //used to share data between scenes
 */
public class Game {

    private static final Game game = new Game();


    private Player Player1;
    private Player Player2;
    private int turn = 1;
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
        System.out.println("Hit Happened ");


        if(turn > 4 ){
            turn = 1;
        }



        switch (turn) {
            case 1:
                showShips(getPlayer1(), true);
                if(Game.returnGame().getPlayer1().checkIfLost()){
                    System.out.println("Player 2 wins.");
                    return true;

                }
                break;
            case 2,4:
                //Zwischenzug
                showShips(getPlayer1(), false);
                showShips(getPlayer2(), false);
                //display text
                break;
            case 3:
                showShips(getPlayer2(), true);
                if(Game.returnGame().getPlayer2().checkIfLost()){
                    System.out.println("Player 1 wins.");
                    return true;

                }


        }
        turn++;



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



    //game.getplayerx.getgameboard.getcell(x,y).fxbutton.setopacity()









}
