CompSci 307: Simulation Project Analysis
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/03_simulation/):

Design Review
=======

### Status
Well written and readable code:
* For the most part, our code is well written and readable and does not require comments to understand.  The majority of our methods are relatively short, and we did a good job utilizing helper methods in order to ensure that our code remains readable.  For example, the code used to update cell states in each cell subclass is easily readable and does not require comments to understand. This is demonstrated in the code snippet below taken from PercolationCell.java: 
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
* Each cell subclass has a similar updateCell() method.  In some cases, this method calls to other helper methods, such as in GameOfLifeCell and PredatorPreyCell.  In those subclasses, this occurs because there are different rules that must be implemented for different cell types, such as square, hexagonal, or triangular, or fish and shark cells.  When this is the case, the code remains readable because of the use of helper methods that are called by the overriding updateCell() method.  The parts of the code that get slightly confusing come when trying to find the correct neighbors for various neighborhood and cell shape types.  This occurs because of the need to check very specific indices to determine if a certain cell should be included in one's neighborhood, and therefore this leads to relatively complex conditional statements as depicted below: 
```java
    private boolean upsideDownHelper(int i, int j, int myX, int myY){
        //Check not this, not cardinal above, not cardinal left/right, not below too left/too right
        return ((i!=myX || j!=myY) && (i!=myX-1 || j!=myY) && (i!=myX || (j!=myY-1 && j!=myY+1)) && (i!=myX+1 || (j!=myY-2 && j!=myY+2)));
    }

    private boolean rightSideUpHelper(int i, int j, int myX, int myY){
        return ((i!=myX || j!=myY) && (i!=myX || (j!=myY-1 && j!=myY+1)) && (i!=myX+1 || j!=myY) && (i!=myX-1 || (j!=myY-2 && j!=myY+2)));
    }
```  
* These helper methods are used to determine the correct neighbors ro upsideDown and rightSideUp triangle cells.  I separated these out into helper methods in an attempt to make the overall code more readable and to avoid having these long conditionals in a more complex method.

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
* Furthermore, our code is flexible in the sense that users can dynamically move between neighborhood types, cell shapes, and edge types after starting the program.  This is done by simply selecting different options from the various display menus.  Finally, the division of our code into distinct MVC sections, separating all backend from frontend javaFX adds even more flexibility to our program, as the backend does not rely on, and is not creating any of the front end visualizations.  Finally, the various properties files that are required and read are done so dynamically based on static final variables set in Main.java.  These variables are read by the Data.java class and can be changed (for the most part) by the user after starting the simulation.    

Dependencies:
* There are several dependencies in our code that, ideally, would be done away with.  Initially, our Cell class was independent of the cell grid.  However, upon creating the "segregation" simulation, we realized that the Cell needed access to the grid in order to find an empty cell to move to (if the cell in question is unsatisfied with its location).  Therefore, we started sending the Grid object (this) to a cell when the Grid class calls a cell's update method.  By doing this, the cell is able to loop through the rows and columns of the grid accessing each cell individually at each row,col index and find an empty location and potentially move to this new spot.  The cell knows nothing about the grid during its entire lifetime other than the brief moment during which it is updating its state.  
* Another dependency that we have in our code is in the Neighbors class and subclasses.  In order to find the correct neighbors, each neighbor subclass must know the shape of the cells in the grid as well as the edge type.  Therefore, each neighborhood subclass is dependent on these two enums.  In order to account for this, these enums, along with the cell grid itself, are passed to each concrete neighborhood cell class.  Then, the subclasses check these two enum parameters and return the correct neighbors accordingly. This dependency was only added once the three variations for Game of Life were implemented (depending on square, hexagonal, or triangular cell shapes; finite, toroidal, or semi-toroidal edge types; and complete, cardinal, or corner neighbor types).  Both of these dependencies are clear and are passed through parameters to various methods.  
* Finally, the Grid class calls both the neighbor and cell classes in order to update a cell, and each concrete neighbor subclass eventually calls the edgeChecker() method throughout its code.  All of these dependencies are less obvious and can be considered "back end" channels.

