package com.example.sinkingships;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        SceneSwitcher.root = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("MainMenu.fxml")));
        SceneSwitcher.stage = stage;

        // Setting the scene size based on the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        SceneSwitcher.currentWindowHeight = screenSize.height-100;
        SceneSwitcher.currentWindowWidth = screenSize.width-100;

        // Plays random 'Krizelshiff' soundbyte
        Soundboard soundboardForIntro = new Soundboard();
        soundboardForIntro.playKrizelshiff();

        SceneSwitcher.setUpNewScene();

        SceneSwitcher.scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F) {
                System.out.println("Enter key was pressed in the scene!");
            }
        });

        System.out.println(stage.getHeight() + " " + stage.getWidth());

        // Set up listeners for screen size changes
        SceneSwitcher.stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            SceneSwitcher.adjustScreenSizeChange();
        });
        SceneSwitcher.stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            SceneSwitcher.adjustScreenSizeChange();
        });
        SceneSwitcher.stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            SceneSwitcher.adjustScreenSizeChange();
        });

        SceneSwitcher.calculateCellAndFontSize();
    }

    public static void main(String[] args) {
        // setProperty is added to deal with font rendering issues
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.text", "t2k");
        launch();
    }
}