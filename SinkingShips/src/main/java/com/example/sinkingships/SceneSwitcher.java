package com.example.sinkingships;

import java.awt.*;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class SceneSwitcher {

    Stage stage;
    Scene scene;
    Parent root;


    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        setUpNewScene(event);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToNewGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("NewGame.fxml"));
        setUpNewScene(event);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToShipPlacement(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ShipPlacement.fxml"));
        setUpNewScene(event);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Game.fxml"));
        setUpNewScene(event);

        scene.setOnKeyPressed(event2 -> {
            if (event2.getCode() == KeyCode.F) {
                System.out.println("Enter key was pressed in the scene!");
                Game.returnGame().HitHappened();
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    private void setUpNewScene(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Setting the scene size based on the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        scene = new Scene(root, screenSize.width-100, screenSize.height-100);

        // Setting a custom cursor
        javafx.scene.image.Image imageForCursor = new Image(String.valueOf(getClass().getResource("/img/cursor_noborder.png")));
        scene.setCursor(new ImageCursor(imageForCursor, 48, 48));
    }

}