package Controller;

import Model.Cell;

import java.util.ArrayList;
import java.util.List;

public class CornerNeighbors extends Neighbors {
    public CornerNeighbors(int x, int y, Cell[][] grid, CellShape shape, EdgeType edgeType){
        super(x, y, grid, shape, edgeType);
    }

    @Override
    public List<Cell> squareNeighbors(Cell[][] cellGrid){
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

    @Override
    public List<Cell> triNeighbors(Cell[][] cellGrid) {
        if(upsideDown()){//upside down triangle - 5,4,3
            return upsideDownNeighbors(cellGrid);
        }
        else{
            return rightSideUpNeighbors(cellGrid);
        }
    }

    public List<Cell> upsideDownNeighbors(Cell[][] cellGrid){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-1; i<myX+2; i++){
            for(int j=myY-2; j<myY+3; j++){
                if(upsideDownHelper(i, j)){
                    Cell temp = edgeCheck(cellGrid, i, j);
                    if(temp!=null){
                        neighbors.add(temp);
                    }
                }
            }
        }

        return neighbors;
    }

    public List<Cell> rightSideUpNeighbors(Cell[][] cellGrid){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-1; i<myX+2; i++){
            for(int j=myY-2; j<myY+3; j++){
                if(righSideUpHelper(i, j)){
                    Cell temp = edgeCheck(cellGrid, i, j);
                    if(temp!=null){
                        neighbors.add(temp);
                    }
                }
            }
        }

        return neighbors;
    }

    public boolean upsideDownHelper(int i, int j){
        //Check not this, not cardinal above, not cardinal left/right, not below too left/too right
        return ((i!=myX || j!=myY) && (i!=myX-1 || j!=myY) && (i!=myX || (j!=myY-1 && j!=myY+1)) && (i!=myX+1 || (j!=myY-2 && j!=myY+2)));
    }

    public boolean righSideUpHelper(int i, int j){
        return ((i!=myX || j!=myY) && (i!=myX || (j!=myY-1 && j!=myY+1)) && (i!=myX+1 || j!=myY) && (i!=myX-1 || (j!=myY-2 && j!=myY+2)));
    }
}
