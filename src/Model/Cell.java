package Model;

import java.util.List;
import java.util.Objects;

abstract public class Cell{
    protected int myCurrentState;
    protected int myNextState;
    protected int myRow;
    protected int myCol;

    public Cell(int row, int col, int state) {
        myRow = row;
        myCol = col;
        myCurrentState = state;
        myNextState = state;
    }

    public void resetState() { myCurrentState = myNextState; }
    public int getMyCurrentState(){return myCurrentState;}
    public int getMyNextState(){return myNextState;}

    abstract public void updateCell(List<Cell> neighbors);


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return myCurrentState == cell.myCurrentState &&
                myRow == cell.myRow &&
                myCol == cell.myCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myCurrentState, myRow, myCol);
    }
}
