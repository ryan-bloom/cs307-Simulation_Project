package Model;

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
        if(this.myCurrentState != 0){
            double percSame = findPercentageSame(neighbors);
            System.out.println(myRow + " " + myCol + " " + percSame);
            if(percSame < THRESHOLD){//this cell is unsatisfied -- moves
                var emptyLocation = findEmptyCell(cellGrid);
                cellGrid[emptyLocation[0]][emptyLocation[1]].myNextState = this.myCurrentState;
                cellGrid[emptyLocation[0]][emptyLocation[1]].myCurrentState = this.myCurrentState;
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
