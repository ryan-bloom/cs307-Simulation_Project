package Model;
import java.util.List;
import javafx.scene.paint.Color;

public class PercolationCell extends Cell {
    private static final Color[] COLORS = {Color.valueOf("#FFFFFF"), Color.WHITE, Color.BLUE};

    /**
     *
     * @param row
     * @param col
     * @param state
     * @param width
     * @param height
     */
    public PercolationCell(int row, int col, int state, double width, double height){
        super(row, col, state, width, height);
    }

    @Override
    public void updateCell(List<Cell> neighbors) {
        if (this.myCurrentState == 1){
            for (Cell n : neighbors){
                if(n.myCurrentState == 2){
                    this.myNextState = 2;
                    updateColor();
                    return;
                }
            }
        }
    }

    @Override
    public void updateColor() {
        int dex = this.myNextState;
        myRectangle.setFill(COLORS[dex]);
    }
}
