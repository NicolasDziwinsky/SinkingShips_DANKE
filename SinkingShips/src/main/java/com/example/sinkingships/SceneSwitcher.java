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

    Soundboard soundboardForIntros = new Soundboard();

    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        setUpNewScene(event);

        // Plays random 'Krizelshiff' soundbyte
        soundboardForIntros.playKrizelshiff();
    }

    public void switchToNewGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("NewGame.fxml"));
        setUpNewScene(event);
        soundboardForIntros.playPaper();
    }

    public void switchToShipPlacement(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ShipPlacement.fxml"));
        setUpNewScene(event);

        // Plays random 'Ran an's gekrizel' soundbyte
        soundboardForIntros.playCueGo();
    }

    public void switchToGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Game.fxml"));
        setUpNewScene(event);

        scene.setOnKeyPressed(event2 -> {
            if (event2.getCode() == KeyCode.F) {
                System.out.println("Enter key was pressed in the scene!");
                Game.HitHappened();
            }
        });

        // Plays random 'Ran an's gekrizel' soundbyte
        soundboardForIntros.playCueGo();
    }

    private void setUpNewScene(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Setting the scene size based on the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        scene = new Scene(root, screenSize.width-100, screenSize.height-100);

        // Setting a custom cursor
        Image imageForCursor = new Image(String.valueOf(getClass().getResource("/img/cursor_noborder.png")));
        scene.setCursor(new ImageCursor(imageForCursor, 48, 48));

        // Set the icon for the program
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/img/icon_small.png"))));
        stage.setScene(scene);
        stage.show();
    }

}