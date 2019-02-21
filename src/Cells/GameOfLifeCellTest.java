package Cells;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeCellTest {
    private Cell deadCell;
    private Cell liveCell;
    private List<Cell> neighbors;

    @BeforeEach
    void setUp() {
        deadCell = new GameOfLifeCell(1, 1, 0, 1, 1);
        liveCell = new GameOfLifeCell(1, 1, 1, 1, 1);
        neighbors = new ArrayList<>();
        Cell temp;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(i==0 || j==2){ //live cells
                    temp = new GameOfLifeCell(i, j, 1, 1, 1);
                }
                else{
                    temp = new GameOfLifeCell(i, j, 0, 1, 1);
                }
                neighbors.add(temp);
            }
        }
    }

    @Test
    void updateCellDeadToAlive() {
        List<Cell> neighs = neighbors.subList(0,4); //3 live, 1 dead
        deadCell.updateCell(neighs);

        var expected = 1;
        var actual = deadCell.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void updateCellDiesOverpopulation(){
        liveCell.updateCell(neighbors);//Too many live neighbors (5)

        var expected = 0;
        var actual = liveCell.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void updateCellDiesUnderpopulation(){
        List<Cell> neighs = neighbors.subList(3,8); // 4 dead; 1 live
        liveCell.updateCell(neighs);

        var expected = 0;
        var actual = liveCell.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void updateCellSurvives(){
        List<Cell> neighs = neighbors.subList(1,5); // 2 live; 2 dead
        liveCell.updateCell(neighs);

        var expected = 1;
        var actual = liveCell.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void cellEquals(){
        Cell c1 = new GameOfLifeCell(1, 1, 1, 1, 1);

        var expected = true;
        var actual = liveCell.equals(c1);
        assertEquals(expected, actual);
    }

    @Test
    void cellDoesNotEqual(){
        Cell c1 = new GameOfLifeCell(1, 2, 0, 1, 1);
        var expected = false;
        var actual = liveCell.equals(c1);
        assertEquals(expected, actual);
    }

    @Test
    void redColorCheck(){
        List<Cell> neighs = neighbors.subList(1,5); // 2 live; 2 dead
        liveCell.updateCell(neighs);

        var expected = Color.RED;
        var actual = liveCell.myRectangle.getFill();
        assertEquals(expected, actual);
    }

    @Test
    void whiteColorCheck(){
        List<Cell> neighs = neighbors.subList(3,8); // 4 dead; 1 live
        liveCell.updateCell(neighs);

        var expected = Color.WHITE;
        var actual = liveCell.myRectangle.getFill();
        assertEquals(expected, actual);
    }
}