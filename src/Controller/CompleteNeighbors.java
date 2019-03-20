package Controller;

import Model.Cell;

import java.util.ArrayList;
import java.util.List;

public class CompleteNeighbors extends Neighbors {
    private final static int LOWER_BOUND_SQUARE = 1;
    private final static int UPPER_BOUND_SQUARE = 2;

    private final static int LOWER_BOUND_TRI = 1;
    private final static int MID_BOUND_TRI = 2;
    private final static int UPPER_BOUND_TRI = 3;


    /**
     * Constructor for complete neighbors subclass
     * Gets full set of neighbors (cardinal and corner)
     * @param x
     * @param y
     * @param grid
     * @param shape
     * @param edgeType
     */
    public CompleteNeighbors(int x, int y, Cell[][] grid, CellShape shape, EdgeType edgeType) {
        super(x, y, grid, shape, edgeType);
    }

    /**
     * Get all neighbors for square cells
     * Use edgeCheck helper method in cell abstract class
     * @param cellGrid
     * @param myX
     * @param myY
     * @return array of cells that are this cell's neighbors
     */
    @Override
    public List<Cell> squareNeighbors(Cell[][] cellGrid, int myX, int myY) {
        List<Cell> neighbors = new ArrayList<>();

        for (int i = myX - LOWER_BOUND_SQUARE; i < myX + UPPER_BOUND_SQUARE; i++) {
            for (int j = myY - LOWER_BOUND_SQUARE; j < myY + UPPER_BOUND_SQUARE; j++) {
                if (i != myX || j != myY) {
                    Cell temp = edgeCheck(cellGrid, i, j);
                    if (temp != null) {
                        neighbors.add(temp);
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Finds upsideDown triangle neighbors looping through rows and columns
     * Checks if location should be counted based on r,c indices
     * Uses edgeCheck helper method
     * @param cellGrid
     * @param myX
     * @param myY
     * @return array of cells that are this cell's neighbors
     */
    @Override
    public List<Cell> upsideDownNeighbors(Cell[][] cellGrid, int myX, int myY){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-LOWER_BOUND_TRI; i<myX+MID_BOUND_TRI; i++){
            for(int j=myY-MID_BOUND_TRI; j<myY+UPPER_BOUND_TRI; j++){
                if((i!=myX || j!=myY) && (i!=myX+1 || (j!=myY-2 && j!=myY+2))){
                    Cell temp = edgeCheck(cellGrid, i, j);
                    if(temp!=null){
                        neighbors.add(temp);
                    }
                }
            }
        }

        return neighbors;
    }

    /**
     * Finds right side up triangle neighbors
     * loops through rows and columns checking if location should be included
     * Uses edgeCheck helper method
     * @param cellGrid
     * @param myX
     * @param myY
     * @return array of cells that are this cell's neighbors
     */
    @Override
    public List<Cell> rightSideUpNeighbors(Cell[][] cellGrid, int myX, int myY){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-LOWER_BOUND_TRI; i<myX+MID_BOUND_TRI; i++){
            for(int j=myY-MID_BOUND_TRI; j<myY+UPPER_BOUND_TRI; j++){
                if((i!=myX || j!=myY) && (i!=myX-1 ||(j!=myY-2 && j!=myY+2))){
                    Cell temp = edgeCheck(cellGrid, i, j);
                    if(temp!=null){
                        neighbors.add(temp);
                    }
                }
            }
        }
        return neighbors;
    }
}
