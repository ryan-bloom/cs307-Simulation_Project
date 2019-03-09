package Controller;

import javafx.scene.paint.Color;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;


public class FileCreator {

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String EQUALS_DELIMITER = "=";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String DATA_EXTENSION = "data\\";
    private static final String RESOURCES_EXTENSION = "src\\Resources\\";
    private static final String ERROR_MSG = "Error saving your simulation";

    public static void writeCsvFile(String fileName, Grid g) throws SimulationException {

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(DATA_EXTENSION + fileName + ".csv");
            //Add a new line separator after the header
            fileWriter.append(g.getMyRows() + "");
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(g.getMyCols() + "");
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new student object list to the CSV file
            for(int i=0; i<g.getMyRows(); i++){
                for(int j = 0;j<g.getMyCols(); j++){
                    fileWriter.append(g.getCellState(j, i) + "");
                    if(!(j==g.getMyCols()-1)) fileWriter.append(COMMA_DELIMITER);
                }
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            } catch (IOException e) {
                throw new SimulationException(ERROR_MSG);
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                throw new SimulationException(ERROR_MSG);
            }
        }
    }

    public static void writePropertiesFile(String fileName, String CsvFile, String Simulation, Map<Integer, Color> cellColors) throws SimulationException{
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(RESOURCES_EXTENSION + fileName + ".properties");
            fileWriter.append("Simulation" + EQUALS_DELIMITER + Simulation);
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append("Name" + EQUALS_DELIMITER);
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append("Description" + EQUALS_DELIMITER);
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append("File" + EQUALS_DELIMITER + CsvFile + ".csv");
            fileWriter.append(NEW_LINE_SEPARATOR);
            for(Map.Entry<Integer, Color> entry: cellColors.entrySet()){
                fileWriter.append("Color" + entry.getKey() + EQUALS_DELIMITER + entry.getKey() + COMMA_DELIMITER + "#" + entry.getValue().toString().substring(2,8).toUpperCase());
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
        } catch (IOException e) {
            throw new SimulationException(ERROR_MSG);
        }finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                throw new SimulationException(ERROR_MSG);
            }
        }
    }
}