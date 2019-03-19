package Model;

import Controller.CellShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FireCellTest {
    private Cell emptyCell;
    private Cell treeCell;
    private Cell burningCell;
    private List<Cell> neighbors;
    private Cell[][] cellGrid;
    private CellShape shape = CellShape.SQUARE;

    @BeforeEach
    void setUp() {
        emptyCell = new FireCell(1, 1, 0, 3);
        treeCell = new FireCell(1, 1, 1, 3);
        burningCell = new FireCell(1, 1, 2, 3);
        neighbors = new ArrayList<>();
        cellGrid = new Cell[5][5];
    }

    @Test
    void treeToBurning() {
        for(int i=0; i<2; i++){
            neighbors.add(treeCell);
        }
        neighbors.add(burningCell);
        treeCell.updateCell(neighbors, cellGrid, shape);

        var expected = 2;
        var actual = treeCell.getMyNextState();
        assertEquals(expected,actual);

    }

    @Test
    void burningToEmpty() {
        for(int i=0; i<4; i++){
            neighbors.add(treeCell);
        }
        neighbors.add(burningCell);
        neighbors.add(emptyCell);
        burningCell.updateCell(neighbors, cellGrid, shape);

        var expected = 0;
        var actual = burningCell.getMyNextState();
        assertEquals(expected, actual);
    }

    @Test
    void emptyToEmpty(){
        for(int i=0; i<3; i++){
            neighbors.add(treeCell);
            neighbors.add(burningCell);
            neighbors.add(emptyCell);
        }
        emptyCell.updateCell(neighbors, cellGrid, shape);

        var expected = 0;
        var actual = emptyCell.getMyNextState();
        assertEquals(expected, actual);
    }
}