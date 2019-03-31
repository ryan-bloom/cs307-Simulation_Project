CompSci 307: Simulation Project Analysis
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/03_simulation/):

Design Review
=======

### Status
What makes the code well-written and readable (i.e., does it do what you expect, does it require comments to understand)?
* In my opinion, the cleanest part of our design was the cell hierarchy. We had an abstract cell class that many different useful methods applicable to each type of cell, along with an abstract updateCell method that varied depending on the rules of the simulation. An example of the updateCell method for the Fire simulation is below. It makes the structure of our project easier to understand because each child of the cell class has one clear method that demonstrates its rules. Because of this structure, it makes it very easy to add a new type of simulation. It is also relatively concise and readable in my opinion.
```java
 public List<Cell> updateCell(List<Cell> neighbors, Grid cellGrid, CellShape shape) {
        List<Cell> temp = new ArrayList<>();
        //Empty stays empty and burning goes to empty always (o/w check neighbors)
        if(getMyCurrentState() == 1){
            for(Cell c: neighbors){
                if(c.getMyCurrentState() == 2){
                    setMyNextState(2);
                    break;
                }
            }
        }
        else{
            setMyNextState(0);
        }
        temp.add(this);
        return temp;
        
    }
}    
```

* There are similar methods to the one above in the all cell classes, and this makes each cell class both simple and powerful in determining the nature of the simulation.

What makes this project's code flexible or not (i.e., what new features do you feel are easy or hard to add)?

* In terms of flexibility, there are some problems that we encountered. There are many places in our code that include many if and else if statements to determine the shape of the cells, the neighborhood types, and the edge types. These are troubling because if someone wanted to add a new type of shape, neighborhood, or edge, they would have to locate each one of these statements and add a new statement. We tried to avoid this as much as possible but at some points it seemed inevitable. We played with the idea of abstraction, but between these three variables and the type of simulation itself, it would have been a very complex hierarchy that we thought would have hurt our design overall. An example of such statements is below, from the Grid class.
```java
    private Neighbors neighborhoodPicker(int x, int y, CellShape shape, EdgeType edgeType, NeighborhoodType neighborhoodType){
            if(neighborhoodType == NeighborhoodType.COMPLETE){
                return new CompleteNeighbors(x, y, this, shape, edgeType);
            }
            else if(neighborhoodType == NeighborhoodType.CARDINAL){
                return new CardinalNeighbors(x, y, this, shape, edgeType);
            }
            else{ //Corner neighborhoodType
                return new CornerNeighbors(x, y, this, shape, edgeType);
            }
        }
```  
* As I mentioned in the previous section, however, I do think it is relatively easy to add a new types of simulation based on the abstract cell hierarchy we created. New configuration files would have to be added and the Grid class would have to change in order to recognize the new simulation in its SimCellPicker method. Overall, I think our design does not make this process very time consuming.

What dependencies between the code are clear (e.g., public methods and parameters) and what are through "back channels" (e.g., static calls, order of method call, requirements for specific subclass types)?
* There are many dependencies in our code. For example, very clear dependencies can be seen in the Main class. It depends on the properties files having the proper information and pointing them to the correct CSV files. It also obviously depends on every class in the Model package in order to update the cells and display the grid correctly. In order to save and load files for the user, it depends on the FileCreator class and its static methods.

* There are also many "back-channel" dependencies, such as the enums used for the shapes, neighborhood types, and edge types being in the correct enum class and being recognized by the Grid class. The Grid class also depends on the Data class because it takes a Data object in its constructor and uses it to initialize its instance variables. If the Data class changes significantly or contains some error, then it will affect the validity of the Grid class. The Grid constructor is below and clearly depends heavily on the Data object parameter.
```java
public Grid(Data dat){
        myData = dat;
        myRows = myData.getHeight();
        myCols = myData.getWidth();
        myCellGrid = new Cell[myRows][myCols];
    }
```

### Overall Design
As briefly as possible, describe everything that needs to be done to add a new kind of simulation to the project.

* In order to add a new simulation, one would first have to create a new class in the Model package that extends the abstract Cell class. It would need to have an updateCell method that corresponded to the rules of the desired simulation. Then, one would need to go into the Grid class and add a new if else statement to the simCellPicker method so that it would recognize the new simulation. Finally, one would need to create properties and CSV files that correspond to the new simulation.

