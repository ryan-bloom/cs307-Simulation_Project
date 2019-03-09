package Model;

import java.util.List;
import java.util.Objects;

abstract public class Cell{
    protected int myCurrentState;
    protected int myNextState;
    protected int myRow;
    protected int myCol;
    protected int myTotalStates;

    public Cell(int row, int col, int state, int numStates) {
        myRow = row;
        myCol = col;
        myCurrentState = state;
        myNextState = state;
        myTotalStates = numStates;
    }

    public Cell(int row, int col, int state) {
        myRow = row;
        myCol = col;
        myCurrentState = state;
        myNextState = state;
    }

    public void resetState() { myCurrentState = myNextState; }
    public int getMyCurrentState(){return myCurrentState;}
    public int getMyNextState(){return myNextState;}
    public int incrementMyCurrentState() {
        if (myCurrentState == myTotalStates - 1) {
            myCurrentState = 0;
        }
        else if (myCurrentState < myTotalStates - 1) {
            myCurrentState++;
        }
        myNextState = myCurrentState;
        return myCurrentState;
    }

    //abstract public void updateCell(List<Cell> neighbors, Cell[][] cellGrid);
    abstract public Cell[][] updateCell(List<Cell> neighbors, Cell[][] cellGrid);



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
