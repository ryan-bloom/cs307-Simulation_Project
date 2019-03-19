package View;

import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class HexagonGrid extends PolygonGrid {

    public HexagonGrid(double windowWidth, double windowHeight, int rows, int cols) {
        super(rows, cols, 6);
        double cellWidth = windowWidth/(cols + 0.5);
        double cellHeight = cellWidth; //nature of hexagonal shape
        initializeShapes(rows, cols, cellWidth, cellHeight);
    }

    public void initializeShapes(int rows, int cols, double cellWidth, double cellHeight) {

        double edgeLength = cellWidth/2;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double x1, x2, x3, x4, x5, x6, y1, y2, y3, y4, y5, y6;
                double offset = (i % 2 == 0) ? cellWidth/2 : 0;
                // https://www.redblobgames.com/grids/hexagons/ SEE "even-r" CONFIGURATION
                // if row is odd, start from left edge - offset is 0, if row is even, start half hexagon over, offset is hexagon width / 2
                // vertices labeled starting from 1 at the top, going clockwise
                x1 = offset + (j * cellWidth) + (cellWidth/2);
                x2 = x1 + (cellWidth/2);
                x3 = x2;
                x4 = x1;
                x5 = x1 - (cellWidth/2);
                x6 = x5;

                y1 = (i % 2 == 0) ? ((i/2) * (cellHeight + edgeLength)) : ((((i + 1)/2) * cellHeight) + (((i - 1)/2) * edgeLength) - (edgeLength/2));
                y2 = y1 + (edgeLength/2);
                y3 = y2 + edgeLength;
                y4 = y1 + cellHeight;
                y5 = y3;
                y6 = y2;

                Polygon hexagon = new Polygon(x1, y1, x2, y2, x3, y3, x4, y4, x5, y5, x6, y6);
                cellViewGrid[i][j] = hexagon;
                ArrayList<Double> coordinates = new ArrayList<>();
                coordinates.add(x1);
                coordinates.add(y1);
                coordinates.add(x2);
                coordinates.add(y2);
                coordinates.add(x3);
                coordinates.add(y3);
                coordinates.add(x4);
                coordinates.add(y4);
                coordinates.add(x5);
                coordinates.add(y5);
                coordinates.add(x6);
                coordinates.add(y6);
//                System.out.println("row: " + i + "column: " + j);
//                System.out.println(coordinates.toString());
            }
        }
    }
}
