package View;

import Controller.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
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
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.FileChooser;
import javafx.event.*;
import javafx.scene.image.ImageView;
import java.util.*;
import java.io.File;

public class Main extends Application {

    private static final int ACTUAL_WINDOW_WIDTH = 1300;
    private static final int WINDOW_HEIGHT = 700;
    private static final int WINDOW_WIDTH = 700;
    private static int FRAMES_PER_SECOND = 6;
    private static double SECOND_DELAY = 3.0 / FRAMES_PER_SECOND;
    private static final Paint BACKGROUND = Color.WHITE;
    private static final String DEFAULT_RESOURCE_PACKAGE = "Resources.";

    //NeighborhoodType
    private static CellShape CELL_SHAPE;
    private static EdgeType EDGE_TYPE;
    private static NeighborhoodType NEIGHBORHOOD_TYPE;

    private Grid myGrid;
    private PolygonGrid myPolygonGrid;
    private Data mySeed;
    private Group myGroup;
    private Timeline myAnimation;
    private ResourceBundle myResources;
    private ResourceBundle styleResources;
    private ResourceBundle errorResources;
    private ResourceBundle simulationResources;
    private ResourceBundle textResources;
    private Stage myStage;
    private ArrayList<XYChart.Series<Number, Number>> mySeries = new ArrayList<>();
    private int startTime = 0;
    private int endTime = 100;
    private int currTime = startTime;

    //GUI Text Strings
    private String User_File;
    private String ImageText;
    private String ColorText;
    private String RunSim;
    private String Time;
    private String NumCells;


