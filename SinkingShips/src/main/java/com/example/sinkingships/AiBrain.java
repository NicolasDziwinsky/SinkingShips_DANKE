package com.example.sinkingships;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AiBrain {
    public Player targetPlayer;
    public Player correspondingPlayer;
    public Cell lastCellHit;
    public ArrayList<Cell> cellsHit = new ArrayList<Cell>();
    public Random rand = new Random();
    public int currentState;

    AiBrain(Player targetPlayer, Player correspondingPlayer) {
        this.targetPlayer = targetPlayer;
        this.correspondingPlayer = correspondingPlayer;
        cellsHit.addAll(Arrays.asList(targetPlayer.getGameBoard().getCells()));
    }

    public void hitCell() {
        Object randResult[] = randomCell(cellsHit.toArray(new Cell[cellsHit.size()]));

        System.out.println(cellsHit.size());

        Cell randCell = (Cell) randResult[0];
        int randInt = (int) randResult[1];

        System.out.println(randInt+ " hit");

        if (randCell.isOccupied() && !randCell.IsHit()) {
            cellsHit.remove(randCell);
            System.out.println(randInt+ " removed");
            lastCellHit = randCell;
            currentState = 1;
            targetPlayer.gameGrid.onPress(randCell, targetPlayer);
        } else if (!randCell.isOccupied() && !randCell.IsHit()) {
            cellsHit.remove(randCell);
            System.out.println(randInt+ " removed");
            targetPlayer.gameGrid.onPress(randCell, targetPlayer);
            currentState = 0;
        } else {
            System.out.println("Already Hit");
        }

    }

    public Object[] randomCell(Object[] cells) {
        int maxValue = cells.length;
        Object[] returnObjects;
        if (maxValue == 0) {
            returnObjects = new Object[]{new Cell(1, 1), 0};
            return returnObjects;
        } else if (maxValue == 1) {
            int value = 0;
            returnObjects = new Object[]{cells[0], value};
        } else {
            int value = rand.nextInt(maxValue);
            returnObjects = new Object[]{cells[value], value};
        }
        return returnObjects;
    }

    public void placeShip() {
        Object[] returnObjects = randomCell(correspondingPlayer.getGameBoard().getCells());
        Cell randCell = (Cell) returnObjects[0];
        correspondingPlayer.getGameBoard().placeNextShip(randCell);
    }

}
