package Model;
import Controller.CellShape;

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
    public Cell[][] updateCell(List<Cell> neighbors, Cell[][] cellGrid, CellShape shape) {
        int liveCount = getLiveCount(neighbors);
        if(shape == CellShape.SQUARE){
            squareUpdate(liveCount);
        }
        else if(shape == CellShape.HEXAGON){
            hexUpdate(liveCount);
        }
        else{
            triUpdate(liveCount);
        }
        cellGrid[myRow][myCol] = this;
        return cellGrid;
    }

    public void squareUpdate(int liveCount){
        if(this.myCurrentState == 1){
            if(liveCount < 2 || liveCount >= 4){
                this.myNextState = 0;
            }
            else{
                this.myNextState = 1;
            }
        }
        else if(liveCount == 3) {
            this.myNextState = 1;
        }
    }

    public void hexUpdate(int liveCount){
        if(this.myCurrentState == 1){
            if(liveCount == 3 || liveCount == 5){
                this.myNextState = 1;
            }
            else{ this.myNextState = 0; }
        }
        else if(liveCount == 2){
            this.myNextState = 1;
        }
    }

    public void triUpdate(int liveCount){
        if(this.myCurrentState == 1){
            if(liveCount == 2 || liveCount == 7){
                this.myNextState = 1;
            }
            else{myNextState = 0;}
        }
        else if(liveCount == 3){
            this.myNextState = 1;
        }
    }

/*    public void liveUpdate(int count){
        if(count < 2 || count >= 4){
            this.myNextState = 0;
        }
        else{
            this.myNextState = 1;
        }
    }*/

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
