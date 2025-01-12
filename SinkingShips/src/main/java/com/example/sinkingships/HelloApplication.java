package com.example.sinkingships;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F) {
                System.out.println("Enter key was pressed in the scene!");
            }
        });

        stage.setTitle("SinkingShips");
        stage.setScene(scene);
        stage.setMaximized(true);
        System.out.println(stage.getHeight() + " " + stage.getWidth());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}