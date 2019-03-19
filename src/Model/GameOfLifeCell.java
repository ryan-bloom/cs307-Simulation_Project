package Model;
import Controller.CellShape;

import java.util.List;


public class GameOfLifeCell extends Cell {
    /**
     * Constructor uses super to set cell and states (2 possible)
     * 0 = dead; 1 = alive
     * @param row
     * @param col
     * @param state
     */
    public GameOfLifeCell(int row, int col, int state){
        super(row, col, state);
    }

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
    public Cell[][] updateCell(List<Cell> neighbors, Cell[][] cellGrid, CellShape shape) {
        int liveCount = getLiveCount(neighbors);
        if(shape == CellShape.SQUARE){
            squareUpdate(liveCount);
        }
        else if(shape == CellShape.HEXAGON){
            hexUpdate(liveCount);
        }
        else{
            triUpdate(liveCount);
        }
        //Set the cell at this location to this (with newly updated states)
        cellGrid[getMyRow()][getMyCol()] = this;
        return cellGrid;
    }

    /**
     * rules for square cells implemented here (3/2,3)
     * @param liveCount
     */
    private void squareUpdate(int liveCount){
        if(this.getMyCurrentState() == 1){
            if(liveCount < 2 || liveCount >= 4){
                this.setMyNextState(0);
            }
            else{
                this.setMyNextState(1);
            }
        }
        else if(liveCount == 3) {
            this.setMyNextState(1);
        }
    }

    /**
     * rules for hex cells implemented here (2/3,5)
     * @param liveCount
     */
    private void hexUpdate(int liveCount){
        if(this.getMyCurrentState() == 1){
            if(liveCount == 3 || liveCount == 5){
                this.setMyNextState(1);
            }
            else{ this.setMyNextState(0); }
        }
        else if(liveCount == 2){
            this.setMyNextState(1);
        }
    }

    /**
     * rules for triangle cells implemented here (3/2,7)
     * @param liveCount
     */
    private void triUpdate(int liveCount){
        if(this.getMyCurrentState() == 1){
            if(liveCount == 2 || liveCount == 7){
                this.setMyNextState(1);
            }
            else{this.setMyNextState(0);}
        }
        else if(liveCount == 3){
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
