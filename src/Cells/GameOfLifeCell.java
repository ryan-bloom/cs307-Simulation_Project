package Cells;

import java.util.List;

public class GameOfLifeCell extends Cell {

    /**
     * States: 0 = dead; 1 = alive
     * @param row
     * @param col
     * @param state
     */
    public GameOfLifeCell(int row, int col, int state){
        super(row, col, state);
    }

    @Override
    public void updateCell(List<Cell> neighbors) {
        int liveCount = 0;
        for(Cell n : neighbors){
            if(n.myCurrentState == 1){
                liveCount++;
            }
        }
        if(this.myCurrentState == 1){ //this is live
            if(liveCount < 2){ this.myNextState = 0; }
            else if(liveCount < 4){ this.myNextState = 1; }
            else{ this.myNextState = 0; }
        }
        else if(liveCount == 3){ //this is dead
            this.myNextState = 1;
        }
    }
}
