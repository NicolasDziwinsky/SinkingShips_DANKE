package com.example.sinkingships;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

        System.out.println(turn);
        if (turn%2 != 0) {
            toggleField(Player2, true);
            toggleField(Player1, false);
            turn +=1;
        } else {
            toggleField(Player1, true);
            toggleField(Player2, false);
            turn +=1;
        }


        return true;
    }

    //funktion
    //rand x
    //rand y


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
