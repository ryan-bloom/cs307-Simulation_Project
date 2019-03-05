package Model;

import java.util.ArrayList;
import java.util.List;

public class CardinalNeighbors extends Neighbors {
    public CardinalNeighbors(int x, int y, Cell[][] grid, String shape, int edgeType){
        super(x, y, grid, shape, edgeType);
    }

/*    @Override
    public List<Cell> findNeighbors(Cell[][] cellGrid, String shape, int edges) {
        List<Cell> neighbors = new ArrayList<>();
        if(shape.toUpperCase().equals("SQUARE")){
            return squareNeighbors(cellGrid, edges);
            //return neighbors;
        }
        else if(shape.toUpperCase().equals("HEXAGON")){
            return hexNeighbors(cellGrid, edges);
        }
        return neighbors;
    }*/

    @Override
    public List<Cell> squareNeighbors(Cell[][] cellGrid, int edgeType){
        List<Cell> neighbors = new ArrayList<>();
        for (int i = myX - 1; i < myX + 2; i++) {
            for (int j = myY - 1; j < myY + 2; j++) {
                if ((i != myX || j != myY) && (i==myX || j==myY)) {
                    Cell temp = edgeCheck(cellGrid, edgeType, i, j);
                    if (temp != null) {
                        neighbors.add(temp);
                    }
                }
            }
        }
        return neighbors;
    }

/*    public List<Cell> hexNeighbors(Cell[][] cellGrid, int edgeType){
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
    }*/
}