    private Map<Integer, Color> cellColors = new HashMap<>();
    private Map<Integer, Image> cellImages = new HashMap<>();
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
        var frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step());
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        setCustomViewControls();
        setRunButton();
        myStage.show();
    }

    private void setCustomViewControls() {
        final ToggleGroup cellGroup = new ToggleGroup();
        ToggleButton imageToggle = new ToggleButton(ImageText);
        imageToggle.setToggleGroup(cellGroup);
        imageToggle.relocate(WINDOW_WIDTH + 150, 0);
        ToggleButton colorToggle = new ToggleButton(ColorText);
        colorToggle.setSelected(true);
        colorToggle.setToggleGroup(cellGroup);
        colorToggle.relocate(WINDOW_WIDTH + 210, 0);
        myGroup.getChildren().addAll(imageToggle, colorToggle);
        imageToggle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> chooseCustomImages());
        for (Integer i : cellColors.keySet()) {
            final ColorPicker colorPicker = new ColorPicker(cellColors.get(i));
            colorPicker.setOnAction((ActionEvent t) -> {
                cellColors.put(i, colorPicker.getValue());
                colorAllCells();
            });
            colorPicker.relocate(700, 30 * i);
            myGroup.getChildren().add(colorPicker);
        }
    }

    private void chooseCustomImages() {
        useImages = true;
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        List<File> imageFiles = fileChooser.showOpenMultipleDialog(myStage);
        if (imageFiles != null) {
            for (int i = 0; i < imageFiles.size(); i++) {
                Image image = new Image(imageFiles.get(i).toURI().toString());
                cellImages.put(i, image);
            }
        }
    }

    private void setRunButton() {
        Button toRun = new Button(RunSim);
        toRun.relocate(WINDOW_WIDTH + 400, 0);
        myGroup.getChildren().add(toRun);

        ChoiceBox neighborType = new ChoiceBox(FXCollections.observableArrayList(NeighborhoodType.CARDINAL.toString(), NeighborhoodType.COMPLETE.toString(), NeighborhoodType.CORNER.toString()));
        neighborType.relocate(WINDOW_WIDTH + 270, 0);
        ChoiceBox edgeType = new ChoiceBox(FXCollections.observableArrayList(EdgeType.FINITE.toString(), EdgeType.SEMITOROIDAL.toString(), EdgeType.TOROIDAL.toString()));
        edgeType.relocate(WINDOW_WIDTH + 270, 40);
        ChoiceBox cellShape = new ChoiceBox(FXCollections.observableArrayList(CellShape.HEXAGON.toString(), CellShape.SQUARE.toString(), CellShape.TRIANGLE.toString()));
        cellShape.relocate(WINDOW_WIDTH + 270, 80);
        myGroup.getChildren().addAll(neighborType, edgeType, cellShape);

        toRun.setOnAction((ActionEvent event) -> {
            for (NeighborhoodType nt : NeighborhoodType.values()) {
                if (neighborType.getValue() != null && neighborType.getValue().equals(nt.toString())) {
                    NEIGHBORHOOD_TYPE = nt;
                }
            }
            for (EdgeType et : EdgeType.values()) {
                if (edgeType.getValue() != null && edgeType.getValue().equals(et.toString())) {
                    EDGE_TYPE = et;
                }
            }
            for (CellShape cs : CellShape.values()) {
                if (cellShape.getValue() != null && cellShape.getValue().equals(cs.toString())) {
//                    if (cs != CELL_SHAPE) {
//                        for (Node n : myGroup.getChildren()) {
//                            Rectangle r = new Rectangle();
//                            Polygon p = new Polygon();
//                            if (n.getClass().equals(r.getClass()) || n.getClass().equals(p.getClass())) {
//                                myGroup.getChildren().remove(n);
//                            }
//                        }
                        CELL_SHAPE = cs;
                    }
                }
            initializePolygonGrid();
            colorAllCells();
            myAnimation.play();
        });
    }

    public Scene setupConfig(int config) {
        try{
            textResources = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, "GUIText"));
            styleResources = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, "Style"));
            myResources = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, styleResources.getString("InitialSimulation")));
            simulationResources = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, "SimulationInfo"));
            possibleStates = Integer.parseInt(simulationResources.getString(myResources.getString("Simulation")));
            errorResources = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, "ErrorMessages"));
            myStage.setTitle(myResources.getString("Simulation"));
        } catch(MissingResourceException e){
            showPopup("Properties file not found");
        }
        initializeGrid();
        initializeShape();
        initializeEdge();
        initializeGUIText();
        initializeNeighbors();
        fillColorsList();
        cellHeight = WINDOW_HEIGHT/mySeed.getHeight();
        cellWidth = WINDOW_WIDTH/mySeed.getWidth();
        Scene initial = new Scene(myGroup, ACTUAL_WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);
        initializePolygonGrid();
        colorAllCells();
        statesGraph();
        initial.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return initial;
    }

    public void initializeGrid(){
        try {
            mySeed = new Data(myResources.getString("File"));
            myGrid = new Grid(mySeed);
            myGrid.fillCellGrid(myResources.getString("Simulation"));
        }catch(MissingResourceException e){
            showPopup(errorResources.getString("MissingProperties"));
        }catch(SimulationException e){
            showPopup(e.getMessage());
        }
    }

    public void initializeGUIText(){
        try{
            User_File = textResources.getString("UserFile");
            ImageText = textResources.getString("Image");
            ColorText = textResources.getString("Color");
            RunSim = textResources.getString("RunSimulation");
            Time = textResources.getString("Time");
            NumCells = textResources.getString("NumCells");
        }catch(MissingResourceException e){
            showPopup(errorResources.getString("MissingProperties"));
        }
    }

    private void initializeShape(){
        try {
            if (styleResources.getString("CellShape").equalsIgnoreCase("SQUARE")) {
                CELL_SHAPE = CellShape.SQUARE;
            } else if (styleResources.getString("CellShape").equalsIgnoreCase("TRIANGLE")) {
                CELL_SHAPE = CellShape.TRIANGLE;
            } else if (styleResources.getString("CellShape").equalsIgnoreCase("HEXAGON")) {
                CELL_SHAPE = CellShape.HEXAGON;
            } else{
                showPopup(errorResources.getString("ShapeError"));
            }
        }catch(MissingResourceException e){
            showPopup(errorResources.getString("MissingResource"));
        }
    }

    private void initializePolygonGrid() {
        if (CELL_SHAPE == CellShape.TRIANGLE || CELL_SHAPE == CellShape.HEXAGON) {
            myPolygonGrid = (CELL_SHAPE == CellShape.TRIANGLE) ? new TriangleGrid(WINDOW_WIDTH, WINDOW_HEIGHT, mySeed.getWidth(),
                    mySeed.getHeight()) : new HexagonGrid(WINDOW_WIDTH, WINDOW_HEIGHT, mySeed.getWidth(), mySeed.getHeight());
        }
    }

    private void initializeEdge(){
        try {
            if (styleResources.getString("EdgeType").equalsIgnoreCase("TOROIDAL")) {
                EDGE_TYPE = EdgeType.TOROIDAL;
            } else if (styleResources.getString("EdgeType").equalsIgnoreCase("SEMITOROIDAL")) {
                EDGE_TYPE = EdgeType.SEMITOROIDAL;
            } else if (styleResources.getString("EdgeType").equalsIgnoreCase("FINITE")) {
                EDGE_TYPE = EdgeType.FINITE;
            } else{
                showPopup(errorResources.getString("EdgeError"));
            }
        }catch(MissingResourceException e){
            showPopup(errorResources.getString("MissingResource"));
        }
    }

    private void initializeNeighbors(){
        try {
            if (styleResources.getString("NeighborType").equalsIgnoreCase("COMPLETE")) {
                NEIGHBORHOOD_TYPE = NeighborhoodType.COMPLETE;
            } else if (styleResources.getString("NeighborType").equalsIgnoreCase("CARDINAL")) {
                NEIGHBORHOOD_TYPE = NeighborhoodType.CARDINAL;
            } else if (styleResources.getString("NeighborType").equalsIgnoreCase("CORNER")) {
                NEIGHBORHOOD_TYPE = NeighborhoodType.CORNER;
            } else{
                showPopup(errorResources.getString("NeighborError"));
            }
        } catch(MissingResourceException e){
            showPopup(errorResources.getString("MissingResource"));
        }
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
            if (s.contains(ColorText)) {
                String[] color = value.split(",");
                try{
                    cellColors.put(Integer.parseInt(color[0]), Color.valueOf(color[1]));
                }catch(IllegalArgumentException e){
                    showPopup(errorResources.getString("ColorHex"));
                }
            }
        }
        try{
            if(cellColors.size()<Integer.parseInt(simulationResources.getString(myResources.getString("Simulation")))){
                showPopup(errorResources.getString("ColorConfiguration"));
            }
        }catch(MissingResourceException e){
            showPopup(errorResources.getString("MissingProperties"));
        }
    }

    public void updateCellView(int row, int col, int state) {
        Node nodeToAdd = newCellNode(row, col, state);
        int newState = (state == possibleStates - 1) ? 0 : state + 1;
        nodeToAdd.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> interactiveStateChange(row, col, newState));//System.out.println("Original state: " + state + " New state: " + newState));//updateCellView(row, col, newState));
        myGroup.getChildren().add(nodeToAdd);
    }

    private void interactiveStateChange(int row, int col, int state) {
        updateCellView(row, col, state);
        myGrid.updateCellState(row, col, state);
    }
    private Node newCellNode(int row, int col, int state) {
        Shape cellShapeView;
        if (CELL_SHAPE == CellShape.HEXAGON || CELL_SHAPE == CellShape.TRIANGLE) {
            cellShapeView = new Polygon();//myPolygonGrid.getCoordinates(row, col));
            ((Polygon) cellShapeView).getPoints().addAll(myPolygonGrid.getCoordinates(row, col));
        }
        else {
            cellShapeView = new Rectangle(row * cellWidth, col * cellHeight, cellWidth, cellHeight);
        }
        if (useImages) {
            ImageView cellImageView = new ImageView(cellImages.get(state));
            cellImageView.setClip(cellShapeView);
            return cellImageView;
        } else {
            var color = cellColors.get(state);
            cellShapeView.setFill(color);
        }
        return cellShapeView;
    }

    private void step() {
        stateCounts = new int[cellColors.size()];
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
        xAxis.setLabel(Time);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(NumCells);
        LineChart graph = new LineChart(xAxis, yAxis);
        String graphStyle = "";

        for (int i = 0; i < possibleStates; i++) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("State " + i);
            mySeries.add(series);
            String hexValue = String.format( "#%02X%02X%02X",
                    (int)( cellColors.get(i).getRed() * 255),
                    (int)( cellColors.get(i).getGreen() * 255),
                    (int)( cellColors.get(i).getBlue() * 255) );
            graphStyle = String.format("%s%s%s%s%s%s", graphStyle, "CHART_COLOR_", (i+1), ":", hexValue, ";");
        }
        graph.setStyle(graphStyle.substring(0, graphStyle.length() - 1));
        graph.getData().addAll(mySeries);
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
            myAnimation.setRate(myAnimation.getRate() + 1.0);
        }
        if (code == KeyCode.DOWN) {
            myAnimation.setRate(myAnimation.getRate() - 1.0);
        }

        // Individual steps
        if (code == KeyCode.RIGHT) {
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
        if(code == KeyCode.S){
            try{
                FileCreator.writeCsvFile(User_File, myGrid);
                FileCreator.writePropertiesFile(User_File, User_File,  myResources.getString("Simulation"), cellColors);
            }catch(SimulationException e){
                showPopup(e.getMessage());
            }
        }
        if(code == KeyCode.L){
            myAnimation.stop();
            try {
                myResources = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, "User_Simulation2"));
            }catch (MissingResourceException e){
                showPopup(errorResources.getString("LoadingError"));
            }
            initializeGrid();
            myAnimation.play();
        }
    }

    public void showPopup(String message) {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorResources.getString("ErrorTitle"));
        alert.setContentText(message);
        alert.showAndWait();
    }
}