package Model;

import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CompleteNeighbors extends Neighbors {

    public CompleteNeighbors(int x, int y, Cell[][] grid, String shape, int edgeType){
        super(x, y, grid, shape, edgeType);
    }

    @Override
    public List<Cell> findNeighbors(Cell[][] cellGrid, String shape, int edgeType) {
        List<Cell> neighs = new ArrayList<>();
        if(shape.toUpperCase().equals("SQUARE")){
            neighs = squareNeighbors(cellGrid, edgeType);
            return neighs;
        }
        if(shape.toUpperCase().equals("HEXAGON")){
            neighs = hexNeighbors(cellGrid, edgeType);
            return neighs;
        }
        //neighs = squareNeighbors(cellGrid, edgeType);
        return neighs;
    }

    public List<Cell> squareNeighbors(Cell[][] cellGrid, int edges){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-1; i<myX+2; i++){
            for(int j=myY-1; j<myY+2; j++){
                if(i != myX || j!=myY){
                    Cell temp = edgeCheck(cellGrid, edges, i, j);
                    if(temp != null){
                        neighbors.add(temp);
                    }
                }
            }
        }
        return neighbors;
    }

    public List<Cell> hexNeighbors(Cell[][] cellGrid, int edges){
        List<Cell> neighbors = new ArrayList<>();

        //Even r-horizontal layout (shoves even rows right)
        for(int i=myX-1; i<myX+2; i++){
            for(int j=myY-1; j<myY+2; j++){
                if((i!=myX || j!= myY) && goodHex(i,j)){
                    Cell temp = edgeCheck(cellGrid, edges, i, j);
                    if(temp != null){
                        neighbors.add(temp);
                    }
                }
            }
        }
        return neighbors;
    }

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
