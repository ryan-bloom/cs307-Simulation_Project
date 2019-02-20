
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Main{
    public static void main (String[] args) throws FileNotFoundException, URISyntaxException {
        Data test  = new Data("GameOfLife_Config_1.txt");
        System.out.println(test.getSimulation());
        System.out.println(test.getHeight() + "  " + test.getWidth());
        for(int i = 0; i<test.getHeight();i++){
            for(int j = 0; j<test.getWidth(); j++){
                System.out.print(test.getStates()[i][j] + " ");
            }
            System.out.println();
        }
    }
}