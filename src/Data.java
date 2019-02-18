import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {

    private String simulation;
    private int height, width;
    private List<Integer> states;

    public Data(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(",|\\n");
        simulation = scanner.next();
        height = Integer.parseInt(scanner.next().trim()); //nextInt() throws an error for me because of \r\n Windows set up
        width = Integer.parseInt(scanner.next().trim()); //trim() gets rid of \r character
        states = new ArrayList<>();
        while(scanner.hasNext()) states.add(Integer.parseInt(scanner.next().trim()));
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
    public List<Integer> getStates(){
        return states;
    }

}
