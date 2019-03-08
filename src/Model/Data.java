package Model;

import Controller.SimulationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
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
            } catch (NumberFormatException e){
                System.out.println("CSV Configuration File is not in correct format");
                System.exit(0);
            } catch (NoSuchElementException e){
                System.out.println("CSV Configuration File is not in correct format");
                System.exit(0);
            }
        } catch (FileNotFoundException e) {
            System.out.println("CSV Configuration File name not found");
            System.exit(0);
        } catch (URISyntaxException e) {
            System.out.println("CSV Configuration File could not be scanned");
            System.exit(0);
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

    public Data(int limits[], int height, int width){
        this.height = height;
        this.width = width;
        //test if less entries in limits than in grid
        if(getSum(limits)<height*width){
            throw new SimulationException("Invalid number of cells in grid in configuration");
        }
        states = new int[height][width];
        for (int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                int state = randomState(limits);
                states[j][i] = state;
                limits[state]--;
            }
        }
    }

    private int getSum(int limits[]){
        int sum = 0;
        for(int i: limits){
            sum+=i;
        }
        return sum;
    }

    private int randomState(int limits[]){
        int state = (int)(Math.random()*limits.length);
        if(limits[state]>0) return state;
        else return randomState(limits);
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
