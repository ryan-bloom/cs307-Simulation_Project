CompSci 307: Simulation Project Analysis
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/03_simulation/):

Design Review
=======

### Status

```java
private void setRunButton() {
    Button toRun = new Button(RunSim);
    toRun.relocate(GRID_WINDOW_WIDTH + RUN_BUTTON_OFFSET, 0);
    myGroup.getChildren().add(toRun);

    ChoiceBox neighborType = new ChoiceBox(FXCollections.observableArrayList(NeighborhoodType.CARDINAL.toString(), NeighborhoodType.COMPLETE.toString(), NeighborhoodType.CORNER.toString()));
    neighborType.relocate(GRID_WINDOW_WIDTH + GRID_BEHAVIOUR_BUTTON_OFFSET, 0);
    ChoiceBox edgeType = new ChoiceBox(FXCollections.observableArrayList(EdgeType.FINITE.toString(), EdgeType.SEMITOROIDAL.toString(), EdgeType.TOROIDAL.toString()));
    edgeType.relocate(GRID_WINDOW_WIDTH + GRID_BEHAVIOUR_BUTTON_OFFSET, GRID_BEHAVIOUR_BUTTON_VERTICAL_OFFSET);
    ChoiceBox cellShape = new ChoiceBox(FXCollections.observableArrayList(CellShape.HEXAGON.toString(), CellShape.SQUARE.toString(), CellShape.TRIANGLE.toString()));
    cellShape.relocate(GRID_WINDOW_WIDTH + GRID_BEHAVIOUR_BUTTON_OFFSET, edgeType.getLayoutY() + GRID_BEHAVIOUR_BUTTON_VERTICAL_OFFSET);
    myGroup.getChildren().addAll(neighborType, edgeType, cellShape);

    toRun.setOnAction((ActionEvent event) -> {
        for (NeighborhoodType nt : NeighborhoodType.values()) {
            if (neighborType.getValue() != null && neighborType.getValue().equals(nt.toString())) {
                NEIGHBORHOOD_TYPE = nt;
            }
        }
        for (EdgeType et : EdgeType.values()) {
            if (edgeType.getValue() != null && edgeType.getValue().equals(et.toString())) {
                EDGE_TYPE = et;
            }
        }
        for (CellShape cs : CellShape.values()) {
            if (cellShape.getValue() != null && cellShape.getValue().equals(cs.toString())) {
                    CELL_SHAPE = cs;
                }
            }
        initializePolygonGrid();
        colorAllCells();
        myAnimation.play();
    });
}
```

In general, the code is well-readable and does not need many comments to understand. I did my best to name all variables and methods as intuitively as possible, so that the reader would see that every method does what they expect
and variables store the data they expect. Knowing that there are three enum classes defined for NeighborhoodType, EdgeType, and CellShape, the above method is easy to understand in the scope of the project.

The backend is flexible because we used as much abstraction as possible - adding a new simulation is done by creating a new Cell implementation and implementing one abstract method which embodies the "rules" of the simulation. Adding new neighborhood and edge types, as well as cell shapes, both make use of abstract classes and Java enums. Thus, adding features relating to these is convenient. However, the front-end is less so flexible, because the front-end was coded somewhat "separately" from the back-end. For example, if you were to add a new type of Grid, say a Pentagon grid, to the backend, you would also have to do separate work to display a "Pentagon" choice in the drop-down GUI menu for cell-shapes. Thus, I feel that implementing features as a whole is convenient/flexible in the backend, but less so for the front end. For example, in the method shown above, the ChoiceBox for cellShape is constructd with all the menu options as parameters - if we added a Pentagon grid option, we would have to add a CellShape enum called Pentagon and add it to this parameter list, and then we would have to check for that in logic elsewhere in the class.

The backend (Model and Controller) are completely independent from the View package, not containing any JFX code in them. The View.Main class is dependent on some of the backend Controller classes, however, such as Grid, Neighbors, and the Enum classes we made.

### Overall Design

New Simulation Type:
* In order to add a new type of simulation to our program, one would have to do 3 things.  First, the programmer would have to create a new cell subclass for this simulation that extends the abstract Cell super class.  Within this new subclass, the programmer would have to implement the rules for updating this new type of cell in its updateCell() method.  Second, the programmer would have to create a properties file for this new simulation type as well, saving this file in the Resources package in the src folder.  This file would need to reference a configuration.csv file for said simulation as well.  The final step to adding a new simulation type would be to add one additional "else if" statement to the simCellPicker() method found in Grid.java class.  This additional statement would look something like this:
```java
else if(simType.equalsIgnoreCase("NEW_SIMULATION_NAME")){
    return new NewSimulationCell(x, y, myData.getStateAt(x, y));
}
```

