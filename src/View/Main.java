package View;

import Model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.List;

public class Main extends Application {

    public static final String SIMULATION = "Percolation";
    public static final int WINDOW_HEIGHT = 700;
    public static final int WINDOW_WIDTH = 700;

    public static int FRAMES_PER_SECOND = 6;
    public static double SECOND_DELAY = 3.0 / FRAMES_PER_SECOND;

    public static final Paint BACKGROUND = Color.WHITE;
    public static final String DEFAULT_RESOURCE_PACKAGE = "Resources";

    //private Cell[][] cellGrid;
    private Grid myGrid;
    private Group myGroup;
    private Animation myAnimation;
    private ResourceBundle myResources;
    private Data mySeed;

    //private Map<String, List<Color>> colorsMap; ---- MIGHT USE THIS IF MAP OF GAME - COLORS FOR SAID GAME
    private List<Color> colorsList;
    private double cellWidth;
    private double cellHeight;
    private boolean isRunning = true;

    public static void main (String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        stage.setScene(setupSeed(0));
        //stage.setTitle(d.getSimulation());
        stage.show();
        var frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step());
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        myAnimation = animation;
        animation.play();
    }

    public Scene setupSeed(int config) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + ".Matt" + SIMULATION);
        Data d = new Data(myResources.getString("File").split(",")[config]);
        fillColorsList(d.getSimulation().toUpperCase());
        myGrid = new Grid(d);
        cellHeight = WINDOW_HEIGHT/d.getHeight();
        cellWidth = WINDOW_WIDTH/d.getWidth();
        myGrid.fillCellGrid();
        myGroup = new Group();
        Scene seed = new Scene(myGroup, WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);
        for (int i = 0; i < d.getWidth(); i++) {
            for (int j = 0; j < d.getHeight(); j++) {
                Node view = updateCellView(i, j, myGrid.getCellState(i, j));//cellGrid[i][j].getMyCurrentState());
                myGroup.getChildren().add(view);
            }
        }
        seed.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return seed;
    }

    //SHOULD JUST READ IN COLORS FROM SEPARATE DATA FILE
    public void fillColorsList(String simulation){
        colorsList = new ArrayList<>();
        if(simulation.equals("GAMEOFLIFE")){
           colorsList.add(Color.WHITE);
           colorsList.add(Color.RED);
        }
        else if(simulation.equals("PERCOLATION")){
            colorsList.add(Color.BLACK);
            colorsList.add(Color.WHITE);
            colorsList.add(Color.BLUE);
        }
        else if(simulation.equals("RPS")){
            colorsList.add(Color.RED);
            colorsList.add(Color.GREEN);
            colorsList.add(Color.BLUE);
        }
        else if(simulation.equals("FIRE")){
            colorsList.add(Color.YELLOW);
            colorsList.add(Color.GREEN);
            colorsList.add(Color.RED);
        }
        else if(simulation.equals("SEGREGATION")){
            colorsList.add(Color.WHITE);
            colorsList.add(Color.BLUE);
            colorsList.add(Color.RED);
        }
    }

    //GOING TO HAVE TO HANDLE RECTANGLES AND IMAGE VIEWS EVENTUALLY
    public Node updateCellView(int row, int col, int state){
        var res = new Rectangle(row * cellWidth, col * cellHeight, cellWidth, cellHeight);
        var color = colorsList.get(state);
        res.setFill(color);
        return res;
    }

    private void step() {
        // updates colors and states of all cells
        for (int i = 0; i < myGrid.getMyRows(); i++) {
            for (int j = 0; j < myGrid.getMyCols(); j++) {
                myGrid.updateGridCell(i, j);
            }
        }
        // resets state of all cells so next update will function correctly
        for (int i = 0; i < myGrid.getMyRows(); i++) {
            for (int j = 0; j < myGrid.getMyCols(); j++) {
                myGroup.getChildren().add(updateCellView(i, j, myGrid.getCellState(i,j)));
                myGrid.resetCell(i, j);
            }
        }
    }

    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.SPACE) {
            if (isRunning) {
                myAnimation.pause();
            }
            else {
                myAnimation.play();
            }
            isRunning = !(isRunning);
        }

        // Increase & decrease simulation speed
        if (code == KeyCode.UP) {
            myAnimation.setRate(myAnimation.getRate() + 1.0);
        }
        if (code == KeyCode.DOWN) {
            myAnimation.setRate(myAnimation.getRate() - 1.0);
        }

        // Individual steps
        if (code == KeyCode.RIGHT && !(isRunning)) {
            step();
        }

        // Override load initial config
//        if (code.isDigitKey()) {
//            myAnimation.pause();
//            setupSeed(Integer.parseInt(code.getName()));
//        }
    }
}