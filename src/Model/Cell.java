package Model;

import Controller.CellShape;

import java.util.List;
import java.util.Objects;

abstract public class Cell{


    protected int myTotalStates;
    private int myCurrentState;
    private int myNextState;
    private int myRow;
    private int myCol;

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

    int getMyRow() { return myRow; }
    int getMyCol() { return myCol; }
    void setMyRow(int newRow) {this.myRow = newRow;}
    void setMyCol(int newCol) {this.myCol = newCol;}
    void setMyCurrentState(int newState) { this.myCurrentState = newState; }
    void setMyNextState(int newState) {this.myNextState = newState; }

    //abstract public void updateCell(List<Cell> neighbors, Cell[][] cellGrid);
    abstract public Cell[][] updateCell(List<Cell> neighbors, Cell[][] cellGrid, CellShape shape);



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
