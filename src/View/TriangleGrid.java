package View;


import javafx.scene.shape.Polygon;

public class TriangleGrid extends PolygonGrid {
    private static final int NUMBER_OF_SIDES = 3;

    public TriangleGrid(double windowWidth, double windowHeight, int rows, int cols) {
        super(rows, cols, 3);
        double cellWidth;
        if (cols % 2 == 1) {
            cellWidth = windowWidth/cols;
        } else {
            cellWidth = ((2 * windowWidth) / (cols + 1));
        }
        double cellHeight = windowHeight / rows;
        initializeShapes(rows, cols, cellWidth, cellHeight);
    }

    public void initializeShapes(int rows, int cols, double cellWidth, double cellHeight) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double x1, x2, x3, y1, y2, y3;
                double offset;
                if ((i + j) % 2 == 0) { // upside down triangle
                    offset = (i % 2 == 0) ? 0 : cellWidth/2; // if row is even, start from left edge - offset is 0, if row is odd, start half triangle over, offset is triangle side length / 2
                    x1 = offset + ((j/2) * cellWidth);
                    x2 = x1 + cellWidth;
                    x3 = ((x1 + x2)/2);
                    y1 = i * cellHeight;
                    y2 = y1;
                    y3 = y2 + cellHeight;
                }
                else { // right side up triangle
                    offset = (i % 2 == 0) ? cellWidth/2 : 0;
                    x1 = offset + ((j/2) * cellWidth);
                    x2 = x1 + cellWidth;
                    x3 = ((x1 + x2)/2);
                    y1 = (i + 1) * cellHeight;
                    y2 = y1;
                    y3 = y2 - cellHeight;
                }
                Polygon triangle = new Polygon(x1, y1, x2, y2, x3, y3);
                cellViewGrid[i][j] = triangle;
            }
        }
    }
}
