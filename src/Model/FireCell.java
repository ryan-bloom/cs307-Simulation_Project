package Model;

import java.util.List;

public class FireCell extends Cell {
    //Neighbors only directly top/bottom and left/right
    /**
     * 0 = empty; 1 = tree; 2 = burning
     * @param row
     * @param col
     * @param state
     */
    public FireCell(int row, int col, int state){
        super(row, col, state);
    }

    @Override
    public void updateCell(List<Cell> neighbors) {
        //Empty stays empty burning goes to empty
        if(myCurrentState == 1){
            for(Cell c: neighbors){
                //Only direct neighbors
                if((c.myCol == myCol || c.myRow == myRow) && c.myCurrentState == 2){
                    myNextState = 2;
                    break;
                }
            }
        }
        else{
            myNextState = 0;
        }
    }
}
