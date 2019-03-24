package Model;

import Controller.CellShape;
import Controller.Data;
import Controller.Grid;
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
    //Cell[][] cellGrid;
    Grid cellGrid;
    CellShape shape = CellShape.SQUARE;

    @BeforeEach
    void setUp() {
        blockedCell = new PercolationCell(1,1, 0, 3);
        openCell = new PercolationCell(1, 1, 1, 3);
        percolatingCell = new PercolationCell(1, 1, 2, 3);
        neighbors = new ArrayList<>();
        Data dat = new Data("Percolation_Config_1.csv");
        cellGrid = new Grid(dat);
        //cellGrid = new Cell[5][5];

        Cell temp;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(i==0){ //blocked cells
                    temp = new PercolationCell(i, j, 0, 3);
                }
                else if(i==1){ //open cells
                    temp = new PercolationCell(i, j, 1, 3);
                }
                else{ //percolating cells
                    temp = new PercolationCell(i, j, 2, 3);
                }
                neighbors.add(temp);
            }
        }
    }

    @Test
    void updateOpenCellToPercolating() {
        List<Cell> neighs = neighbors.subList(4,7); //2 open, 1 percolating
        openCell.updateCell(neighs, cellGrid, shape);
        var expected = 2;
        var actual = openCell.getMyNextState();
        assertEquals(expected, actual);
    }

    @Test
    void updateOpenCellToOpen(){
        List<Cell> neighs = neighbors.subList(0,6); //3 blocked, 3 open cells
        openCell.updateCell(neighs, cellGrid, shape);
        var expected = 1;
        var actual = openCell.getMyNextState();
        assertEquals(expected, actual);
    }

    @Test
    void updateBlockedCell(){
        blockedCell.updateCell(neighbors, cellGrid, shape);
        var expected = 0;
        var actual = blockedCell.getMyNextState();
        assertEquals(expected, actual);
    }

    @Test
    void updatePercolatingCell(){
        percolatingCell.updateCell(neighbors, cellGrid, shape);
        var expected = 2;
        var actual = percolatingCell.getMyNextState();
        assertEquals(expected, actual);
    }

    @Test
    void percolatingEquals(){
        Cell temp = new PercolationCell(1, 1, 0, 3);
        var actual = temp.equals(blockedCell);
        assertTrue(actual);
    }

    @Test
    void percolatingDoesNotEqual(){
        var actual = openCell.equals(percolatingCell);
        assertFalse(actual);
    }
}