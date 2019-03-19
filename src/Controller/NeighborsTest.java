package Controller;

import Model.Cell;
import Model.GameOfLifeCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NeighborsTest {
    CornerNeighbors myCornerNeighbors;
    CompleteNeighbors myCompleteNeighbors;
    CardinalNeighbors myCardinalNeighbors;
    Cell[][] myCellGrid;

    @BeforeEach
    void setUpSquares() {
        myCellGrid = new Cell[10][10];
        fillGrid();
    }

    void fillGrid(){
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                myCellGrid[i][j] = new GameOfLifeCell(i, j, 1, 2);
            }
        }
    }

    @Test
    void squareCornerCompleteToroidalNeighbors() {
        myCompleteNeighbors = new CompleteNeighbors(0,0, myCellGrid, CellShape.SQUARE, EdgeType.TOROIDAL);
        List<Cell> neighbors = myCompleteNeighbors.findNeighbors(myCellGrid);

        var expected = 8;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void squareCornerCompleteFiniteNeighbors(){
        myCompleteNeighbors = new CompleteNeighbors(0,0, myCellGrid, CellShape.SQUARE, EdgeType.FINITE);
        List<Cell> neighbors = myCompleteNeighbors.findNeighbors(myCellGrid);

        var expected = 3;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void hexCornerCompleteToroidalNeighbors(){
        myCompleteNeighbors = new CompleteNeighbors(0,0, myCellGrid, CellShape.HEXAGON, EdgeType.TOROIDAL);
        List<Cell> neighbors = myCompleteNeighbors.findNeighbors(myCellGrid);

        var expected = 6;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void hexCornerCompleteFiniteNeighbors(){
        myCompleteNeighbors = new CompleteNeighbors(0,0, myCellGrid, CellShape.HEXAGON, EdgeType.FINITE);
        List<Cell> neighbors = myCompleteNeighbors.findNeighbors(myCellGrid);

        var expected = 3;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void hexEvenRowSideCompleteFiniteNeighbors(){
        myCompleteNeighbors = new CompleteNeighbors(4,0, myCellGrid, CellShape.HEXAGON, EdgeType.FINITE);
        List<Cell> neighbors = myCompleteNeighbors.findNeighbors(myCellGrid);

        var expected = 5;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void hexOddRowSideCompleteFiniteNeighbors(){
        myCompleteNeighbors = new CompleteNeighbors(3,0, myCellGrid, CellShape.HEXAGON, EdgeType.FINITE);
        List<Cell> neighbors = myCompleteNeighbors.findNeighbors(myCellGrid);

        var expected = 3;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void triCornerCompleteToroidalNeighbors(){
        myCompleteNeighbors = new CompleteNeighbors(0,0, myCellGrid, CellShape.TRIANGLE, EdgeType.TOROIDAL);
        List<Cell> neighbors = myCompleteNeighbors.findNeighbors(myCellGrid);

        var expected = 12;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void triCornerUpsideDownCompleteFiniteNeighbors(){
        myCompleteNeighbors = new CompleteNeighbors(0,0, myCellGrid, CellShape.TRIANGLE, EdgeType.FINITE);
        List<Cell> neighbors = myCompleteNeighbors.findNeighbors(myCellGrid);

        var expected = 4;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void triSideRightSideUpCompleteFiniteNeighbors(){
        myCompleteNeighbors = new CompleteNeighbors(1,0, myCellGrid, CellShape.TRIANGLE, EdgeType.FINITE);
        List<Cell> neighbors = myCompleteNeighbors.findNeighbors(myCellGrid);

        var expected = 7;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void squareSideCardinalFiniteNeighbors(){
        myCardinalNeighbors = new CardinalNeighbors(3, 0, myCellGrid, CellShape.SQUARE, EdgeType.FINITE);
        List<Cell> neighbors = myCardinalNeighbors.findNeighbors(myCellGrid);

        var expected = 3;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void squareCornerCardinalFiniteNeighbors(){
        myCardinalNeighbors = new CardinalNeighbors(9, 9, myCellGrid, CellShape.SQUARE, EdgeType.FINITE);
        List<Cell> neighbors = myCardinalNeighbors.findNeighbors(myCellGrid);

        var expected = 2;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void hexOddCornerCardinalFiniteNeighbors(){
        myCardinalNeighbors = new CardinalNeighbors(9, 9, myCellGrid, CellShape.HEXAGON, EdgeType.FINITE);
        List<Cell> neighbors = myCardinalNeighbors.findNeighbors(myCellGrid);

        var expected = 3;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void hexEvenMiddleCardinalFiniteNeighbors(){
        myCardinalNeighbors = new CardinalNeighbors(3, 6, myCellGrid, CellShape.HEXAGON, EdgeType.FINITE);
        List<Cell> neighbors = myCardinalNeighbors.findNeighbors(myCellGrid);

        var expected = 6;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void hexEvenCornerCardinalToroidalNeighbors(){
        myCardinalNeighbors = new CardinalNeighbors(0, 9, myCellGrid, CellShape.HEXAGON, EdgeType.TOROIDAL);
        List<Cell> neighbors = myCardinalNeighbors.findNeighbors(myCellGrid);

        var expected = 6;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void triUpsideDownCornerCardinalToroidalNeighbors(){
        myCardinalNeighbors = new CardinalNeighbors(0, 0, myCellGrid, CellShape.TRIANGLE, EdgeType.TOROIDAL);
        List<Cell> neighbors = myCardinalNeighbors.findNeighbors(myCellGrid);

        var expected = 3;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void triRightSideUpCornerCardinalFiniteNeighbors(){
        myCardinalNeighbors = new CardinalNeighbors(0, 9, myCellGrid, CellShape.TRIANGLE, EdgeType.FINITE);
        List<Cell> neighbors = myCardinalNeighbors.findNeighbors(myCellGrid);

        var expected = 2;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void squareCornerCornerFiniteNeighbors(){
        myCornerNeighbors = new CornerNeighbors(0,0, myCellGrid, CellShape.SQUARE, EdgeType.FINITE);
        List<Cell> neighbors = myCornerNeighbors.findNeighbors(myCellGrid);

        var expected = 1;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void squareSideCornerFiniteNeighbors(){
        myCornerNeighbors = new CornerNeighbors(5,0, myCellGrid, CellShape.SQUARE, EdgeType.FINITE);
        List<Cell> neighbors = myCornerNeighbors.findNeighbors(myCellGrid);

        var expected = 2;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void squareCornerCornerToroidalNeighbors(){
        myCornerNeighbors = new CornerNeighbors(0,0, myCellGrid, CellShape.SQUARE, EdgeType.TOROIDAL);
        List<Cell> neighbors = myCornerNeighbors.findNeighbors(myCellGrid);

        var expected = 4;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void hexEvenCornerCornerToroidalNeighbors(){
        myCornerNeighbors = new CornerNeighbors(0,0, myCellGrid, CellShape.HEXAGON, EdgeType.TOROIDAL);
        List<Cell> neighbors = myCornerNeighbors.findNeighbors(myCellGrid);

        var expected = 6;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void hexOddSideCornerFiniteNeighbors(){
        myCornerNeighbors = new CornerNeighbors(5,0, myCellGrid, CellShape.HEXAGON, EdgeType.FINITE);
        List<Cell> neighbors = myCornerNeighbors.findNeighbors(myCellGrid);

        var expected = 3;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void triUpsideDownCornerToroidalCornerNeighbors(){
        myCornerNeighbors = new CornerNeighbors(0,0, myCellGrid, CellShape.TRIANGLE, EdgeType.TOROIDAL);
        List<Cell> neighbors = myCornerNeighbors.findNeighbors(myCellGrid);

        var expected = 9;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void triRightSideUpSideFiniteCornerNeighbors(){
        myCornerNeighbors = new CornerNeighbors(3,0, myCellGrid, CellShape.TRIANGLE, EdgeType.FINITE);
        List<Cell> neighbors = myCornerNeighbors.findNeighbors(myCellGrid);

        var expected = 5;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void triUpsideDownMiddleFiniteCornerNeighbors(){
        myCornerNeighbors = new CornerNeighbors(3,5, myCellGrid, CellShape.TRIANGLE, EdgeType.FINITE);
        List<Cell> neighbors = myCornerNeighbors.findNeighbors(myCellGrid);

        var expected = 9;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void squareCornerSemiToroidalCompleteNeighbors(){
        myCompleteNeighbors = new CompleteNeighbors(0,0, myCellGrid, CellShape.SQUARE, EdgeType.SEMITOROIDAL);
        List<Cell> neighbors = myCompleteNeighbors.findNeighbors(myCellGrid);

        var expected = 7;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void triUpsideDownCornerSemiToroidalCornerNeighbors(){
        myCornerNeighbors = new CornerNeighbors(0,0, myCellGrid, CellShape.TRIANGLE, EdgeType.SEMITOROIDAL);
        List<Cell> neighbors = myCornerNeighbors.findNeighbors(myCellGrid);

        var expected = 7;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void triRightSideUpTopCornerSemiToroidalCompleteNeighbors(){
        myCompleteNeighbors = new CompleteNeighbors(0,9, myCellGrid, CellShape.TRIANGLE, EdgeType.SEMITOROIDAL);
        List<Cell> neighbors = myCompleteNeighbors.findNeighbors(myCellGrid);

        var expected = 11;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void triRightSideUpBottomCornerSemiToroidalCompleteNeighbors(){
        myCompleteNeighbors = new CompleteNeighbors(9,0, myCellGrid, CellShape.TRIANGLE, EdgeType.SEMITOROIDAL);
        List<Cell> neighbors = myCompleteNeighbors.findNeighbors(myCellGrid);

        var expected = 10;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void hexEvenCornerSemiToroidalCompleteNeighbors(){
        myCompleteNeighbors = new CompleteNeighbors(0,9, myCellGrid, CellShape.HEXAGON, EdgeType.SEMITOROIDAL);
        List<Cell> neighbors = myCompleteNeighbors.findNeighbors(myCellGrid);

        var expected = 5;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

    @Test
    void hexOddCornerSemiToroidalCornerNeighbors(){
        myCornerNeighbors = new CornerNeighbors(9,9, myCellGrid, CellShape.HEXAGON, EdgeType.SEMITOROIDAL);
        List<Cell> neighbors = myCornerNeighbors.findNeighbors(myCellGrid);

        var expected = 6;
        var actual = neighbors.size();
        assertEquals(expected, actual);
    }

}