package View;

import javafx.scene.shape.Polygon;


public abstract class PolygonGrid {

    protected Polygon[][] cellViewGrid;
    private int numSides;

    public PolygonGrid(int rows, int cols, int polygonSides) {
        cellViewGrid = new Polygon[rows][cols];
        numSides = polygonSides;
    }

    public abstract void initializeShapes(int rows, int cols, double cellWidth, double cellHeight);

    public Double[] getCoordinates(int row, int col) {
        double[] coordinates = new double[numSides];
        for (int i = 0; i < numSides; i++) {
            coordinates[i] = cellViewGrid[row][col].getPoints().indexOf(i);
        }
        return cellViewGrid[row][col].getPoints().toArray(new Double[numSides]);
        //return coordinates;
    }
}
