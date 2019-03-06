package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.security.InvalidParameterException;
import java.util.Scanner;

public class Data {

    private int height, width;
    private int[][] states;

    public Data(String fileName) {
        try (Scanner scanner = new Scanner(new File(this.getClass().getClassLoader().getResource(fileName).toURI()))){
            scanner.useDelimiter(",|\\n");
            try{
                height = Integer.parseInt(scanner.next().trim());
                width = Integer.parseInt(scanner.next().trim());
                states = new int[height][width];
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        states[j][i] = Integer.parseInt(scanner.next().trim());
                    }
                }
            }catch (NumberFormatException e){
                throw new InvalidParameterException("CSV Configuration File is not in correct format");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Data (double prob[], int height, int width){
        this.height = height;
        this.width = width;
        states = new int[height][width];
        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                double rand = Math.random();
                for(int k = 0; k<prob.length; k++){
                    if(rand<prob[k] & rand>=0) {
                        states[j][i] = k;
                    }
                    rand -= prob[k];
                }
            }
        }
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
