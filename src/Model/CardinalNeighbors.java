package Model;

import java.util.List;

public class CardinalNeighbors extends Neighbors {
    public CardinalNeighbors(int x, int y, Cell[][] grid, String shape, int edgeType){
        super(x, y, grid, shape, edgeType);
    }

    @Override
    public List<Cell> findNeighbors(Cell[][] cellGrid, String shape, int edges) {
        return null;
    }
}
