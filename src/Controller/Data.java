package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Data {

    private int height, width;
    private int[][] states;
    private ResourceBundle myResources = ResourceBundle.getBundle("Resources.ErrorMessages");

    /**
     * Constructs a Data object with height, width, and states based on the name of a CSV file
     * @param fileName
     */

    public Data(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(this.getClass().getClassLoader().getResource(fileName).toURI()));
            scanner.useDelimiter(",|\\n");
            height = Integer.parseInt(scanner.next().trim());
            width = Integer.parseInt(scanner.next().trim());
            states = new int[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int state = Integer.parseInt(scanner.next().trim());
                    states[j][i] = state;
                }
            }
        } catch (NumberFormatException e){
            throw new SimulationException(myResources.getString("CSVParse"));
        } catch (NoSuchElementException e){
            throw new SimulationException(myResources.getString("CSVStates"));
        } catch (FileNotFoundException e) {
            throw new SimulationException(myResources.getString("NoFile"));
        } catch (URISyntaxException e) {
            throw new SimulationException(myResources.getString("NotReadable"));
        } catch (NullPointerException e){
            throw new SimulationException(myResources.getString("NullPoint"));
        }
    }

    /**
     * Constructs a Data object based on an array of probabilities for each state, and the height and width of the array
     * @param prob
     * @param height
     * @param width
     */

    public Data (double prob[], int height, int width){
        this.height = height;
        this.width = width;
        if(getDoubleSum(prob)!=1){
            throw new SimulationException(myResources.getString("ProbabilityError"));
        }
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

    /**
     * Constructs a Data object based on certain numbers of cells for each state, and the height and width of the array
     * @param limits
     * @param height
     * @param width
     */

    public Data(int limits[], int height, int width) throws SimulationException{
        this.height = height;
        this.width = width;
        //test if less entries in limits than in grid
        if(getIntSum(limits)<height*width){
            throw new SimulationException(myResources.getString("InvalidCells"));
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

    private int getIntSum(int limits[]){
        int sum = 0;
        for(int i: limits){
            sum+=i;
        }
        return sum;
    }

    private int getDoubleSum(double prob[]){
        int sum = 0;
        for(double i: prob){
            sum+=i;
        }
        return sum;
    }

    private int randomState(int limits[]){
        int state = (int)(Math.random()*limits.length);
        if(limits[state]>0) return state;
        else return randomState(limits);
    }

    /**
     * Returns the height of the array
     */
    public int getHeight(){
        return height;
    }

    /**
     * Returns the width of the array
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the value stored at a certain point (i, j) in the array
     * @param i
     * @param j
     */
    public int getStateAt(int i, int j){
        return states[i][j];
    }
}