You can put links to commits like this: [My favorite commit](https://coursework.cs.duke.edu/compsci307_2019spring/example_bins/commit/33a37fe42915da319f7ae140c8e66555cf28d2c8)

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

### Flexibility
Flexible Design:
* I believe that our code is flexible.  Upon separating out our three main packages into Model, View, and Controller packages, we were able to remove all references to javaFX from any class not involved in creating the simulation view.  This, right off the bat, enables our code to be flexible in the sense that none of the backend relies on any javaFX and none of the backend is really needed to create the view.  Furthermore, our code enables users to dynamically select different colors, images, and neighborhood types for the simulation to display.  Finally, the addition of entirely new simulations to our project can be done with relative ease.  One would simply have to follow these three steps to add a new simulation.
1. First, the programmer would have to create a new cell subclass for this simulation that extends the abstract Cell super class.  Within this new subclass, the programmer would have to implement the rules for updating this new type of cell in its updateCell() method.
2. Second, the programmer would have to create a properties file for this new simulation type as well, saving this file in the Resources package in the src folder.  This file would need to reference a configuration.csv file for said simulation as well.  
3. The final step to adding a new simulation type would be to add one additional "else if" statement to the simCellPicker() method found in Grid.java class.  After completing these three simple steps, the new simulation will be ready to run. 

Teammate's Feature Implemented:

### Your Design

Abstraction created and how it helps/hurts design:
* We implemented an abstraction for the Neighbors class with concrete subclasses for Complete, Cardinal, and Corner neighborhoods.  When initially thinking about the best ways to design this part of our program, we ran into some issues because we wanted to create concrete subclasses for every possible neighborhood combination.  However, there were three factors that impacted a neighborhood: neighbors type (complete, cardinal, corner), edge type (toroidal, finite, semi-toroidal), and cell shape (square, hexagonal, and triangular).  Accounting for all of these combinations would have led to 27 sub classes and seemed like overkill.  Therefore, we decided to make 3 subclasses (one for Complete, Cardinal, and Corner neighborhoods) and then send parameters for CellShape and EdgeType.  This helped our overall design by allowing us to switch between Complete, Cardinal, and Corner neighborhoods with relative ease (as seen in the Grid.java method neighborhoodPicker()).  However, passing the CellShape and EdgeType as parameters slightly hurt our design, as each neighborhood subclass now required more code and more methods to check these two parameters and handle them accordingly (as seen in the Neighbors.java edgeCheck() method).  Overall, the Neighbors abstraction helped our design because each class simply focused on one type of neighborhood (whereas neighborhoods were previously found in the Grid.java class), but caused a few issued with respect to accounting for cellShape and EdgeType within each neighborhood class. 

Design checklist issues
* In some of our classes, we use magic values to display and loop through the correct indices for cells in a grid.  I started to work on fixing this issue by creating private static final variables in various classes that hold these "magic values," and then calling theses instance variables in place of the actual integers in my code.  This is seen in CompleteNeighbors.java displayed below.  Unfortunately, I did not have enough time to remove all of these magic values via the use of member variables as some are still used in various conditional statements, and I am unsure of other ways in which to avoid their use.
```java
public class CompleteNeighbors extends Neighbors {
    private final static int LOWER_BOUND_SQUARE = 1;
    private final static int UPPER_BOUND_SQUARE = 2;

    private final static int LOWER_BOUND_TRI = 1;
    private final static int MID_BOUND_TRI = 2;
    private final static int UPPER_BOUND_TRI = 3;
    ...
    }
```

* Another design checklist issue is that our Main.java class is relatively large compared to other classes and compared to what might be best code design.  I believe that Main.java acts as one "smart" class that uses a few "dumb" helpers instead of several smaller, more concise classes.  We did not have time to refactor this code, but I would have solely left the actual running of the simulation in the Main.java class and separated out all parts of the class that are involved with setting up the stage/scene and reading in data into a separate SetUp.java class.  For example, I would move methods such as setupConfig(), initializeGrid(), initializeEdge(), initializeShape(), initializeNeighborhood() to separate classes in order to consolidate and compress this one large Main.java class. This change would help with the idea of having classes responsible for one and only one thing as well.  

One feature I implemented in detail:
* One interesting feature that I implemented was the SegregationCell.java subclass.  I initially began implementing this feature the same way that I did all the previous cell subclasses, by creating a new concrete subclass and starting to write its updateCell() method with the rules for this specific simulation.  However, I quickly realized that this simulation was different from the previous ones because the cell in question doesn't necessarily change states after each step, but instead changes locations within the grid.  Before dealing with this, I implemented the findPercentSame() helper method that is called by the updateCell method to determine whether or not the cell in question has to actually move (if it doesn't have enough similar neighbors as determined by the THRESHOLD instance variable).  Then, once I determined that this cell is unhappy, I moved onto dealing with how to handle that situation.
* First, I realized that I needed to find a location on the grid that held an empty cell.  In order to do this, I originally passed the entire Cell[][] grid as a parameter to my updateCell method, looped through this grid and listed out all cells with state = 0 (empty).  This is seen in the following commit, which is mostly adding new code to the program: [Pass Cell[][] as parameter for SegregationCell.java updateCell() Method.](https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team01/commit/f2261a58bbbfaed024a738ca171a5828686fc82e)  Then, I selected the first empty location that I found using the findEmptyCells() helper method and simply set that empty cell's state to "this" cell's state, while setting "this" cell's state = to 0 (empty) effectively trading places with the empty cell.
* Later on, I ran into two issues with this implementation.  First of all, selecting the first empty cell often led to an infinite loop occurring where a cell continuously switches between 2 locations (neither of which satisfies its threshold).  Therefore, in order to do away with this, instead of selecting the first empty cell, I found all empty cells and used the randomEmptyLocation() helper method to randomly select which of the empty cell's "this" current cell was to move to.  This edit is found in this commit, which is mostly refactoring and editing of previously written code in order to debug this infite loop." [Randomly selected empty cell for unhappy cell to move to.](https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team01/commit/0c9a88e0380fea0ca78342d3bab528128a91beb0)  The second issue that I faced was the fact that passing in the entire Cell[][] grid was a bad design.  Therefore, instead of doing this, I passed in the Grid object and used the Grid.getMyRows() and Grid.getMyCols() methods in order to find the size of this grid.  Using these indices and the method Grid.getCellAt(), I was able to loop through the entire grid accessing individual cells and determining if they were empty or not without passing in the entire Cell[][] object.
* This code is designed like this because it maintains a similar format to all other concrete cell subclasses, implementing an updateCell() method to update its cell state.  I also implemented the various helper methods referenced above to keep the updateCell() method short and readable.  All of the methods used here are very readable and require little to no comments to understand.  The need to pass along the entire Grid object to this cell's updateCell method did impact the design of the code because each cell class now accepted the Grid object as a parameter.  This dependency was not needed prior to implementing the segregation simulation, and was needed for predator prey simulation as well.  


