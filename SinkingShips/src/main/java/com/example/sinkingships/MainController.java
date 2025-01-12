package com.example.sinkingships;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

//all major logic should happen here, because in here all created objects can interact with one another
public class MainController {
    @FXML
    public TextField Name1;
    @FXML
    public TextField Name2;
    @FXML
    public CheckBox AiCheckbox;
    @FXML
    public GridPane PlacementGridFX;
    @FXML
    public GridPane ContainerGrid;
    @FXML
    public Label PlayerNamePlacement;
    @FXML
    public Button StartGame;
    @FXML
    public GridPane Map1;
    @FXML
    public GridPane Map2;

    public int gridCounter = 0;
    public SceneSwitcher SceneSwitcher = new SceneSwitcher();

    /**
     * Test Function
     */
    public void generatePlayersTest() {
        //Generates Players and adds them to the GameInstance
        Game.returnGame().setPlayer1(new Player(Name1.getText(), false));
        Game.returnGame().setPlayer2(new Player(Name1.getText(), false));


        //Output general Player info
        System.out.println(Game.returnGame().getPlayer1().playerInfo());
        System.out.println(Game.returnGame().getPlayer1().playerInfo());

        //Example how to manipulate the individual nodes of the gameboard
        Game.returnGame().getPlayer1().getGameBoard().getCell(2, 4).setOccupied();
        Game.returnGame().getPlayer1().getGameBoard().getCell(2, 5).setOccupied();
        Game.returnGame().getPlayer1().getGameBoard().getCell(2, 6).setOccupied();

        Game.returnGame().getPlayer1().getGameBoard().getCell(2, 4).setIsHit();
        Game.returnGame().getPlayer1().getGameBoard().getCell(3, 4).setIsHit();
        Game.returnGame().getPlayer1().getGameBoard().getCell(4, 4).setIsHit();

        //example of placing a ship on the board
        Ship shipHorizontal = new Ship(3, true);
        Game.returnGame().getPlayer1().getGameBoard().placeShip(shipHorizontal, Game.returnGame().getPlayer1().getGameBoard().getCell(5, 8));

        Ship shipVertical = new Ship(4, false);
        Game.returnGame().getPlayer1().getGameBoard().placeShip(shipVertical, Game.returnGame().getPlayer1().getGameBoard().getCell(9, 5));

        //example of checking if "shipVertical" is sunk
        Game.returnGame().getPlayer1().getGameBoard().getCell(9, 5).setIsHit();
        Game.returnGame().getPlayer1().getGameBoard().getCell(9, 6).setIsHit();
        Game.returnGame().getPlayer1().getGameBoard().getCell(9, 7).setIsHit();
        Game.returnGame().getPlayer1().getGameBoard().getCell(9, 8).setIsHit();

        System.out.println("Is Ship Sunk?: " + shipVertical.checkIfSunk());

        //example of checking if Player Lost
        //It only checks ships, not any other occupied cells
        System.out.println("Player 1 Lost?: " + Game.returnGame().getPlayer1().checkIfLost());


        //Outputs the Gameboard of Player1 in a comprehensible form into the Terminal
        Game.returnGame().getPlayer1().getGameBoard().outputTextVersion();
    }
    /**
     * Generates two players and adds them to the Game object
     */
    public void generatePlayers() {
        Game.returnGame().setPlayer1(new Player(Name1.getText(), false));
        Game.returnGame().setPlayer2(new Player(Name2.getText(), false));
    }

    //This block abstracts the scene switching, which is actually handled by the SceneSwitcher
    public void switchToMainMenu(ActionEvent event) throws IOException {
        SceneSwitcher.switchToMainMenu(event);
    }
    public void switchToNewGame(ActionEvent event) throws IOException {
        SceneSwitcher.switchToNewGame(event);
    }
    public void switchToShipPlacement(ActionEvent event) throws IOException, InterruptedException {
        generatePlayers();
        SceneSwitcher.switchToShipPlacement(event);
    }
    public EventHandler<ActionEvent> switchToGame(ActionEvent event) throws IOException {
        SceneSwitcher.switchToGame(event);
        return null;
    }




    /**
     * //Sets up the Placement Grid for the User, the first one is for player 1, second one for player 2
     * //Potential for improvement
     */
    public void initPlacementGrid(int Player) {
        Game game = Game.returnGame();
        if (Player == 0) {
            game.getPlayer1().getGameBoard().placementGrid = new PlacementGrid(PlacementGridFX, game.getPlayer1().getGameBoard());
            PlayerNamePlacement.setText(Game.returnGame().getPlayer1().getName());
        } else {
            game.getPlayer2().getGameBoard().placementGrid = new PlacementGrid(PlacementGridFX, game.getPlayer2().getGameBoard());
            PlayerNamePlacement.setText(Game.returnGame().getPlayer2().getName());
        }
    }
    /**
     * //Sets up the Placement Grid for the User, the first one is for player 1, second one for player 2
     * //Potential for improvement
     */
    public void nextGrid(ActionEvent event) throws IOException {
        Game game = Game.returnGame();
        if (gridCounter == 0) {
            StartGame.setText("Next Player");
        } else if (gridCounter == 1) {
            game.getPlayer1().getGameBoard().placementGrid.resetField();
            StartGame.setText("Start Game");
        } else if (gridCounter == 2) {
            switchToGame(event);
        }
        initPlacementGrid(gridCounter);
        gridCounter++;
    }
    /**
     * Initializes both gamefields
     */
    public void StartGame() {
        Game game = Game.returnGame();
        game.getPlayer1().gameGrid = new GameGrid(Map1, game.getPlayer1());
        game.getPlayer2().gameGrid = new GameGrid(Map2, game.getPlayer2());
    }




}