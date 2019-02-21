package Cells;
import java.util.List;
import javafx.scene.paint.Color;

public class PercolationCell extends Cell {
    /**
     * States: 0 = blocked, 1 = open, 2 = percolated
     * @param row
     * @param col
     * @param state
     */
    public PercolationCell(int row, int col, int state, double width, double height){
        super(row, col, state, width, height);
    }

    @Override
    public void updateCell(List<Cell> neighbors) {
        if (this.myCurrentState == 1){
            for(Cell n : neighbors){
                if(n.myCurrentState == 2){
                    this.myNextState = 2;
                    break;
                }
            }
        }
    }

    @Override
    public void updateColor() {
        if (myNextState == 0) {
            myRectangle.setFill(Color.BLACK);
        }
        else if (myNextState == 1) {
            myRectangle.setFill(Color.WHITE);
        }
        else if (myNextState == 2) {
            myRectangle.setFill(Color.BLUE);
        }
    }
}
