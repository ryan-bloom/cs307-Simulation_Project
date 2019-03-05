package Model;

import java.util.ArrayList;
import java.util.List;

public class CornerNeighbors extends Neighbors {
    public CornerNeighbors(int x, int y, Cell[][] grid, String shape, int edgeType){
        super(x, y, grid, shape, edgeType);
    }

    @Override
    public List<Cell> squareNeighbors(Cell[][] cellGrid, int edges){
        List<Cell> neighbors = new ArrayList<>();

        for (int i = myX - 1; i < myX + 2; i++) {
            for (int j = myY - 1; j < myY + 2; j++) {
                if (i != myX && j != myY) {
                    Cell temp = edgeCheck(cellGrid, edges, i, j);
                    if (temp != null) {
                        neighbors.add(temp);
                    }
                }
            }
        }
        return neighbors;
    }

    @Override
    public List<Cell> triNeighbors(Cell[][] cellGrid, int edges) {
        if(doubleEven() || doubleOdd()){//upside down triangle - 5,4,3
            return upsideDownNeighbors(cellGrid, edges);
        }
        else{
            return rightSideUpNeighbors(cellGrid, edges);
        }
    }

    public List<Cell> upsideDownNeighbors(Cell[][] cellGrid, int edges){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-1; i<myX+2; i++){
            for(int j=myY-2; j<myY+3; j++){
                if((i!=myX || j!=myY) && (i!=myX-1 || j!=myY) && (i!=myX || (j!=myY-1 && j!=myY+1))){
                    Cell temp = edgeCheck(cellGrid, edges, i, j);
                    if(temp!=null){
                        neighbors.add(temp);
                    }
                }
            }
        }


        return neighbors;
    }

    public List<Cell> rightSideUpNeighbors(Cell[][] cellGrid, int edges){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-1; i<myX+2; i++){
            for(int j=myY-2; j<myY+3; j++){
                if((i!=myX || j!=myY) && (i!=myX || (j!=myY-1 && j!=myY+1)) && (i!=myX+1 || j!=myY)){
                    Cell temp = edgeCheck(cellGrid, edges, i, j);
                    if(temp!=null){
                        neighbors.add(temp);
                    }
                }
            }
        }

        return neighbors;
    }
}
