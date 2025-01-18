package com.example.sinkingships;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Used to initialize and interact with the JavaFX Grid, and place down occupied cells
 */
public class PlacementGrid {
    GameBoard gameBoard;
    public GridPane gridPane;
    public ImageView shipPreview;


    /**
     * Class constructor, also adds a button to every single cell to make them interactable
     */
    PlacementGrid(GridPane GridPane, GameBoard gameBoard, ImageView shipPreview) {
        this.gridPane = GridPane;
        this.gameBoard = gameBoard;
        this.shipPreview = shipPreview;

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
            button.getStyleClass().add("placementCellButton");

            int finalX = x;
            int finalY = y;
            button.onActionProperty().set(event -> {onPress(finalX, finalY);});
            this.gridPane.add(button, x-1, y-1);
            gameBoard.getCell(finalX, finalY).fxButton = button;

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
     * The code to be run when a cell is pressed, in this case the cell will be marked as occupied
     */
    public void onPress(int x, int y) {
        Game.placedRandom = true;
        gameBoard.placeNextShip(gameBoard.getCell(x, y));
        gameBoard.outputTextVersion();
    }
    /**
     * Currently all images will be removed
     */
    public void resetField() {
        for (Cell cell : gameBoard.getCells()) {
            if (cell.image != null) cell.image.imageProperty().set(null);
        }
    }

    public void setShipPreview(Image image) {
        shipPreview.setImage(image);
    }
}
