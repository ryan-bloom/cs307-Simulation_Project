package Model;

import java.util.List;

public abstract class Neighbors {
    protected List<Cell> myNeighbors;
    protected int myX;
    protected int myY;

    public Neighbors(int x, int y, Cell[][] myGrid, String cellShape, int edges){
        myX = x;
        myY = y;
        myNeighbors = findNeighbors(myGrid, cellShape, edges);
    }

    public abstract List<Cell> findNeighbors(Cell[][] cellGrid, String shape, int edges);

    public List<Cell> getMyNeighbors(){return myNeighbors;}



    public int toroidal(int curr, int max){
        if(curr >= max){ return 0; }
        else if(curr < 0){ return max - 1; }
        else{ return curr; }
    }

    public boolean finite(int loc, int max){
        return(loc>=0 && loc<max);
    }

    public boolean goodHex(int r, int c){
        return((r!=myX+1) || (c != myY-1 && c!=myY+1));
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
