package com.example.sinkingships;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainMenu.fxml"));

        // Setting the scene size based on the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Scene scene = new Scene(fxmlLoader.load(), screenSize.getWidth()-100, screenSize.getHeight()-100);

        // Setting a custom cursor
        Image imageForCursor = new Image(String.valueOf(getClass().getResource("/img/cursor_noborder.png")));
        scene.setCursor(new ImageCursor(imageForCursor, 48, 48));

        // Set the icon for the program
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/img/icon_small.png"))));

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F) {
                System.out.println("Enter key was pressed in the scene!");
            }
        });

        stage.setTitle("KRIZL SHIFF");
        stage.setScene(scene);
        // Emma turned off the maximized setting for now ;)
        //stage.setMaximized(true);
        System.out.println(stage.getHeight() + " " + stage.getWidth());
        stage.show();
    }

    public static void main(String[] args) {
        // setProperty lines are added to deal with font rendering issues
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.text", "t2k");
        launch();
    }
}