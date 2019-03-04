package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    private final String TEST_FILE = "DataTest_Config_1.csv";
    private final String EMPTY_FILE = "DataTest_Config_2.csv";
    private Data d,e;

    @BeforeEach
    void setUp(){
        d = new Data(TEST_FILE);
        e = new Data(EMPTY_FILE);
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
            arr[i][i] = 1;
        }
        assertArrayEquals(arr, d.getStates());
    }

    @Test
    void getStatesWithEmptyFile(){
        int[][] arr = new int[0][0];
        assertArrayEquals(arr, e.getStates());
    }
}