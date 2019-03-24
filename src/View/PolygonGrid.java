package View;

import javafx.scene.shape.Polygon;

public abstract class PolygonGrid {

    protected Polygon[][] cellViewGrid;
    protected int numSides;

    public PolygonGrid(int rows, int cols, int polygonSides) {
        cellViewGrid = new Polygon[rows][cols];
        numSides = polygonSides;
    }

    public abstract void initializeShapes(int rows, int cols, double cellWidth, double cellHeight);

    public double[] getCoordinates(int row, int col) {
        Double[] coordinates = cellViewGrid[row][col].getPoints().toArray(new Double[2 * numSides]);
        double[] convertedCoordinates = new double[2 * numSides];
        for (int i = 0; i < numSides * 2; i++) {
            convertedCoordinates[i] = coordinates[i].doubleValue();
        }
        return convertedCoordinates;
    }
}
