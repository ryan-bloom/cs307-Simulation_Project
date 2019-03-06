package Model;

import Controller.CellShape;

import java.util.List;

public class RPSCell extends Cell{
    private static final int THRESHOLD = 3;

    /**
     * 0 = rock; 1 = paper; 2 = scissor
     * @param row
     * @param col
     * @param state
     */
    public RPSCell(int row, int col, int state){
        super(row, col, state);
    }

    @Override
    public Cell[][] updateCell(List<Cell> neighbors, Cell[][] cellGrid, CellShape shape) {
        //rock = index 0; paper = index 1; scissor = index 2
        int[] nCounts = {0, 0, 0};
        for(Cell c: neighbors){
            int tempState = c.myCurrentState;
            if(tempState != myCurrentState){
                //nCounts[tempState] = nCounts[tempState] + 1;
                int temp = nCounts[tempState] + 1;
                nCounts[tempState] = temp;
                //Assumption --> first enemy neighbor checked >= THRESHOLD wins
                if(temp >= THRESHOLD){
                    myNextState = tempState;
                    cellGrid[myRow][myCol] = this;
                }
            }
        }
        return cellGrid;
    }
}
