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



    public String boardInfo() {
        return null;
    }

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