Describe the overall design of the program, without referring to specific data structures or actual code (focus on how the classes relate to each other through behavior (methods) rather than their state (instance variables)).
* As mentioned previously, there is a Cell hierarchy in model with one abstract class and many concrete classes that extend it. Each subclass has its own implementation based on the simulation it represents. There is a Grid class that contains the Cells and updates them accordingly. Besides their implementation, cells are updated based on their shapes, neighborhood types, and edge types. This information is stored in Enums and classes in the Controller package. There is the Data class which populates the grid based on information read in from a CSV file, based on set probabilities, or based on certain numbers of cells. In the View package, all of these are used to display the Grid and step through it to show the cells' behavior over time.

Justify why your overall code is designed the way it is and any issues the team wrestled with when coming up with the final design.
* Our code is designed the way it is because we wanted our code to be as flexible as possible. The abstract cell hierarchy allows this to happen because it is relatively easy to add a new type of simulation, as described above. This did create some trouble for us at times. For example, the Segregation simulation required us to make updateCell take the Grid as a parameter in order to know which spaces were empty in the Grid. This meant that every updateCell method for every simulation had to take a Grid parameter also, even though none of them used it. Despite small problems like these, this seems to be the most logical implementation. We also considered splitting up the Main class, because it is very long at the moment relative to other classes. This would mean splitting it into multiple different classes, potentially with static methods that Main could call. Eventually, we decided that the current implementation with many helper methods was the best way to approach the View. And although the Main class is long, each method within it is not excessively long.

### Flexibility
Describe what you think makes this project's overall design flexible or not (i.e., what new features do you feel it is able to support adding easily).
* I believe it is relatively easy to implement new simulations, but difficult to implement new Shapes, neighborhood types edge types. For further explanation, read sections above about how to implement a new simulation under "Overall Design" and what makes our program flexible under "Status".

Describe one feature from the assignment specification that was implemented by a teammate in detail:

```java
Cell edgeCheck(Grid cellGrid, int x, int y){
        int tempX;
        int tempY;

        //toroidal -- loop to other side of grid always
        if(myEdgeType == EdgeType.TOROIDAL){
            tempX = toroidal(x, cellGrid.getMyRows());
            tempY = toroidal(y, cellGrid.getMyCols());
            return cellGrid.getCellAt(tempX, tempY);
        }

        //finite -- never loop to other side of grid
        else if(myEdgeType == EdgeType.FINITE && finite(x, cellGrid.getMyRows()) && finite(y, cellGrid.getMyCols())){
            return cellGrid.getCellAt(x,y);
        }
        //Semi toroidal -- corners don't overflow, only direct cardinal overflow
        //Yes left right and top bottom overflow
        else if(myEdgeType == EdgeType.SEMITOROIDAL){
            tempX = toroidal(x, cellGrid.getMyRows());
            tempY = toroidal(y, cellGrid.getMyCols());
            //Corner flip -- not allowed (one temp must be same as original - no change)
            if(tempX == x || tempY == y){
                return cellGrid.getCellAt(tempX, tempY);
            }
        }
        return null;
    }
```

* What is interesting about this code? (why did you choose it?)
  * The code above defines we check for edge cases based on the type of edges. I chose this code because I feel like these intricacies are often forgotten about but make are key in making the simulation work as intended for literal edge cases. 
* Describe the design of this feature in detail? (what parts are closed? what implementation details are encapsulated? what assumptions are made? do they limit its flexibility?)
  * This design makes intelligent use of booleans and helper functions in order to determine how to deal with edge cases. It contains many if statements that determine the type of edge based on the Enum and then either wrapping the Grid around for toroidal, not wrapping at all for finite, or wrapping over one axis for semitoroidal. This can be implemented to correctly identify a cell's neighbors. One problem is that the method returns null if it gets an unrecognized type of edge. This should be changed to a thrown exception that can be caught and handled later, although because it is using an Enum this case may never occur.
* How flexible is the design of this feature? (is it clear how to extend the code as designed? what kind of change might be hard given this design?)
  * This feature is fairly flexible. In order to change it to accommodate a new edge type, a new if statement would need to be added, along with a new Enum for the type of edge. Depending on the nature and complexity of the edge type, a boolean helper function might need to be created.

### Your Design

