package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PercolationCellTest {
    Cell blockedCell;
    Cell openCell;
    Cell percolatingCell;
    List<Cell> neighbors;
    Cell[][] cellGrid;

    @BeforeEach
    void setUp() {
        blockedCell = new PercolationCell(1,1, 0);
        openCell = new PercolationCell(1, 1, 1);
        percolatingCell = new PercolationCell(1, 1, 2);
        neighbors = new ArrayList<>();
        cellGrid = new Cell[5][5];

        Cell temp;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(i==0){ //blocked cells
                    temp = new PercolationCell(i, j, 0);
                }
                else if(i==1){ //open cells
                    temp = new PercolationCell(i, j, 1);
                }
                else{ //percolating cells
                    temp = new PercolationCell(i, j, 2);
                }
                neighbors.add(temp);
            }
        }
    }

    @Test
    void updateOpenCellToPercolating() {
        List<Cell> neighs = neighbors.subList(4,7); //2 open, 1 percolating
        openCell.updateCell(neighs, cellGrid);
        var expected = 2;
        var actual = openCell.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void updateOpenCellToOpen(){
        List<Cell> neighs = neighbors.subList(0,6); //3 blocked, 3 open cells
        openCell.updateCell(neighs, cellGrid);
        var expected = 1;
        var actual = openCell.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void updateBlockedCell(){
        blockedCell.updateCell(neighbors, cellGrid);
        var expected = 0;
        var actual = blockedCell.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void updatePercolatingCell(){
        percolatingCell.updateCell(neighbors, cellGrid);
        var expected = 2;
        var actual = percolatingCell.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void percolatingEquals(){
        Cell temp = new PercolationCell(1, 1, 0);
        var actual = temp.equals(blockedCell);
        assertTrue(actual);
    }

    @Test
    void percolatingDoesNotEqual(){
        var actual = openCell.equals(percolatingCell);
        assertFalse(actual);
    }
}