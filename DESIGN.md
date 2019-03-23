simulation
====

### Status
Well written and readable code:
* For the most part, our code is well written and readable and does not require comments to understand.  The majority of our methods are relatively short 


Flexible Code:
* In order to add new features, such as a new simulation type, one would simply have to create a new concrete subclass for this simulation.  This subclass, which would extend the "Cell" abstract class, would implement the new simulation's rules in its update method.  Then, one would simply have to create a properties file with configurations for this simulation (or hypothetically one could use an existing properties file for a simulation with the same number of states as this new one).  Finally, adding an additional "if" statement in the simCellPicker() method in Grid.java class for this new simulation would allow users to run this new feature.  
* A similar process would be required for adding new neighborhood types.  One would create the new neighborhood class, add this new neighborhood to the enum NeighborhoodType, and add an additional "if" statement in neighborhoodPicker() method in Grid.java.  However, adding new edge types and cell shapes would be slightly complex, as they would have to be added to the enum classes and accounted for in each of the neighborhood concrete subclasses as well.  
* Furthermore, our code is flexible in the sense that users can dynamically move between simulation types and neighborhood types after starting the program.  This is done by simply selecting different options from the various display menus.  Finally, the division of our code into distinct MVC sections, separating all backend from frontend javaFX adds even more flexibility to our program, as the backend does not rely on, and is not creating any of the front end visualizations.  

Dependencies:
* There are several dependencies in our code that, ideally, would be done away with.  Initially, our Cell class was independent of the cell grid.  However, upon creating the "segregation" simulation, we realized that the Cell needed access to the grid in order to find an empty cell to move to (if the cell in question is unsatisfied with its location).  Therefore, we started sending the grid to a cell when the cell calls its update method.  By doing this, the cell is able to find an empty location and potentially move to this new spot.  The cell knows nothing about the grid during its entire lifetime other than the brief moment during which it is updating its state.  
* Another dependency that we have in our code is in the Neighbors class and subclasses.  In order to find the correct neighbors, each neighbor subclass must know the shape of the cells in the grid as well as the edge type.  Therefore, each neighborhood subclass is dependent on these two enums.  In order to account for this, these enums, along with the cell grid itself, are passed to each concrete neighborhood cell class.  Then, the subclasses check these parameters and return the correct neighbors accordingly.  Each Cell's update method must take the CellShape into account as well.  This dependency was only added once the three variations for Game of Life were implemented (depending on square, hexagonal, or triangular cell shapes).  


### Overall Design
* how to add new feature?

Overall Design:
* Main.java sets the stage and scene and starts the simulation, displaying everything to the user (from the cells themselves to the button and menu inputs for users to tinker with).  In order to do this, Main communicates with several classes in the Controller and Model package, such as the Data.java class, in order to read in the property and configuration files.  Then, through the step method in Main, Main loops through the cells and calls various Controller classes to run the actual simulation.  Specifically, Main communicates with Grid.java to fill the cell grid, and then Grid.java calls Neighbors subclasses in order to correctly find and retrieve Cells' neighbors.  Then, Grid.java calls the backend Cell classes, passing along each cell's neighbors, in order for each cell to update itself according to the simulation that was selected by the user (and recorded by Main.java).  Once the cell updates itself, it communicates this update back to Grid.java, which then sends this new state to Main.java, which displays the new grid to the user. 

Why this Design?
* From the start, we knew that we wanted to have an abstract cell class with concrete subclasses representing each different type of simulation.  However, our initial design did not separate our classes into the MVC format.  Then, upon hearing about the requirement to separate our front end from our back end, we decided it would be most effective to physically create three packages, Model, View, and Controller, and move classes into whichever we thought they best belonged.  We struggled to determine which classes belonged in our Controller package, as it was empty for a large portion of our project.  However, upon creating the Neighbors abstract and respective concrete subclasses, we decided that these, along with the grid class, acted as buffers between the front and backend, as they did not truly do any calculations that impacted and updated the simulation itself.  Instead, they helped the backend Cell classes run the simulation by providing information to these Cell classes as required (such as the Cell's neighbors).  One other debate that we had with respect to our overall design was whether or not we should split the Main.java class into multiple smaller classes in our View package.  After discussion, we determined that splitting Main into several smaller classes would have led to the emergence of more dependencies in our code (between these newly created classes).  Therefore, we kept Main as is instead of trying to divide it into smaller classes.

### Your Design
* Abstraction created and how helps/hurts design.  Neighbors abstraction (for Complete, Cardinal, Corner neighborhoods).  Tough because 2 other neighbor impacting states (shape and edge type).  Originally wanted subclass for all specific neighborhoods but that would have been 27 sub classes and seemed like overkill.  Decided to make 3 subclasses and send parameters for CellShape and EdgeType (helped design because simpilified the overall neighbor abstraction, hurt design because led to if/else if statements being needed to check on CellShape and EdgeType). Also in grid, have to check neighborhood type before calling cell update methods.  

* Design checklist issues - dependencies between classes (passing parameters is dangerous)  

* One feature that you implemented in detail -- cell abstraction 

### Alternate Design 
* 2 design decisions made -- 1 separate neighbors class from grid class (helped because each class focuses on fewer things and more succinct and divided and independent; hurt because slightly dependent on one another because entire cell grid must be passed to neighbor class by grid when finding neighbors).

2 --- passing cell shape and edge type as enum parameters   

Not passing data to Grid -- get info from data then create grid so can access this grid from cell classes without needing data or needing to pass Cell[][]