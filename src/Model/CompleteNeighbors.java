package Model;

import java.util.ArrayList;
import java.util.List;

public class CompleteNeighbors extends Neighbors {

    public CompleteNeighbors(int x, int y, Cell[][] grid, String shape, String edgeType) {
        super(x, y, grid, shape, edgeType);
    }

    @Override
    public List<Cell> squareNeighbors(Cell[][] cellGrid, String edgeType) {
        List<Cell> neighbors = new ArrayList<>();

        for (int i = myX - 1; i < myX + 2; i++) {
            for (int j = myY - 1; j < myY + 2; j++) {
                if (i != myX || j != myY) {
                    Cell temp = edgeCheck(cellGrid, edgeType, i, j);
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
    public List<Cell> triNeighbors(Cell[][] cellGrid, String edgeType){
        if(upsideDown()){//upside down triangle - 5,4,3
            return upsideDownNeighbors(cellGrid, edgeType);
        }
        else{
            return rightSideUpNeighbors(cellGrid, edgeType);
        }
    }

    public List<Cell> upsideDownNeighbors(Cell[][] cellGrid, String edgeType){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-1; i<myX+2; i++){
            for(int j=myY-2; j<myY+3; j++){
                if((i!=myX || j!=myY) && (i!=myX+1 || (j!=myY-2 && j!=myY+2))){
                    Cell temp = edgeCheck(cellGrid, edgeType, i, j);
                    if(temp!=null){
                        neighbors.add(temp);
                    }
                }
            }
        }

        return neighbors;
    }

    public List<Cell> rightSideUpNeighbors(Cell[][] cellGrid, String edgeType){
        List<Cell> neighbors = new ArrayList<>();

        for(int i=myX-1; i<myX+2; i++){
            for(int j=myY-2; j<myY+3; j++){
                if((i!=myX || j!=myY) && (i!=myX-1 ||(j!=myY-2 && j!=myY+2))){
                    Cell temp = edgeCheck(cellGrid, edgeType, i, j);
                    if(temp!=null){
                        neighbors.add(temp);
                    }
                }
            }
        }
        return neighbors;
    }
}
