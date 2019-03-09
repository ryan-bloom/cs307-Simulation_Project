package Model;

import Controller.CellShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PredatorPreyCell extends Cell {
    private static final int GESTATION_PERIOD = 6;
    private static final int ENERGY = 2;
    private static final int FISH_ENERGY = 1;

    private int myReproductionTime;
    private int myEnergyLeft;

    /**
     * 0 = empty; 1 = fish; 2 = shark
     * @param row
     * @param col
     * @param state
     */
    public PredatorPreyCell(int row, int col, int state, int numStates){
        super(row, col, state, numStates);
        myReproductionTime = 0;
        myEnergyLeft = ENERGY;
    }
    public PredatorPreyCell(int row, int col, int state) { super(row, col, state); }

    @Override
    public Cell[][] updateCell(List<Cell> neighbors, Cell[][] cellGrid, CellShape shape) {
        if(getMyCurrentState() == 1){
            return fishUpdate(neighbors, cellGrid);
        }
        else if(getMyCurrentState() == 2){
            return sharkUpdate(neighbors, cellGrid);
        }
        return cellGrid;
    }

    private Cell[][] fishUpdate(List<Cell> neighbors, Cell[][] cellGrid){
        myReproductionTime += 1;
        List<Cell> possNext = fillSubNeighbors(neighbors, 0);
        if(!possNext.isEmpty()){
            Cell nextLoc = randDirection(possNext);
            //System.out.println("HERE");
            return moveCell(nextLoc, cellGrid);
        }
        return cellGrid;
    }

    private Cell[][] sharkUpdate(List<Cell> neighbors, Cell[][] cellGrid){
        myReproductionTime++;
        List<Cell> fishNear = fillSubNeighbors(neighbors, 1);
        List<Cell> emptyNear = fillSubNeighbors(neighbors, 0);

        if(!fishNear.isEmpty()){
            Cell nextLoc = randDirection(fishNear);
            myEnergyLeft += FISH_ENERGY;
            cellGrid = moveCell(nextLoc, cellGrid);
            return cellGrid;
        }
        else{
            //Lose energy if no fish to eat
            myEnergyLeft--;
            if(myEnergyLeft <= 0){
                cellGrid[getMyRow()][getMyCol()].setMyNextState(0);
                cellGrid[getMyRow()][getMyCol()].setMyCurrentState(0);
                return cellGrid;
            }
            else if(!emptyNear.isEmpty()){
                Cell nextLoc = randDirection(emptyNear);
                cellGrid = moveCell(nextLoc, cellGrid);
            }
        }
        return cellGrid;
    }

    private Cell[][] moveCell(Cell nextLocationCell, Cell[][] cellGrid){
        Cell temp;
        int prevRow = this.getMyRow();
        int prevCol = this.getMyCol();
        int nxtRow = nextLocationCell.getMyRow();
        int nxtCol = nextLocationCell.getMyCol();
        if(this.myReproductionTime >= GESTATION_PERIOD){
            this.resetReproductionTime();
            temp = new PredatorPreyCell(prevRow, prevCol, this.getMyCurrentState());
        }
        else{
            temp = new PredatorPreyCell(prevRow, prevCol, 0);
        }
        this.newLocation(nxtRow, nxtCol);
        cellGrid[nxtRow][nxtCol] = this;
        cellGrid[prevRow][prevCol] = temp;
        return cellGrid;
    }

    private Cell randDirection(List<Cell> potentials){
        Random rand = new Random();
        int dir = rand.nextInt(potentials.size());
        return potentials.get(dir);
    }

    private void newLocation(int r, int c){
        this.setMyRow(r);
        this.setMyCol(c);
    }

    void resetReproductionTime(){myReproductionTime = 0;}

    private List<Cell> fillSubNeighbors(List<Cell> neighbors, int state){
        List<Cell> res = new ArrayList<>();
        for(Cell c:neighbors){
            if(c.getMyCurrentState() == state){
                res.add(c);
            }
        }
        return res;
    }

    int getMyReproductionTime(){return myReproductionTime;}
    int getMyEnergyLeft(){return myEnergyLeft;}
}
