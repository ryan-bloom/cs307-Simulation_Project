package Cells;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeCellTest {
    private Cell c0;
    private List<Cell> neighbors;

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
    void updateCellDeadToAlive() {
        c0 = new GameOfLifeCell(1, 1, 0);
        List<Cell> neighs = neighbors.subList(0,4); //3 live, 1 dead
        c0.updateCell(neighs);

        var expected = 1;
        var actual = c0.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void updateCellDiesOverpopulation(){
        c0 = new GameOfLifeCell(1,1, 1);
        c0.updateCell(neighbors);//Too many live neighbors (5)

        var expected = 0;
        var actual = c0.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void updateCellDiesUnderpopulation(){
        c0 = new GameOfLifeCell(1, 1, 1);
        List<Cell> neighs = neighbors.subList(3,8); // 4 dead; 1 live
        c0.updateCell(neighs);

        var expected = 0;
        var actual = c0.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void updateCellSurvives(){
        c0 = new GameOfLifeCell(1, 1, 1);
        List<Cell> neighs = neighbors.subList(1,5); // 2 live; 2 dead
        c0.updateCell(neighs);

        var expected = 1;
        var actual = c0.myNextState;
        assertEquals(expected, actual);
    }
}