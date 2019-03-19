package Controller;

import Model.Cell;

import java.util.ArrayList;
import java.util.List;

public class CornerNeighbors extends Neighbors {
    /**
     * Constructor takes in CellShape and EdgeType
     * Finds neighbors if CornerNeighborhood type is selected by user (Non-cardinal)
     * @param x
     * @param y
     * @param grid
     * @param shape
     * @param edgeType
     */
    public CornerNeighbors(int x, int y, Cell[][] grid, CellShape shape, EdgeType edgeType){
        super(x, y, grid, shape, edgeType);
    }

    /**
     * Finds all corner neighbors for square cells
     * @param cellGrid
     * @param myX
     * @param myY
     * @return array of cells that are this cell's neighbors
     */
    @Override
    public List<Cell> squareNeighbors(Cell[][] cellGrid, int myX, int myY){
        List<Cell> neighbors = new ArrayList<>();

        for (int i = myX - 1; i < myX + 2; i++) {
            for (int j = myY - 1; j < myY + 2; j++) {
                if (i != myX && j != myY) {
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
     * Finds corner neighbors for triangles
     * uses helper methods upsideDown and rightSideUp because neighbor locations vary based on orientation
     * @param cellGrid
     * @param x
     * @param y
     * @return array of cells that are this cell's neighbors
     */
    @Override
    public List<Cell> triNeighbors(Cell[][] cellGrid, int x, int y) {
        if(upsideDown()){//upside down triangle - 5,4,3
            return upsideDownNeighbors(cellGrid, x, y);
        }
        else{
            return rightSideUpNeighbors(cellGrid, x, y);
        }
    }

    /**
     * Loops through rows and cols and determines cells to be included in corner neighborhood
     * @param cellGrid
     * @param myX
     * @param myY
     * @return array of cells that are this cell's neighbors
     */
    private List<Cell> upsideDownNeighbors(Cell[][] cellGrid, int myX, int myY){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-1; i<myX+2; i++){
            for(int j=myY-2; j<myY+3; j++){
                //Use helper method because logic statement is long
                if(upsideDownHelper(i, j, myX, myY)){
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
     * Loops through rows and cols and determines cells to be included in corner neighborhood
     * @param cellGrid
     * @param myX
     * @param myY
     * @return array of cells that are this cell's neighbors
     */
    private List<Cell> rightSideUpNeighbors(Cell[][] cellGrid, int myX, int myY){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-1; i<myX+2; i++){
            for(int j=myY-2; j<myY+3; j++){
                //Use helper method because logic statement is long
                if(rightSideUpHelper(i, j, myX, myY)){
                    Cell temp = edgeCheck(cellGrid, i, j);
                    if(temp!=null){
                        neighbors.add(temp);
                    }
                }
            }
        }

        return neighbors;
    }

    private boolean upsideDownHelper(int i, int j, int myX, int myY){
        //Check not this, not cardinal above, not cardinal left/right, not below too left/too right
        return ((i!=myX || j!=myY) && (i!=myX-1 || j!=myY) && (i!=myX || (j!=myY-1 && j!=myY+1)) && (i!=myX+1 || (j!=myY-2 && j!=myY+2)));
    }

    private boolean rightSideUpHelper(int i, int j, int myX, int myY){
        return ((i!=myX || j!=myY) && (i!=myX || (j!=myY-1 && j!=myY+1)) && (i!=myX+1 || j!=myY) && (i!=myX-1 || (j!=myY-2 && j!=myY+2)));
    }
}
