package Model;

import Controller.CellShape;
import Controller.Grid;

import java.util.List;
import java.util.Objects;

abstract public class Cell {


    protected int myTotalStates;
    private int myCurrentState;
    private int myNextState;
    private int myRow;
    private int myCol;

    /**
     * Constructor - used in concrete subclasses
     * @param row
     * @param col
     * @param state
     */
    public Cell(int row, int col, int state, int numStates) {
        myRow = row;
        myCol = col;
        myCurrentState = state;
        myNextState = state;
        myTotalStates = numStates;
    }

    /**
     * Method called by grid class's resetCell method (which is called in main)
     */
    public void resetState() { myCurrentState = myNextState; }
    /**
     * getter returns current state
     * @return
     */
    public int getMyCurrentState(){return myCurrentState;}

    //Getters and setters for variables (package private because used in concrete subclasses)
    int getMyNextState(){return myNextState;}
    public int getMyRow() { return myRow; }
    public int getMyCol() { return myCol; }
    void setMyRow(int newRow) {this.myRow = newRow;}
    void setMyCol(int newCol) {this.myCol = newCol;}
    public void setMyCurrentState(int newState) { this.myCurrentState = newState; }
    public void setMyNextState(int newState) {this.myNextState = newState; }

    /**
     * Abstract method differs per cell type
     * Where the rules for different simulations are implemented
     * @param neighbors
     * @param cellGrid
     * @param shape
     * @return
     */
    abstract public List<Cell> updateCell(List<Cell> neighbors, Grid cellGrid, CellShape shape);

    /**
     * Equals method checks if this cell equals another in question
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return myCurrentState == cell.myCurrentState &&
                myRow == cell.myRow &&
                myCol == cell.myCol;
    }

    /**
     * Returns a hashcode for this cell
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(myCurrentState, myRow, myCol);
    }
}
