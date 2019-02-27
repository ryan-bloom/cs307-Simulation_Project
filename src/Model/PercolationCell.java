package Model;
import java.util.List;

public class PercolationCell extends Cell {
    /**
     * 0 = blocked; 1 = open; 2 = percolating
     * @param row
     * @param col
     * @param state
     */
    public PercolationCell(int row, int col, int state){
        super(row, col, state);
    }

    @Override
    public void updateCell(List<Cell> neighbors) {
        if (this.myCurrentState == 1){
            for (Cell n : neighbors){
                if(n.myCurrentState == 2){
                    this.myNextState = 2;
                    return;
                }
            }
        }
    }
}
