simulation
====

### Status
Well written and readable code:
* For the most part, our code is well written and readable and does not require comments to understand.  The majority of our methods are relatively short, and we did a good job utilizing helper methods in order to ensure that our code remains readable.  For example, the code used to update cell states in each cell subclass is easily readable and does not require comments to understand. This is demonstracted in the code snippet below taken from PercolationCell.java: 
```java
public List<Cell> updateCell(List<Cell> neighbors, Grid cellGrid, CellShape shape) {
        List<Cell> temp = new ArrayList<>();
        //Only state changes happen if current state is 1 (then check neighbors)
        if (this.getMyCurrentState() == 1){
            for (Cell n : neighbors){
                if(n.getMyCurrentState() == 2){
                    this.setMyNextState(2);
                    temp.add(this);
                }
            }
        }
        return temp;
    }
```
Each cell subclass has a similar updateCell() method.  In some cases, this method calls to other helper methods, such as in GameOfLifeCell and PredatorPreyCell.  In those subclasses, this occurs because there are different rules that must be implemented for different cell types, such as square, hexagonal, or triangular, or fish and shark cells.  When this is the case, the code remains readable because of the use of helper methods that are called by the overriding updateCell() method.  


Flexible Code:
* In order to add new features, such as a new simulation type, one would simply have to create a new concrete subclass for this simulation.  This subclass, which would extend the "Cell" abstract class, would implement the new simulation's rules in its update method.  Then, one would have to create a properties file with configurations for this simulation (or hypothetically one could use an existing properties file for a simulation with the same number of possible states as this new one).  Finally, adding an additional "if" statement in the simCellPicker() method in Grid.java class for this new simulation would allow users to run this new feature. The simCellPicker() method is not the best design, as it implements several if/else if statements, but it allows for dynamic changing between simulation types.  Part of this method is depicted here:
```java

private Cell simCellPicker(String simType, int x, int y){
        if(simType.equalsIgnoreCase("GAMEOFLIFE")){
            return new GameOfLifeCell(x, y, myData.getStateAt(x, y));
        }
        else if(simType.equalsIgnoreCase("PERCOLATION")){
            return new PercolationCell(x, y, myData.getStateAt(x, y));
        }
        else if(simType.equalsIgnoreCase("FIRE")){
            return new FireCell(x, y, myData.getStateAt(x, y));
        }
        ...
        else{...}
```  
* A similar process would be required for adding new neighborhood types.  One would create the new neighborhood class, add this new neighborhood to the enum NeighborhoodType, and add an additional "if" statement in neighborhoodPicker() method in Grid.java.  However, adding new edge types and cell shapes would be slightly complex, as they would have to be added to the enum classes and accounted for in each of the neighborhood concrete subclasses as well where we check for said parameters as seen in the part of the edgeCheck method displayed below:
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
        ...
