package com.example.sinkingships;

import java.io.IOException;
import java.util.Timer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class SceneSwitcher {

    Stage stage;
    Scene scene;
    Parent root;


    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToNewGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("NewGame.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToShipPlacement(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ShipPlacement.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        scene.setOnKeyPressed(event2 -> {
            if (event2.getCode() == KeyCode.F) {
                System.out.println("Enter key was pressed in the scene!");
                Game.returnGame().HitHappened();
            }
        });
        stage.setScene(scene);
        stage.show();
    }

}