Describe an abstraction (either a class or class hierarchy) you created and how it helped (or hurt) the design.
*  An abstraction I used related to the creation of a custom exception. It extended Runtime Exception and had three separate constructors depending on the number of parameters. Overall, it helped the design of the program in some regard but hurt it in others. Almost every time an error was caught in a program, especially when reading in data, a custom SimulationException was thrown. This made it much easier to catch exceptions in Main because I only had to check for SimulationExceptions and did not need ten different catch clauses. However, this decreased the specificity of the exceptions and could potentially make the program more difficult to debug because it was less clear what was causing the exception. Although, each one had its own message associated with it, and this could be used to trace back to the original exception. The best example of this is in the Data constructor that reads in a CSV file. The code is below.

```java
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
```

Discuss any Design Checklist issues within your code (justify why they do not need to be fixed or describe how they could be fixed if you had more time). Note, the checklist tool used in the last lab can be useful in automatically finding many checklist issues.
* The main design checklist problem with my code is the one above: catching one exception and throwing another. In order to resolve this, I attempted to implement the initCause() method. This would allow me to throw a custom exception but attribute it to another exception. This would make it much easier to ascertain where a certain SimulationException was coming from. This presented some problems because it required I throw a Throwable exception, and this exception is kind of a catch-all exception. I found that this Throwable exception made the code messier, less specific, and caused more problems than it fixed. If I had more time, I would have looked into the initCause() method further or tried some other method to deal with throwing exceptions in a more specific way.

Describe one feature that you implemented in detail:
* Provide links to one or more GIT commits that contributed to implementing this feature, identifying whether the commit represented primarily new development, debugging, testing, refactoring, or something else.
  * I created the FileCreator class that is used to write CSV and properties files when the User wants to save a simulation to load later. This is the link to the [GIT commit for writing CSV files](https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team01/commit/a63a930e0930aa544446b5480284390c631c2079) and this is the [GIT commit for writing properties files](https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team01/commit/e2083e8dc86bb1d8a0ae521aa68cdfaaf4d1ffbf).
  
* Justify why the code is designed the way it is or what issues you wrestled with that made the design challenging.
  * The code is designed to use FileWriter create new CSV and properties files that save a simulation exactly as it is when the S key is pressed so that it can be loaded when L is pressed. Because of some last minute changes to the code, this feature was not implemented perfectly in Main. One problem is that the "User_Simulation2" below should not contain the 2 and should just be "User_Simulation".
```java  
  if(code == KeyCode.L){
              myAnimation.stop();
              try {
                  myResources = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, "User_Simulation2"));
              }catch (MissingResourceException e){
                  showPopup(errorResources.getString("LoadingError"));
              }
              initializeGrid();
              myAnimation.play();
          }
```  
* (cont.)
  * When L is pressed, it would then reassign myResources to be the User_Simulation properties file which points to the saved CSV file. Without changing the name of User_Simulation2 the user just gets an error pop-up and the program restarts. 
  * The FileCreator class itself has a lot of magic values just based on the nature of the strictness of CVS and properties files. They all have to be formatted in the same way, and this requires that they be constructed in the same way with the same delimiters, values, and keys. If I had more time, I would have tried to reduce these magic values and make the program cleaner in general.

* Are there any assumptions or dependencies from this code that impact the overall design of the program? If not, how did you hide or remove them?
  * One assumption is that the user will not try to save and load on the same run of the program. For some reason, it seems that the program has to terminate for the files to be written, and after that they can be loaded on subsequent runs. This is included in the error pop-up that appears if the program does not work properly (it tells the user to try restarting the program).
### Alternate Designs
Describe two design decisions made during the project in detail:
What alternate designs were considered? What are the trade-offs of the design choice (describe the pros and cons of the different designs)? Which would you prefer and why (it does not have to be the one that is currently implemented)?
* When throwing exceptions, we considered a few different options. We considered throwing different exceptions as they occurred, or throwing a uniform custom exception with unique error messages to make catching easier in Main. We decided to throw custom exceptions because it made it meant we could have one unified catch clause without the need for several different clauses in Main. This also meant that our exceptions were less specific, and this could make debugging more difficult in the future. The "Your Design" section has more information and code examples related to this design decision.

* We also decided to keep the Main class as one cohesive whole as it is now. We considered breaking it up into a few separate classes because it is our longest class by far. This would make it slightly more readable and comprehensible. However, we could not find a clear way to break it up, and found that the methods within it were short enough to warrant its place as one class. It also has a clear and unique purpose: to display the GUI. 

