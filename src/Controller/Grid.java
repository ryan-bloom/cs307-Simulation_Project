package Controller;

import Model.*;

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

    public void fillCellGrid(String simType){
        myCellGrid = new Cell[myRows][myCols];
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                myCellGrid[i][j] = simCellPicker(simType, i, j);
            }
        }
    }

    public void updateGridCell(int row, int col, CellShape shape, EdgeType edgeType, NeighborhoodType neighborhoodType){
        Neighbors n = neighborhoodPicker(row, col, shape, edgeType, neighborhoodType);
        myCellGrid = myCellGrid[row][col].updateCell(n.getMyNeighbors(), myCellGrid, shape);
    }


    public Cell simCellPicker(String simType, int x, int y){
        if(simType.toUpperCase().equals("GAMEOFLIFE")){
            return new GameOfLifeCell(x, y, myData.getStates()[x][y]);
        }
        else if(simType.toUpperCase().equals("PERCOLATION")){
            return new PercolationCell(x, y, myData.getStates()[x][y]);
        }
        else if(simType.toUpperCase().equals("FIRE")){
            return new FireCell(x, y, myData.getStates()[x][y]);
        }
        else if(simType.toUpperCase().equals("RPS")){
            return new RPSCell(x, y, myData.getStates()[x][y]);
        }
        else if(simType.toUpperCase().equals("SEGREGATION")){
            return new SegregationCell(x, y, myData.getStates()[x][y]);
        }
        else{
            return new PredatorPreyCell(x, y, myData.getStates()[x][y]);
        }
    }


    public Neighbors neighborhoodPicker(int x, int y, CellShape shape, EdgeType edgeType, NeighborhoodType neighborhoodType){
        if(neighborhoodType == NeighborhoodType.COMPLETE){
            return new CompleteNeighbors(x, y, myCellGrid, shape, edgeType);
        }
        else if(neighborhoodType == NeighborhoodType.CARDINAL){
            return new CardinalNeighbors(x, y, myCellGrid, shape, edgeType);
        }
        else{ //Corner neighborhoodType
            return new CornerNeighbors(x, y, myCellGrid, shape, edgeType);
        }
    }

    public void resetCell(int row, int col){
        myCellGrid[row][col].resetState();
    }
    public int getCellState(int row, int col){ return myCellGrid[row][col].getMyCurrentState(); }
    public Cell[][] getCellGrid(){ return myCellGrid; }
    public int getMyRows(){return myRows;}
    public int getMyCols(){return myCols;}
}
