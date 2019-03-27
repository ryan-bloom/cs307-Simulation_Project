package Controller;

import Controller.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    private final String TEST_FILE = "DataTest_Config_1.csv";
    private final String EMPTY_FILE = "DataTest_Config_2.csv";
    private final String LETTER_FILE = "DataTest_Config_3.csv";
    private Data d, f;

    @BeforeEach
    void setUp(){
        try{
            d = new Data(TEST_FILE);
        }catch(Throwable e){
            Assertions.fail();
        }
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
    void getStatesAt() {
        int[][] arr = new int[5][5];
        for(int i = 0; i<5; i++){
            arr[i][i] = 1;
        }
        assertEquals(1, d.getStateAt(1,1));
    }
    @Test
    void getRandomStates(){
        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        for(int i=0; i<f.getHeight(); i++){
            for(int j =0; j<f.getWidth(); j++){
                if(f.getStateAt(i,j)==0) sum1++;
                else if(f.getStateAt(i, j)==1) sum2++;
                else if(f.getStateAt(i, j)==2) sum3++;
            }
        }
        assertEquals(10, sum1);
        assertEquals(5, sum2);
        assertEquals(10, sum3);
    }

    @Test
    void checkInvalidNumberCellsException(){
        Assertions.assertThrows(SimulationException.class, () -> new Data(new int[]{8, 8, 8}, 5, 5));
    }

    @Test
    void checkFileNotFoundException(){
        Assertions.assertThrows(SimulationException.class, () -> new Data(""));
    }

    @Test
    void checkNullException(){
        Assertions.assertThrows(SimulationException.class, () -> new Data(null));
    }

    @Test
    void checkEmptyException(){
        Assertions.assertThrows(SimulationException.class, () -> new Data(EMPTY_FILE));
    }

    @Test
    void checkFormatException(){
        Assertions.assertThrows(SimulationException.class, () -> new Data(LETTER_FILE));
    }

    @Test
    void checkProbabilityException(){
        Assertions.assertThrows(SimulationException.class, () -> new Data(new double[]{0.1}, 5, 5));
    }
}