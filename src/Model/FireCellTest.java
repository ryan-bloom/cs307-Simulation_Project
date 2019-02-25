package Model;

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

    @BeforeEach
    void setUp() {
        emptyCell = new FireCell(1, 1, 0, 1, 1);
        treeCell = new FireCell(1, 1, 1, 1, 1);
        burningCell = new FireCell(1, 1, 2, 1, 1);
        neighbors = new ArrayList<>();
    }

    @Test
    void updateCell() {
    }

    @Test
    void updateColor() {
    }
}