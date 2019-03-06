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
    public PredatorPreyCell(int row, int col, int state){
        super(row, col, state);
        myReproductionTime = 0;
        myEnergyLeft = ENERGY;
    }

    @Override
    public Cell[][] updateCell(List<Cell> neighbors, Cell[][] cellGrid, CellShape shape) {
        if(myCurrentState == 1){
            return fishUpdate(neighbors, cellGrid);
        }
        else if(myCurrentState == 2){
            return sharkUpdate(neighbors, cellGrid);
        }
        return cellGrid;
    }

    public Cell[][] fishUpdate(List<Cell> neighbors, Cell[][] cellGrid){
        myReproductionTime += 1;
        List<Cell> possNext = fillSubNeighbors(neighbors, 0);
        if(!possNext.isEmpty()){
            Cell nextLoc = randDirection(possNext);
            //System.out.println("HERE");
            return moveCell(nextLoc, cellGrid);
        }
        return cellGrid;
    }

    public Cell[][] sharkUpdate(List<Cell> neighbors, Cell[][] cellGrid){
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
                cellGrid[myRow][myCol].myNextState = 0;
                cellGrid[myRow][myCol].myCurrentState = 0;
                return cellGrid;
            }
            else if(!emptyNear.isEmpty()){
                Cell nextLoc = randDirection(emptyNear);
                cellGrid = moveCell(nextLoc, cellGrid);
            }
        }
        return cellGrid;
    }

    public Cell[][] moveCell(Cell nextLocationCell, Cell[][] cellGrid){
        Cell temp;
        int prevRow = this.myRow;
        int prevCol = this.myCol;
        int nxtRow = nextLocationCell.myRow;
        int nxtCol = nextLocationCell.myCol;
        if(this.myReproductionTime >= GESTATION_PERIOD){
            this.resetReproductionTime();
            temp = new PredatorPreyCell(prevRow, prevCol, this.myCurrentState);
            //System.out.println(temp.myRow + " " + temp.myCol);
        }
        else{
            temp = new PredatorPreyCell(prevRow, prevCol, 0);
        }
        this.newLocation(nxtRow, nxtCol);
        //this.myNextState = this.myCurrentState;
        cellGrid[nxtRow][nxtCol] = this;
        cellGrid[prevRow][prevCol] = temp;
        return cellGrid;
    }

    public Cell randDirection(List<Cell> potentials){
        Random rand = new Random();
        int dir = rand.nextInt(potentials.size());
        return potentials.get(dir);
    }

    public void newLocation(int r, int c){
        this.myRow = r;
        this.myCol = c;
    }

    public void resetReproductionTime(){myReproductionTime = 0;}

    public List<Cell> fillSubNeighbors(List<Cell> neighbors, int state){
        //System.out.println("MY LOCATION: " + myRow + " " + myCol);
        List<Cell> res = new ArrayList<>();
        for(Cell c:neighbors){
            if((c.myRow == this.myRow || c.myCol == this.myCol) && c.myCurrentState == state){
                res.add(c);
                //System.out.println(c.myRow + " " + c.myCol);
            }
        }
        return res;
    }

    public int getMyReproductionTime(){return myReproductionTime;}
    public int getMyEnergyLeft(){return myEnergyLeft;}
}
