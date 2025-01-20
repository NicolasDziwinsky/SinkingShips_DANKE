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

    /**
     * The current stage.
     */
    public static Stage stage;
    /**
     * The current scene.
     */
    public static Scene scene;
    /**
     * The current root.
     */
    public static Parent root;

    /**
     * Stores the current width of the current window.
     */
    public static double currentWindowWidth;
    /**
     * Stores the current height of the current window.
     */
    public static double currentWindowHeight;
    /**
     * Stores the minimum width for displayed windows.
     */
    public static int minWindowWidth = 1400;
    /**
     * Store the minimum height for display windows.
     */
    public static int minWindowHeight = 800;
    /**
     * Tells you if the current window is currently maximized.
     */
    public static boolean windowIsMaximized;

    /**
     * A soundboard object to play the sound cues for new scenes with.
     */
    private static final Soundboard soundboardForIntros = new Soundboard();



    /**
     * Switch to the main menu scene.
     */
    public static void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("MainMenu.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        setUpNewScene();

        // Plays random 'Krizelshiff' soundbyte
        soundboardForIntros.playKrizelshiff();
    }
    /**
     * Switch to the new game scene.
     */
    public static void switchToNewGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("NewGame.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        setUpNewScene();
        soundboardForIntros.playPaper();
    }
    /**
     * Switch to the ship placement scene.
     */
    public static void switchToShipPlacement(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("ShipPlacement.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        setUpNewScene();

        // Plays random 'Ran an's gekrizel' soundbyte
        soundboardForIntros.playCueGo();
    }
    /**
     * Switch to the game scene.
     */
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

    /**
     * Set up everything that needs to be set up when the game switches to a new scene.
     */
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

    /**
     * Stores the current screen size into variables and recalculates the new sizing for GUI elements.
     */
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
            int fontSize = Game.cellSize/2;
            if(Game.cellSize > 90){
                fontSize = 45;
            }
            root.setStyle("-fx-font-size: " + fontSize + "px;");
        }
    }

}