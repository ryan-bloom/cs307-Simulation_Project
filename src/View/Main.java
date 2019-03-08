package View;

import Controller.*;
import Model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.FileChooser;
import javafx.event.*;
import javafx.scene.image.ImageView;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.io.File;
import java.util.List;

public class Main extends Application {

    private static final String SIMULATION = "PredatorPrey1";
    private static final int ACTUAL_WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final int WINDOW_WIDTH = 700;
    //public static final String STYLESHEET = "styles.css";
    private static int FRAMES_PER_SECOND = 6;
    private static double SECOND_DELAY = 3.0 / FRAMES_PER_SECOND;
    private static final Paint BACKGROUND = Color.WHITE;
    private static final String DEFAULT_RESOURCE_PACKAGE = "Resources.";


    //NeighborhoodType
    private static final CellShape CELL_SHAPE = CellShape.SQUARE;
    private static final EdgeType EDGE_TYPE = EdgeType.TOROIDAL;
    private static final NeighborhoodType NEIGHBORHOOD_TYPE = NeighborhoodType.COMPLETE;

    private Grid myGrid;
    private Data mySeed;
    private Group myGroup;
    private Timeline myAnimation;
    private ResourceBundle myResources;
    private Stage myStage;

    private Map<Integer, Color> cellColors = new HashMap<>();
    private Map<Integer, Color> customColors = new HashMap<>();
    private Map<Integer, Image> cellImages = new HashMap<>();
    private double cellWidth;
    private double cellHeight;
    private boolean isRunning = true;
    private boolean useImages = false;
    private boolean useCustomColors = false;

    public static void main (String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        myStage = stage;
        myGroup = new Group();
        myStage.setScene(setupSeed(1));
        myStage.setTitle(myResources.getString("Simulation"));

        var frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step());
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);


        final ToggleGroup cellGroup = new ToggleGroup();
        ToggleButton imageToggle = new ToggleButton("Image");
        imageToggle.setToggleGroup(cellGroup);
        imageToggle.relocate(WINDOW_WIDTH, 500);
        ToggleButton colorToggle = new ToggleButton("Color");
        colorToggle.setSelected(true);
        colorToggle.setToggleGroup(cellGroup);
        colorToggle.relocate(WINDOW_WIDTH + 60, 500);
        myGroup.getChildren().addAll(imageToggle, colorToggle);

        Button toRun = new Button("Run simulation");
        toRun.relocate(700, 200);
        myGroup.getChildren().add(toRun);
        toRun.setOnAction((ActionEvent event) -> {
            if (imageToggle.isSelected()) {
                useImages = true;
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
            myAnimation.play();
        });
        myStage.show();
    }

    public Scene setupSeed(int config) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + SIMULATION);
        //mySeed = new Data(new double[]{0.4,0.55, 0.05}, 5, 5);
        mySeed = new Data(myResources.getString("File"));
        //mySeed = new Data(new int[]{15,9,1}, 5, 5);
        fillColorsList();
        myGrid = new Grid(mySeed);
        cellHeight = WINDOW_HEIGHT/mySeed.getHeight();
        cellWidth = WINDOW_WIDTH/mySeed.getWidth();

        myGrid.fillCellGrid(myResources.getString("Simulation"));
        Scene initial = new Scene(myGroup, ACTUAL_WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);

        for (Integer i : cellColors.keySet()) {
            final ColorPicker colorPicker = new ColorPicker(cellColors.get(i));
            colorPicker.setOnAction((ActionEvent t) -> {
                cellColors.put(i, colorPicker.getValue());
                colorAllCells();
            });
            colorPicker.relocate(700, 40 * i);
            myGroup.getChildren().add(colorPicker);
        }

        colorAllCells();
        initial.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        //seed.getStylesheets().add(STYLESHEET);
        //FileCreator.writeCsvFile("User_Simulation", myGrid);
        //FileCreator.writePropertiesFile("User_Simulation", "User_Simulation",  myResources.getString("Simulation"), cellColors);
        return initial;
    }


    public void colorAllCells() {
        for (int i = 0; i < mySeed.getWidth(); i++) {
            for (int j = 0; j < mySeed.getHeight(); j++) {
                Node view = updateCellView(i, j, myGrid.getCellState(i, j));
                myGroup.getChildren().add(view);
            }
        }
    }
    //SHOULD JUST READ IN COLORS FROM SEPARATE DATA FILE
    public void fillColorsList() {
        for(String s: myResources.keySet()){
            String value = myResources.getString(s);
            if(s.contains("Color")) {
                String[] color = value.split(",");
                cellColors.put(Integer.parseInt(color[0]), Color.valueOf(color[1]));
            }
        }
    }

    public Node updateCellView(int row, int col, int state) {
        if (useImages) {
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
                myGrid.updateGridCell(i, j, CELL_SHAPE, EDGE_TYPE, NEIGHBORHOOD_TYPE);
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
        if (code == KeyCode.P) {
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