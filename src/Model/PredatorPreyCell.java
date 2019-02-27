package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PredatorPreyCell extends Cell {
    private static final int GESTATION_PERIOD = 3;
    private static final int ENERGY = 3;
    private static final int FISH_ENERGY = 1;

    private int myReproductionTime;
    private int myEnergyUnits;

    /**
     * 0 = empty; 1 = fish; 2 = shark
     * @param row
     * @param col
     * @param state
     */
    public PredatorPreyCell(int row, int col, int state){
        super(row, col, state);
        myReproductionTime = 0;
        myEnergyUnits = ENERGY;
    }

    @Override
    public Cell[][] updateCell(List<Cell> neighbors, Cell[][] cellGrid) {
        if(this.myCurrentState == 1){
            cellGrid = fishUpdate(neighbors, cellGrid);
        }
        else if(this.myCurrentState == 2){
            cellGrid = sharkUpdate(neighbors, cellGrid);
        }
        return cellGrid;
    }

    public Cell[][] fishUpdate(List<Cell> neighbors, Cell[][] cellGrid){
        myReproductionTime++;
        List<Cell> possNext = new ArrayList<>();
        for(Cell c:neighbors){
            if((c.myRow == myRow || c.myCol == myCol) && c.myCurrentState == 0){
                possNext.add(c);
            }
        }
        if(possNext.isEmpty()){
            return cellGrid;
        }
        else{
            Cell nextLoc = randDirection(possNext);
            cellGrid = moveCell(nextLoc, cellGrid);
        }
        return cellGrid;
    }

    public Cell[][] sharkUpdate(List<Cell> neighbors, Cell[][] cellGrid){
        myReproductionTime++;
        List<Cell> fishNear = new ArrayList<>();
        List<Cell> emptyNear = new ArrayList<>();

        for(Cell c : neighbors){
            if((c.myRow == myRow || c.myCol == myCol) && c.myCurrentState != 2){
                if(c.myCurrentState == 0){
                    emptyNear.add(c);
                }
                else{ fishNear.add(c); }
            }
        }
        if(!fishNear.isEmpty()){
            Cell nextLoc = randDirection(fishNear);
            myEnergyUnits += FISH_ENERGY;
            cellGrid = moveCell(nextLoc, cellGrid);
        }
        else{
            myEnergyUnits--;
            if(myEnergyUnits <= 0){
                cellGrid[myRow][myCol].myNextState = 0;
                return cellGrid;
            }
            else if(!emptyNear.isEmpty()){
                Cell nextLoc = randDirection(emptyNear);
                cellGrid = moveCell(nextLoc, cellGrid);
            }
        }
        return cellGrid;
    }

    public Cell randDirection(List<Cell> potentials){
        Random rand = new Random();
        int dir = rand.nextInt(potentials.size());
        return potentials.get(dir);
    }

    public void resetReproductionTime(){myReproductionTime = 0;}

    public Cell[][] moveCell(Cell nextLocationCell, Cell[][] cellGrid){
        int newRow = nextLocationCell.myRow;
        int newCol = nextLocationCell.myCol;
        if(myReproductionTime >= GESTATION_PERIOD){
            this.resetReproductionTime();
            cellGrid[myRow][myCol] = new PredatorPreyCell(myRow, myCol, this.myCurrentState);
        }
        else{
            cellGrid[myRow][myCol].myNextState = 0;
        }
        cellGrid[newRow][newCol] = this;
        return cellGrid;
    }
}
