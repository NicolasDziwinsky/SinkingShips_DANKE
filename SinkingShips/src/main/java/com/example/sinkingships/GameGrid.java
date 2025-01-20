package com.example.sinkingships;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameGrid {
    GridPane gridPane;
    GameBoard gameBoard;
    MainController mainController;
    private ImageView ivToDisplayHover;
    public List<Image> HitImages = new ArrayList<Image>();
    public List<Image> MissImages = new ArrayList<Image>();
    //boolean hidden

    /**
     * Initializes the Gamefield for the specified player
     */
    public GameGrid(GridPane gridPane, Player player, MainController mainController) {
        this.gridPane = gridPane;
        this.gameBoard = player.getGameBoard();
        this.mainController = mainController;

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
            button.setPrefSize(Game.cellSize,Game.cellSize);
            button.setOpacity(0);

            // Adds a class so you can do some css styling
            button.getStyleClass().add("gameCellButton");

            int finalX = x;
            int finalY = y;
            button.onActionProperty().set(event -> {onPress(finalX, finalY, player);});
            button.onMouseEnteredProperty().set(this::handleCellMouseEnter);
            button.onMouseExitedProperty().set(this::handleCellMouseExit);
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

        setUpHoverDisplay();
    }

    /**
     * On press, sets the pressed cell to hit
     */
    public int onPress(int x, int y, Player player) {
        Cell cell = player.getGameBoard().getCell(x, y);
        return onPressContainer(cell, x, y, player);
    }
    public int onPress(Cell cell, Player player) {
        int x = cell.getX();
        int y = cell.getY();
        return onPressContainer(cell, x, y, player);
    }

    private int onPressContainer(Cell cell, int x, int y, Player player) {
        Random rand = new Random();
        int randIntTill5 = rand.nextInt(6);
        int randIntTill2 = rand.nextInt(3);

        if (!cell.IsHit() && !Game.gameOver) {
            cell.setIsHit();
            if (cell.isOccupied()) {
                addImageToGridPane(HitImages.get(randIntTill5), cell, x, y);
                animateTheCellHit(cell, player, true);
                Game.HitHappened();
                return 0;
            } else {
                addImageToGridPane(MissImages.get(randIntTill2), cell, x, y);
                animateTheCellHit(cell, player, false);
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
    private void addImageToGridPane(Image image, Cell cell, int x, int y) {
        cell.image = new ImageView();
        cell.image.setImage(image);
        gridPane.add(cell.image, x-1, y-1);

        // Makes the image resize together with the GridPane
        cell.image.fitWidthProperty().bind(gridPane.widthProperty().multiply(0.1));
        cell.image.fitHeightProperty().bind(gridPane.heightProperty().multiply(0.1));
    }

    // Methods to handle the mouse over shooting cursor on cells
    private void setUpHoverDisplay(){
        Image imageForCursor = new Image(String.valueOf(getClass().getResource("/img/cursor_shoot.png")));
        ivToDisplayHover = new ImageView();
        ivToDisplayHover.setImage(imageForCursor);
        ivToDisplayHover.setMouseTransparent(true);
    }
    private void handleCellMouseEnter(MouseEvent event) {
        Button thisButton = (Button) event.getSource();
        mainController.autoRotateInGameCanons((Button)event.getSource());
        ivToDisplayHover.setFitHeight(Game.cellSize);
        ivToDisplayHover.setFitWidth(Game.cellSize);
        ivToDisplayHover.setLayoutX(thisButton.localToScene(0,0).getX());
        ivToDisplayHover.setLayoutY(thisButton.localToScene(0,0).getY());
        mainController.RootPane.getChildren().add(ivToDisplayHover);
    }
    private void handleCellMouseExit(MouseEvent event) {
        mainController.RootPane.getChildren().remove(ivToDisplayHover);
    }

    /**
     * Tells the main controller to play sounds and animates stuff for the hit cell.
     * @param cellToAnimate The cell to animate.
     * @param currentPlayer The current player.
     * @param isHit Is true if the cell contained a ship that was hit.
     */
    private void animateTheCellHit(Cell cellToAnimate, Player currentPlayer, boolean isHit){
        if(!Game.getPlayer1().isAI() || !Game.getPlayer2().isAI()) {
            Thread animationThread = new Thread(() -> {
                Image oldImage = cellToAnimate.image.getImage();
                cellToAnimate.image.setImage(null);

                cellToAnimate.image.setImage(new Image(String.valueOf(getClass().getResource("/img/cursor_shoot.png"))));
                if (currentPlayer == Game.getPlayer1()) {
                    mainController.playCanonTargetHit(false, isHit, true, true);
                } else if (currentPlayer == Game.getPlayer2()) {
                    mainController.playCanonTargetHit(true, isHit, true, true);
                }
                letCurrentThreadWait(600);
                if (isHit) {
                    cellToAnimate.image.setImage(new Image(String.valueOf(getClass().getResource("/img/boom_glow.png"))));
                    letCurrentThreadWait(600);
                }
                cellToAnimate.image.setImage(oldImage);

                // Swtich out the active gun

                if(currentPlayer == Game.getPlayer1() && !Game.getPlayer2().isAI()){
                    mainController.switchActiveCanon(true);
                } else if(!Game.getPlayer2().isAI()) {
                    mainController.switchActiveCanon(false);
                }
            });
            animationThread.start();
        }
    }
    private void letCurrentThreadWait(int milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
