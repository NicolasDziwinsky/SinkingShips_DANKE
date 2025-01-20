package com.example.sinkingships;

import java.io.IOException;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;

//all major logic should happen here, because in here all created objects can interact with one another
public class MainController {
    // General
    @FXML public AnchorPane RootPane;
    @FXML public GridPane PlacementGridFX;
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
    @FXML public Button SoundEffectReset;
    @FXML public Slider SoundEffectSlider;
    @FXML public Button Back;
    @FXML public ImageView ShipPreview;
    @FXML public VBox WinningScreen;
    @FXML public Label WinningScreenPlayer;
    @FXML public Label WinningScreenTurns;
    @FXML public Button WinningScreenExit;
    @FXML public ImageView Title;

    // New Game
    @FXML public TextField Name1;
    @FXML public TextField Name2;
    @FXML public CheckBox AiCheckbox1;
    @FXML public CheckBox AiCheckbox2;
    @FXML public ImageView TicTacToeDeco;

    // Main Menu
    @FXML public Button NewGame;
    @FXML public Button LoadGame;
    @FXML public Button Tutorial;
    @FXML public Button Scoreboard;
    @FXML public Button Exit;
    @FXML public Button Credits;
    @FXML public ImageView SubTitle;

    // Canons
    @FXML public VBox Player1CanonSelector;
    @FXML public VBox Player2CanonSelector;
    @FXML public ImageView CanonImage1;
    @FXML public Button CanonImage1Back;
    @FXML public Button CanonImage1Forward;
    @FXML public ImageView CanonImage2;
    @FXML public Button CanonImage2Back;
    @FXML public Button CanonImage2Forward;
    private boolean canonIsHovered;
    private boolean player1GunEnabled;
    private boolean player2GunEnabled;
    private Image player1GunDefault;
    private Image player1GunShooting;
    private Image player2GunDefault;
    private Image player2GunShooting;
    private int currentGunPlayer1;
    private int currentGunPlayer2;

    // Placement Grid
    @FXML public Button RotateButton;

    // Game
    private int gridCounter = 0;
    private Player currentPlayer;



    // Everything in this block is to try out stuff with the moving canons and playing sounds
    @FXML public void initialize() {
        if(CanonImage1 != null && CanonImage2 != null) {
            currentGunPlayer1 = 1;
            currentGunPlayer2 = 1;
        }
        if(GunMenu1 != null && GunMenu2 != null && GunMenu3 != null) {
            GunMenu1.setPickOnBounds(false);
            GunMenu2.setPickOnBounds(false);
            GunMenu3.setPickOnBounds(false);
        }
        if(GunPlayer1 != null && GunPlayer2 != null) {
            player1GunEnabled = true;
            GunPlayer1.setPickOnBounds(false);
            GunPlayer1.setImage(player1GunDefault);
            GunPlayer1.setImage(Game.getPlayer1().gunDefaultImage);
            GunPlayer2.setPickOnBounds(false);
            GunPlayer2.setImage(player2GunDefault);
            GunPlayer2.setImage(Game.getPlayer2().gunDefaultImage);
            formatEnabledCanons();
        }
        canonIsHovered = false;
        setCurrentGunImages();
        setUpGameBoardSize();

        if(SceneSwitcher.stage != null) {
            // Adds Listeners to adjust the size of elements when the screen size changes
            SceneSwitcher.stage.widthProperty().addListener((obs, oldScene, newScene) -> {
                setUpGameBoardSize();
            });
            SceneSwitcher.stage.heightProperty().addListener((obs, oldScene, newScene) -> {
                setUpGameBoardSize();
            });
        }

        if(WinningScreen != null) {
            WinningScreen.visibleProperty().addListener((obs, oldScene, newScene) -> {
                setUpGameBoardSize();
            });
        }

        setUpSoundControls();
    }

