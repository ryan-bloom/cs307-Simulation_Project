import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Data {

    private String simulation;
    private int height, width;
    private int[][] states;

    public Data(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(",|\\n");
        simulation = scanner.next();
        height = Integer.parseInt(scanner.next().trim()); //nextInt() throws an error for me because of \r\n Windows set up
        width = Integer.parseInt(scanner.next().trim()); //trim() gets rid of \r character
        states = new int[height][width];
        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                if(scanner.hasNext()) states[i][j] = Integer.parseInt(scanner.next().trim());
            }
        }
    }

    public String getSimulation(){
        return simulation;
    }
    public int getHeight(){
        return height;
    }
    public int getWidth() {
        return width;
    }
    public int[][] getStates(){
        return states;
    }

}
