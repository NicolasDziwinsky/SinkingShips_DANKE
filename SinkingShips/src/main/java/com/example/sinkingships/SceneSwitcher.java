package com.example.sinkingships;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

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

    public static Stage stage;
    public static Scene scene;
    public static Parent root;

    public static double currentWindowWidth, currentWindowHeight;
    public static int minWindowWidth = 1400;
    public static int minWindowHeight = 800;
    public static boolean windowIsMaximized;

    private static final Soundboard soundboardForIntros = new Soundboard();

    public static void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("MainMenu.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        setUpNewScene();

        // Plays random 'Krizelshiff' soundbyte
        soundboardForIntros.playKrizelshiff();
    }

    public static void switchToNewGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("NewGame.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        setUpNewScene();
        soundboardForIntros.playPaper();
    }

    public static void switchToShipPlacement(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("ShipPlacement.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        setUpNewScene();

        // Plays random 'Ran an's gekrizel' soundbyte
        soundboardForIntros.playCueGo();
    }

    public static void switchToGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("Game.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        setUpNewScene();

        scene.setOnKeyPressed(event2 -> {
            if (event2.getCode() == KeyCode.F) {
                System.out.println("Enter key was pressed in the scene!");
                Game.HitHappened();
            }
        });

        // Plays random 'Ran an's gekrizel' soundbyte
        soundboardForIntros.playCueGo();
    }

    public static void setUpNewScene(){
        calculateCellAndFontSize();

        // Setting the scene size based on the screen size
        scene = new Scene(root);

        // Set the screen size to be the same as it was in the previous scene
        if(windowIsMaximized){
            stage.setMaximized(true);
        } else {
            stage.setHeight(currentWindowHeight);
            stage.setWidth(currentWindowWidth);
            stage.setMaximized(false);
        }

        // Setting a custom cursor
        Image imageForCursor = new Image(String.valueOf(SceneSwitcher.class.getResource("/img/cursor_noborder.png")));
        scene.setCursor(new ImageCursor(imageForCursor, 48, 48));

        // Set the icon for the program
        stage.getIcons().add(new Image(String.valueOf(SceneSwitcher.class.getResource("/img/icon_small.png"))));
        stage.setScene(scene);
        stage.show();
        stage.setMinHeight(minWindowHeight);
        stage.setMinWidth(minWindowWidth);
        stage.setTitle("KRIZLSHIFF");
    }
    public static void adjustScreenSizeChange(){
        currentWindowHeight = stage.getHeight();
        currentWindowWidth = stage.getWidth();
        windowIsMaximized = stage.isMaximized();
        calculateCellAndFontSize();
    }

    /**
     * Calculates the ideal cell size for the current window size and saves it in the game class
     */
    public static void calculateCellAndFontSize(){
        int idealHeight = (int)SceneSwitcher.currentWindowHeight-270;
        int idealWidth = (int)SceneSwitcher.currentWindowWidth/2-220;

        // Different fonz size settings for maximized window
        if(stage != null && stage.isMaximized()){
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            idealHeight = screenSize.height-320;
            idealWidth = screenSize.width/2-220;
        }

        if(idealHeight > idealWidth) {
            Game.cellSize = idealWidth/10;
        } else {
            Game.cellSize = idealHeight/10;
        }
        if(root != null){
            root.setStyle("-fx-font-size: " + String.valueOf(Game.cellSize/2) + "px;");
        }
    }

}