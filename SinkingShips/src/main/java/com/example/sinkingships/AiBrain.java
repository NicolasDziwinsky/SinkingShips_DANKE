package com.example.sinkingships;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AiBrain {
    public Player targetPlayer;
    public Cell lastCellHit;
    public ArrayList<Cell> cellsHit = new ArrayList<Cell>();
    public Random rand = new Random();
    public int currentState;

    AiBrain(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
        cellsHit.addAll(Arrays.asList(targetPlayer.getGameBoard().getCells()));
    }

    public void hitCell() {
        System.out.println(cellsHit.size());
        Object randResult[] = randomCell(cellsHit.toArray(new Cell[cellsHit.size()]));
        System.out.println(cellsHit.size());
        Cell randCell = (Cell) randResult[0];
        int randInt = (int) randResult[1];
        System.out.println(randInt+ " hit");
        if (randCell.isOccupied() && !randCell.IsHit()) {
            targetPlayer.gameGrid.onPress(randCell, targetPlayer);
            cellsHit.remove(randCell);
            System.out.println(randInt+ " removed");
        } else if (!randCell.isOccupied() && !randCell.IsHit()) {
            targetPlayer.gameGrid.onPress(randCell, targetPlayer);
            cellsHit.remove(randCell);
            System.out.println(randInt+ " removed");
        }

    }

    public Object[] randomCell(Object[] cells) {
        int maxValue = cells.length;
        int value = rand.nextInt(maxValue);
        Object[] returnObjects = new Object[]{cells[value], value};
        return returnObjects;
    }

}
