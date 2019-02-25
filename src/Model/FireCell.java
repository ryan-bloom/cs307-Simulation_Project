package Model;

import javafx.scene.paint.Color;

import java.util.List;

public class FireCell extends Cell {
    private static final Color[] COLORS = {Color.YELLOW, Color.GREEN, Color.RED};

    //Neighbors only directly top/bottom and left/right
    /**
     * 0 = empty; 1 = tree; 2 = burning
     * @param row
     * @param col
     * @param state
     * @param width
     * @param height
     */
    public FireCell(int row, int col, int state, double width, double height){
        super(row, col, state, width, height);
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
        updateColor();
    }

    @Override
    public void updateColor() {
        int dex = myNextState;
        myRectangle.setFill(COLORS[dex]);
    }
}
