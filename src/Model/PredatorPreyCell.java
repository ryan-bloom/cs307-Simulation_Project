package Model;

import Controller.CellShape;
import Controller.Grid;

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
    public PredatorPreyCell(int row, int col, int state, int numStates){
        super(row, col, state, numStates);
        myReproductionTime = 0;
        myEnergyLeft = ENERGY;
    }
    //public PredatorPreyCell(int row, int col, int state) { super(row, col, state, 3); }

    /**
     * Uses helper methods to update cell depending on if current cell is shark or fish
     * @param neighbors
     * @param cellGrid
     * @param shape
     * @return
     */
    @Override
    public List<Cell> updateCell(List<Cell> neighbors, Grid cellGrid, CellShape shape) {
        List<Cell> tempUpdatedCells = new ArrayList<>();
        if(getMyCurrentState() == 1){
            return fishUpdate(neighbors, cellGrid);
        }
        else if(getMyCurrentState() == 2){
            return sharkUpdate(neighbors, cellGrid);
        }
        return tempUpdatedCells;
    }

    /**
     * If current cell is fish -- this helper method is used
     * @param neighbors
     * @param cellGrid
     * @return
     */
    private List<Cell> fishUpdate(List<Cell> neighbors, Grid cellGrid){
        List<Cell> updatedCells = new ArrayList<>();
        //Survived another round (increase reproductionTime)
        myReproductionTime += 1;
        List<Cell> possNext = fillSubNeighbors(neighbors, 0);
        //Find empty surrounding neighbors and go there
        if(!possNext.isEmpty()){
            Cell nextLoc = randDirection(possNext);
            return moveCell(nextLoc);
        }
        return updatedCells;
    }

    /**
     * If current cell is shark use this update method
     * @param neighbors
     * @param cellGrid
     * @return
     */
    private List<Cell> sharkUpdate(List<Cell> neighbors, Grid cellGrid){
        List<Cell> tempNewCells = new ArrayList<>();
        myReproductionTime++;
        //Separate neighbor cells b/w empty and fish because go fish first
        List<Cell> fishNear = fillSubNeighbors(neighbors, 1);
        List<Cell> emptyNear = fillSubNeighbors(neighbors, 0);

        //Fish isn't empty -- pick random place and eat that fish
        if(!fishNear.isEmpty()){
            Cell nextLoc = randDirection(fishNear);
            myEnergyLeft += FISH_ENERGY;
            tempNewCells = moveCell(nextLoc);
            return tempNewCells;
        }
        else{
            //Lose energy if no fish to eat
            myEnergyLeft--;
            if(myEnergyLeft <= 0){
                this.setMyNextState(0);
                this.setMyCurrentState(0);
                tempNewCells.add(this);
                return tempNewCells;
                //cellGrid[getMyRow()][getMyCol()].setMyNextState(0);
                //cellGrid[getMyRow()][getMyCol()].setMyCurrentState(0);
                //return cellGrid;
            }
            else if(!emptyNear.isEmpty()){
                Cell nextLoc = randDirection(emptyNear);
                tempNewCells = moveCell(nextLoc);
            }
        }
        return tempNewCells;
    }

    /**
     * Called by both fish and shark update methods (moves current cell to newly/randomly selected neighbor)
     * @param nextLocationCell
     * @return
     */
    private List<Cell> moveCell(Cell nextLocationCell){
        List<Cell> tempList = new ArrayList<>();
        Cell temp;
        int prevRow = this.getMyRow();
        int prevCol = this.getMyCol();
        int nxtRow = nextLocationCell.getMyRow();
        int nxtCol = nextLocationCell.getMyCol();
        if(this.myReproductionTime >= GESTATION_PERIOD){
            this.resetReproductionTime();
            temp = new PredatorPreyCell(prevRow, prevCol, this.getMyCurrentState(), 3);
        }
        else{
            temp = new PredatorPreyCell(prevRow, prevCol, 0, 3);
        }
        this.newLocation(nxtRow, nxtCol);
        tempList.add(this);
        tempList.add(temp);
        return tempList;
        //cellGrid[nxtRow][nxtCol] = this;
        //cellGrid[prevRow][prevCol] = temp;
        //return cellGrid;
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
