package Controller;

import java.io.FileWriter;
import java.io.IOException;


public class CsvFileWriter {

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    public static void writeCsvFile(String fileName, Grid g) {

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);
            //Add a new line separator after the header
            fileWriter.append(g.getMyRows() + "");
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(g.getMyCols() + "");
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new student object list to the CSV file
            for(int i=0; i<g.getMyRows(); i++){
                for(int j = 0;j<g.getMyCols(); j++){
                    fileWriter.append(g.getCellState(j, i) + "");
                    if(!(j==g.getMyCols()-1)) fileWriter.append(COMMA_DELIMITER);
                }
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter");
                e.printStackTrace();
            }

        }
    }
}