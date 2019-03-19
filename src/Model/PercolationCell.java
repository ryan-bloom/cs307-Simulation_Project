package Model;
import Controller.CellShape;
import Controller.Grid;

import java.util.ArrayList;
import java.util.List;

public class PercolationCell extends Cell {
    /**
     * Constructor uses super
     * 0 = blocked; 1 = open; 2 = percolating
     * @param row
     * @param col
     * @param state
     */
    public PercolationCell(int row, int col, int state, int numStates){
        super(row, col, state, numStates);
    }
    //public PercolationCell(int row, int col, int state) { super(row, col, state); }

    /**
     * Implement rules for percolation simulation
     * @param neighbors
     * @param cellGrid
     * @param shape
     * @return
     */
    @Override
    public List<Cell> updateCell(List<Cell> neighbors, Grid cellGrid, CellShape shape) {
        List<Cell> temp = new ArrayList<>();
        //Only state changes happen if current state is 1 (then check neighbors)
        if (this.getMyCurrentState() == 1){
            for (Cell n : neighbors){
                if(n.getMyCurrentState() == 2){
                    this.setMyNextState(2);
                    temp.add(this);
                    //cellGrid[getMyRow()][getMyCol()] = this;
                }
            }
        }
        return temp;
        //return cellGrid;
    }
}
