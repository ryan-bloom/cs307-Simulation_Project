package Model;
import Controller.CellShape;
import Controller.Grid;

import java.util.ArrayList;
import java.util.List;


public class GameOfLifeCell extends Cell {
    //Rule numbers vary for cell shapes
    private static final int SQUARE_REVIVE = 3;
    private static final int SQUARE_SURVIVE1 = 2;
    private static final int SQUARE_SURVIVE2 = 3;

    private static final int HEX_REVIVE = 2;
    private static final int HEX_SURVIVE1 = 3;
    private static final int HEX_SURVIVE2 = 5;

    private static final int TRI_REVIVE = 3;
    private static final int TRI_SURVIVE1 = 2;
    private static final int TRI_SURVIVE2 = 7;

    /**
     * Constructor uses super to set cell and states (2 possible)
     * 0 = dead; 1 = alive
     * @param row
     * @param col
     * @param state
     */
    public GameOfLifeCell(int row, int col, int state, int numStates){
        super(row, col, state, numStates);
    }
    //public GameOfLifeCell(int row, int col, int state) { super(row, col, state); }

    /**
     * Update method splits into 3 update methods
     * 1 implements rules for square cells; 1 for hexagonal cells; 1 for triangular cells
     * Need 3 because different rules for each shape
     * @param neighbors
     * @param cellGrid
     * @param shape
     * @return
     */
    @Override
    public List<Cell> updateCell(List<Cell> neighbors, Grid cellGrid, CellShape shape) {
        List<Cell> temp = new ArrayList<>();
        int liveCount = getLiveCount(neighbors);
        if(shape == CellShape.SQUARE){
            allUpdate(liveCount, SQUARE_REVIVE, SQUARE_SURVIVE1, SQUARE_SURVIVE2);
        }
        else if(shape == CellShape.HEXAGON){
            allUpdate(liveCount, HEX_REVIVE, HEX_SURVIVE1, HEX_SURVIVE2);
        }
        else{
            allUpdate(liveCount, TRI_REVIVE, TRI_SURVIVE1, TRI_SURVIVE2);
        }
        //Set the cell at this location to this (with newly updated states)
        temp.add(this);
        return temp;
    }

    private void allUpdate(int liveCount, int revive, int survive1, int survive2){
        if(this.getMyCurrentState() == 1){
            if(liveCount == survive1 || liveCount == survive2){
                this.setMyNextState(1);
            }
            else{
                this.setMyNextState(0);
            }
        }
        else if(liveCount == revive) {
            this.setMyNextState(1);
        }
    }

    /**
     * Helper method gets number of living neighbor cells
     * @param neighbors
     * @return
     */
    private int getLiveCount(List<Cell> neighbors){
        int liveCount = 0;
        for(Cell n : neighbors){
            if(n.getMyCurrentState() == 1){
                liveCount++;
            }
        }
        return liveCount;
    }
}
