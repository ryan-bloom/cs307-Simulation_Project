package Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Neighbors {
    protected List<Cell> myNeighbors;
    protected int myX;
    protected int myY;
    protected String myEdgeType;
    protected String myShape;

    public Neighbors(int x, int y, Cell[][] myGrid, String cellShape, String edgeType){
        myX = x;
        myY = y;
        myEdgeType = edgeType;
        myShape = cellShape;
        myNeighbors = findNeighbors(myGrid);
    }

    public List<Cell> findNeighbors(Cell[][] cellGrid){
        if (myShape.toUpperCase().equals("SQUARE")) {
            return squareNeighbors(cellGrid, myEdgeType);
        }
        else if (myShape.toUpperCase().equals("HEXAGON")) {
            return hexNeighbors(cellGrid, myEdgeType);
        }
        else{ //TRIANGLE
            return triNeighbors(cellGrid, myEdgeType);
        }
    }

    public abstract List<Cell> squareNeighbors(Cell[][] cellGrid, String edgeType);
    public abstract List<Cell> triNeighbors(Cell[][] cellGrid, String edgeType);
    //Same for all neighbor types (Complete, Cardinal, Corner)
    public List<Cell> hexNeighbors(Cell[][] cellGrid, String edgeType){
        List<Cell> neighbors = new ArrayList<>();

        //Even r-horizontal layout (shoves even rows right)
        for (int i = myX - 1; i < myX + 2; i++) {
            for (int j = myY - 1; j < myY + 2; j++) {
                if ((i != myX || j != myY) && goodHex(i, j)) {
                    Cell temp = edgeCheck(cellGrid, edgeType, i, j);
                    if (temp != null) {
                        neighbors.add(temp);
                    }
                }
            }
        }
        return neighbors;
    }

    public List<Cell> getMyNeighbors(){return myNeighbors;}


    //Edge check helper methods
    public int toroidal(int curr, int max){
        if(curr >= max){ return 0; }
        else if(curr < 0){ return max - 1; }
        else{ return curr; }
    }
    public boolean finite(int loc, int max){
        return(loc>=0 && loc<max);
    }

    //Used for hex shaped cells (eliminate fake neighbors based on even row shift)
    public boolean goodHex(int r, int c){
        return((r!=myX+1) || (c != myY-1 && c!=myY+1));
    }

    //Used for triangle shaped cells (determine if upsideDown or rightSideUp based on indices)
    public boolean upsideDown(){
        return ((myX%2==0 && myY%2 == 0) || (myX%2 != 0 && myY%2 != 0));
    }

    //Called by each shapeNeighbors method on each cell to check for edge cases
    public Cell edgeCheck(Cell[][] cellGrid, String edgeType, int x, int y){
        int tempX;
        int tempY;

        //toroidal
        if(edgeType.toUpperCase().equals("TOROIDAL")){
            tempX = toroidal(x, cellGrid.length);
            tempY = toroidal(y, cellGrid[0].length);
            return cellGrid[tempX][tempY];
        }
        //finite
        else if(edgeType.toUpperCase().equals("FINITE") && finite(x, cellGrid.length) && finite(y, cellGrid[0].length)){
            return cellGrid[x][y];
        }
        return null;
    }
}
