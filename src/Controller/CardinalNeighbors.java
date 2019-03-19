package Controller;

import Model.Cell;

import java.util.ArrayList;
import java.util.List;

public class CardinalNeighbors extends Neighbors {
    /**
     * Constructor sets Cerdinal Neighborhood (takes in CellShape and EdgeType)
     * @param x
     * @param y
     * @param grid
     * @param shape
     * @param edgeType
     */
    public CardinalNeighbors(int x, int y, Cell[][] grid, CellShape shape, EdgeType edgeType){
        super(x, y, grid, shape, edgeType);
    }

    /**
     * Finds cardinal cell neighbors for square cell shapes (directly up/down, left/right)
     * Uses edgeCheck helper method in abstract class Cells (correct neighbors for desired edgeType)
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
                if ((i != myX || j != myY) && (i==myX || j==myY)) {
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
     * Finds all cardinal neighbors for triangle cells
     * Uses helper methods upsideDown and rightSideUp because triangle orientation impacts neighbor locations
     * @param cellGrid
     * @param x
     * @param y
     * @return array of cells that are this cell's neighbors
     */
    @Override
    public List<Cell> triNeighbors(Cell[][] cellGrid, int x, int y) {
        if(upsideDown()){
            return upsideDownNeighbors(cellGrid, x, y);
        }
        else{
            return rightSideUpNeighbors(cellGrid, x, y);
        }
    }

    /**
     * For upsideDown triangle cells loop through row above and below
     * Loop through columns 2 to the left and 2 to the right for furthest neighbors
     * Uses edgeCheck helper method in abstract cells class
     * @param cellGrid
     * @param myX
     * @param myY
     * @return array of cells that are this cell's neighbors
     */
    private List<Cell> upsideDownNeighbors(Cell[][] cellGrid, int myX, int myY){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-1; i<myX+1; i++){
            for(int j=myY-1; j<myY+2; j++){
                if((i!=myX || j!=myY) && (i!=myX-1 || j==myY)){
                    Cell temp = edgeCheck(cellGrid, i, j);
                    if(temp!=null){ neighbors.add(temp); }
                }
            }
        }

        return neighbors;
    }

    /**
     * For rightSideUp triangle cells loop through row above and below
     * Loop through columns 2 to the left and 2 to the right for furthest neighbors
     * Uses edgeCheck helper method in abstract cells class
     * @param cellGrid
     * @param myX
     * @param myY
     * @return array of cells that are this cell's neighbors
     */
    private List<Cell> rightSideUpNeighbors(Cell[][] cellGrid, int myX, int myY){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX; i<myX+2; i++){
            for(int j=myY-1; j<myY+2; j++){
                if((i!=myX || j!=myY) && (i!=myX+1 || j==myY)){
                    Cell temp = edgeCheck(cellGrid, i, j);
                    if(temp!=null){ neighbors.add(temp); }
                }
            }
        }

        return neighbors;
    }
}