    /**
     * Sets up the size of the game boards so they fit the screen well
     */
    private void setUpGameBoardSize(){
        // Resize the Game Boards
        double newMapSize = Game.cellSize*10+ (double) Game.cellSize /5+ (double) Game.cellSize /5;
        Insets newMapPadding = new Insets((double) Game.cellSize /5);
        Insets newTextFieldPadding = new Insets((double) Game.cellSize /5, Game.cellSize, (double) Game.cellSize /5, Game.cellSize);
        if(Map1 != null && Map2 != null) {
            Map1.setPrefSize(newMapSize,newMapSize);
            Map1.setPadding(newMapPadding);
            Map2.setPrefSize(newMapSize,newMapSize);
            Map2.setPadding(newMapPadding);
        }
        if(PlacementGridFX != null) {
            PlacementGridFX.setPrefSize(newMapSize,newMapSize);
            PlacementGridFX.setPadding(newMapPadding);
        }

        // Ship Placement Window
        if(PlacementGridFX != null) {
            ShipPreview.setFitHeight(Game.cellSize*2);
            ShipPreview.setFitWidth(Game.cellSize*10);
            AnchorPane.setTopAnchor(ShipPreview, 80.0);
            AnchorPane.setLeftAnchor(ShipPreview, (double)Game.cellSize*10+Game.cellSize/2+80.0);
            AnchorPane.setTopAnchor(RotateButton, (double)Game.cellSize*2+80.0);
            AnchorPane.setLeftAnchor(RotateButton, (double)Game.cellSize*13+80.0);
            AnchorPane.setTopAnchor(RandomPlacement, (double)Game.cellSize*10+Game.cellSize/5*2+80.0);
            AnchorPane.setLeftAnchor(RandomPlacement, 90.0);
            AnchorPane.setLeftAnchor(PlayerNamePlacement, (double)Game.cellSize*13+84.0);
            AnchorPane.setTopAnchor(PlayerNamePlacement, (double)Game.cellSize*4+Game.cellSize/2+80.0);
        }

        // Reposition the guns
        if(GunPlayer1 != null && GunPlayer2 != null) {
            AnchorPane.setTopAnchor(GunPlayer1, (double)Game.cellSize*3+80-GunPlayer1.getFitHeight()/2);
            AnchorPane.setLeftAnchor(GunPlayer1, (double)Game.cellSize*10+48-GunPlayer1.getFitWidth()/4);
            AnchorPane.setTopAnchor(GunPlayer2, (double)Game.cellSize*7+80-GunPlayer1.getFitHeight()/2);
            AnchorPane.setRightAnchor(GunPlayer2, (double)Game.cellSize*10+48-GunPlayer1.getFitWidth()/4);
        }

        // Resize "New Game" Window
        if(CanonImage1 != null && CanonImage2 != null) {
            if(SceneSwitcher.currentWindowWidth/SceneSwitcher.currentWindowHeight > 1.8){
                TicTacToeDeco.setOpacity(0);
            } else {
                TicTacToeDeco.setOpacity(1);
            }
            CanonImage1.setFitHeight(Game.cellSize * 4);
            CanonImage1.setFitWidth(Game.cellSize * 4);
            CanonImage1Back.setPrefHeight(Game.cellSize * 4);
            CanonImage1Back.setPrefWidth(Game.cellSize * 2);
            CanonImage1Forward.setPrefHeight(Game.cellSize * 4);
            CanonImage1Forward.setPrefWidth(Game.cellSize * 2);
            CanonImage2.setFitHeight(Game.cellSize * 4);
            CanonImage2.setFitWidth(Game.cellSize * 4);
            CanonImage2Back.setPrefHeight(Game.cellSize * 4);
            CanonImage2Back.setPrefWidth(Game.cellSize * 2);
            CanonImage2Forward.setPrefHeight(Game.cellSize * 4);
            CanonImage2Forward.setPrefWidth(Game.cellSize * 2);
            Name1.setPrefWidth(Game.cellSize * 8);
            Name1.setPrefHeight(Game.cellSize * 3);
            Name1.setPadding(newTextFieldPadding);
            Name2.setPrefWidth(Game.cellSize * 8);
            Name2.setPrefHeight(Game.cellSize * 3);
            Name2.setPadding(newTextFieldPadding);
            AnchorPane.setLeftAnchor(Player1CanonSelector, (double)Game.cellSize * 3);
            AnchorPane.setTopAnchor(Player1CanonSelector, (double)Game.cellSize * 2);
            AnchorPane.setRightAnchor(Player2CanonSelector, (double)Game.cellSize * 3);
            AnchorPane.setTopAnchor(Player2CanonSelector, (double)Game.cellSize * 2);
        }

        // Reposition Winning Screen
        if(WinningScreen != null) {
            AnchorPane.setLeftAnchor(WinningScreen, RootPane.getWidth() / 2 - WinningScreen.getWidth() / 2);
            AnchorPane.setTopAnchor(WinningScreen, RootPane.getHeight() / 2 - WinningScreen.getHeight() / 2);
        }
    }



