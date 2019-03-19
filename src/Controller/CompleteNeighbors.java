package Controller;

import Model.Cell;

import java.util.ArrayList;
import java.util.List;

public class CompleteNeighbors extends Neighbors {

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

        for (int i = myX - 1; i < myX + 2; i++) {
            for (int j = myY - 1; j < myY + 2; j++) {
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

    //HEX NEIGHBORS SAME FOR COMPLETE CARDINAL AND CORNER THEREFORE METHOD IN ABSTRACT NEIGHBORS CLASS

    /**
     * Use upsideDown and rightSideUp helper methods because triangle orientation impacts neighbors
     * @param cellGrid
     * @param x
     * @param y
     * @return array of cells that are this cell's neighbors
     */
    @Override
    public List<Cell> triNeighbors(Cell[][] cellGrid, int x, int y){
        if(upsideDown()){//upside down triangle - 5,4,3
            return upsideDownNeighbors(cellGrid, x, y);
        }
        else{
            return rightSideUpNeighbors(cellGrid, x, y);
        }
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
    private List<Cell> upsideDownNeighbors(Cell[][] cellGrid, int myX, int myY){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-1; i<myX+2; i++){
            for(int j=myY-2; j<myY+3; j++){
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
    private List<Cell> rightSideUpNeighbors(Cell[][] cellGrid, int myX, int myY){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-1; i<myX+2; i++){
            for(int j=myY-2; j<myY+3; j++){
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
