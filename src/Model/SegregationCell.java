package Model;

import javafx.scene.paint.Color;
import java.util.List;

public class SegregationCell extends Cell {
    //private static final Color[] COLORS = {Color.WHITE, Color.RED, Color.BLUE};
    private static final double THRESHOLD = 0.3; //30% for satisfaction

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
        double percSame = findPercentageSame(neighbors);
        if(percSame <= THRESHOLD){//this cell is unsatisfied -- moves
            var emptyLocation = findEmptyCell(cellGrid);
            var tempCell = cellGrid[emptyLocation[0]][emptyLocation[1]];
            tempCell.myNextState = this.myCurrentState;
            cellGrid[emptyLocation[0]][emptyLocation[1]] = tempCell;
            cellGrid[myRow][myCol].myNextState = 0;
            //this.myNextState = 0;
        }
        return cellGrid;
    }

    public double findPercentageSame(List<Cell> neighbors){
        int diff = 0;
        for(Cell c: neighbors){
            if(c.myCurrentState != 0 && c.myCurrentState == myCurrentState){
                diff++;
            }
        }
        return diff/8.0;
    }

   public int[] findEmptyCell(Cell[][] cellGrid){
        int[] res = new int[2];
        for (int i = 0; i < cellGrid.length; i++) {
            for (int j = 0; j < cellGrid[0].length; j++) {
                if(cellGrid[i][j].getMyCurrentState() == 0){
                    res[0] = i;
                    res[1] = j;
                    //System.out.println(res[0] + " " + res[1]);
                    return res;
                }
            }
        }
        return res;
    }
}
