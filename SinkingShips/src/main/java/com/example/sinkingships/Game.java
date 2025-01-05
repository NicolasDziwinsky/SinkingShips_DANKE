package com.example.sinkingships;

import javafx.scene.control.Button;
/**
 * //Singleton
 *     //used to share data between scenes
 */
public class Game {

    private static final Game game = new Game();

    private Game() {}
    private Player Player1;
    private Player Player2;
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

}
