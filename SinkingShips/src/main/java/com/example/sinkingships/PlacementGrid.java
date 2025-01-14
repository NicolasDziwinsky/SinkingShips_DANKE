package com.example.sinkingships;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Used to initialize and interact with the JavaFX Grid, and place down occupied cells
 */
public class PlacementGrid {
    GameBoard gameBoard;
    private GridPane gridPane;


    /**
     * Class constructor, also adds a button to every single cell to make them interactable
     */
    PlacementGrid(GridPane GridPane, GameBoard gameBoard) {
        this.gridPane = GridPane;
        this.gameBoard = gameBoard;

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
        gameBoard.placeNextShip(gameBoard.getCell(x, y));
        gameBoard.outputTextVersion();
    }
    /**
     * Currently all cells will be reset
     */
    public void resetField() {
        for (Cell cell : gameBoard.getCells()) {
            cell.fxButton.setOpacity(0);
        }
    }
}