We have three packages in our source code. The View package contains Main.java, which contains nearly all the code for rendering the JFX components - the GUI, the simulation cells, etc. Model contains the logic for the individual cell classes (one cell per simulation), and these classes contain the implementation of the simulation's "rules" in the updateCell() method. Controller contains the classes that the View package uses to access the grid's state and determine what to display - Grid.java, Neighbors.java, are the two main examples. Controller also has classes like Data.java which Main uses to initialize the grid and to do other file-related tasks, like saving configurations.

Before coding anything, our group made a few design decisions. First, we were going to maintain the simulation's state with a 2D array of Cell objects. Every simulation would have its own Cell implementation, and this implementation would implement the abstract method updateCell(), since each simulation has different rules. The Main.java class would access a cell's state, and have associated a certain JFX Node to that cell's state (either an image or some polygon shape). By knowing the cell's position in the grid in terms or rows and columns, the Main class knows where to display the JFX Node. Thus, the Main class contains a JFX animation that essentially loops over the simulation grid and updates the state of each cell, while also drawing JFX nodes in appropriate places. The backend became more complicated as new features were added, but the overall structure was preserved. A number of backend classes were added to help the Grid class maintain the simulation's state, such as Neighbors.java and some enumerations.

Some issues we wrestled with was whether we should abstract the Grid itself, or the Cell objects. Should we have a GameOfLife Grid, or a GameOfLife Cell? We chose to abstract cells because we thought the different between each simulation is the way cells interact with eachother, whereas the grid can be different within the same simulation (based on the neighbor type, edge type, and cell shape). I personally wrestled with design issues in the View package, as most of the code is in the Main class. I think the Main class could have been split up and not as centralized, as the readability suffered greatly once I added a full GUI.

### Flexibility

I think our code is flexible in the case of adding a new simulation, due to the way we abstracted the Cell class. As aforementioned, adding a new simulation only requires adding a new Cell implementation, adding a configuration resource file, and altering a chain of if statements in the Controller.Grid class. On the other hand, extending new types of Neighborhoods, Edge types, and Cell shapes is not as convenient. There are enumerations for each Grid parameter, and there are more places in the code that need editing. As of right now, there is a Neighbors class that is abstract, and has three implementations for each type of Neighborhood Type we currently support (Cardinal, Complete, and Corners). For each implementation, you must implement a different set of neighbors for Triangular, Square, and Hexagonal squares. Thus, every additional Neighborhood type requires the creation of a new Neighbors implementation and the implementation of as many methods as there are cell shapes. Similarly, adding a cell shape would require implementing a method for that cell shape in each Neighbors implementation. As a minor note, we have an inheritance hierarchy AND a Enumeration class for Neighborhood types, so that Enumerations class must also be maintained accordingly. On the front end, representing the addition of new back-end features (such as new grid parameters like neighborhood types, cell shapes, and edge types) would require an understading of the Main class, making the Main class less flexible. For example, the setRunButton() method shown above creates one ChoiceBox for each type of Grid parameter, and then fills the ChoiceBox will all of the menu options as parameters. To add a Neighborhood type, we would have to create a new Enumeration as mentioned before, and add it to this set of parmaeters. To add an entirely new grid parameter other than cellShape, neighbohood type, and edge type, we'd have to create a new choice box and possibly a new Enum class and then fill that choice box with those enum values. In both cases, sets of if statements must be added or altered throughout the Main class:

