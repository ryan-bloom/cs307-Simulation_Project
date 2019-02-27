package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    private final String TEST_FILE = "Test_Configuration_1.csv";
    private final String EMPTY_FILE = "Test_Configuration_2.csv";
    private Data d,e;

    @BeforeEach
    void setUp(){
        d = new Data(TEST_FILE);
        e = new Data(EMPTY_FILE);
    }

    @Test
    void getSimulation() {
        assertEquals("Test", d.getSimulation());
    }

    @Test
    void getSimulationWithEmptyFile() {
        assertEquals(null, e.getSimulation());
    }

    @Test
    void getHeight() {
        assertEquals(5, d.getHeight());
    }

    @Test
    void getHeightWithEmptyFile(){
        assertEquals(0, e.getHeight());
    }

    @Test
    void getWidth() {
        assertEquals(5, d.getWidth());
    }

    @Test
    void getWidthWithEmptyFile(){
        assertEquals(0, e.getWidth());
    }

    @Test
    void getStates() {
        int[][] arr = new int[5][5];
        for(int i = 0; i<5; i++){
            for(int j = 0; j<5; j++){
                if(i==j) arr[j][i] = 1;
                else arr[j][i] = 0;
            }
        }
        assertArrayEquals(arr, d.getStates());
    }

    @Test
    void getStatesWithEmptyFile(){
        int[][] arr = new int[0][0];
        assertArrayEquals(arr, e.getStates());
    }
}