    // Methods to handle the gun selection menu
    @FXML public void handleNextCanonPlayer1(){
        currentGunPlayer1++;
        currentGunPlayer1 = checkRotationInt(currentGunPlayer1);
        displayCurrentCanon(CanonImage1, currentGunPlayer1, "");
    }
    @FXML public void handlePrevCanonPlayer1(){
        currentGunPlayer1--;
        currentGunPlayer1 = checkRotationInt(currentGunPlayer1);
        displayCurrentCanon(CanonImage1, currentGunPlayer1, "");
    }
    @FXML public void handleNextCanonPlayer2(){
        currentGunPlayer2++;
        currentGunPlayer2 = checkRotationInt(currentGunPlayer2);
        displayCurrentCanon(CanonImage2, currentGunPlayer2, "_flip");
    }
    @FXML public void handlePrevCanonPlayer2(){
        currentGunPlayer2--;
        currentGunPlayer2 = checkRotationInt(currentGunPlayer2);
        displayCurrentCanon(CanonImage2, currentGunPlayer2, "_flip");
    }
    private void displayCurrentCanon(ImageView ivForCanon, int currentGunNumber, String flipString){
        switch (currentGunNumber){
            case 1:
                ivForCanon.setImage(new Image(String.valueOf(getClass().getResource("/img/canon/canon_1" + flipString + "_paper_square.png"))));
                break;
            case 2:
                ivForCanon.setImage(new Image(String.valueOf(getClass().getResource("/img/canon/canon_2" + flipString + "_paper_square.png"))));
                break;
            case 3:
                ivForCanon.setImage(new Image(String.valueOf(getClass().getResource("/img/canon/canon_3" + flipString + "_paper_square.png"))));
                break;
            default:
                break;
        }
        setCurrentGunImages();
    }
    private int checkRotationInt(int rotatingInt){
        if(rotatingInt < 0){
            return 3;
        }
        if(rotatingInt > 3){
            return 1;
        }
        return rotatingInt;
    }
    private void setCurrentGunImages(){
        switch (currentGunPlayer1){
            case 1:
                player1GunDefault = new Image(String.valueOf(getClass().getResource("/img/canon/canon_1_paper.png")));
                player1GunShooting = new Image(String.valueOf(getClass().getResource("/img/canon/canon_1_paper_boom.png")));
                break;
            case 2:
                player1GunDefault = new Image(String.valueOf(getClass().getResource("/img/canon/canon_2_paper.png")));
                player1GunShooting = new Image(String.valueOf(getClass().getResource("/img/canon/canon_2_paper_boom.png")));
                break;
            case 3:
                player1GunDefault = new Image(String.valueOf(getClass().getResource("/img/canon/canon_3_paper.png")));
                player1GunShooting = new Image(String.valueOf(getClass().getResource("/img/canon/canon_3_paper_boom.png")));
                break;
        }
        switch (currentGunPlayer2){
            case 1:
                player2GunDefault = new Image(String.valueOf(getClass().getResource("/img/canon/canon_1_flip_paper.png")));
                player2GunShooting = new Image(String.valueOf(getClass().getResource("/img/canon/canon_1_flip_paper_boom.png")));
                break;
            case 2:
                player2GunDefault = new Image(String.valueOf(getClass().getResource("/img/canon/canon_2_flip_paper.png")));
                player2GunShooting = new Image(String.valueOf(getClass().getResource("/img/canon/canon_2_flip_paper_boom.png")));
                break;
            case 3:
                player2GunDefault = new Image(String.valueOf(getClass().getResource("/img/canon/canon_3_flip_paper.png")));
                player2GunShooting = new Image(String.valueOf(getClass().getResource("/img/canon/canon_3_flip_paper_boom.png")));
                break;
        }
    }

    // Methods to handle standard mouse over stuff
    @FXML public void handleMouseEntered(MouseEvent mouseEvent) {
        Image imageForCursor = new Image(String.valueOf(getClass().getResource("/img/cursor_finger.png")));
        ((Node) mouseEvent.getSource()).setCursor(new ImageCursor(imageForCursor, 48, 48));
    }

