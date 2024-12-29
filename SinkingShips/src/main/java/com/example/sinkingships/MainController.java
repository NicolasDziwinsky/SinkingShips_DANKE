package com.example.sinkingships;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

    public SceneSwitcher SceneSwitcher = new SceneSwitcher();

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