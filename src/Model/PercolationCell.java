package Model;
import Controller.CellShape;

import java.util.List;

public class PercolationCell extends Cell {
    /**
     * 0 = blocked; 1 = open; 2 = percolating
     * @param row
     * @param col
     * @param state
     */
    public PercolationCell(int row, int col, int state, int numStates){
        super(row, col, state, numStates);
    }
    public PercolationCell(int row, int col, int state) { super(row, col, state); }

    @Override
    public Cell[][] updateCell(List<Cell> neighbors, Cell[][] cellGrid, CellShape shape) {
        if (this.getMyCurrentState() == 1){
            for (Cell n : neighbors){
                if(n.getMyCurrentState() == 2){
                    this.setMyNextState(2);
                    cellGrid[getMyRow()][getMyCol()] = this;
                }
            }
        }
        return cellGrid;
    }
}
