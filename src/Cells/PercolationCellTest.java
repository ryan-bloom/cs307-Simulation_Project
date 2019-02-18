package Cells;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PercolationCellTest {
    Cell c0;
    List<Cell> neighbors;

    @BeforeEach
    void setUp() {
        neighbors = new ArrayList<>();
        Cell temp;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(i==0 || j==2){ //live cells
                    temp = new GameOfLifeCell(i, j, 1);
                }
                else{
                    temp = new GameOfLifeCell(i, j, 0);
                }
                neighbors.add(temp);
            }
        }
    }

    @Test
    void updateCell() {
    }
}