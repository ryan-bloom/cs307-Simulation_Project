package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SegregationCell extends Cell {
    //private static final Color[] COLORS = {Color.WHITE, Color.RED, Color.BLUE};
    private static final double THRESHOLD = 0.7; //30% for satisfaction

    /**
     * 0 = empty; 1 = red; 2 = blue
     * @param row
     * @param col
     * @param state
     */
    public SegregationCell(int row, int col, int state){
        super(row, col, state);
    }

    @Override
    public Cell[][] updateCell(List<Cell> neighbors, Cell[][] cellGrid) {
        if(this.myCurrentState != 0){
            double percSame = findPercentageSame(neighbors);
            if(percSame < THRESHOLD){//this cell is unsatisfied -- moves
                var newLoc = randomEmptyLocation(findEmptyCells(cellGrid));
                cellGrid[newLoc[0]][newLoc[1]].myNextState = this.myCurrentState;
                cellGrid[newLoc[0]][newLoc[1]].myCurrentState = this.myCurrentState;
                cellGrid[myRow][myCol].myCurrentState = 0;
                cellGrid[myRow][myCol].myNextState = 0;
            }
        }
        return cellGrid;
    }

    public double findPercentageSame(List<Cell> neighbors){
        int same = 0;
        double total = 0;
        for(Cell c: neighbors) {
            if (c.myCurrentState != 0) {
                total++;
                if (c.myCurrentState == this.myCurrentState) {
                    same++;
                }
            }
        }
        return same/total;
    }

    public List<Cell> findEmptyCells(Cell[][] cellGrid){
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

    public int[] randomEmptyLocation(List<Cell> possibleCells){
        int[] res = new int[2];
        Random rand = new Random();
        int dex = rand.nextInt(possibleCells.size());
        res[0] = possibleCells.get(dex).myRow;
        res[1] = possibleCells.get(dex).myCol;
        return res;
     }
}