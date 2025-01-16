package com.example.sinkingships;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;

//all major logic should happen here, because in here all created objects can interact with one another
public class MainController {
    @FXML
    public AnchorPane RootPane;
    @FXML
    public TextField Name1;
    @FXML
    public TextField Name2;
    @FXML
    public CheckBox AiCheckbox;
    @FXML
    public GridPane PlacementGridFX;
    @FXML
    public GridPane ContainerGrid;
    @FXML
    public Label PlayerNamePlacement;
    @FXML
    public Button StartGame;
    @FXML
    public GridPane Map1;
    @FXML
    public GridPane Map2;
    @FXML
    public ImageView GunPlayer1;
    @FXML
    public ImageView GunPlayer2;

    public int gridCounter = 0;
    public SceneSwitcher SceneSwitcher = new SceneSwitcher();

    /**
     * Everything in this block is to try out stuff with the moving canons and playing sounds
     */
    @FXML
    public void initialize() {
        // Runs the method to rotate guns every time the mouse cursor is moved inside the root pane
        // If the parameter given to this method is 'null' it removes the method that makes the gun follow the cursor
        // Suggestion: Only the gun of the active player should follow the cursor
        // setOnMouseMoved only works as long as the cursor doesn't enter a button, so for every button the methods need to be run separately
        if(GunPlayer2 != null) {
            GunPlayer2.setOpacity(0.6);
            RootPane.setOnMouseMoved(this::autoRotateLeftGun);
            RootPane.setOnMouseClicked(this::testShooting);
        }
    }
    private void testShooting(MouseEvent mouseEvent) {
        Thread testThread = new Thread(this::testShootingThread);
        testThread.start();
    }
    private void testShootingThread(){
        if(GunPlayer1 != null){
            GunPlayer1.setImage(new Image(String.valueOf(getClass().getResource("/img/canon_3_paper_boom.png"))));
        }

        // Playing around with playing sounds at random
        try {
            URL audioFile = getClass().getResource("/audio/boom.wav");
            Random random = new Random();
            int shot = random.nextInt(3);
            if(shot == 1){
                audioFile = getClass().getResource("/audio/boom2.wav");
            }
            if(shot == 2) {
                audioFile = getClass().getResource("/audio/boom3.wav");
            }
            if(shot == 0) {
                audioFile = getClass().getResource("/audio/boom.wav");
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            //FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            //gainControl.setValue(random.nextFloat(0.0f,1.0f));
            //float range = gainControl.getMaximum() - gainControl.getMinimum();
            //float gain = (range * random.nextFloat(0.5f,1.0f)) + gainControl.getMinimum();
            //gainControl.setValue(gain);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        // Waiting for 200 milliseconds before switching out the sprite again
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if(GunPlayer1 != null){
            GunPlayer1.setImage(new Image(String.valueOf(getClass().getResource("/img/canon_3_paper_long.png"))));
        }
    }
    private void autoRotateBothGuns(MouseEvent eventFromMouse) {
        autoRotateLeftGun(eventFromMouse);
        autoRotateRightGun(eventFromMouse);
    }
    public void autoRotateLeftGun(MouseEvent eventFromMouse) {
        double gunCenterX = GunPlayer1.getLayoutX() + GunPlayer1.getFitWidth() / 2;
        double gunCenterY = GunPlayer1.getLayoutY() + GunPlayer1.getFitHeight() / 2;
        double gunAngle = getAngleForImage(gunCenterX, gunCenterY, eventFromMouse);

        // Keeps the gun from rotating beyond a certain threshold
        if (gunAngle > 80){
            gunAngle = 80;
        }
        if (gunAngle < -80){
            gunAngle = -80;
        }

        GunPlayer1.setRotate(gunAngle);
    }
    public void autoRotateRightGun(MouseEvent eventFromMouse) {
        double gunCenterX = GunPlayer2.getLayoutX() + GunPlayer2.getFitWidth() / 2;
        double gunCenterY = GunPlayer2.getLayoutY() + GunPlayer2.getFitHeight() / 2;
        double gunAngle = getAngleForImage(gunCenterX, gunCenterY, eventFromMouse);

        // Keeps the gun from rotating beyond a certain threshold
        if (gunAngle < 100 && gunAngle >= 0){
            gunAngle = 100;
        }
        if (gunAngle > -100 && gunAngle < 0){
            gunAngle = -100;
        }

        GunPlayer2.setRotate(gunAngle + 180); // the 180 degrees are added so the gun is looking in the right direction
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
        Game.returnGame().setPlayer1(new Player(Name1.getText(), false));
        Game.returnGame().setPlayer2(new Player(Name1.getText(), false));


        //Output general Player info
        System.out.println(Game.returnGame().getPlayer1().playerInfo());
        System.out.println(Game.returnGame().getPlayer1().playerInfo());

        //Example how to manipulate the individual nodes of the gameboard
        Game.returnGame().getPlayer1().getGameBoard().getCell(2, 4).setOccupied();
        Game.returnGame().getPlayer1().getGameBoard().getCell(2, 5).setOccupied();
        Game.returnGame().getPlayer1().getGameBoard().getCell(2, 6).setOccupied();

        Game.returnGame().getPlayer1().getGameBoard().getCell(2, 4).setIsHit();
        Game.returnGame().getPlayer1().getGameBoard().getCell(3, 4).setIsHit();
        Game.returnGame().getPlayer1().getGameBoard().getCell(4, 4).setIsHit();

        //example of placing a ship on the board
        Ship shipHorizontal = new Ship(3, true);
        Game.returnGame().getPlayer1().getGameBoard().placeShip(shipHorizontal, Game.returnGame().getPlayer1().getGameBoard().getCell(5, 8));

        Ship shipVertical = new Ship(4, false);
        Game.returnGame().getPlayer1().getGameBoard().placeShip(shipVertical, Game.returnGame().getPlayer1().getGameBoard().getCell(9, 5));

        //example of checking if "shipVertical" is sunk
        Game.returnGame().getPlayer1().getGameBoard().getCell(9, 5).setIsHit();
        Game.returnGame().getPlayer1().getGameBoard().getCell(9, 6).setIsHit();
        Game.returnGame().getPlayer1().getGameBoard().getCell(9, 7).setIsHit();
        Game.returnGame().getPlayer1().getGameBoard().getCell(9, 8).setIsHit();

        System.out.println("Is Ship Sunk?: " + shipVertical.checkIfSunk());

        //example of checking if Player Lost
        //It only checks ships, not any other occupied cells
        System.out.println("Player 1 Lost?: " + Game.returnGame().getPlayer1().checkIfLost());


        //Outputs the Gameboard of Player1 in a comprehensible form into the Terminal
        Game.returnGame().getPlayer1().getGameBoard().outputTextVersion();
    }
    /**
     * Generates two players and adds them to the Game object
     */
    public void generatePlayers() {
        Game.returnGame().setPlayer1(new Player(Name1.getText(), false));
        Game.returnGame().setPlayer2(new Player(Name2.getText(), AiCheckbox.isSelected()));
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
        Game game = Game.returnGame();
        if (Player == 0) {
            game.getPlayer1().getGameBoard().placementGrid = new PlacementGrid(PlacementGridFX, game.getPlayer1().getGameBoard());
            PlayerNamePlacement.setText(Game.returnGame().getPlayer1().getName());
        } else {
            game.getPlayer2().getGameBoard().placementGrid = new PlacementGrid(PlacementGridFX, game.getPlayer2().getGameBoard());
            PlayerNamePlacement.setText(Game.returnGame().getPlayer2().getName());
        }
    }
    /**
     * //Sets up the Placement Grid for the User, the first one is for player 1, second one for player 2
     * //Potential for improvement
     */
    public void nextGrid(ActionEvent event) throws IOException {
        Game game = Game.returnGame();
        if (gridCounter == 0) {
            StartGame.setText("Next Player");
        } else if (gridCounter == 1) {
            game.getPlayer1().getGameBoard().placementGrid.resetField();
            StartGame.setText("Start Game");
        } else if (gridCounter == 2) {
            switchToGame(event);
        }
        initPlacementGrid(gridCounter);
        gridCounter++;
    }

    /**
     * Initializes both gamefields
     */
    public void StartGame() {
        Game game = Game.returnGame();
        game.getPlayer1().gameGrid = new GameGrid(Map1, game.getPlayer1());
        game.getPlayer2().gameGrid = new GameGrid(Map2, game.getPlayer2());
        game.HitHappened();
    }




}