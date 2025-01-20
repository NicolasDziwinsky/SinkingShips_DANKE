package com.example.sinkingships;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;

/**
 * Used to initialize and interact with the JavaFX Grid, and place down occupied cells
 */
public class PlacementGrid {
    GameBoard gameBoard;
    public GridPane gridPane;
    public ImageView shipPreview;
    public ImageView shipPreviewHover;
    public boolean currentShipOrientation;
    public MainController mainController;

    /**
     * Class constructor, also adds a button to every single cell to make them interactable
     */
    PlacementGrid(GridPane GridPane, GameBoard gameBoard, ImageView shipPreview, MainController mainController) {
        this.gridPane = GridPane;
        this.gameBoard = gameBoard;
        this.shipPreview = shipPreview;
        this.mainController = mainController;

        //init the placement grid
        //1.Making the Grid interactable via invisible buttons
        int x = 1;
        int y = 1;
        int i = 0;

        while (i < 100) {
            Button button = new Button();
            button.setPrefSize(Game.cellSize,Game.cellSize);
            button.setOpacity(0);

            // Adds a class so you can do some css styling
            button.getStyleClass().add("placementCellButton");

            int finalX = x;
            int finalY = y;
            button.onActionProperty().set(event -> {onPress(finalX, finalY);});
            button.onMouseEnteredProperty().set(this::handleCellMouseEnter);
            button.onMouseExitedProperty().set(this::handleCellMouseExit);
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

    // Methods to handle the mouse over ship preview
    private void setUpHoverDisplay() {
        shipPreviewHover = new ImageView();
        shipPreviewHover.setFitHeight(Game.cellSize);
        shipPreviewHover.setFitWidth(Game.cellSize*gameBoard.placementShips[gameBoard.shipCounter].getLength());
        shipPreviewHover.setImage(shipPreview.getImage());
        shipPreviewHover.setMouseTransparent(true);
        shipPreviewHover.setOpacity(0.5);

        if(!shipPreview.getTransforms().isEmpty()){
            Rotate rotateImage = new Rotate(90, shipPreviewHover.getTranslateX()+ (double) Game.cellSize /2, shipPreviewHover.getTranslateY()+ (double) Game.cellSize /2);
            shipPreviewHover.getTransforms().add(rotateImage);
        } else {
            shipPreviewHover.getTransforms().clear();
        }
    }
    private void handleCellMouseEnter(MouseEvent event) {
        setUpHoverDisplay();
        Button thisButton = (Button) event.getSource();
        mainController.autoRotateInGameCanons((Button)event.getSource());
        shipPreviewHover.setLayoutX(thisButton.localToScene(0,0).getX());
        shipPreviewHover.setLayoutY(thisButton.localToScene(0,0).getY());
        mainController.RootPane.getChildren().add(shipPreviewHover);
    }
    private void handleCellMouseExit(MouseEvent event) {
        mainController.RootPane.getChildren().remove(shipPreviewHover);
    }
}
