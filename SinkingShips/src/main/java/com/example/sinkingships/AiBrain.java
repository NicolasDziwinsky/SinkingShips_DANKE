package com.example.sinkingships;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AiBrain {
    public Player targetPlayer;
    public Player correspondingPlayer;
    /**
     * The Last cell hit and occupied
     */
    public Cell lastCellHit;
    /**
     * All the cells on the field of the
     * target player
     */
    public ArrayList<Cell> cellsNotHit = new ArrayList<Cell>();
    public Random rand = new Random();
    /**
     * Current state of the ai
     * 0: Hitting randomly, until hit cell is occupied
     * 1: Last Cell hit was occupied, tries surrounding cells
     */
    public int currentState;

    AiBrain(Player targetPlayer, Player correspondingPlayer) {
        this.targetPlayer = targetPlayer;
        this.correspondingPlayer = correspondingPlayer;
        cellsNotHit.addAll(Arrays.asList(targetPlayer.getGameBoard().getCells()));
    }

    /**
     * hits a cell in the cellsNotHit list (with or without delay)
     */
    public void hitCell(Boolean withDelay) {
        Cell cellToHit = DecideCellToHit();
        System.out.println(cellToHit.getX() + "/" + cellToHit.getY());
        cellsNotHit.remove(cellToHit);

        if (withDelay) {
            Game.delay(500, () ->targetPlayer.gameGrid.onPress(cellToHit, targetPlayer) );
        } else {
            targetPlayer.gameGrid.onPress(cellToHit, targetPlayer);
        }
    }

    /**
     * Decides which cell to hit, depends on the current state
     */
    public Cell DecideCellToHit() {
        Object randResult[] = randomCell(cellsNotHit.toArray(new Cell[cellsNotHit.size()]));
        Cell randCell = (Cell) randResult[0];
        int randInt = (int) randResult[1];
        System.out.println(correspondingPlayer.getName() + " Current State: " + currentState);

        switch (currentState) {
            //Searching
            case 0:
                if (randCell.isOccupied() && !randCell.IsHit()) {
                    lastCellHit = randCell;
                    currentState = 1;
                    return randCell;
                } else if (!randCell.isOccupied() && !randCell.IsHit()) {
                    currentState = 0;
                    return  randCell;
                }
                break;
            //Attack mode
            case 1:
                int x = lastCellHit.getX();
                int y = lastCellHit.getY();
                ArrayList<Cell> possibleNextMoves = new ArrayList<>();

                if (cellsNotHit.contains(targetPlayer.getGameBoard().getCell(x+1, y))) {
                    possibleNextMoves.add(targetPlayer.getGameBoard().getCell(x+1, y));
                }
                if (cellsNotHit.contains(targetPlayer.getGameBoard().getCell(x-1, y))) {
                    possibleNextMoves.add(targetPlayer.getGameBoard().getCell(x-1, y));
                }
                if (cellsNotHit.contains(targetPlayer.getGameBoard().getCell(x, y+1))) {
                    possibleNextMoves.add(targetPlayer.getGameBoard().getCell(x, y+1));
                }
                if (cellsNotHit.contains(targetPlayer.getGameBoard().getCell(x, y-1))) {
                    possibleNextMoves.add(targetPlayer.getGameBoard().getCell(x, y-1));
                }


                for(Cell cell:possibleNextMoves) {
                    System.out.print(cell.getX() + "/" + cell.getY() + " ");
                }

                if (possibleNextMoves.size() == 0) {
                    currentState = 0;
                    return DecideCellToHit();
                }

                randCell = possibleNextMoves.get(rand.nextInt(possibleNextMoves.size()));


                if (randCell.isOccupied()) {
                    lastCellHit = randCell;
                    return randCell;
                } else if (!randCell.isOccupied() && !randCell.IsHit() && currentState == 1) {
                    return  randCell;
                } else {
                    currentState = 0;
                    return  randCell;
                }
        }
        return randCell;
    }

    /**
     * Returns an Array of objects, [0] is the random cell generated out of
     * the passed Array, [1] is the numeric value generated.
     *
     * Generates Number, that can only be as large as the Cell array length - 1
     * Returns cell from array with the random number as the index for the array
     */
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

}
