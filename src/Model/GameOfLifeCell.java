package Model;
import java.util.List;
import javafx.scene.paint.Color;


public class GameOfLifeCell extends Cell {

    /**
     * States: 0 = dead; 1 = alive
     * @param row
     * @param col
     * @param state
     */
    public GameOfLifeCell(int row, int col, int state, double width, double height){
        super(row, col, state, width, height);
    }

    @Override
    public void updateCell(List<Cell> neighbors) {
        int liveCount = 0;
        for(Cell n : neighbors){
            if(n.myCurrentState == 1){
                liveCount++;
            }
        }
        if (this.myCurrentState == 1){ //this is live
            if(liveCount < 2 || liveCount >= 4){
                this.myNextState = 0;
            }
            else{
                this.myNextState = 1;
            }
        }
        else if (liveCount == 3){ //this is dead
            this.myNextState = 1;
        }
        updateColor();
    }

    public void updateColor() {
        if (myNextState == 1) {
            myRectangle.setFill(Color.RED);
        }
        else if (myNextState == 0) {
            myRectangle.setFill(Color.WHITE);
        }
    }
}
