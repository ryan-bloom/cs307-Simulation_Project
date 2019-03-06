package View;

import Controller.CellShape;
import Controller.EdgeType;
import Controller.Grid;
import Model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.FileChooser;
import javafx.event.*;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.io.File;
import java.util.List;

public class Main extends Application {

    public static final String SIMULATION = "Percolation";
    public static final int ACTUAL_WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 700;
    public static final int WINDOW_WIDTH = 700;
    //public static final String STYLESHEET = "styles.css";

    public static int FRAMES_PER_SECOND = 6;
    public static double SECOND_DELAY = 3.0 / FRAMES_PER_SECOND;

    public static final Paint BACKGROUND = Color.WHITE;
    public static final String DEFAULT_RESOURCE_PACKAGE = "Resources.";
    public static final String DATA_EXTENSION = "data\\";

    private static final CellShape CELL_SHAPE = CellShape.SQUARE;
    private static final EdgeType EDGE_TYPE = EdgeType.FINITE;

    private Grid myGrid;
    private Data mySeed;
    private Group myGroup;
    private Timeline myAnimation;
    private ResourceBundle myResources;
    private Stage myStage;

    private HashMap<Integer, Color> cellColors = new HashMap<>();
    private HashMap<Integer, Image> cellImages = new HashMap<>();
    private double cellWidth;
    private double cellHeight;
    private boolean isRunning = true;

    public static void main (String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        myStage = stage;
        myGroup = new Group();
        myStage.setScene(setupSeed(1));
        myStage.setTitle(myResources.getString("Simulation"));
        RadioButton cellChoiceImage = new RadioButton("Image");
        cellChoiceImage.relocate(700, 500);
        myGroup.getChildren().add(cellChoiceImage);
        Button toRun = new Button("Run simulation");
        toRun.setOnAction((ActionEvent event) -> {
            if (cellChoiceImage.isSelected()) {
                final FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("All Images", "*.*"),
                        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                        new FileChooser.ExtensionFilter("PNG", "*.png")
                );
                List<File> imageFiles = fileChooser.showOpenMultipleDialog(stage);
                if (imageFiles != null) {
                    for (int i = 0; i < imageFiles.size(); i++) {
                        Image image = new Image(imageFiles.get(i).toURI().toString());
                        cellImages.put(i, image);
                    }
                }
            }
            var frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step());
            myAnimation = new Timeline();
            myAnimation.setCycleCount(Timeline.INDEFINITE);
            myAnimation.getKeyFrames().add(frame);
            myAnimation.play();
        });
        toRun.relocate(700, 200);
        myGroup.getChildren().add(toRun);
        myStage.show();

    }

    public Scene setupSeed(int config) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Matt" + SIMULATION);
        mySeed = new Data(myResources.getString("File").split(",")[config - 1]);
        fillColorsList();
        myGrid = new Grid(mySeed);
        cellHeight = WINDOW_HEIGHT/mySeed.getHeight();
        cellWidth = WINDOW_WIDTH/mySeed.getWidth();
        myGrid.fillCellGrid();
        Scene seed = new Scene(myGroup, ACTUAL_WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);

        for (Integer i : cellColors.keySet()) {
            final ColorPicker colorPicker = new ColorPicker();
            colorPicker.setOnAction((ActionEvent t) -> {
                cellColors.put(i, colorPicker.getValue());
            });
            colorPicker.relocate(700, 40 * i);
            myGroup.getChildren().add(colorPicker);
        }

        for (int i = 0; i < mySeed.getWidth(); i++) {
            for (int j = 0; j < mySeed.getHeight(); j++) {
                Node view = updateCellView(i, j, myGrid.getCellState(i, j));//cellGrid[i][j].getMyCurrentState());
                myGroup.getChildren().add(view);
            }
        }
        seed.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        //seed.getStylesheets().add(STYLESHEET);
        return seed;
    }

    //SHOULD JUST READ IN COLORS FROM SEPARATE DATA FILE
    public void fillColorsList(){
        for(String s: myResources.keySet()){
            String value = myResources.getString(s);
            if(s.contains("Color")) {
                String[] color = value.split(",");
                cellColors.put(Integer.parseInt(color[0]), Color.valueOf(color[1]));
            }
        }
    }

    public Node updateCellView(int row, int col, int state) {
        if (cellImages.get(state) != null) {
            var res = new ImageView(cellImages.get(state));
            res.setFitHeight(cellHeight);
            res.setFitWidth(cellWidth);
            res.setX(row * cellWidth);
            res.setY(col * cellHeight);
            return res;
        }
        else {
            var res = new Rectangle(row * cellWidth, col * cellHeight, cellWidth, cellHeight);
            var color = cellColors.get(state);
            res.setFill(color);
            return res;
        }
    }

    private void step() {
        // updates colors and states of all cells
        for (int i = 0; i < myGrid.getMyRows(); i++) {
            for (int j = 0; j < myGrid.getMyCols(); j++) {
                //myGrid.updateGridCell(i, j);
                myGrid.updateGridCell(i, j, CELL_SHAPE, EDGE_TYPE);
                //myGroup.getChildren().add(updateCellView(i, j, myGrid.getCellState(i,j)));
            }
        }
        // resets state of all cells so next update will function correctly
        for (int i = 0; i < myGrid.getMyRows(); i++) {
            for (int j = 0; j < myGrid.getMyCols(); j++) {
                //myGroup.getChildren().add(updateCellView(i, j, myGrid.getCellState(i,j)));
                myGrid.resetCell(i, j);
                myGroup.getChildren().add(updateCellView(i, j, myGrid.getCellState(i,j)));
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
        if (code.isDigitKey()) {
            myAnimation.stop();
            myGroup.getChildren().clear();
            myStage.setScene(setupSeed(Integer.parseInt(code.getName())));
            myAnimation.play();
        }
    }
}