package com.example.sinkingships;

import java.io.IOException;
import java.util.Random;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

//all major logic should happen here, because in here all created objects can interact with one another
public class MainController {
    // General
    @FXML public AnchorPane RootPane;
    @FXML public GridPane PlacementGridFX;
    @FXML public GridPane ContainerGrid;
    @FXML public Label PlayerNamePlacement;
    @FXML public Button StartGame;
    @FXML public Button RandomPlacement;
    @FXML public GridPane Map1;
    @FXML public GridPane Map2;
    @FXML public ImageView GunPlayer1;
    @FXML public ImageView GunPlayer2;
    @FXML public ImageView GunMenu1;
    @FXML public ImageView GunMenu2;
    @FXML public ImageView GunMenu3;
    @FXML public Button SoundMusicReset;
    @FXML public Button SoundEffectReset;
    @FXML public Slider SoundMusicSlider;
    @FXML public Slider SoundEffectSlider;
    @FXML public Button Back;
    @FXML public ImageView ShipPreview;

    // New Game
    @FXML public TextField Name1;
    @FXML public TextField Name2;
    @FXML public CheckBox AiCheckbox1;
    @FXML public CheckBox AiCheckbox2;

    // Main Menu
    @FXML public Button NewGame;
    @FXML public Button LoadGame;
    @FXML public Button Tutorial;
    @FXML public Button Scoreboard;
    @FXML public Button Exit;
    @FXML public Button Credits;

    public boolean canonIsHovered;

    public int gridCounter = 0;
    public Player currentPlayer;
    public SceneSwitcher SceneSwitcher = new SceneSwitcher();

    // Everything in this block is to try out stuff with the moving canons and playing sounds
    @FXML
    public void initialize() {
        if(GunPlayer2 != null) {
            GunPlayer2.setOpacity(0.6);
        }

        if(GunMenu1 != null) {
            GunMenu1.setPickOnBounds(false);
        }
        if(GunMenu2 != null) {
            GunMenu2.setPickOnBounds(false);
        }
        if(GunMenu3 != null) {
            GunMenu3.setPickOnBounds(false);
        }
        if(GunPlayer1 != null) {
            GunPlayer1.setPickOnBounds(false);
        }
        if(GunPlayer2 != null) {
            GunPlayer2.setPickOnBounds(false);
        }
        canonIsHovered = false;
    }

    @FXML
    public void handleMouseEntered(MouseEvent mouseEvent) {
        Image imageForCursor = new Image(String.valueOf(getClass().getResource("/img/cursor_finger.png")));
        ((Node) mouseEvent.getSource()).setCursor(new ImageCursor(imageForCursor, 48, 48));
    }
    @FXML
    public void handleMouseClicked(MouseEvent mouseEvent) {
        if(!canonIsHovered) {
            setUpShootingInGame(mouseEvent);
            setUpShootingInMenu(mouseEvent);
        }
    }
    @FXML
    public void handleMouseMoved(MouseEvent mouseEvent) {
        if(GunMenu1 != null && GunMenu2 != null && GunMenu3 != null) {
            autoRotateAllMenuCanons(mouseEvent);
        }
        if(GunPlayer1 != null) {
            autoRotateLeftCanon(mouseEvent);
        }
        /*if(GunPlayer2 != null) {
            autoRotateRightCanon(mouseEvent);
        }*/
    }
    @FXML
    public void handleCatClick(MouseEvent mouseEvent) {
        Soundboard meowingSoundboard = new Soundboard();
        meowingSoundboard.playCatNoise();
    }
    @FXML
    public void handleCatEntered(MouseEvent mouseEvent) {
        handleMouseEntered(mouseEvent);
        canonIsHovered = true;
    }
    @FXML
    public void handleCatExited(MouseEvent mouseEvent) {
        canonIsHovered = false;
    }

