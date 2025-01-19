package com.example.sinkingships;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
        Cell cellToHit = DecideCellToHit();
        System.out.println(cellToHit.getX() + "/" + cellToHit.getY());
        cellsHit.remove(cellToHit);
        Game.delay(500, () ->targetPlayer.gameGrid.onPress(cellToHit, targetPlayer) );

    }


    public Cell DecideCellToHit() {
        Object randResult[] = randomCell(cellsHit.toArray(new Cell[cellsHit.size()]));
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
                int randAxisDecision = rand.nextInt(1,3);
                ArrayList<Cell> possibleNextMoves = new ArrayList<>();

                if (cellsHit.contains(targetPlayer.getGameBoard().getCell(x+1, y))) {
                    possibleNextMoves.add(targetPlayer.getGameBoard().getCell(x+1, y));
                }
                if (cellsHit.contains(targetPlayer.getGameBoard().getCell(x-1, y))) {
                    possibleNextMoves.add(targetPlayer.getGameBoard().getCell(x-1, y));
                }
                if (cellsHit.contains(targetPlayer.getGameBoard().getCell(x, y+1))) {
                    possibleNextMoves.add(targetPlayer.getGameBoard().getCell(x, y+1));
                }
                if (cellsHit.contains(targetPlayer.getGameBoard().getCell(x, y-1))) {
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
