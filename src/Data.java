import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Data {

    private String simulation;
    private int height, width;
    private int[][] states;

    public Data(String fileName) {
        Scanner scanner = initializeScanner(fileName);
        if(scanner.hasNext())simulation = scanner.next().trim();
        if(scanner.hasNext())height = Integer.parseInt(scanner.next().trim()); //nextInt() throws an error for me because of \r\n Windows set up
        if(scanner.hasNext())width = Integer.parseInt(scanner.next().trim()); //trim() gets rid of \r character
        states = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (scanner.hasNext()) states[j][i] = Integer.parseInt(scanner.next().trim());
            }
        }
    }

    public Scanner initializeScanner(String fileName) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(this.getClass().getClassLoader().getResource(fileName).toURI()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        scanner.useDelimiter(",|\\n");
        return scanner;
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