    // Methods to make the canons shoot on mouse click
    private void setUpShootingInGame(MouseEvent mouseEvent) {
        Thread leftPlayerGunThread = new Thread(this::shootingThreadPlayerCanonLeft);
        leftPlayerGunThread.start();
        //Thread rightPlayerGunThread = new Thread(this::shootingThreadPlayerGunRight);
        //rightPlayerGunThread.start();
    }
    private void setUpShootingInMenu(MouseEvent mouseEvent) {
        Random rnd = new Random();
        int randomNumber = rnd.nextInt(3);
        switch (randomNumber) {
            case 0:
                Thread menuGunAThread = new Thread(this::shootingThreadMenuCanonA);
                menuGunAThread.start();
                break;
            case 1:
                Thread menuGunBThread = new Thread(this::shootingThreadMenuCanonB);
                menuGunBThread.start();
                break;
            case 2:
                Thread menuGunCThread = new Thread(this::shootingThreadMenuCanonC);
                menuGunCThread.start();
                break;

        }
    }
    private void shootingThreadPlayerCanonLeft(){
        Image shootingImage = new Image(String.valueOf(getClass().getResource("/img/canon/canon_3_paper_boom.png")));
        Image defaultImage = new Image(String.valueOf(getClass().getResource("/img/canon/canon_3_paper.png")));
        setUpShootingCanon(GunPlayer1, defaultImage, shootingImage, true);
    }
    private void shootingThreadPlayerCanonRight(){
        Image shootingImage = new Image(String.valueOf(getClass().getResource("/img/canon/canon_2_flip_paper_boom.png")));
        Image defaultImage = new Image(String.valueOf(getClass().getResource("/img/canon/canon_2_flip_paper.png")));
        setUpShootingCanon(GunPlayer2, defaultImage, shootingImage, true);
    }
    private void shootingThreadMenuCanonA(){
        Image shootingImage = new Image(String.valueOf(getClass().getResource("/img/canon/canon_1_flip_paper_boom.png")));
        Image defaultImage = new Image(String.valueOf(getClass().getResource("/img/canon/canon_1_flip_paper.png")));
        setUpShootingCanon(GunMenu1, defaultImage, shootingImage, false);
    }
    private void shootingThreadMenuCanonB(){
        Image shootingImage = new Image(String.valueOf(getClass().getResource("/img/canon/canon_2_flip_paper_boom.png")));
        Image defaultImage = new Image(String.valueOf(getClass().getResource("/img/canon/canon_2_flip_paper.png")));
        setUpShootingCanon(GunMenu2, defaultImage, shootingImage, false);
    }
    private void shootingThreadMenuCanonC(){
        Image shootingImage = new Image(String.valueOf(getClass().getResource("/img/canon/canon_3_paper_boom.png")));
        Image defaultImage = new Image(String.valueOf(getClass().getResource("/img/canon/canon_3_paper.png")));
        setUpShootingCanon(GunMenu3, defaultImage, shootingImage, false);
    }
    private void setUpShootingCanon(ImageView imageViewOfGun, Image standardGun, Image shootingGun, boolean playImpact){
        imageViewOfGun.setImage(shootingGun);
        Soundboard soundboardForGun = new Soundboard();
        soundboardForGun.playCanonShot();

        // Waiting for 600 milliseconds before switching out the sprite again
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        imageViewOfGun.setImage(standardGun);
        if (playImpact){
            soundboardForGun.playImpactBoom();
        }
    }

