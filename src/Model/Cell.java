package Model;

import java.util.List;
import java.util.Objects;
import javafx.scene.shape.Rectangle;

abstract public class Cell {
    protected int myCurrentState;
    protected int myNextState;
    protected int myRow;
    protected int myCol;
    protected Rectangle myRectangle;

    public Cell(int row, int col, int state, double width, double height) {
        myRow = row;
        myCol = col;
        myCurrentState = state;
        myNextState = state;
        myRectangle = new Rectangle(row * width, col * height, width, height);
        updateColor();
    }

    public Rectangle getShape() { return myRectangle; }
    public void resetState() { myCurrentState = myNextState; }

    abstract public void updateCell(List<Cell> neighbors);

    abstract public void updateColor();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return myCurrentState == cell.myCurrentState &&
                myRow == cell.myRow &&
                myCol == cell.myCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myCurrentState, myRow, myCol);
    }
}
