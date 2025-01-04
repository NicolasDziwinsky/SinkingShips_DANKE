package com.example.sinkingships;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainMenu.fxml"));
        System.setProperty("prism.allowhidpi", "false");

        double scaleFacor = Screen.getPrimary().getOutputScaleX();
        double baseWidth = 1920;
        double baseHeight = 1080;

        double scaledWidth = baseWidth / scaleFacor;
        double scaledHeight = baseHeight / scaleFacor;
        Scene scene = new Scene(fxmlLoader.load(), scaledWidth, scaledHeight);

        stage.setTitle("SinkingShips");
        stage.setScene(scene);
        stage.sizeToScene();
       /*stage.setMinWidth(650);
        stage.setMinHeight(650);*/

        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) { System.setProperty("prism.allowhidpi", "false");
        launch();
    }
}

