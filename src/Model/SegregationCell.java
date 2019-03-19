package Model;

import Controller.CellShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SegregationCell extends Cell {
    private static final double THRESHOLD = 0.7; //70% for satisfaction = more segregation

    /**
     * Constructor sets cell of 1 of 3 states
     * 0 = empty; 1 = red; 2 = blue
     * @param row
     * @param col
     * @param state
     */
    public SegregationCell(int row, int col, int state){
        super(row, col, state);
    }

    /**
     * Implements segregation simulation rules
     * uses helper methods to determine if cell is "happy" and if not -- moves to empty location
     * @param neighbors
     * @param cellGrid
     * @param shape
     * @return
     */
    @Override
    public Cell[][] updateCell(List<Cell> neighbors, Cell[][] cellGrid, CellShape shape) {
        if(this.getMyCurrentState() != 0){
            double percSame = findPercentageSame(neighbors);
            if(percSame < THRESHOLD){//this cell is unsatisfied -- moves
                var newLoc = randomEmptyLocation(findEmptyCells(cellGrid));
                cellGrid[newLoc[0]][newLoc[1]].setMyNextState(this.getMyCurrentState());
                cellGrid[newLoc[0]][newLoc[1]].setMyCurrentState(this.getMyCurrentState());
                cellGrid[getMyRow()][getMyCol()].setMyCurrentState(0);
                cellGrid[getMyRow()][getMyCol()].setMyNextState(0);
            }
        }
        return cellGrid;
    }

    /**
     * Helper method gets number of similar neighbors
     * Used in update method to compare to threshold and determine if cell should move
     * @param neighbors
     * @return
     */
    double findPercentageSame(List<Cell> neighbors){
        int same = 0;
        double total = 0;
        for(Cell c: neighbors) {
            if (c.getMyCurrentState() != 0) {
                total++;
                if (c.getMyCurrentState() == this.getMyCurrentState()) {
                    same++;
                }
            }
        }
        return same/total;
    }

    /**
     * Helper method finds empty cell location if this cell is unhappy
     * @param cellGrid
     * @return
     */
    List<Cell> findEmptyCells(Cell[][] cellGrid){
        List<Cell> res = new ArrayList<>();
        for(int i=0; i<cellGrid.length; i++){
            for(int j=0; j<cellGrid.length; j++){
                if(cellGrid[i][j].getMyCurrentState() == 0){
                    res.add(cellGrid[i][j]);
                }
            }
        }
        return res;
    }

    /**
     * Helper method randomly selects one of the open locations found by helper method above
     * Allows cell to randomly jump to empty locations
     * @param possibleCells
     * @return
     */
    int[] randomEmptyLocation(List<Cell> possibleCells){
        int[] res = new int[2];
        Random rand = new Random();
        int dex = rand.nextInt(possibleCells.size());
        res[0] = possibleCells.get(dex).getMyRow();
        res[1] = possibleCells.get(dex).getMyCol();
        return res;
     }
}