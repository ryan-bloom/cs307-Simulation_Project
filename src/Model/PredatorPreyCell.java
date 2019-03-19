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
     * Uses super to set cell and states
     * 0 = empty; 1 = fish; 2 = shark
     * Also sets reproductionTime to 0 and energyLeft to ENERGY variable (only for pred-prey cells)
     * @param row
     * @param col
     * @param state
     */
    public PredatorPreyCell(int row, int col, int state){
        super(row, col, state);
        myReproductionTime = 0;
        myEnergyLeft = ENERGY;
    }

    /**
     * Uses helper methods to update cell depending on if current cell is shark or fish
     * @param neighbors
     * @param cellGrid
     * @param shape
     * @return
     */
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

    /**
     * If current cell is fish -- this helper method is used
     * @param neighbors
     * @param cellGrid
     * @return
     */
    private Cell[][] fishUpdate(List<Cell> neighbors, Cell[][] cellGrid){
        //Survived another round (increase reproductionTime)
        myReproductionTime += 1;
        List<Cell> possNext = fillSubNeighbors(neighbors, 0);
        //Find empty surrounding neighbors and go there
        if(!possNext.isEmpty()){
            Cell nextLoc = randDirection(possNext);
            return moveCell(nextLoc, cellGrid);
        }
        return cellGrid;
    }

    /**
     * If current cell is shark use this update method
     * @param neighbors
     * @param cellGrid
     * @return
     */
    private Cell[][] sharkUpdate(List<Cell> neighbors, Cell[][] cellGrid){
        myReproductionTime++;
        //Separate neighbor cells b/w empty and fish because go fish first
        List<Cell> fishNear = fillSubNeighbors(neighbors, 1);
        List<Cell> emptyNear = fillSubNeighbors(neighbors, 0);

        //Fish isn't empty -- pick random place and eat that fish
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

    /**
     * Called by both fish and shark update methods (moves current cell to newly/randomly selected neighbor)
     * @param nextLocationCell
     * @param cellGrid
     * @return
     */
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

    //Helper method randomly selects which neighboring cell to move to
    private Cell randDirection(List<Cell> potentials){
        Random rand = new Random();
        int dir = rand.nextInt(potentials.size());
        return potentials.get(dir);
    }

    //Sets row/col after moving to new location
    private void newLocation(int r, int c){
        this.setMyRow(r);
        this.setMyCol(c);
    }

    //After reach gestation period must reset time to 0 for fish and sharks
    void resetReproductionTime(){myReproductionTime = 0;}

    //Gets empty/fish neighboring cells
    private List<Cell> fillSubNeighbors(List<Cell> neighbors, int state){
        List<Cell> res = new ArrayList<>();
        for(Cell c:neighbors){
            if(c.getMyCurrentState() == state){
                res.add(c);
            }
        }
        return res;
    }

    //Getters for reproduction time and energyLeft
    int getMyReproductionTime(){return myReproductionTime;}
    int getMyEnergyLeft(){return myEnergyLeft;}
}
