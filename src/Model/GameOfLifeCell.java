package Model;
import java.util.List;


public class GameOfLifeCell extends Cell {
    /**
     * 0 = dead; 1 = alive
     * @param row
     * @param col
     * @param state
     */
    public GameOfLifeCell(int row, int col, int state){
        super(row, col, state);
    }

    @Override
    public Cell[][] updateCell(List<Cell> neighbors, Cell[][] cellGrid) {
        int liveCount = getLiveCount(neighbors);
        if (this.myCurrentState == 1){ //this is live
            liveUpdate(liveCount);
        }
        else if (liveCount == 3){ //this is dead, revive
            this.myNextState = 1;
        }
        cellGrid[myRow][myCol] = this;
        return cellGrid;
    }

    public void liveUpdate(int count){
        if(count < 2 || count >= 4){
            this.myNextState = 0;
        }
        else{
            this.myNextState = 1;
        }
    }

    public int getLiveCount(List<Cell> neighbors){
        int liveCount = 0;
        for(Cell n : neighbors){
            if(n.myCurrentState == 1){
                liveCount++;
            }
        }
        return liveCount;
    }
}
