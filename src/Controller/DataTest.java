package Controller;

import Controller.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    private final String TEST_FILE = "DataTest_Config_1.csv";
    private Data d, f;

    @BeforeEach
    void setUp(){
        d = new Data(TEST_FILE);
        f = new Data(new int[]{10,5,10}, 5,5);
    }

    @Test
    void getHeight() {
        assertEquals(5, d.getHeight());
    }

    @Test
    void getWidth() {
        assertEquals(5, d.getWidth());
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
    void getRandomStates(){
        int[][] arr = f.getStates();
        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        for(int i=0; i<f.getHeight(); i++){
            for(int j =0; j<f.getWidth(); j++){
                if(f.getStates()[i][j]==0) sum1++;
                else if(f.getStates()[i][j]==1) sum2++;
                else if(f.getStates()[i][j]==2) sum3++;
            }
        }
        assertEquals(10, sum1);
        assertEquals(5, sum2);
        assertEquals(10, sum3);
    }
}