    // Methods to the canons rotate to always look at the mouse cursor
    private void autoRotateAllInGameCanons(MouseEvent eventFromMouse) {
        autoRotateLeftCanon(eventFromMouse);
        autoRotateRightCanon(eventFromMouse);
    }
    private void autoRotateLeftCanon(MouseEvent eventFromMouse) {
        autoRotateCanon(80, -80, true, GunPlayer1, eventFromMouse);
    }
    private void autoRotateRightCanon(MouseEvent eventFromMouse) {
        autoRotateCanon(80, -80, false, GunPlayer2, eventFromMouse);
    }
    private void autoRotateAllMenuCanons(MouseEvent eventFromMouse) {
        autoRotateMenuCanon1(eventFromMouse);
        autoRotateMenuCanon2(eventFromMouse);
        autoRotateMenuCanon3(eventFromMouse);
    }
    private void autoRotateMenuCanon1(MouseEvent eventFromMouse) {
        autoRotateCanon(150, -120, false, GunMenu1, eventFromMouse);
    }
    private void autoRotateMenuCanon2(MouseEvent eventFromMouse) {
        autoRotateCanon(120, -150, false, GunMenu2, eventFromMouse);
    }
    private void autoRotateMenuCanon3(MouseEvent eventFromMouse) {
        autoRotateCanon(40, -70, true, GunMenu3, eventFromMouse);
    }
    /**
     * Rotates the given image to look towards the mouse cursor, from a certain upper and lower threshold for the angle.
     * @param angleUpper Highest angle.
     * @param angleLower Lowest angle.
     * @param leftToRight True if image looks from left to right by default. False if it looks right to left.
     * @param ivToRotate The ImageView to rotate.
     * @param eventFromMouse The mouse event of the cursor to use to calculate the angle towards.
     */
    private void autoRotateCanon(double angleUpper, double angleLower, boolean leftToRight, ImageView ivToRotate, MouseEvent eventFromMouse) {
        double gunCenterX = ivToRotate.getLayoutX() + ivToRotate.getFitWidth() / 2;
        double gunCenterY = ivToRotate.getLayoutY() + ivToRotate.getFitHeight() / 2;
        double gunAngle = getAngleForImage(gunCenterX, gunCenterY, eventFromMouse);

        // Rotates the gun inside the given threshold angles
        if(leftToRight) {
            if (gunAngle > angleUpper) {
                gunAngle = angleUpper;
            }
            if (gunAngle < angleLower) {
                gunAngle = angleLower;
            }
            ivToRotate.setRotate(gunAngle);
        } else {
            if (gunAngle < angleUpper && gunAngle >= 0){
                gunAngle = angleUpper;
            }
            if (gunAngle > angleLower && gunAngle < 0){
                gunAngle = angleLower;
            }
            ivToRotate.setRotate(gunAngle + 180);
        }
    }
    /***
     * Calculates the angle at which an image has to be rotated so it follows the mouse cursor
     * @param imageCenterX Center of the image to rotate
     * @param imageCenterY Center of the image to rotate
     * @param eventFromMouse The mouse event to get the cursor position
     * @return The angle for the Image
     */
    private double getAngleForImage(double imageCenterX, double imageCenterY, MouseEvent eventFromMouse) {
        double deltaX = eventFromMouse.getX() - imageCenterX;
        double deltaY = eventFromMouse.getY() - imageCenterY;
        double imageAngle = Math.toDegrees(Math.atan2(deltaY, deltaX));
        return imageAngle;
    }

    /**
     * Test Function
     */
    public void generatePlayersTest() {
        //Generates Players and adds them to the GameInstance
        Game.setPlayer1(new Player(Name1.getText(), false, GunPlayer1));
        Game.setPlayer2(new Player(Name1.getText(), false, GunPlayer2));


        //Output general Player info
        System.out.println(Game.getPlayer1().playerInfo());
        System.out.println(Game.getPlayer1().playerInfo());

        //Example how to manipulate the individual nodes of the gameboard
        Game.getPlayer1().getGameBoard().getCell(2, 4).setOccupied();
        Game.getPlayer1().getGameBoard().getCell(2, 5).setOccupied();
        Game.getPlayer1().getGameBoard().getCell(2, 6).setOccupied();

        Game.getPlayer1().getGameBoard().getCell(2, 4).setIsHit();
        Game.getPlayer1().getGameBoard().getCell(3, 4).setIsHit();
        Game.getPlayer1().getGameBoard().getCell(4, 4).setIsHit();

        //example of placing a ship on the board
        Ship shipHorizontal = new Ship(3, true);
        Game.getPlayer1().getGameBoard().placeShip(shipHorizontal, Game.getPlayer1().getGameBoard().getCell(5, 8));

        Ship shipVertical = new Ship(4, false);
        Game.getPlayer1().getGameBoard().placeShip(shipVertical, Game.getPlayer1().getGameBoard().getCell(9, 5));

        //example of checking if "shipVertical" is sunk
        Game.getPlayer1().getGameBoard().getCell(9, 5).setIsHit();
        Game.getPlayer1().getGameBoard().getCell(9, 6).setIsHit();
        Game.getPlayer1().getGameBoard().getCell(9, 7).setIsHit();
        Game.getPlayer1().getGameBoard().getCell(9, 8).setIsHit();

        System.out.println("Is Ship Sunk?: " + shipVertical.checkIfSunk());

        //example of checking if Player Lost
        //It only checks ships, not any other occupied cells
        System.out.println("Player 1 Lost?: " + Game.getPlayer1().checkIfLost());


        //Outputs the Gameboard of Player1 in a comprehensible form into the Terminal
        Game.getPlayer1().getGameBoard().outputTextVersion();
    }
    /**
     * Generates two players and adds them to the Game object
     */
    public void generatePlayers() {
        Game.setPlayer1(new Player(Name1.getText(), AiCheckbox1.isSelected(), GunPlayer1));
        Game.setPlayer2(new Player(Name2.getText(), AiCheckbox2.isSelected(), GunPlayer2));
        Game.aiBrain1 = new AiBrain(Game.getPlayer2(), Game.getPlayer1());
        Game.aiBrain2 = new AiBrain(Game.getPlayer1(), Game.getPlayer2());
    }

