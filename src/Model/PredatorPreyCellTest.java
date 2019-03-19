package Model;

import Controller.CellShape;
import Controller.Data;
import Controller.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PredatorPreyCellTest {
    PredatorPreyCell emptyCell;
    PredatorPreyCell fishCell;
    PredatorPreyCell sharkCell;
    List<Cell> neighbors;
    //PredatorPreyCell[][] cellGrid;
    Grid cellGrid;
    CellShape shape  = CellShape.SQUARE;

    @BeforeEach
    void setUp() {
        emptyCell = new PredatorPreyCell(0, 0, 0, 3);
        fishCell = new PredatorPreyCell(0, 1, 1, 3);
        sharkCell = new PredatorPreyCell(0, 2, 2, 3);
        neighbors = new ArrayList<>();
        //cellGrid = new PredatorPreyCell[5][5];
        Data dat = new Data("PredatorPrey_Config_2.csv");
        cellGrid = new Grid(dat);
    }

    void fishSurroundSharkSetup(){
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(i==0 || j==0 || i==4 || j==4){
                    cellGrid.setCellAt(i, j, new PredatorPreyCell(i, j, 2, 3));
                    //cellGrid[i][j] = new PredatorPreyCell(i, j, 2, 3);
                }
                else if(i==1 && j==1){
                    cellGrid.setCellAt(i, j, new PredatorPreyCell(i, j, 1, 3));
                    //cellGrid[i][j] = new PredatorPreyCell(i, j, 1, 3);
                }
                else{
                    cellGrid.setCellAt(i, j, new PredatorPreyCell(i, j, 0, 3));
                    //cellGrid[i][j] = new PredatorPreyCell(i, j, 0, 3);
                }
            }
        }
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(i!=1 || j!= 1){
                    //neighbors array has only shark cells;
                    //specifically neighbors or cellType1 in location 1,1
                    //neighbors.add(cellGrid[i][j]);
                    neighbors.add(cellGrid.getCellAt(i, j));
                }
            }
        }
    }

    void oneFishSetup(){
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(i==2 && j==2){
                    cellGrid.setCellAt(i, j, new PredatorPreyCell(i, j, 1, 3));
                    //cellGrid[i][j] = new PredatorPreyCell(i, j, 1, 3);
                }
                else{
                    cellGrid.setCellAt(i, j, new PredatorPreyCell(i, j, 0, 3));
                    //cellGrid[i][j] = new PredatorPreyCell(i, j, 0, 3);
                }
            }
        }
        for(int i=1; i<4; i++){
            for(int j=1; j<4; j++){
                if(i!=2 || j!= 2){
                    //neighbors array has only empty cells to start;
                    //specifically neighbors or cellType1 in location 1,1
                    //neighbors.add(cellGrid[i][j]);
                    neighbors.add(cellGrid.getCellAt(i, j));
                }
            }
        }
    }

    void oneSharkSetup(){
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(i==2 && j==2){
                    //cellGrid[i][j] = new PredatorPreyCell(i, j, 2, 3);
                    cellGrid.setCellAt(i, j, new PredatorPreyCell(i, j, 2, 3));
                }
                else{
                    //cellGrid[i][j] = new PredatorPreyCell(i, j, 0, 3);
                    cellGrid.setCellAt(i, j, new PredatorPreyCell(i, j, 0, 3));
                }
            }
        }
        for(int i=1; i<4; i++){
            for(int j=1; j<4; j++){
                if(i!=2 || j!= 2){
                    //neighbors array has only empty cells to start;
                    //specifically neighbors or cellType1 in location 1,1
                    //neighbors.add(cellGrid[i][j]);
                    neighbors.add(cellGrid.getCellAt(i, j));
                }
            }
        }
    }

    void sharkEatFishSetup(){
        for(int i=0; i<5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 2 && j == 2) {
                    //cellGrid[i][j] = new PredatorPreyCell(i, j, 2, 3);
                    cellGrid.setCellAt(i, j, new PredatorPreyCell(i, j, 2, 3));
                } else if (i == 1) {
                    //cellGrid[i][j] = new PredatorPreyCell(i, j, 1, 3);
                    cellGrid.setCellAt(i, j, new PredatorPreyCell(i, j, 1, 3));
                } else {
                    //cellGrid[i][j] = new PredatorPreyCell(i, j, 0, 3);
                    cellGrid.setCellAt(i, j, new PredatorPreyCell(i, j, 0, 3));
                }
            }
        }
        for(int i=1; i<4; i++){
            for(int j=1; j<4; j++){
                if(i!=2 || j!= 2){
                    //neighbors array has empty cells and line of fish above to start;
                    //specifically neighbors or cellType1 in location 1,1
                    //neighbors.add(cellGrid[i][j]);
                    neighbors.add(cellGrid.getCellAt(i, j));
                }
            }
        }
    }


    @Test
    void resetReproductionFish() {
        fishSurroundSharkSetup();
        fishCell.updateCell(neighbors, cellGrid, shape);

        var expected1 = 1;
        var actual1 = fishCell.getMyReproductionTime();
        assertEquals(expected1, actual1);

        fishCell.resetReproductionTime();
        var expected2 = 0;
        var actual2 = fishCell.getMyReproductionTime();
        assertEquals(expected2, actual2);
    }

    @Test
    void fishReproduce() {
        oneFishSetup();
        Cell temp = cellGrid.getCellAt(2,2);
        temp.updateCell(neighbors, cellGrid, shape);
        temp.updateCell(neighbors, cellGrid, shape);
        temp.updateCell(neighbors, cellGrid, shape);
        temp.updateCell(neighbors, cellGrid, shape);
        temp.updateCell(neighbors, cellGrid, shape);

        //System.out.println(temp.myRow + " " + temp.myCol);
        //This is where the new fish will be left
        int[] prev = {temp.getMyRow(), temp.getMyCol()};
        temp.updateCell(neighbors, cellGrid, shape);

        var expected = 1;
        var actual = cellGrid.getCellAt(prev[0],prev[1]).getMyCurrentState();
        assertEquals(expected, actual);
    }

/*    @Test
    void sharkDiesUpdate() {
        oneSharkSetup();
        Cell temp = cellGrid.getCellAt(2,2);

        var expected1 = 2;
        var actual1 = temp.getMyEnergyLeft();
        assertEquals(expected1, actual1);

        temp.updateCell(neighbors, cellGrid, shape);
        temp.updateCell(neighbors, cellGrid, shape);

        var expected2 = 0;
        var actual2 = temp.getMyEnergyLeft();
        assertEquals(expected2, actual2);
    }

    @Test
    void sharkEatsFish(){
        sharkEatFishSetup();
        PredatorPreyCell tempShark = cellGrid[2][2];

        var expected1 = 2;
        var actual1 = tempShark.getMyEnergyLeft();
        assertEquals(expected1, actual1);

        tempShark.updateCell(neighbors, cellGrid, shape);
        var expected2 = 3;
        var actual2 = tempShark.getMyEnergyLeft();
        assertEquals(expected2, actual2);
    }*/

    @Test void sharkMovesToFishOverEmpty(){
        sharkEatFishSetup();
        Cell tempShark = cellGrid.getCellAt(2,2);
        tempShark.updateCell(neighbors, cellGrid, shape);
        var expected = 1;
        var actual = tempShark.getMyRow();
        assertEquals(expected, actual);
    }
}