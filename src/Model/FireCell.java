package Model;

import Controller.CellShape;

import java.util.List;

public class FireCell extends Cell {
    //Neighbors only directly top/bottom and left/right
    /**
     * 0 = empty; 1 = tree; 2 = burning
     * @param row
     * @param col
     * @param state
     */
    public FireCell(int row, int col, int state){
        super(row, col, state);
    }

    @Override
    public Cell[][] updateCell(List<Cell> neighbors, Cell[][] cellGrid, CellShape shape) {
        //Empty stays empty burning goes to empty
        if(myCurrentState == 1){
            for(Cell c: neighbors){
                if((c.myCol == myCol || c.myRow == myRow) && c.myCurrentState == 2){
                    myNextState = 2;
                    break;
                }
            }
        }
        else{
            myNextState = 0;
        }
        cellGrid[myRow][myCol] = this;
        return cellGrid;
    }
}
