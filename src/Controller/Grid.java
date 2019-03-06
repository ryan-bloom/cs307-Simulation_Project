package Controller;

import Model.Cell;
import Model.Data;
import Model.FireCell;
import Model.PercolationCell;

public class Grid {
    private Data myData;
    private Cell[][] myCellGrid;
    private int myRows;
    private int myCols;

    public Grid(Data dat){
        myData = dat;
        myRows = myData.getHeight();
        myCols = myData.getWidth();
    }

    public void fillCellGrid(){
        myCellGrid = new Cell[myRows][myCols];
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                //myCellGrid[i][j] = new GameOfLifeCell(i, j, myData.getStates()[i][j]);
                myCellGrid[i][j] = new PercolationCell(i, j, myData.getStates()[i][j]);
                //myCellGrid[i][j] = new RPSCell(i, j, myData.getStates()[i][j]);
                //myCellGrid[i][j] = new FireCell(i, j, myData.getStates()[i][j]);
                //myCellGrid[i][j] = new SegregationCell(i, j, myData.getStates()[i][j]);
                //myCellGrid[i][j] = new PredatorPreyCell(i, j, myData.getStates()[i][j]);
            }
        }
    }

    public void updateGridCell(int row, int col, CellShape shape, EdgeType edgeType){
        var neighbors = new CompleteNeighbors(row, col, myCellGrid, shape, edgeType);
        //var neighbors = new CardinalNeighbors(row, col, myCellGrid, shape, edgeType);
        //var neighbors = new CornerNeighbors(row, col, myCellGrid, shape, edgeType);
        myCellGrid = myCellGrid[row][col].updateCell(neighbors.getMyNeighbors(), myCellGrid, shape);
        //return myCellGrid[row][col].getMyNextState();
    }

    //public Cell getCellAt(int row, int col){return myCellGrid[row][col];}

    public void resetCell(int row, int col){
        myCellGrid[row][col].resetState();
    }
    public int getCellState(int row, int col){ return myCellGrid[row][col].getMyCurrentState(); }
    public Cell[][] getCellGrid(){ return myCellGrid; }
    public int getMyRows(){return myRows;}
    public int getMyCols(){return myCols;}
}