### Alternate Designs
Design Decision 1: Making only 3 Neighbor subclasses (Complete, Cardinal, Corner)
* This design decision was made when the final round of requirements was released in which we needed to account for several neighborhood types, edge types, and cell shapes.  As described above, we decided to create an abstract Neighbors class with three concrete subclasses, CompleteNeighbors, CardinalNeighbors, and ConcreteNeighbors.  We then passed in CellShape and EdgeType parameters to account for these other 2 possible variations to neighborhoods.  Our other idea was to create 27 neighbor subclasses, each one with a specific and unique combination of the three categories (cell shape, edge type, neighbor type).  
* Our decision to restrict our subclasses to 3 and pass in parameters helped to keep the Neighbors subclasses more easily manageable and to keep our Controller package less overwhelming.  It also ensured that our neighborhoodPicker() method in Grid.java was far shorter than it could have been if we had to select one of 27 possible neighborhood types.  On the other hand, not creating 27 subclasses and having to check for cell shape and edge types in each neighborhood class led to slightly messier and longer code in each subclass.  Instead of simply being able to implement the exact neighborhood selected needed, we now needed to check for which type of cell and edge we had to focus on.  Therefore, each cell subclass now contains methods for square, triangular, and hexagonal cell types and uses the edgeChecker() helper method as well.  In the end, I believe that I would have preferred to create 27 subclasses, each one specific and unique to a neighborhood.  I could have packaged these subclasses in a "Neighborhoods" sub package within the Controller package in order to keep Controller less overwhelming.  I also think that one long method in Grid.java that selects the correct concrete subclass would have been better design than the messier checks done in each of the 3 neighborhood subclasses now.   

Design Decision 2: Dividing all of our classes into Model, View, Controller packages
* Upon starting our project, we did not separate our classes into the MVC format.  Our cell classes, which make up our model and backend computations imported and used javaFX, and communicated directly with the Main.java front end class.  After realizing that we needed to separate our project into MVC, we talked about the best ways to do this.  We thought it would be most clear cut and dry if we were to physically create three packages for MVC and separate our classes into their respective locations thereby strictly defining our Model, View, and Controller components.  We knew that Main.java was a View class and that all of the Cell classes were to be in the Model, but we struggled determining what to add to our Controller package.  We initially had Grid and Neighbors abstract/sub classes in Model, but after discussions, we decided to move these to the Controller package because they worked more as a buffer between the front end and backend as opposed to actually driving the simulations.  These classes pass along data and information needed by the Cells to update themselves as opposed to actually making the calculations and updates that drive the program (which was left to the Cell classes in Model).  
* This design decision helped us to mentally and physically separate out our Model, View, and Controller classes.  I believe that this made it far more clear and far easier to remove all of the javaFX from any class that is not in Model.  Furthermore, it helped us to understand the roles of each class.  For example, after realizing that Grid and Neighbors should be in controller, we understood that these classes didn't actually make changes or calculations that drove the program.  Instead, we determined that their main focus was to communicate between the front and back ends.  On the other hand, this separation forced us to lengthen our Main.java code.  This is because, previously, cell shapes and colors would be held by each individual cell object and passed to the front end to be displayed.  However, now that javaFX is nowhere to be found in any Cell class, all of these rectangles, hexagons, and triangles had to be initialized, drawn, colored in, and displayed by Main.java.  This could have been done more cleanly by separating out various other classes in our View package, but we ran out of time and we were unable to do so.  In the end, this setback does not overcome the benefits and organization than came along with our decision to create our three packages and if I were to redo this project, I would once again create a Model, View, and Controller package.  I might even go a step further and add subpackages within these three folders, such as a Model.Cell package and a Controller.Neighbors package.   