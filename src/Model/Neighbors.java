package Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Neighbors {
    protected List<Cell> myNeighbors;
    protected int myX;
    protected int myY;
    protected int myEdges;
    protected String myShape;

    public Neighbors(int x, int y, Cell[][] myGrid, String cellShape, int edges){
        myX = x;
        myY = y;
        myEdges = edges;
        myShape = cellShape;
        myNeighbors = findNeighbors(myGrid);
    }

    public List<Cell> findNeighbors(Cell[][] cellGrid){
        if (myShape.toUpperCase().equals("SQUARE")) {
            return squareNeighbors(cellGrid, myEdges);
        }
        else if (myShape.toUpperCase().equals("HEXAGON")) {
            return hexNeighbors(cellGrid, myEdges);
        }
        else{
            return triNeighbors(cellGrid, myEdges);
        }
    }

    public abstract List<Cell> squareNeighbors(Cell[][] cellGrid, int edges);
    public abstract List<Cell> triNeighbors(Cell[][] cellGrid, int edges);

    //Same for all neighbor types (Complete, Cardinal, Corner)
    public List<Cell> hexNeighbors(Cell[][] cellGrid, int edges){
        List<Cell> neighbors = new ArrayList<>();

        //Even r-horizontal layout (shoves even rows right)
        for (int i = myX - 1; i < myX + 2; i++) {
            for (int j = myY - 1; j < myY + 2; j++) {
                if ((i != myX || j != myY) && goodHex(i, j)) {
                    Cell temp = edgeCheck(cellGrid, edges, i, j);
                    if (temp != null) {
                        neighbors.add(temp);
                    }
                }
            }
        }
        return neighbors;
    }

    public List<Cell> getMyNeighbors(){return myNeighbors;}

    public int toroidal(int curr, int max){
        if(curr >= max){ return 0; }
        else if(curr < 0){ return max - 1; }
        else{ return curr; }
    }

    public boolean finite(int loc, int max){
        return(loc>=0 && loc<max);
    }

    //Used for hex shaped cells
    public boolean goodHex(int r, int c){
        return((r!=myX+1) || (c != myY-1 && c!=myY+1));
    }

    //Used for triangle shaped cells
    public boolean doubleEven(){
        return(myX%2 == 0 && myY%2 == 0);
    }
    public boolean doubleOdd(){
        return(myX%2 != 0 && myY%2 != 0);
    }

    public Cell edgeCheck(Cell[][] cellGrid, int edges, int x, int y){
        int tempX;
        int tempY;

        //toroidal
        if(edges == 0){
            tempX = toroidal(x, cellGrid.length);
            tempY = toroidal(y, cellGrid[0].length);
            return cellGrid[tempX][tempY];
        }
        //finite
        else if(edges == 1 && finite(x, cellGrid.length) && finite(y, cellGrid[0].length)){
            return cellGrid[x][y];
        }
        return null;
    }
}
