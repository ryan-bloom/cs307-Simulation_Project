package Model;

import javafx.scene.paint.Color;

import java.util.List;

public class SegregationCell extends Cell {
    private static final Color[] COLORS = {Color.WHITE, Color.RED, Color.BLUE};
    private static final double THRESHOLD = 0.3; //30% for satisfaction

    /**
     * 0 = empty; 1 = red; 2 = blue
     * @param row
     * @param col
     * @param state
     * @param width
     * @param height
     */
    public SegregationCell(int row, int col, int state, double width, double height){
        super(row, col, state, width, height);
    }

    @Override
    public void updateCell(List<Cell> neighbors) {
        double perc = findPercentage(neighbors);

    }

    public double findPercentage(List<Cell> neighbors){
        int diff = 0;
        for(Cell c: neighbors){
            if(c.myCurrentState != 0 && c.myCurrentState != myCurrentState){
                diff++;
            }
        }
        return diff/8.0;
    }

    @Override
    public void updateColor() {

    }
}
