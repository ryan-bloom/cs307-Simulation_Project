package Model;

import javafx.scene.Node;

import java.util.ArrayList;

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
                //myCellGrid[i][j] = new PercolationCell(i, j, myData.getStates()[i][j]);
                //myCellGrid[i][j] = new RPSCell(i, j, myData.getStates()[i][j]);
                myCellGrid[i][j] = new FireCell(i, j, myData.getStates()[i][j]);
            }
        }
        //return myCellGrid;
    }

    public void resetCell(int row, int col){
        myCellGrid[row][col].resetState();
    }

    public int updateGridCell(int row, int col){
        myCellGrid[row][col].updateCell(findNeighbors(row, col));
        return myCellGrid[row][col].getMyNextState();
    }

    public ArrayList<Cell> findNeighbors(int i, int j) {
        return toroidalNeighbors(i, j);
    }

    public ArrayList<Cell> toroidalNeighbors(int i, int j) {
        ArrayList<Cell> neighbors = new ArrayList<>();

        //default values if not edge case
        int left = i - 1;
        int right = i + 1;
        int top = j - 1;
        int bottom = j + 1;

        //left edge
        if (i == 0) {
            left = myCellGrid.length - 1;
        }
        //right edge
        if (i == myCellGrid.length - 1) {
            right = 0;
        }
        //top edge
        if (j == 0) {
            top = myCellGrid[0].length - 1;
        }
        //bottom edge
        if (j == myCellGrid[0].length - 1) {
            bottom = 0;
        }
        neighbors.add(myCellGrid[left][top]);
        neighbors.add(myCellGrid[i][top]);
        neighbors.add(myCellGrid[right][top]);
        neighbors.add(myCellGrid[left][j]);
        neighbors.add(myCellGrid[right][j]);
        neighbors.add(myCellGrid[left][bottom]);
        neighbors.add(myCellGrid[i][bottom]);
        neighbors.add(myCellGrid[right][bottom]);
        return neighbors;
    }

    //public Cell getCellAt(int row, int col){return myCellGrid[row][col];}
    public int getCellState(int row, int col){ return myCellGrid[row][col].getMyCurrentState(); }
    public Cell[][] getCellGrid(){ return myCellGrid; }
    public int getMyRows(){return myRows;}
    public int getMyCols(){return myCols;}
    public void setGrid(Cell[][] newGrid){myCellGrid = newGrid;}
}
