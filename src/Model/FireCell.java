package Model;

import Controller.CellShape;
import Controller.Grid;

import java.util.ArrayList;
import java.util.List;

public class FireCell extends Cell {
    //Neighbors only directly top/bottom and left/right
    /**
     * Super constructor used for 3 states
     * 0 = empty; 1 = tree; 2 = burning
     * @param row
     * @param col
     * @param state
     */
    public FireCell(int row, int col, int state, int numStates){
        super(row, col, state, numStates);
    }
    public FireCell(int row, int col, int state) { super(row, col, state); }

    /**
     * update method implements fire simulation rules
     * @param neighbors
     * @param cellGrid
     * @param shape
     * @return
     */
    @Override
    public List<Cell> updateCell(List<Cell> neighbors, Grid cellGrid, CellShape shape) {
        List<Cell> temp = new ArrayList<>();
        //Empty stays empty and burning goes to empty always (o/w check neighbors)
        if(getMyCurrentState() == 1){
            for(Cell c: neighbors){
                if(c.getMyCurrentState() == 2){
                    setMyNextState(2);
                    break;
                }
            }
        }
        else{
            setMyNextState(0);
        }
        temp.add(this);
        return temp;
        //Grid.setCellAt(getMyRow(), getMyCol(), this);
        //cellGrid[getMyRow()][getMyCol()] = this;
        //return cellGrid;
    }
}
