simulation
====

### High Level Goals
Throughout our project, we worked hard to keep the Model, View, and Controller classes as separate as possible.  We did this by physically separating our different Model, View, and Controller packages and sorting our classes into those three categories.  Additionally, the only classes that import and or use any javaFX code are found in the View package.  We also worked hard to follow the design checklist, avoiding protected and public instance variables and keeping our methods short and understandable through the use of various helper methods and useful naming techniques.  Additionally, we utilized abstractions where we thought they would help our design, specifically with respect to various simulation types (which were accounted for in the Cell class abstractions) and neighborhood types (accounted for in the Neighbors abstraction).  Overall, we wanted the user to be able to use the view to dynamically select various parameters, such as cell shape, edge type, etc. and to have the view then pass these selections to the controller (specifically the Grid class) which would then find the correct neighborhood and call the model (cell classes) to actually update the states of each cell.  These updates would then be passed back up the chain from Cell class (model) to the Grid class (controller) and finally to main (view) to be displayed.  

### Adding New Features:
New Simulation Type:
* In order to add a new type of simulation to our program, one would have to do 3 things.  First, the programmer would have to create a new cell subclass for this simulation that extends the abstract Cell super class.  Within this new subclass, the programmer would have to implement the rules for updating this new type of cell in its updateCell() method.  Second, the programmer would have to create a properties file for this new simulation type as well, saving this file in the Resources package in the src folder.  This file would need to reference a configuration.csv file for said simulation as well.  The final step to adding a new simulation type would be to add one additional "else if" statement to the simCellPicker() method found in Grid.java class.  This additional statement would look something like this: 
```java
else if(simType.equalsIgnoreCase("NEW_SIMULATION_NAME")){
    return new NewSimulationCell(x, y, myData.getStateAt(x, y));
}
```
These are all of the steps required to add a new simulation type to our program.

New Neighborhood Types:
* A similar set of steps is required to add new neighborhood types to our program.  However, adding this additional feature is slightly less cut and dry.  If one wants to add a new neighbor type (such as Cardinal or Complete), one would simply create a new concrete NEWTYPENeighbor.java subclass that takes in cell shape and edge types for parameters.  Then, one would have to implement the rules for this new type of neighborhood, checking for each cell shape and edge type as he/she goes.  Finally, an additional enum type would have to be added to the NeighborhoodType.java enums and another else/if statement would be added to the neighborhoodPicker() method in Grid.java that allows this new neighborhood type to be initialized.  

* However, if someone wants to add a new cell shape or edge type, which both impact neighborhoods, it gets a little bit more complex.  First, he/she would have to add the new cell shape or edge type to the correct enum class in the Controller package (CellShape.java; EdgeType.java).  Then, if one added a new edge type, he/she would have to edit the edgeChecker() method in Neighborhood.java abstract class to account for the new rules associated with this edge type.  On the other hand, if someone wanted to add a new cell shape, in addition to adding another abstract method in Neighbors.java abstract class and implementing this method within each of the three concrete subclasses, he/she would have to work in the View package to physically draw and create the visualization for said new cell shape.  ************ROHAN WRITE MORE ABOUT THIS HERE************

### Major Design Choices
One major design choice that we made was the separation of the Neighbors abstract class out of the Grid.java class.  Originally, Grid.java found each cell's neighborhood and passed this List<> to the individual cells upon updating them.  Moving this functionality into its own class helped to limit the responsibilities of the Grid class, which helped our overall design.  Then, however, we were faced with another design decision.  We had to decide how to divide the Neighbors abstract class into concrete subclasses.  The struggle here came along with the fact that there are 3 fields that impact a neighborhood:
1. Neighbors Type
2. Edge Type
3. Cell Shape

We initially wanted to create concrete subclasses for every possible neighborhood combination.  However, accounting for all of these combinations would have led to 27 sub classes and this seemed like overkill.  Therefore, we decided to make 3 subclasses (one for Complete, Cardinal, and Corner neighborhoods) and then pass in parameters for CellShape and EdgeType.  This helped our overall design by allowing us to switch between Complete, Cardinal, and Corner neighborhoods with relative ease (as seen in the Grid.java method neighborhoodPicker()).  However, passing the CellShape and EdgeType as parameters slightly hurt our design, as each neighborhood subclass now required more code and more methods to check these two parameters and handle them accordingly (as seen in the Neighbors.java edgeCheck() method).  Overall, the Neighbors abstraction helped our design because each class simply focused on one type of neighborhood (whereas neighborhoods were previously found in the Grid.java class), but caused a few issued with respect to accounting for cellShape and EdgeType within each neighborhood class. 

Another design choice that we made was to keep many methods in our Main.java class as opposed to separating out smaller classes within our view package.  

### Assumptions and Decisions