```java
private void initializeNeighbors(){
        try {
            if (styleResources.getString("NeighborType").equalsIgnoreCase("COMPLETE")) {
                NEIGHBORHOOD_TYPE = NeighborhoodType.COMPLETE;
            } else if (styleResources.getString("NeighborType").equalsIgnoreCase("CARDINAL")) {
                NEIGHBORHOOD_TYPE = NeighborhoodType.CARDINAL;
            } else if (styleResources.getString("NeighborType").equalsIgnoreCase("CORNER")) {
                NEIGHBORHOOD_TYPE = NeighborhoodType.CORNER;
            } else{
                showPopup(errorResources.getString("NeighborError"));
            }
        } catch(MissingResourceException e){
            showPopup(errorResources.getString("MissingResource"));
        }
    }
```
a new "else if" statement would need to be added for additional Neighborhood types here, to represent the new NeighborhoodType enum added. Each one of the current Grid parameters has an associated initializePARMATER() function that
simply checks the resource file to determine what choice was set in there. in setRunButton(), the GUI-menus associated with each paramater are checked when Run is pressed, in case the user has changed the parameters. Thus, these are the two places where code would need to be altered for a new choice for a certain parameter. I cannot explicitly say what work would need to be done to add an entirely new parameter, as it depends on the parameter. At the very least, a new ChoiceBox must be made, filled with options based on Enumerations made for that new parameter, and then a new initializePARMATER() method must be written in the Main class to check for it in the resource files.

The implementation of Neighborhood types, a feature my teammate Ryan implemented, is interesting because he used a Neighbors.java abstract class, and associated Enumeration class. The abstract Neighbors class is implemented differently because for every type of Neighborhood, the act of finding which cells constitute as neighbors is different. The Enumeration is used to check which type of Neighborhood the simulation is using, rather than type-checking the Neighbors instance we are storing. The reason the Enumeration itself isn't sufficient is because for every type of neighborhood, which cells count as neighbors will differ. Ryan could have used only the Enumeration and simply have checked if NEIGHBORHOOD_TYPE == CORNER, findCornerNeighbors(), and have an implemented method for each neighborhood type. But then, for each neighborhood type, finding the neighbors also depends on the edge-type and cell shape. So, in every if-condition for each neighborhood type, there would be X nested if conditions for X cell shapes. Instead, he has one implemented Neighbors class for each neighborhood type, and each class implements a method for finding neighbors for each cell shape. Currently, it may seem that having the nested if conditions is just as readable as the use of an abstract class, but if more shapes and neighborhoods were added, the nested if statements would grow rather quickly, affecting readability.

The flexibility of Neighborhood types is reliant on editing the Enumeration class, which is simple, and creating a new implementation of Neighbors and implementing methods that return the neighboring Cells for each cellShape. On one hand, it's clear that this is what needs to be done to add a new Neighborhood Type, but given this design, it would be difficult to add a new CellShape option, as every single implementation of Neighbors would need an additional method for that type of cell shape, and there would need to be an abstract method for that cell shape in the parent Neighbors abstract class.

### Your Design

I created an abstraction for various cell shapes on the front end, the PolygonGrid abstract class. It helped the design by refactoring all of the JFX shape logic out of the Main class. Main no longer needs to know which shapes to display, and where to display them - PolygonGrid's implementations will do all of this work. PolygonGrid contains a 2D Array of Polygon objects, such that each Polygon object is associated with a cell in the simulation. These Polygon objects maintain position, so the Main class can check the cell in row i, column j, and accordingly display whichever Polygon resides at cellViewGrid[i][j] from PolygonGrid.java. Each implementation of PolygonGrid does all of the geometry-related math to find all of the coordinates for each polygon in the grid, and stores them in the 2D Array. Thus, adding a new Cell Shape would be done by creating a new implementation of PolygonGrid and implementing one method, initializeShapes(), which takes the number of rows and cols in the simulation, and each cell's width and height (which is calculated in the constructor and passed in).

One of the design checklist issues I faced, but could not resolve, was the delegation of work. "Delegate work: create several classes that work together distributing intelligence, rather than one "smart" class and a few "dumb" helpers". In the View package, the Main class is the "smart class" that does basically all the work, relying on some help from the PolygonGrid hierarchy. The Main class wasn't so clustered and dense until I added the GUI during the Complete phase of the project - the GUI added a lot of code and helper methods because it required the construction of many JavaFX objects, all of which need to be accessible by the Main class, since the Main class is the one displaying the JavaFX window. I could have refactored much of the GUI code into a different class, each class for one GUI component, and then sent all the JavaFX Node objects to the Main class, given more time.

