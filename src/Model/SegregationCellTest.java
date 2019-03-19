package Model;

import Controller.CellShape;
import Controller.Data;
import Controller.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SegregationCellTest {
    SegregationCell emptyCell;
    SegregationCell type1Cell;
    SegregationCell type2Cell;
    List<Cell> neighbors;
    //SegregationCell[][] cellGrid;
    Grid cellGrid;
    CellShape shape = CellShape.SQUARE;

    @BeforeEach
    void setUp() {
        emptyCell = new SegregationCell(0, 0, 0, 3);
        type1Cell = new SegregationCell(0, 1, 1, 3);
        type2Cell = new SegregationCell(0, 2, 2, 3);
        neighbors = new ArrayList<>();
        //cellGrid = new SegregationCell[5][5];
        Data dat = new Data("Segregation_Config_1.csv");
    }

    void happyType2andEmptysetUp(){
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(i==0 || j==0 || i==4 || j==4){
                    cellGrid[i][j] = new SegregationCell(i, j, 2, 3);
                }
                else if(i==1 && j==1){
                    cellGrid[i][j] = new SegregationCell(i, j, 1, 3);
                }
                else{
                    cellGrid[i][j] = new SegregationCell(i, j, 0, 3);
                }
            }
        }
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(i!=1 || j!= 1){
                    //neighbors array has only type2 cells;
                    //specifically neighbors or cellType1 in location 1,1
                    neighbors.add(cellGrid[i][j]);
                }
            }
        }
    }

    @Test
    void unhappyType1PercentCheck() {
        //No type 1 cells in neighbors
        for(int i=0; i<6; i++){
            if(i%2 != 0){ neighbors.add(type2Cell); }
            else{ neighbors.add(emptyCell); }
        }

        var expected = 0;
        var actual = type1Cell.findPercentageSame(neighbors);
        assertEquals(expected, actual);
    }

    @Test
    void happyType2PercentCheck() {
        happyType2andEmptysetUp();

        var expected = 1.0;
        var actual = type2Cell.findPercentageSame(neighbors);
        assertEquals(expected, actual);
    }

    @Test
    void findEmptyCellsInGrid() {
        happyType2andEmptysetUp();
        //8 empty cells in this setup
        List<Cell> empties = type1Cell.findEmptyCells(cellGrid);

        var expected = 8;
        var actual = empties.size();
        assertEquals(expected, actual);
    }

    @Test
    void randomCellSelector(){
        happyType2andEmptysetUp();
        //8 empty cells in this setup
        List<Cell> empties = type1Cell.findEmptyCells(cellGrid);
        int[] newLoc = type1Cell.randomEmptyLocation(empties);
        List<Integer> possRC = new ArrayList<>();
        for(int i=1; i<4; i++){
            possRC.add(i);
        }

        int newR = newLoc[0];
        int newC = newLoc[1];

        assertTrue(possRC.contains(newR) && possRC.contains(newC));
    }

    @Test
    void randomCellSelector2(){
        happyType2andEmptysetUp();
        //8 empty cells in this setup
        List<Cell> empties = type1Cell.findEmptyCells(cellGrid);
        int[] newLoc = type1Cell.randomEmptyLocation(empties);

        int newR = newLoc[0];
        int newC = newLoc[1];
        //In created cellGrid -- 1,1 is a type1 cell (not empty)
        assertTrue(!(newR == 1 && newC == 1));
    }

    @Test
    void updateUnhappyType1(){
        happyType2andEmptysetUp();
        SegregationCell temp = cellGrid[1][1];

        var expected1 = 1;
        var actual1 = temp.getMyCurrentState();
        assertEquals(expected1, actual1);

        temp.updateCell(neighbors, cellGrid, shape);

        var expected2 = 0;
        var actual2 = cellGrid[1][1].getMyCurrentState();
        assertEquals(expected2, actual2);
    }

    @Test
    void updateHappyType2(){
        happyType2andEmptysetUp();
        SegregationCell temp = cellGrid[0][1];

        var expected1 = 2;
        var actual1 = temp.getMyCurrentState();
        assertEquals(expected1, actual1);

        temp.updateCell(neighbors, cellGrid, shape);

        var expected2 = 2;
        var actual2 = cellGrid[0][1].getMyCurrentState();
        assertEquals(expected2, actual2);

    }
}