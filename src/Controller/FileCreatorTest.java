package Controller;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileCreatorTest {
    private String fileName = "CreatorTest";
    private String invalidName = "Bad \n Name \\";
    private String Simulation = "Simulation";
    private Grid g;
    private Map<Integer, Color> colors;

    @BeforeEach
    public void setUp(){
        g = new Grid(new Data("DataTest_Config_1.csv"));
        colors = new HashMap<>();
        colors.put(1, Color.RED);
    }

    @Test
    public void checkCSVInvalidName(){
        assertThrows(SimulationException.class, () ->FileCreator.writeCsvFile(invalidName, g));
    }

    @Test
    public void checkCSVNullFileName(){
        assertThrows(SimulationException.class, () -> FileCreator.writeCsvFile(null, g));
    }

    @Test
    public void checkCSVNullGrid(){
        assertThrows(SimulationException.class, () ->FileCreator.writeCsvFile(fileName, null));
    }

    @Test
    public void checkPropertiesInvalidFileName(){
        assertThrows(SimulationException.class, ()->FileCreator.writePropertiesFile(invalidName, fileName, Simulation, colors));
    }

    @Test
    public void checkPropertiesNullColors(){
        assertThrows(SimulationException.class, () ->FileCreator.writePropertiesFile(fileName, fileName, Simulation, null));
    }


}