```  
* Furthermore, our code is flexible in the sense that users can dynamically move between neighborhood types, cell shapes, and edge types after starting the program.  This is done by simply selecting different options from the various display menus.  Finally, the division of our code into distinct MVC sections, separating all backend from frontend javaFX adds even more flexibility to our program, as the backend does not rely on, and is not creating any of the front end visualizations.  

Dependencies:
* There are several dependencies in our code that, ideally, would be done away with.  Initially, our Cell class was independent of the cell grid.  However, upon creating the "segregation" simulation, we realized that the Cell needed access to the grid in order to find an empty cell to move to (if the cell in question is unsatisfied with its location).  Therefore, we started sending the Grid object (this) to a cell when the Grid class calls a cell's update method.  By doing this, the cell is able to loop through the rows and columsn of the grid accessing each cell individually at each row,col index and find an empty location and potentially move to this new spot.  The cell knows nothing about the grid during its entire lifetime other than the brief moment during which it is updating its state.  
* Another dependency that we have in our code is in the Neighbors class and subclasses.  In order to find the correct neighbors, each neighbor subclass must know the shape of the cells in the grid as well as the edge type.  Therefore, each neighborhood subclass is dependent on these two enums.  In order to account for this, these enums, along with the cell grid itself, are passed to each concrete neighborhood cell class.  Then, the subclasses check these two enum parameters and return the correct neighbors accordingly. This dependency was only added once the three variations for Game of Life were implemented (depending on square, hexagonal, or triangular cell shapes; finite, toroidal, or semi-toroidal edge types; and complete, cardinal, or corner neighbor types).  


### Overall Design
How to add a new type of simulation:
* In order to add a new type of simulation to our program, one would have to do 3 things.  First, the programmer would have to create a new cell subclass for this simulation that extends the abstract Cell super class.  Within this new subclass, the programmer would have to implement the rules for updating this new type of cell in its updateCell() method.  Second, the programmer would have to create a properties file for this new simulation type as well, saving this file in the Resources package in the src folder.  This file would need to reference a configuration.csv file for said simulation as well.  The final step to adding a new simulation type would be to add one additional "else if" statement to the simCellPicker() method found in Grid.java class.  This additional statement would look something like this: 
```java
else if(simType.equalsIgnoreCase("NEW_SIMULATION_NAME")){
    return new NewSimulationCell(x, y, myData.getStateAt(x, y));
}
```
These are all of the steps required to add a new simulation type to our program.  

Overall Design:
* Main.java sets the stage and scene and starts the simulation, displaying everything to the user (from the cells themselves to the button and menu inputs for users to tinker with).  In order to do this, Main communicates with several classes in the Controller and Model package, such as the Data.java class, in order to read in the property and configuration files.  Then, through the step method in Main, Main loops through the cells and calls various Controller classes to run the actual simulation.  Specifically, Main communicates with Grid.java to fill the cell grid, and then Grid.java calls Neighbors subclasses in order to correctly find and retrieve Cells' neighbors.  Then, Grid.java calls the backend Cell classes, passing along each cell's neighbors, in order for each cell to update itself according to the simulation that was selected by the user (and recorded by Main.java).  Once the cell updates itself, it communicates this update back to Grid.java, which then sends this new state to Main.java, which displays the new grid to the user. 

Why this Design?
* From the start, we knew that we wanted to have an abstract cell class with concrete subclasses representing each different type of simulation.  However, our initial design did not separate our classes into the MVC format.  Then, upon hearing about the requirement to separate our front end from our back end, we decided it would be most effective to physically create three packages, Model, View, and Controller, and move classes into whichever we thought they best belonged.  We struggled to determine which classes belonged in our Controller package, as it was empty for a large portion of our project.  However, upon creating the Neighbors abstract and respective concrete subclasses, we decided that these, along with the grid class, acted as buffers between the front and backend, as they did not truly do any calculations that impacted and updated the simulation itself.  Instead, they helped the backend Cell classes run the simulation by providing information to these Cell classes as required (such as the Cell's neighbors).  One other debate that we had with respect to our overall design was whether or not we should split the Main.java class into multiple smaller classes in our View package.  After discussion, we determined that splitting Main into several smaller classes would have led to the emergence of more dependencies in our code (between these newly created classes).  Therefore, we kept Main as is instead of trying to divide it into smaller classes.

### Your Design
Abstraction created and how helps/hurts design:
* We implemented an abstraction for the Neighbors class with concrete subclasses for Complete, Cardinal, and Corner neighborhoods.  When initially thinking about the best ways to design this part of our program, we ran into some issues because we wanted to create concrete subclasses for every possible neighborhood combination.  However, there were three factors that impacted a neighborhood: neighbors type (complete, cardinal, corner), edge type (toroidal, finite, semi-toroidal), and cell shape (square, hexagonal, and triangular).  Accounting for all of these combinations would have led to 27 sub classes and seemed like overkill.  Therefore, we decided to make 3 subclasses (one for Complete, Cardinal, and Corner neighborhoods) and then send parameters for CellShape and EdgeType.  This helped our overall design by allowing us to switch between Complete, Cardinal, and Corner neighborhoods with relative ease (as seen in the Grid.java method neighborhoodPicker()).  However, passing the CellShape and EdgeType as parameters slightly hurt our design, as each neighborhood subclass now required more code and more methods to check these two parameters and handle them accordingly (as seen in the Neighbors.java edgeCheck() method).  Overall, the Neighbors abstraction helped our design because each class simply focused on one type of neighborhood (whereas neighborhoods were previously found in the Grid.java class), but caused a few issued with respect to accounting for cellShape and EdgeType within each neighborhood class. 

Design checklist issues
* Dependencies between classes (passing parameters is dangerous)??? 

One feature implemented in detail:
* One feature that you implemented in detail

### Alternate Design 
Design Decision 1: Making only 3 Neighborhood subclasses (Complete, Cardinal, Corner)
* This design decision was made when the final round of requirements was released in which we needed to account for several neighborhood types, edge types, and cell shapes.  As described above, we decided to create an abstract Neighbors class with three concrete subclasses, CompleteNeighbors, CardinalNeighbors, and ConcreteNeighbors.  We then passed in CellShape and EdgeType parameters to account for these other 2 possible variations to neighborhoods.  Our other idea was to create 27 neighbor subclasses, each one with a specific and unique combination of the three categories (cell shape, edge type, neighbor type).  
* Our decision to restrict our subclasses to 3 and pass in parameters helped to keep the Neighbors subclasses more easily manageable and to keep our Controller package less overwhelming.  It also ensured that our neighborhoodPicker() method in Grid.java was far shorter than it could have been if we had to select one of 27 possible neighborhood types.  On the other hand, not creating 27 subclasses and having to check for cell shape and edge types in each neighborhood class led to slightly messier and longer code in each subclass.  Instead of simply being able to implement the exact neighborhood selected needed, we now needed to check for which type of cell and edge we had to focus on.  Therefore, each cell subclass now contains methods for square, triangular, and hexagonal cell types and uses the edgeChecker() helper method as well.  In the end, I believe that I would have preferred to create 27 subclasses, each one specific and unique to a neighborhood.  I could have packaged these subclasses in a "Neighborhoods" sub package within the Controller package in order to keep Controller less overwhelming.  I also think that one long method in Grid.java that selects the correct concrete subclass would have been better design than the messier checks done in each of the 3 neighborhood subclasses now.   

Design Decision 2: