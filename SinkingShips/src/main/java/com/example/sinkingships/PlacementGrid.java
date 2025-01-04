package com.example.sinkingships;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PlacementGrid {
    GameBoard gameBoard;
    private GridPane gridPane;

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



    public void onPress(int x, int y) {
        gameBoard.getCell(x, y).setOccupied();
        gameBoard.outputTextVersion();

        gameBoard.getCell(x, y).fxButton.setOpacity(100);
    }

    public void resetField() {
        for (Cell cell : gameBoard.getCells()) {
            cell.fxButton.setOpacity(0);
        }
    }
}