    // Handle the gun shooting at random places
    @FXML public void handleMouseClicked(MouseEvent mouseEvent) {
        if(!canonIsHovered) {
            if(GunPlayer1 != null && GunPlayer2 != null) {
                setUpShootingInGame(mouseEvent);
            }
            if(GunMenu1 != null && GunMenu2 != null && GunMenu3 != null) {
                setUpShootingInMenu(mouseEvent);
            }
        }
    }
    @FXML public void handleMouseMoved(MouseEvent mouseEvent) {
        if(GunMenu1 != null && GunMenu2 != null && GunMenu3 != null) {
            autoRotateAllMenuCanons(mouseEvent);
        }
        if(GunPlayer1 != null && player1GunEnabled) {
            autoRotateLeftCanon(mouseEvent);
        }
        if(GunPlayer2 != null && player2GunEnabled) {
            autoRotateRightCanon(mouseEvent);
        }
    }

    // Handle the canons making cat noises when you click on them
    @FXML public void handleCatClick(MouseEvent mouseEvent) {
        Soundboard meowingSoundboard = new Soundboard();
        meowingSoundboard.playCatNoise();
    }
    @FXML public void handleCatEntered(MouseEvent mouseEvent) {
        handleMouseEntered(mouseEvent);
        canonIsHovered = true;
    }
    @FXML public void handleCatExited(MouseEvent mouseEvent) {
        canonIsHovered = false;
    }

    // Set the Volume
    @FXML public void handleSoundMute(ActionEvent actionEvent) {
        if(Soundboard.effectsIsMuted){
            Soundboard.effectsIsMuted = false;
            SoundEffectSlider.setValue(1);
        } else {
            Soundboard.effectsIsMuted = true;
            SoundEffectSlider.setValue(0);
        }
        setUpSoundControls();
    }
    @FXML public void handleSoundSet(MouseEvent mouseEvent) {
        if(Soundboard.effectsIsMuted){
            Soundboard.effectsIsMuted = false;
        }
        float floatToSet = (float) SoundEffectSlider.getValue()/100;
        Soundboard.setVolume(floatToSet);
        setUpSoundControls();
    }
    private void setUpSoundControls(){
        if(Soundboard.effectsIsMuted){
            SoundEffectReset.getParent().getStyleClass().clear();
            SoundEffectReset.getParent().getStyleClass().add("soundSettingsReverse");
            SoundEffectSlider.setValue(0);
        } else {
            SoundEffectReset.getParent().getStyleClass().clear();
            SoundEffectReset.getParent().getStyleClass().add("soundSettings");
            SoundEffectSlider.setValue(Soundboard.getVolume()*100);
        }
    }

