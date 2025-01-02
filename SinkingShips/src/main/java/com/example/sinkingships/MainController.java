package com.example.sinkingships;

import java.io.IOException;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//all major logic should happen here, because in here all created objects can interact with one another
public class MainController {
    @FXML
    public TextField Name1;
    @FXML
    public TextField Name2;
    @FXML
    public CheckBox AiCheckbox;

    public Player Players[] = new Player[2];
    public SceneSwitcher SceneSwitcher = new SceneSwitcher();
    public MainMenuController MainMenuController = new MainMenuController();


    //This function is currently filled with examples for people to better understand
    public void generatePlayers() {
        //Generates Players and adds them to Players[]
        Player Player1 = new Player(Name1.getText(), false);
        Player Player2 = new Player(Name2.getText(), AiCheckbox.isSelected());
        Players[0] = Player1;
        Players[1] = Player2;

        //Output general Player info
        System.out.println(Players[0].playerInfo());
        System.out.println(Players[1].playerInfo());

        //Example how to manipulate the individual nodes of the gameboard
        Players[0].getGameBoard().getCell(2, 4).setOccupied();
        Players[0].getGameBoard().getCell(2, 5).setOccupied();
        Players[0].getGameBoard().getCell(2, 6).setOccupied();

        Players[0].getGameBoard().getCell(2, 4).setIsHit();
        Players[0].getGameBoard().getCell(3, 4).setIsHit();
        Players[0].getGameBoard().getCell(4, 4).setIsHit();

        //example of placing a ship on the board
        Ship shipHorizontal = new Ship(3, true);
        Players[0].getGameBoard().placeShip(shipHorizontal, Players[0].getGameBoard().getCell(5, 8));

        Ship shipVertical = new Ship(4, false);
        Players[0].getGameBoard().placeShip(shipVertical, Players[0].getGameBoard().getCell(9, 5));

        //example of checking if "shipVertical" is sunk
        Players[0].getGameBoard().getCell(9, 5).setIsHit();
        Players[0].getGameBoard().getCell(9, 6).setIsHit();
        Players[0].getGameBoard().getCell(9, 7).setIsHit();
        Players[0].getGameBoard().getCell(9, 8).setIsHit();

        System.out.println("Is Ship Sunk?: " + shipVertical.checkIfSunk());

        //example of checking if Player Lost
        //It only checks ships, not any other occupied cells
        System.out.println("Player 1 Lost?: " + Players[0].checkIfLost());


        //Outputs the Gameboard of Player1 in a comprehensible form into the Terminal
        Players[0].getGameBoard().outputTextVersion();
    }


    //This block abstracts the scene switching, which is actually handled by the SceneSwitcher
    public void switchToMainMenu(ActionEvent event) throws IOException {
        SceneSwitcher.switchToMainMenu(event);
    }
    public void switchToNewGame(ActionEvent event) throws IOException {
        SceneSwitcher.switchToNewGame(event);
    }
    public void switchToShipPlacement(ActionEvent event) throws IOException {
        SceneSwitcher.switchToShipPlacement(event);
    }
    public void switchToGame(ActionEvent event) throws IOException {
        SceneSwitcher.switchToGame(event);
    }




}