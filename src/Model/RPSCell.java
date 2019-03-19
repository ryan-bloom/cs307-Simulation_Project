package Model;

import Controller.CellShape;
import Controller.Grid;

import java.util.ArrayList;
import java.util.List;

public class RPSCell extends Cell{
    private static final int THRESHOLD = 3;

    /**
     * Constructor uses super to set cell of 1 of 3 states
     * 0 = rock; 1 = paper; 2 = scissor
     * @param row
     * @param col
     * @param state
     */
    public RPSCell(int row, int col, int state, int numStates){
        super(row, col, state, numStates);
    }
    //public RPSCell(int row, int col, int state) { super(row, col, state); }

    /**
     * Update method implements rps rules
     * if 2 or more colors reach threshold -- first one found overtakes current cell location
     * @param neighbors
     * @param cellGrid
     * @param shape
     * @return
     */
    @Override
    public List<Cell> updateCell(List<Cell> neighbors, Grid cellGrid, CellShape shape) {
        List<Cell> tempNew = new ArrayList<>();
        //rock = index 0; paper = index 1; scissor = index 2 in nCounts array
        int[] nCounts = {0, 0, 0};
        for(Cell c: neighbors){
            int tempState = c.getMyCurrentState();
            if(tempState != this.getMyCurrentState()){
                int temp = nCounts[tempState] + 1;
                nCounts[tempState] = temp;
                //Assumption --> first enemy neighbor checked >= THRESHOLD wins
                if(temp >= THRESHOLD){
                    this.setMyNextState(tempState);
                    tempNew.add(this);
                    //cellGrid[getMyRow()][getMyCol()] = this;
                }
            }
        }
        return tempNew;
    }
}
