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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.FileChooser;
import javafx.event.*;
import javafx.scene.image.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.io.File;
import java.util.List;

public class Main extends Application {

    public static final String SIMULATION = "Percolation";
    public static final int ACTUAL_WINDOW_WIDTH = 1200;
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
    private PolygonGrid myPolygonGrid;
    private Data mySeed;
    private Group myGroup;
    private Timeline myAnimation;
    private ResourceBundle myResources;
    private Stage myStage;
    private ArrayList<XYChart.Series<Number, Number>> mySeries = new ArrayList<>();
    private int startTime = 0;
    private int endTime = 100;
    private int currTime = startTime;



    private HashMap<Integer, Color> cellColors = new HashMap<>();
    private HashMap<Integer, Image> cellImages = new HashMap<>();
    private double cellWidth;
    private double cellHeight;
    private boolean isRunning = true;
    private boolean useImages = false;
    private int[] stateCounts;
    private int possibleStates;


    public static void main (String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        myStage = stage;
        myGroup = new Group();
        myStage.setScene(setupConfig(1));
        myStage.setTitle(myResources.getString("Simulation"));

        var frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step());
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);

        final ToggleGroup cellGroup = new ToggleGroup();
//        cellViewToggles("Color", cellGroup, 0, 500).setSelected(true);
//        cellViewToggles("Image", cellGroup, 50, 500);
        ToggleButton imageToggle = new ToggleButton("Image");
        imageToggle.setToggleGroup(cellGroup);
        imageToggle.relocate(WINDOW_WIDTH + 150, 0);
        ToggleButton colorToggle = new ToggleButton("Color");
        colorToggle.setSelected(true);
        colorToggle.setToggleGroup(cellGroup);
        colorToggle.relocate(WINDOW_WIDTH + 210, 0);
        myGroup.getChildren().addAll(imageToggle, colorToggle);

        Button toRun = new Button("Run simulation");
        toRun.relocate(WINDOW_WIDTH + 270, 0);
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


    public Scene setupConfig(int config) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Matt" + SIMULATION);
        mySeed = new Data(myResources.getString("File").split(",")[config - 1]);
        System.out.println(myResources.getString("File").split(",")[config - 1]);
        possibleStates = mySeed.getNumStates();
        fillColorsList();
        myGrid = new Grid(mySeed);
        cellHeight = WINDOW_HEIGHT/mySeed.getHeight();
        cellWidth = WINDOW_WIDTH/mySeed.getWidth();
        myGrid.fillCellGrid();
        Scene initial = new Scene(myGroup, ACTUAL_WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);

        for (Integer i : cellColors.keySet()) {
            final ColorPicker colorPicker = new ColorPicker(cellColors.get(i));
            colorPicker.setOnAction((ActionEvent t) -> {
                cellColors.put(i, colorPicker.getValue());
                colorAllCells();
            });
            colorPicker.relocate(700, 30 * i);
            myGroup.getChildren().add(colorPicker);
        }
        //myPolygonGrid = new TriangleGrid(WINDOW_WIDTH, WINDOW_HEIGHT, mySeed.getWidth(), mySeed.getHeight());
        myPolygonGrid = new HexagonGrid(WINDOW_WIDTH, WINDOW_HEIGHT, mySeed.getWidth(), mySeed.getHeight());
        colorAllCells();
        statesGraph();
        initial.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        //seed.getStylesheets().add(STYLESHEET);
        return initial;
    }

    public void colorAllCells() {
        for (int i = 0; i < mySeed.getWidth(); i++) {
            for (int j = 0; j < mySeed.getHeight(); j++) {
                updateCellView(i, j, myGrid.getCellState(i, j));
            }
        }
    }

    public void fillColorsList() {
        for(String s: myResources.keySet()){
            String value = myResources.getString(s);
            if (s.contains("Color")) {
                String[] color = value.split(",");
                cellColors.put(Integer.parseInt(color[0]), Color.valueOf(color[1]));
            }
        }
    }

    public void updateCellView(int row, int col, int state) {
        Node nodeToAdd = newCellNode(row, col, state);
        nodeToAdd.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> updateCellView(row, col, myGrid.incrementCellState(row, col)));
        myGroup.getChildren().add(nodeToAdd);
    }

    private Node newCellNode(int row, int col, int state) {
        Node cellView;
        if (useImages) {
            cellView = new ImageView(cellImages.get(state));
            ((ImageView) cellView).setFitHeight(cellHeight);
            ((ImageView) cellView).setFitHeight(cellHeight);
            ((ImageView) cellView).setFitWidth(cellWidth);
            ((ImageView) cellView).setX(row * cellWidth);
            ((ImageView) cellView).setY(col * cellHeight);
        } else {
            //cellView = new Rectangle(row * cellWidth, col * cellHeight, cellWidth, cellHeight);
            cellView = new Polygon();
            ((Polygon) cellView).getPoints().addAll(myPolygonGrid.getCoordinates(row, col));
            for (Double d : myPolygonGrid.getCoordinates(row, col)) {
                System.out.print(d + ",");
            }
            var color = cellColors.get(state);
            System.out.println("row: " + row + "col: " + col);
            System.out.println(state);
            ((Polygon) cellView).setFill(color);
        }
        return cellView;
    }

    private void step() {
        stateCounts = new int[cellColors.size()];
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
                updateCellView(i, j, myGrid.getCellState(i, j));
                stateCounts[myGrid.getCellState(i, j)]++;
            }
        }
        for (int i = 0; i < possibleStates; i++) {
            mySeries.get(i).getData().add(new XYChart.Data<>(currTime, stateCounts[i]));
        }
        currTime++;
        if (currTime == endTime) {
            startTime += 100;
            endTime += 100;
        }
    }

    private void statesGraph() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Time");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Cells by State");
        LineChart graph = new LineChart(xAxis, yAxis);

        for (int i = 0; i <= possibleStates; i++) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("State " + (i + 1));
            mySeries.add(series);
            String hexValue = String.format( "#%02X%02X%02X",
                    (int)( cellColors.get(0).getRed() * 255),
                    (int)( cellColors.get(0).getGreen() * 255),
                    (int)( cellColors.get(0).getBlue() * 255) );
        }
        graph.getData().addAll(mySeries);

        String hexValue = String.format( "#%02X%02X%02X",
                (int)( cellColors.get(0).getRed() * 255),
                (int)( cellColors.get(0).getGreen() * 255),
                (int)( cellColors.get(0).getBlue() * 255) );
        graph.setStyle("COLOR1:" + hexValue);
        graph.relocate(WINDOW_WIDTH, 300);
        myGroup.getChildren().add(graph);

    }


    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.P) {
            if (isRunning) {
                myAnimation.pause();
                isRunning = false;
            }
            else {
                myAnimation.play();
                isRunning = true;
            }
        }

        // Increase & decrease simulation speed
        if (code == KeyCode.UP) {
            System.out.println("UP");
            myAnimation.setRate(myAnimation.getRate() + 1.0);
        }
        if (code == KeyCode.DOWN) {
            System.out.println("DOWN");
            myAnimation.setRate(myAnimation.getRate() - 1.0);
        }

        // Individual steps
        if (code == KeyCode.RIGHT) {
            System.out.println("RIGHT ARROW KEY");
            if (!isRunning) {
                step();
            }
        }

        // Override load different initial config
        if (code.isDigitKey()) {
            myAnimation.stop();
            myGroup.getChildren().clear();
            myStage.setScene(setupConfig(Integer.parseInt(code.getName())));
            myAnimation.play();
        }
    }
}