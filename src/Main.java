import Cells.Cell;
import Cells.GameOfLifeCell;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;

public class Main extends Application {
    public static final String SIMULATION_FILE = "GameOfLife_Config_2.csv";
    public static final int WINDOW_HEIGHT = 700;
    public static final int WINDOW_WIDTH = 700;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.WHITE;


    private Cell[][] cellGrid;
    private Rectangle[][] displayGrid;
    private Group myGroup;
    private Animation myAnimation;

    public static void main (String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        File filename = new File(SIMULATION_FILE);
        try {
            Data d = new Data(filename);
            cellGrid = new Cell[d.getHeight()][d.getWidth()];
            displayGrid = new Rectangle[d.getHeight()][d.getWidth()];
            myGroup = new Group();
            var scene = new Scene(myGroup, WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);
            double cellHeight = WINDOW_HEIGHT/d.getHeight();
            double cellWidth = WINDOW_WIDTH/d.getWidth();
            for (int i = 0; i < d.getWidth(); i++) {
                for (int j = 0; j < d.getHeight(); j++) {
                    cellGrid[i][j] = new GameOfLifeCell(i, j, d.getStates()[i][j]);
                    displayGrid[i][j] = new Rectangle(i * cellWidth, j * cellHeight, cellWidth, cellHeight);
                    //displayGrid[i][j].setFill(); MUST SET COLOR OF CELL HERE BASED ON CELL'S STATE
                    myGroup.getChildren().add(displayGrid[i][j]);
                }
            }
            stage.setScene(scene);
            stage.setTitle(d.getSimulation());
            stage.show();

        }
        catch (FileNotFoundException e) {
            System.out.println("Invalid file!");
        }
        var frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        myAnimation = animation;
        animation.play();

    }

    private void step(double elapsedTime) {
        for (int i = 0; i < cellGrid.length; i++) {
            for (int j = 0; j < cellGrid[0].length; j++) {

            }
        }
    }
}