    // Methods to make the canons shoot on mouse click
    public void setUpShootingInGame(MouseEvent mouseEvent) {
        if(player1GunEnabled) {
            Thread leftPlayerGunThread = new Thread(this::shootingThreadPlayerCanonLeft);
            leftPlayerGunThread.start();
        }
        if(player2GunEnabled) {
            Thread rightPlayerGunThread = new Thread(this::shootingThreadPlayerCanonRight);
            rightPlayerGunThread.start();
        }
    }
    public void setUpShootingInMenu(MouseEvent mouseEvent) {
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
        setUpShootingCanon(GunPlayer1, Game.getPlayer1().gunDefaultImage, Game.getPlayer1().gunShootingImage, false);
    }
    private void shootingThreadPlayerCanonRight(){
        setUpShootingCanon(GunPlayer2, Game.getPlayer2().gunDefaultImage, Game.getPlayer2().gunShootingImage, false);
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
    public void playCanonTargetHit(boolean isLeftPlayer, boolean isHit, boolean playShot, boolean playImpact){
        Thread newGunThread = new Thread(() -> {
            setUpShootingCanon(isLeftPlayer, isHit, playShot, playImpact);
        });
        newGunThread.start();
    }
    private void setUpShootingCanon(boolean isLeftPlayer, boolean isHit, boolean playShot, boolean playImpact){
        Soundboard soundboardForGun = new Soundboard();
        if(playShot) {
            if (isLeftPlayer) {
                GunPlayer1.setImage(Game.getPlayer1().gunShootingImage);
                soundboardForGun.playCanonShot();

                // Waiting for 600 milliseconds before switching out the sprite again
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                GunPlayer1.setImage(Game.getPlayer1().gunDefaultImage);
            } else {
                GunPlayer2.setImage(Game.getPlayer2().gunShootingImage);
                soundboardForGun.playCanonShot();

                // Waiting for 600 milliseconds before switching out the sprite again
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                GunPlayer2.setImage(Game.getPlayer2().gunDefaultImage);
            }
        }
        if (playImpact) {
            if(isHit) {
                soundboardForGun.playImpactBoom();
            } else {
                soundboardForGun.playMissed();
            }
        }
    }
    private void setUpShootingCanon(ImageView theGun, Image standardGun, Image shootingGun, boolean playImpact){
        theGun.setImage(shootingGun);
        Soundboard soundboardForGun = new Soundboard();
        soundboardForGun.playCanonShot();

        // Waiting for 600 milliseconds before switching out the sprite again
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        theGun.setImage(standardGun);
        if (playImpact) {
            soundboardForGun.playImpactBoom();
        }
    }

    // Methods to the canons rotate to always look at the mouse cursor
    public void autoRotateInGameCanons(Button buttonToLookTowards) {
        if(player1GunEnabled) {
            autoRotateCanon(80, -80, true, true, buttonToLookTowards);
        }
        if(player2GunEnabled) {
            autoRotateCanon(80, -80, false, false, buttonToLookTowards);
        }
    }
    private void autoRotateLeftCanon(MouseEvent eventFromMouse) {
        autoRotateCanon(80, -80, true, GunPlayer1, eventFromMouse);
    }
    private void autoRotateRightCanon(MouseEvent eventFromMouse) {
        autoRotateCanon(80, -80, false, GunPlayer2, eventFromMouse);
    }
    private void autoRotateAllMenuCanons(MouseEvent eventFromMouse) {
        autoRotateCanon(150, -120, false, GunMenu1, eventFromMouse);
        autoRotateCanon(120, -150, false, GunMenu2, eventFromMouse);
        autoRotateCanon(40, -70, true, GunMenu3, eventFromMouse);
    }
    public void switchActiveCanon(boolean isLeftPlayer){
        if(isLeftPlayer) {
            player1GunEnabled = true;
            player2GunEnabled = false;
        } else {
            player1GunEnabled = false;
            player2GunEnabled = true;
        }
        formatEnabledCanons();
    }
    private void formatEnabledCanons(){
        if(player1GunEnabled){
            GunPlayer1.setOpacity(1);
        } else {
            GunPlayer1.setOpacity(0.4);
            GunPlayer1.setRotate(0);
        }
        if(player2GunEnabled){
            GunPlayer2.setOpacity(1);
        } else {
            GunPlayer2.setOpacity(0.4);
            GunPlayer2.setRotate(0);
        }
    }
    public void autoRotateCanon(double angleUpper, double angleLower, boolean leftToRight, boolean isLeftPlayer, Button buttonToLookTowards){
        ImageView ivToRotate = GunPlayer1;
        if(!isLeftPlayer){
            ivToRotate = GunPlayer2;
        }

        double gunCenterX = ivToRotate.getLayoutX() + ivToRotate.getFitWidth() / 2;
        double gunCenterY = ivToRotate.getLayoutY() + ivToRotate.getFitHeight() / 2;
        double gunAngle = getAngleForImage(gunCenterX, gunCenterY, buttonToLookTowards);

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
    /**
     * Rotates the given image to look towards the mouse cursor, from a certain upper and lower threshold for the angle.
     * @param angleUpper Highest angle.
     * @param angleLower Lowest angle.
     * @param leftToRight True if image looks from left to right by default. False if it looks right to left.
     * @param ivToRotate The ImageView to rotate.
     * @param eventFromMouse The mouse event of the cursor to use to calculate the angle towards.
     */
    public void autoRotateCanon(double angleUpper, double angleLower, boolean leftToRight, ImageView ivToRotate, MouseEvent eventFromMouse) {
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
     * Calculates the angle at which an image has to be rotated so it follows the mouse cursor.
     * @param imageCenterX Center of the image to rotate.
     * @param imageCenterY Center of the image to rotate.
     * @param eventFromMouse The mouse event to get the cursor position.
     * @return The angle for the Image.
     */
    private double getAngleForImage(double imageCenterX, double imageCenterY, MouseEvent eventFromMouse) {
        double deltaX = eventFromMouse.getX() - imageCenterX;
        double deltaY = eventFromMouse.getY() - imageCenterY;
        double imageAngle = Math.toDegrees(Math.atan2(deltaY, deltaX));
        return imageAngle;
    }
    /**
     * Calculates the angle at which an image has to be rotated so it looks towards the cell.
     * @param imageCenterX Center of the image to rotate.
     * @param imageCenterY Center of the image to rotate.
     * @param buttonToLookTowards The button to look towards.
     * @return The angle for the Image.
     */
    private double getAngleForImage(double imageCenterX, double imageCenterY, Button buttonToLookTowards) {
        double deltaX = buttonToLookTowards.localToScene(0,0).getX() - imageCenterX;
        double deltaY = buttonToLookTowards.localToScene(0,0).getY() - imageCenterY;
        double imageAngle = Math.toDegrees(Math.atan2(deltaY, deltaX));
        return imageAngle;
    }



    /**
     * Test Function
     */
    public void generatePlayersTest() {
        //Generates Players and adds them to the GameInstance
        Game.setPlayer1(new Player(Name1.getText(), false, "GunPlayer1", player1GunDefault, player1GunShooting));
        Game.setPlayer2(new Player(Name1.getText(), false, "GunPlayer2", player2GunDefault, player2GunShooting));


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
        Game.setPlayer1(new Player(Name1.getText(), AiCheckbox1.isSelected(), "GunPlayer1", player1GunDefault, player1GunShooting));
        Game.setPlayer2(new Player(Name2.getText(), AiCheckbox2.isSelected(), "GunPlayer2", player2GunDefault, player2GunShooting));
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



    /**
     * //Sets up the Placement Grid for the User, the first one is for player 1, second one for player 2
     * //Potential for improvement
     */
    public void initPlacementGrid(int Player) {
        if (Player == 0) {
            Game.getPlayer1().getGameBoard().placementGrid = new PlacementGrid(PlacementGridFX, Game.getPlayer1().getGameBoard(), ShipPreview, this);
            PlayerNamePlacement.setText(Game.getPlayer1().getName() + "\r\n... place your ships");
            Game.getPlayer1().getGameBoard().placementGrid.setShipPreview(Game.getPlayer1().getGameBoard().placementShips[0].shipImage);
        } else {
            Game.getPlayer2().getGameBoard().placementGrid = new PlacementGrid(PlacementGridFX, Game.getPlayer2().getGameBoard(), ShipPreview, this);
            Game.getPlayer2().getGameBoard().placementGrid.setShipPreview(Game.getPlayer1().getGameBoard().placementShips[0].shipImage);
            PlayerNamePlacement.setText(Game.getPlayer2().getName() + "\r\n... place your ships");
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
            SceneSwitcher.switchToGame(event);
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

    }



    public void RotateShip() {
        for (Ship ship:Game.getPlayer1().getGameBoard().placementShips) {
            ship.turnShip();
        }
        for (Ship ship:Game.getPlayer2().getGameBoard().placementShips) {
            ship.turnShip();
        }

        if (ShipPreview.getTransforms().isEmpty()) {
            Rotate rotateImage = new Rotate(90, ShipPreview.getTranslateX()+ (double) Game.cellSize, ShipPreview.getTranslateY()+ (double) Game.cellSize);
            ShipPreview.getTransforms().add(rotateImage);
        } else {
            ShipPreview.getTransforms().clear();
        }
    }

    public void activateAiButton() {
        if (AiCheckbox1.isDisabled()) {
            AiCheckbox1.disableProperty().set(false);
        } else {
            AiCheckbox1.disableProperty().set(true);
            if (AiCheckbox1.isSelected()) {
                AiCheckbox1.selectedProperty().set(false);
            }
        }
    }

    /**
     * Initializes both gamefields
     */
    public void StartGame() {
        Game.getPlayer1().gameGrid = new GameGrid(Map1, Game.getPlayer1(), this);
        Game.getPlayer2().gameGrid = new GameGrid(Map2, Game.getPlayer2(), this);
        Game.winningScreen = WinningScreen;
        Game.HitHappened();
        StartGame.disableProperty().set(true);
    }
}