    //This block abstracts the scene switching, which is actually handled by the SceneSwitcher
    public void switchToMainMenu(ActionEvent event) throws IOException {
        SceneSwitcher.switchToMainMenu(event);
    }
    public void switchToNewGame(ActionEvent event) throws IOException {
        SceneSwitcher.switchToNewGame(event);
    }
    public void switchToShipPlacement(ActionEvent event) throws IOException, InterruptedException {
        generatePlayers();
        SceneSwitcher.switchToShipPlacement(event);
    }
    public EventHandler<ActionEvent> switchToGame(ActionEvent event) throws IOException {
        SceneSwitcher.switchToGame(event);
        return null;
    }




    /**
     * //Sets up the Placement Grid for the User, the first one is for player 1, second one for player 2
     * //Potential for improvement
     */
    public void initPlacementGrid(int Player) {
        if (Player == 0) {
            Game.getPlayer1().getGameBoard().placementGrid = new PlacementGrid(PlacementGridFX, Game.getPlayer1().getGameBoard(), ShipPreview);
            PlayerNamePlacement.setText(Game.getPlayer1().getName());
            Game.getPlayer1().getGameBoard().placementGrid.setShipPreview(Game.getPlayer1().getGameBoard().placementShips[0].shipImage);
        } else {
            Game.getPlayer2().getGameBoard().placementGrid = new PlacementGrid(PlacementGridFX, Game.getPlayer2().getGameBoard(), ShipPreview);
            Game.getPlayer2().getGameBoard().placementGrid.setShipPreview(Game.getPlayer1().getGameBoard().placementShips[0].shipImage);
            PlayerNamePlacement.setText(Game.getPlayer2().getName());
        }
    }
    /**
     * //Sets up the Placement Grid for the User, the first one is for player 1, second one for player 2
     * //Potential for improvement
     */
    public void nextGrid(ActionEvent event) throws IOException {
        if (gridCounter == 0) {
            currentPlayer = Game.getPlayer1();
            StartGame.setText("Next Player");
        } else if (gridCounter == 1) {
            Game.placedRandom = false;
            Game.getPlayer1().getGameBoard().placementGrid.resetField();
            currentPlayer = Game.getPlayer2();
            StartGame.setText("Start Game");
            RandomPlacement.setOpacity(1);
        } else if (gridCounter == 2) {
            switchToGame(event);
        }
        initPlacementGrid(gridCounter);
        gridCounter++;
    }

    public void PlaceRandom()throws IOException {
        if (!Game.placedRandom) {
            currentPlayer.getGameBoard().placeShipsRandomly();
            RandomPlacement.setOpacity(0.6);
            Game.placedRandom = true;
        }
        if (currentPlayer.isAI()){

        }

    }



    public void RotateShip() {
        for (Ship ship:Game.getPlayer1().getGameBoard().placementShips) {
            ship.turnShip();
        }
        for (Ship ship:Game.getPlayer2().getGameBoard().placementShips) {
            ship.turnShip();
        }

        if (ShipPreview.getRotate() == 90) {
            ShipPreview.rotateProperty().set(0);
        } else {
            ShipPreview.rotateProperty().set(90);
        }
    }



    /**
     * Initializes both gamefields
     */
    public void StartGame() {

        Game.getPlayer1().gameGrid = new GameGrid(Map1, Game.getPlayer1());
        Game.getPlayer2().gameGrid = new GameGrid(Map2, Game.getPlayer2());
        Game.HitHappened();
    }




}