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
        cellGrid[getMyRow()][getMyCol()] = this;
        return cellGrid;
    }

    public void squareUpdate(int liveCount){
        if(this.getMyCurrentState() == 1){
            if(liveCount < 2 || liveCount >= 4){
                this.setMyNextState(0);
            }
            else{
                this.setMyNextState(1);
            }
        }
        else if(liveCount == 3) {
            this.setMyNextState(1);
        }
    }

    public void hexUpdate(int liveCount){
        if(this.getMyCurrentState() == 1){
            if(liveCount == 3 || liveCount == 5){
                this.setMyNextState(1);
            }
            else{ this.setMyNextState(0); }
        }
        else if(liveCount == 2){
            this.setMyNextState(1);
        }
    }

    public void triUpdate(int liveCount){
        if(this.getMyCurrentState() == 1){
            if(liveCount == 2 || liveCount == 7){
                this.setMyNextState(1);
            }
            else{this.setMyNextState(0);}
        }
        else if(liveCount == 3){
            this.setMyNextState(1);
        }
    }

    public int getLiveCount(List<Cell> neighbors){
        int liveCount = 0;
        for(Cell n : neighbors){
            if(n.getMyCurrentState() == 1){
                liveCount++;
            }
        }
        return liveCount;
    }
}