[Hexagon/Triangle polygon grids](https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team01/commit/fb1b6100c2047d6fb5056ff44415d217e4d69c03)
This commit represents primarily new development, the adding of the PolygonGrid class and its extended child classes. One of the design issues I wrestled with in implementing this feature was whether or not I should refactor the code for a Square grid. For JavaFX, there is a native Square and Rectangle class that also extends Shape, and we wrote the Basic part of the assignment using the Rectangle class. To use total abstraction, I could have refactored all the code related to rendering JFX Rectangles to an implementation of PolygonGrid called RectangleGrid, where I would have implemented initializeShapes() and constructed four-sided polygons, calculating the coordinates of each vertex for each polygon. The trade-off for this absolute abstraction was that the code for JavaFX Rectangles was much simpler - a Rectangle could be constructed just by passing in the coordinates of its top left vertex, and its width and height.
```java
private Node newCellNode(int row, int col, int state) {
        Shape cellShapeView;
        if (CELL_SHAPE == CellShape.HEXAGON || CELL_SHAPE == CellShape.TRIANGLE) {
            cellShapeView = new Polygon(myPolygonGrid.getCoordinates(row, col));
        }
        else {
            cellShapeView = new Rectangle(row * cellWidth, col * cellHeight, cellWidth, cellHeight);
        }
        if (useImages) {
            ImageView cellImageView = new ImageView(cellImages.get(state));
            cellImageView.setClip(cellShapeView);
            return cellImageView;
        } else {
            var color = cellColors.get(state);
            cellShapeView.setFill(color);
        }
        return cellShapeView;
    }
```

This code snippet shows the logic for constructing a new Rectangle object in the correct place. The logic was so simple that refactoring that one line of code out to an entirely new class seemed unnecessary. Of course, the trade off is that the line of code here would be deleted, and the if-statements that check the enum CELL_SHAPE would also be deleted. Because the difference was minimal, I opt to leave the code as is. The only assumption/dependency from this code that affects the overall design of the program is that the Main class can only display JavaFX Nodes that are derived from the Shape class. Even if the simulation is displaying images, the images are re-fitted to a Shape object.

### Alternate Designs

Design Decision 1: The Neighbors inheritance hierarchy and Enumeration

As aforementioned, we created an abstract Neighbors class with three concrete implementations: CompleteNeighbors, CardinalNeighbors, and ConcreteNeighbors. Instead of having one Neighbors class with many nested if statements that accounted for every combination of Neighborhood type, Edge type, and Cell shape, we used a combination of inheritance and enumerations. The alternate designs considered were one method with complex conditional logic for each combination of the three grid parameters, or a set of classes, each one representing a combination of the parameters. For example, CompleteNeighbors would extend Neighbors, and then CompleteSquareNeighbors would extend CompleteNeighbors, etc. Or, we could have simply had CompleteSquareNeighbors extend Neighbors, and CompleteHexagonNeighbors extend Neighbors, etc. In either situation, adding a new neighborhood type would require the creation of many new classes and modularity of our code would have suffered. The middle ground between one class for each combination of the three grid-parameters and one class for all of them was the solution we ended up with. It was hard to tell which was harder to extend - adding new cases to a complicated set of if-else conditions, or writing up a new set of classes each of which implements the same method in different ways. Creating a new set of subclasses for each combination of the grid-parameters would also increase the if-else checks in Grid.java that checks the enum NEIGHBORHOOD_TYPE to determine which subclass of Neighbors.java to actually construct. Thus, that method would also grow long and unreadable. I prefer the design we chose, but would have been content with a different decision so long as it wasn't one of the extremes.

Design Decision 2: One Cell implementation per Simulation

When we first decided to have an abstract Cell class, and have one implementation for Simulation, we also considered the idea of having as many Cell implementations as there are states for each simulation. For example, an "ON" cell and an "OFF" cell for GameOfLife. We immediately saw how troublesome this would be - although the implementation of the "rules" for each subclass would be easier, as only the rules for one state would have to be coded, the transition between states would become messy. During each step of the simulation, we would have to gather the neighboring cells and then check their type, and then convert the Cell in question to a different type (or construct a new Cell of that type) to represent the change in state. This seemed like poor design immediately to us, so we decided against it. At the time, we didn't realize we had to separate Model and View such that Model had no JFX dependencies. So, each Cell implementation back then contained a JFX Node that represented it's view in the simulation. For example, a GameOfLife Cell would contain a JFX Rectangle that would be colored either red or white based on its state - if we had chosen the alternate design, the logic for maintaining and coloring the contained JFX shape would have been cleaner, but this tradeoff was rendered irrelevant when we removed all JFX dependencies from the Model package. I definitely prefer the design we chose.
