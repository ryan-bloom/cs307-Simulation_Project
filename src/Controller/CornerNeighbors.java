package Controller;

import Model.Cell;

import java.util.ArrayList;
import java.util.List;

public class CornerNeighbors extends Neighbors {
    private final static int LOWER_BOUND_SQUARE = 1;
    private final static int UPPER_BOUND_SQUARE = 2;

    private final static int LOWER_BOUND_TRI = 1;
    private final static int MID_BOUND_TRI = 2;
    private final static int UPPER_BOUND_TRI = 3;

    /**
     * Constructor takes in CellShape and EdgeType
     * Finds neighbors if CornerNeighborhood type is selected by user (Non-cardinal)
     * @param x
     * @param y
     * @param grid
     * @param shape
     * @param edgeType
     */
    public CornerNeighbors(int x, int y, Grid grid, CellShape shape, EdgeType edgeType){
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
    public List<Cell> squareNeighbors(Grid cellGrid, int myX, int myY){
        List<Cell> neighbors = new ArrayList<>();

        for (int i = myX - LOWER_BOUND_SQUARE; i < myX + UPPER_BOUND_SQUARE; i++) {
            for (int j = myY - LOWER_BOUND_SQUARE; j < myY + UPPER_BOUND_SQUARE; j++) {
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

    /**
     * Loops through rows and cols and determines cells to be included in corner neighborhood
     * @param cellGrid
     * @param myX
     * @param myY
     * @return array of cells that are this cell's neighbors
     */
    @Override
    public List<Cell> upsideDownNeighbors(Grid cellGrid, int myX, int myY){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-LOWER_BOUND_TRI; i<myX+MID_BOUND_TRI; i++){
            for(int j=myY-MID_BOUND_TRI; j<myY+UPPER_BOUND_TRI; j++){
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
    @Override
    public List<Cell> rightSideUpNeighbors(Grid cellGrid, int myX, int myY){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-LOWER_BOUND_TRI; i<myX+MID_BOUND_TRI; i++){
            for(int j=myY-MID_BOUND_TRI; j<myY+UPPER_BOUND_TRI; j++){
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
