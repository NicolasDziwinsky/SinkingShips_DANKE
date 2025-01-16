package com.example.sinkingships;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GameGrid {
    GridPane gridPane;
    GameBoard gameBoard;
    //boolean hidden

    /**
     * Initializes the Gamefield for the specified player
     */
    public GameGrid(GridPane gridPane, Player player) {
        this.gridPane = gridPane;
        this.gameBoard = player.getGameBoard();

        //init the placement grid
        //1.Making the Grid interactable via invisible buttons
        int x = 1;
        int y = 1;
        int i = 0;

        while (i < 100) {
            Button button = new Button();
            button.setPrefSize(80,80);
            button.setOpacity(0);

            // Adds a class so you can do some css styling
            button.getStyleClass().add("gameCellButton");

            int finalX = x;
            int finalY = y;
            button.onActionProperty().set(event -> {onPress(finalX, finalY, player);});
            gridPane.add(button, x-1, y-1);
            player.getGameBoard().getCell(finalX, finalY).fxButton = button;
            player.getGameBoard().getCell(finalX, finalY).fxButton.setDisable(true);

            if (x == 10) {
                x = 1;
                y++;
            } else {
                x++;
            }
            i++;
        }
    }

    /**
     * On press, sets the pressed cell to hit
     */
    public void onPress(int x, int y, Player player) {
        Cell cell = player.getGameBoard().getCell(x, y);

        if (!cell.IsHit()) {
            cell.setIsHit();
            /*
            Image image = new Image(String.valueOf(getClass().getResource("/img/target_hit1.jpg")));
            ImageView HitImage = new ImageView();
            HitImage.setImage(image);
            HitImage.setFitHeight(70);
            HitImage.setFitWidth(70);
            gridPane.add(HitImage, x-1, y-1);
            */
            Game.returnGame().HitHappened();
        } else {
            System.out.println("Already Hit");
        }



        //Test Funktion

        if(cell.isOccupied()) {
            cell.fxButton.setText("Hit");
        }
        System.out.println(player.getName());
        player.getGameBoard().outputTextVersion();
        //
    }
}
