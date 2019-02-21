package Cells;

import java.util.List;
import javafx.scene.shape.Rectangle;

abstract public class Cell {
    protected int myCurrentState;
    protected int myNextState;
    protected int myRow;
    protected int myCol;
    protected Rectangle myRectangle;

    public Cell(int row, int col, int state, double width, double height){
        myRow = row;
        myCol = col;
        myCurrentState = state;
        myNextState = -1;
        myRectangle = new Rectangle(row * width, col * height, width, height);
    }

    public Rectangle getShape() {
        return myRectangle;
    }

    public void resetState() {
        myCurrentState = myNextState;
    }

    abstract public void updateCell(List<Cell> neighbors);

    abstract public void updateColor();


}
