import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DataTest {

    private final String TESTFILE = "Test_Config_1.csv";
    private final String EMPTYFILE = "Test_Config_2.csv";
    Data d, e;

    @BeforeEach
    public void setUp(){
        d = new Data(TESTFILE);
        e = new Data(EMPTYFILE);
    }

    @org.junit.jupiter.api.Test
    void getSimulation() {
        assertEquals("Test", d.getSimulation());
    }
    @org.junit.jupiter.api.Test
    void getSimulationWithEmptyFile(){
        assertEquals(null, e.getSimulation());
    }

    @org.junit.jupiter.api.Test
    void getHeight() {
        assertEquals(5, d.getHeight());
    }

    @org.junit.jupiter.api.Test
    void getHeightWithEmptyFile() {
        assertEquals(0, e.getHeight());
    }

    @org.junit.jupiter.api.Test
    void getWidth() {
        assertEquals(5, d.getWidth());
    }

    @org.junit.jupiter.api.Test
    void getWidthWithEmptyFile() {
        assertEquals(0, e.getWidth());
    }

    @org.junit.jupiter.api.Test
    void getStates() {
        int[][] expected = new int[5][5];
        for(int i=0; i<5; i++){
            for (int j=0;j<5;j++){
                if(i==j)expected[i][j] = 1;
                else expected[i][j] = 0;
            }
        }
        assertArrayEquals(expected,d.getStates());
    }

    @org.junit.jupiter.api.Test
    void getStatesWithEmptyFile(){
        int[][] expected = new int[0][0];
        assertArrayEquals(expected, e.getStates());
    }

}