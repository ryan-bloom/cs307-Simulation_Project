package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RPSCellTest {
    private Cell rockCell;
    private Cell paperCell;
    private Cell scissorCell;
    private List<Cell> neighbors;


    @BeforeEach
    void setUp() {
        rockCell = new RPSCell(1, 1, 0);
        paperCell = new RPSCell(1, 1, 1);
        scissorCell = new RPSCell(1, 1, 2);
        neighbors = new ArrayList<>();
    }

    @Test
    void rockToPaper() {
        for(int i=0; i<5; i++){
            neighbors.add(paperCell);
        }
        neighbors.add(rockCell);
        neighbors.add(scissorCell);
        rockCell.updateCell(neighbors);

        var expected = 1;
        var actual = rockCell.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void paperToScissor() {
        for(int i=0; i<4; i++){
            neighbors.add(scissorCell);
        }
        for(int i=0; i<3; i++){
            neighbors.add(paperCell);
        }
        neighbors.add(rockCell);
        paperCell.updateCell(neighbors);

        var expected = 2;
        var actual = paperCell.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void scissorToRockTie(){
        for(int i=0; i<4; i++){
            //Order matters here
            neighbors.add(rockCell);
            neighbors.add(paperCell);
        }
        scissorCell.updateCell(neighbors);

        var expected =0;
        var actual = scissorCell.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void scissorToPaperTie(){
        for(int i=0; i<4; i++){
            //Order matters here
            neighbors.add(paperCell);
            neighbors.add(rockCell);
        }
        scissorCell.updateCell(neighbors);

        var expected = 1;
        var actual = scissorCell.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void noStateChange(){
        //No number of neighbors reaches threshold
        for(int i=0; i<2; i++){
            neighbors.add(rockCell);
            neighbors.add(paperCell);
            neighbors.add(scissorCell);
        }
        rockCell.updateCell(neighbors);

        var expected = 0;
        var actual = rockCell.myNextState;
        assertEquals(expected, actual);
    }

/*    @Test
    void redToGreen(){
        for(int i=0; i<5; i++){
            neighbors.add(paperCell);
        }
        var expected1 = Color.RED;
        var actual1 = rockCell.myRectangle.getFill();
        assertEquals(expected1, actual1);

        rockCell.updateCell(neighbors);

        var expected2 = Color.GREEN;
        var actual2 = rockCell.myRectangle.getFill();
        assertEquals(expected2, actual2);
    }

    @Test
    void blueToRed(){
        for(int i=0; i<4; i++){
            neighbors.add(rockCell);
        }
        var expected1 = Color.BLUE;
        var actual1 = scissorCell.myRectangle.getFill();
        assertEquals(expected1, actual1);

        scissorCell.updateCell(neighbors);

        var expected2 = Color.RED;
        var actual2 = scissorCell.myRectangle.getFill();
        assertEquals(expected2, actual2);
    }*/
}