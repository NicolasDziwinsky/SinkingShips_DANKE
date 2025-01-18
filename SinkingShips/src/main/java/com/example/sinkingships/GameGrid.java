package com.example.sinkingships;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameGrid {
    GridPane gridPane;
    GameBoard gameBoard;
    public List<Image> HitImages = new ArrayList<Image>();
    public List<Image> MissImages = new ArrayList<Image>();
    //boolean hidden

    /**
     * Initializes the Gamefield for the specified player
     */
    public GameGrid(GridPane gridPane, Player player) {
        this.gridPane = gridPane;
        this.gameBoard = player.getGameBoard();

        //init the placement grid
        HitImages.add(new Image(String.valueOf(getClass().getResource("/img/target_hit1.png"))));
        HitImages.add(new Image(String.valueOf(getClass().getResource("/img/target_hit2.png"))));
        HitImages.add(new Image(String.valueOf(getClass().getResource("/img/target_hit3.png"))));
        HitImages.add(new Image(String.valueOf(getClass().getResource("/img/target_hit4.png"))));
        HitImages.add(new Image(String.valueOf(getClass().getResource("/img/target_hit5.png"))));
        HitImages.add(new Image(String.valueOf(getClass().getResource("/img/target_hit6.png"))));

        MissImages.add(new Image(String.valueOf(getClass().getResource("/img/target_missed1.png"))));
        MissImages.add(new Image(String.valueOf(getClass().getResource("/img/target_missed2.png"))));
        MissImages.add(new Image(String.valueOf(getClass().getResource("/img/target_missed3.png"))));
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
    public int onPress(int x, int y, Player player) {
        Cell cell = player.getGameBoard().getCell(x, y);

        Random rand = new Random();
        int randIntTill5 = rand.nextInt(6);
        int randIntTill2 = rand.nextInt(3);

        if (!cell.IsHit() && Game.gameOver == false) {
            cell.setIsHit();
            if (cell.isOccupied()) {
                cell.image = new ImageView();
                cell.image.setImage(HitImages.get(randIntTill5));
                cell.image.setFitWidth(70);
                cell.image.setFitHeight(70);
                gridPane.add(cell.image, x-1, y-1);
                Game.HitHappened();
                return 0;
            } else {
                cell.image = new ImageView();
                cell.image.setImage(MissImages.get(randIntTill2));
                cell.image.setFitWidth(70);
                cell.image.setFitHeight(70);
                gridPane.add(cell.image, x-1, y-1);
                Game.HitHappened();
                return 1;

            }
        } else if(Game.gameOver) {
            System.out.println("Game Over");
        } else {
            System.out.println("Already Hit");
            return 2;
        }
        return 0;
    }

    public int onPress(Cell cell, Player player) {

        int x = cell.getX();
        int y = cell.getY();

        Random rand = new Random();
        int randIntTill5 = rand.nextInt(6);
        int randIntTill2 = rand.nextInt(3);

        if (!cell.IsHit() && Game.gameOver == false) {
            cell.setIsHit();
            if (cell.isOccupied()) {
                cell.image = new ImageView();
                cell.image.setImage(HitImages.get(randIntTill5));
                cell.image.setFitWidth(70);
                cell.image.setFitHeight(70);
                gridPane.add(cell.image, x-1, y-1);
                Game.HitHappened();
                return 0;
            } else {
                cell.image = new ImageView();
                cell.image.setImage(MissImages.get(randIntTill2));
                cell.image.setFitWidth(70);
                cell.image.setFitHeight(70);
                gridPane.add(cell.image, x-1, y-1);
                Game.HitHappened();
                return 1;

            }
        } else if(Game.gameOver) {
            System.out.println("Game Over");
        } else {
            System.out.println("Already Hit");
            return 2;
        }
        return 0;
    }
}
