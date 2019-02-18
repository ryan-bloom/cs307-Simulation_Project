package Cells;

import java.util.List;

abstract public class Cell {
    protected int myCurrentState;
    protected int myNextState;
    protected int myRow;
    protected int myCol;

    public Cell(int row, int col, int state){
        myRow = row;
        myCol = col;
        myCurrentState = state;
        myNextState = -1;
    }

    abstract public void updateCell(List<Cell> neighbors);
}
