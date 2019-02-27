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
import java.util.*;

public class Main extends Application {
    
    public static final String SIMULATION = "Percolation";
    public static final int WINDOW_HEIGHT = 700;
    public static final int WINDOW_WIDTH = 700;

    public static int FRAMES_PER_SECOND = 6;
    public static double SECOND_DELAY = 3.0 / FRAMES_PER_SECOND;

    public static final Paint BACKGROUND = Color.WHITE;
    public static final String DEFAULT_RESOURCE_PACKAGE = "Resources.";

    //private Cell[][] cellGrid;
    private Grid myGrid;
    private Group myGroup;
    private Animation myAnimation;
    private ResourceBundle myResources;

    //private Map<String, List<Color>> colorsMap; ---- MIGHT USE THIS IF MAP OF GAME - COLORS FOR SAID GAME
    private HashMap<Integer, Color> colors = new HashMap<>();
    private double cellWidth;
    private double cellHeight;
    private boolean isRunning = true;

    public static void main (String[] args) {
        launch(args);
    }

    public void start(Stage stage) {

        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Matt" + SIMULATION);
        Data d = new Data(myResources.getString("File"));
        fillColorsList();
        myGrid = new Grid(d);
        cellHeight = WINDOW_HEIGHT/d.getHeight();
        cellWidth = WINDOW_WIDTH/d.getWidth();
        myGrid.fillCellGrid();
        myGroup = new Group();
        var scene = new Scene(myGroup, WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);

        for (int i = 0; i < d.getWidth(); i++) {
            for (int j = 0; j < d.getHeight(); j++) {
                Node view = updateCellView(i, j, myGrid.getCellState(i, j));//cellGrid[i][j].getMyCurrentState());
                myGroup.getChildren().add(view);
            }
        }
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        stage.setScene(scene);
        stage.setTitle(d.getSimulation());
        stage.show();
        var frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step());
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        myAnimation = animation;
        animation.play();
    }

    //SHOULD JUST READ IN COLORS FROM SEPARATE DATA FILE
    public void fillColorsList(){
        for(String s: myResources.keySet()){
            String value = myResources.getString(s);
            if(s.contains("Color")) {
                String[] color = value.split(",");
                colors.put(Integer.parseInt(color[0]), Color.valueOf(color[1]));
            }
        }
    }

//GOING TO HAVE TO HANDLE RECTANGLES AND IMAGE VIEWS EVENTUALLY
    public Node updateCellView(int row, int col, int state){
        var res = new Rectangle(row * cellWidth, col * cellHeight, cellWidth, cellHeight);
        var color = colors.get(state);
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
        //Pause and Resume
        if (code == KeyCode.SPACE) {
            if (isRunning) {
                myAnimation.pause();
                isRunning = false;
            }
            else {
                myAnimation.play();
                isRunning = true;
            }
        }

        //Increase or decrease animation rate
        if (code == KeyCode.UP) {
            myAnimation.pause();
            System.out.println(myAnimation.getRate());
            myAnimation.setRate(myAnimation.getRate() + 1.0);
            System.out.println(myAnimation.getRate());
            System.out.println("Pressing up!");
            myAnimation.play();
        }
        if (code == KeyCode.DOWN) {
            myAnimation.pause();
            System.out.println(myAnimation.getRate());
            myAnimation.setRate(myAnimation.getRate() - 1.0);
            System.out.println(myAnimation.getRate());
            System.out.println("Pressing up!");
            myAnimation.play();
        }

        //Step through simulation
        if (code == KeyCode.RIGHT && !(isRunning)) {
            step();
        }

        //Load various initial configs
        if (code == KeyCode.DIGIT1) {
            
        }

        if (code == KeyCode.DIGIT2) {

        }
        if (code == KeyCode.DIGIT3) {

        }

        if (code == KeyCode.DIGIT4) {

        }
        if (code == KeyCode.DIGIT5) {

        }

        if (code == KeyCode.DIGIT6) {

        }
    }
}