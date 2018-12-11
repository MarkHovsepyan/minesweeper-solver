package minesweeper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import csp.CSPSolver;

public class SolverAgent {

    private Tile[][] tileArray;
    public static int noOfMines;

    public SolverAgent(Tile[][] tileArray, int noOfMines) {
        this.tileArray = tileArray;
        this.noOfMines = noOfMines;
    }

    public Set<Tile> solve() {
        CSPSolver objCsp = new CSPSolver();
        Map<Tile, Set<Tile>> inputMap = generateInputForCsp();
        if (inputMap == null) {
            //Probably all the Tiles equals no of mines
            for (int i = 0; i < tileArray.length; i++) {
                for (int j = 0; j < tileArray[0].length; j++) {
                    if (tileArray[i][j].isEnabled()) {
                        tileArray[i][j].setMarked(true);
                        noOfMines--;
                        levelCreator.noOfMines--;
                    }
                }
            }
            return null;
        } else {

        }
        Set<Tile> safeTiles = objCsp.solve(inputMap);
        return safeTiles.isEmpty() ? null : safeTiles;
    }

    private Map<Tile, Set<Tile>> generateInputForCsp() {

        Map<Tile, Set<Tile>> retMap = new HashMap<Tile, Set<Tile>>();
        Set<Tile> exploredTiles = getExploredTiles();
        int noOfUnexploredMines = 0;
        Set<Tile> setUnexplored = new HashSet<Tile>();
        for (Tile exploredTile : exploredTiles) {
            Set<Tile> unexploredNeighbours = getUnexploredNeighbours(exploredTile);
            retMap.put(exploredTile, unexploredNeighbours);
            setUnexplored.addAll(unexploredNeighbours);
        }

        if (setUnexplored.size() == noOfMines) {
            return null;
        } else {
            return retMap;
        }

    }

    private Set<Tile> getExploredTiles() {
        Set<Tile> retSet = new HashSet<Tile>();

        for (int i = 0; i < tileArray.length; i++) {
            for (int j = 0; j < tileArray[i].length; j++) {
                if (tileArray[i][j].getNoOfMinesAround() > 0 && !tileArray[i][j].isEnabled()) {
                    retSet.add(tileArray[i][j]);
                }
            }
        }

        return retSet;
    }


    private Set<Tile> getUnexploredNeighbours(Tile objTile) {

        int x = objTile.getLocX();
        int y = objTile.getLocY();
        Set<Tile> retSet = new HashSet<Tile>();

        if (y - 1 >= 0) {
            if (tileArray[x][y - 1].isEnabled()) {
                retSet.add(tileArray[x][y - 1]);
            }
        }

        if (x - 1 >= 0) {
            if (y - 1 >= 0) {
                if (tileArray[x - 1][y - 1].isEnabled()) {
                    retSet.add(tileArray[x - 1][y - 1]);
                }
            }

            if (y + 1 < tileArray[0].length) {
                if (tileArray[x - 1][y + 1].isEnabled()) {
                    retSet.add(tileArray[x - 1][y + 1]);
                }
                if (tileArray[x - 1][y].isEnabled()) {
                    retSet.add(tileArray[x - 1][y]);
                }
            }

            if (y + 1 < tileArray[0].length) {
                if (tileArray[x][y + 1].isEnabled()) {
                    retSet.add(tileArray[x][y + 1]);
                }
            }

            if (x + 1 < tileArray.length) {
                if (tileArray[x + 1][y].isEnabled()) {
                    retSet.add(tileArray[x + 1][y]);
                }

                if (y + 1 < tileArray[0].length) {
                    if (tileArray[x + 1][y + 1].isEnabled()) {
                        retSet.add(tileArray[x + 1][y + 1]);
                    }
                }

                if (y - 1 >= 0) {
                    if (tileArray[x + 1][y - 1].isEnabled()) {
                        retSet.add(tileArray[x + 1][y - 1]);
                    }
                }
            }

        }
        return retSet;
    }
}