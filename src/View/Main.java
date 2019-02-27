package View;

import Model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class Main extends Application {
    //public static final String SIMULATION_CONFIGURATION = "Fire_Config_1.csv";
    public static final int WINDOW_HEIGHT = 700;
    public static final int WINDOW_WIDTH = 700;
    public static final int FRAMES_PER_SECOND = 2;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.WHITE;
    public static final String DEFAULT_RESOURCE_PACKAGE = "Resources.";

    private Cell[][] cellGrid;
    private Group myGroup;
    private ResourceBundle myResources;

    public static void main (String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "MattPercolation");
        Data d = new Data(myResources.getString("File"));
        cellGrid = new Cell[d.getHeight()][d.getWidth()];
        myGroup = new Group();
        var scene = new Scene(myGroup, WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);
        double cellHeight = WINDOW_HEIGHT/d.getHeight();
        double cellWidth = WINDOW_WIDTH/d.getWidth();
        for (int i = 0; i < d.getWidth(); i++) {
            for (int j = 0; j < d.getHeight(); j++) {
                //cellGrid[i][j] = new GameOfLifeCell(i, j, d.getStates()[i][j], cellWidth, cellHeight);
                cellGrid[i][j] = new PercolationCell(i, j, d.getStates()[i][j], cellWidth, cellHeight);
                //cellGrid[i][j] = new RPSCell(i, j, d.getStates()[i][j], cellWidth, cellHeight);
                //cellGrid[i][j] = new FireCell(i, j, d.getStates()[i][j], cellWidth, cellHeight);
                myGroup.getChildren().add(cellGrid[i][j].getShape());
            }
        }
        stage.setScene(scene);
        stage.setTitle(d.getSimulation());
        stage.show();
        var frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
//MOVE THESE METHODS TO A GRID CLASS IN MODEL PACKAGE
    private ArrayList<Cell> findNeighbors(int i, int j) {
        return toroidalNeighbors(i, j);
    }

    private ArrayList<Cell> toroidalNeighbors(int i, int j) {
        ArrayList<Cell> neighbors = new ArrayList<>();

        //default values if not edge case
        int left = i - 1;
        int right = i + 1;
        int top = j - 1;
        int bottom = j + 1;

        //left edge
        if (i == 0) {
            left = cellGrid.length - 1;
        }
        //right edge
        if (i == cellGrid.length - 1) {
            right = 0;
        }
        //top edge
        if (j == 0) {
            top = cellGrid[0].length - 1;
        }
        //bottom edge
        if (j == cellGrid[0].length - 1) {
            bottom = 0;
        }
        neighbors.add(cellGrid[left][top]);
        neighbors.add(cellGrid[i][top]);
        neighbors.add(cellGrid[right][top]);
        neighbors.add(cellGrid[left][j]);
        neighbors.add(cellGrid[right][j]);
        neighbors.add(cellGrid[left][bottom]);
        neighbors.add(cellGrid[i][bottom]);
        neighbors.add(cellGrid[right][bottom]);
        return neighbors;
    }
//MOVE THE ABOVE TO GRID CLASS


    private void step(double elapsedTime) {
        // updates colors and states of all cells
        for (int i = 0; i < cellGrid.length; i++) {
            for (int j = 0; j < cellGrid[0].length; j++) {
                cellGrid[i][j].updateCell(findNeighbors(i, j));
            }
        }
        // resets state of all cells so next update will function correctly
        for (int i = 0; i < cellGrid.length; i++) {
            for (int j = 0; j < cellGrid[0].length; j++) {
                cellGrid[i][j].resetState();
            }
        }
    }
}