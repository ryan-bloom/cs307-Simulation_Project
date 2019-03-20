package Controller;

import Model.Cell;

import java.util.ArrayList;
import java.util.List;

public abstract class Neighbors {
    private List<Cell> myNeighbors;
    private int myX;
    private int myY;
    private EdgeType myEdgeType;
    private CellShape myCellShape;

    /**
     * Constructor needs cellShape and edgeType as well as cellGrid to loop through and find neighbors
     * @param x
     * @param y
     * @param myGrid
     * @param cellShape
     * @param edgeType
     */
    public Neighbors(int x, int y, Cell[][] myGrid, CellShape cellShape, EdgeType edgeType){
        myX = x;
        myY = y;
        myEdgeType = edgeType;
        myCellShape = cellShape;
        myNeighbors = findNeighbors(myGrid);
    }

    /**
     * calls correct helper method to find right neighbors based on CellShape
     * @param cellGrid
     * @return
     */
    List<Cell> findNeighbors(Cell[][] cellGrid){
        if (myCellShape == CellShape.SQUARE) {
            return squareNeighbors(cellGrid, myX, myY);
        }
        else if (myCellShape == CellShape.HEXAGON) {
            return hexNeighbors(cellGrid);
        }
        else{ //TRIANGLE
            return triNeighbors(cellGrid, myX, myY);
        }
    }

    /**
     * Abstract squareNeighbors and triNeighbors in each of 3 concrete subclasses
     * Loops through cellGrid and fills neighbors array accordingly
     * @param cellGrid
     * @param x
     * @param y
     * @return list of cell neighbors for this cell
     */
    public abstract List<Cell> squareNeighbors(Cell[][] cellGrid, int x, int y);
    //public abstract List<Cell> triNeighbors(Cell[][] cellGrid, int x, int y);

    public List<Cell> triNeighbors(Cell[][] cellGrid, int x, int y) {
        if(upsideDown()){
            return upsideDownNeighbors(cellGrid, x, y);
        }
        else{
            return rightSideUpNeighbors(cellGrid, x, y);
        }
    }

    public abstract List<Cell> upsideDownNeighbors(Cell[][] cellGrid, int x, int y);
    public abstract List<Cell> rightSideUpNeighbors(Cell[][] cellGrid, int x, int y);
    //Same for all neighbor types (Complete, Cardinal, Corner)

    /**
     * hexNeighbors in abstract class not in concrete subclasses because same for all 3 types of neighborshoods
     * Complete, Cardinal, Corner all have same hex neighbors (still calls edgeCheck helper method)
     * @param cellGrid
     * @return list of neighbors for this cell
     */
    private List<Cell> hexNeighbors(Cell[][] cellGrid){
        List<Cell> neighbors = new ArrayList<>();

        //Even r-horizontal layout (shoves even rows right)
        for (int i = myX - 1; i < myX + 2; i++) {
            for (int j = myY - 1; j < myY + 2; j++) {
                if ((i != myX || j != myY) && goodHex(i, j)) {
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
     * Getter returns list of neighbors
     * @return
     */
    List<Cell> getMyNeighbors(){
        List<Cell> copy = myNeighbors;
        return copy;
    }


    //Edge check helper method (if edgeType is toroidal)
    //Also used for semiToroidal edgeTypes in edgeCheck method
    private int toroidal(int curr, int max){
        if(curr >= max){
            return 0;
        }
        else if(curr < 0){
            return max - 1;
        }
        else{
            return curr;
        }
    }
    //Edge check helper method if edgeType is finite (return true if within span of grid)
    private boolean finite(int loc, int max){
        return(loc>=0 && loc<max);
    }

    //Used for hex shaped cells (eliminate neighbors included in the loop but not actually touching current cell based on even row shift)
    private boolean goodHex(int r, int c){
        if(myX%2 == 0){//Even rows shifted right
            return((c!=myY-1) || (r != myX-1 && r!=myX+1));
        }
        else{//Odd rows shifted left
            return((c!=myY+1) || (r != myX-1 && r!=myX+1));
        }
    }

    //Used for triangle shaped cells (determine if upsideDown or rightSideUp based on indices)
    //Must: 0,0 is upside down
    private boolean upsideDown(){
        return ((myX%2==0 && myY%2 == 0) || (myX%2 != 0 && myY%2 != 0));
    }

    /**
     * Called by each shapeNeighbors method on each cell checking edge cases
     * 3 different posibilities (toroidal, finite, semitoroidal -- use helper methods above)
     * @param cellGrid
     * @param x
     * @param y
     * @return
     */
    Cell edgeCheck(Cell[][] cellGrid, int x, int y){
        int tempX;
        int tempY;

        //toroidal -- loop to other side of grid always
        if(myEdgeType == EdgeType.TOROIDAL){
            tempX = toroidal(x, cellGrid.length);
            tempY = toroidal(y, cellGrid[0].length);
            return cellGrid[tempX][tempY];
        }

        //finite -- never loop to other side of grid
        else if(myEdgeType == EdgeType.FINITE && finite(x, cellGrid.length) && finite(y, cellGrid[0].length)){
            return cellGrid[x][y];
        }
        //Semi toroidal -- corners don't overflow, only direct cardinal overflow
        //Yes left right and top bottom overflow
        else if(myEdgeType == EdgeType.SEMITOROIDAL){
            tempX = toroidal(x, cellGrid.length);
            tempY = toroidal(y, cellGrid[0].length);
            //Corner flip -- not allowed (one temp must be same as original - no change)
            if(tempX == x || tempY == y){
                return cellGrid[tempX][tempY];
            }
        }
        return null;
    }
}
