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
}
