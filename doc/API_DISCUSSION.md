Ryan Bloom (rab83), Rohan Reddy (rr195), Matt Rose (mjr68)

### API Discovery (Categorizing Public Methods):

External
* CompleteNeighbors (grid)
* CornerNeighbors (grid)
* CardinalNeighbors (grid)
* writeCsvFile (main)
* writePropertiesFile (main)
* SimulationException
* Grid 
* fillCellGrid (main)
* resetCell (main)
* updateGridCell (main)
* getCellState (main)
* updateCellState (main)
* Data (main)
* Cell (grid)
* Cell.updateCell (grid)
* getCellState (FileCreator)
* Data.getHeigh
* Data.getWidth
* Data.getStates
* getMyRows
* getMyCols

Internal
* squareNeighbors (CompleteNeighbors, CornerNeighbors, CardinalNeighbors)
* triNeighbors (CompleteNeighbors, CornerNeighbors, CardinalNeighbors)
* Neighbors (Grid)
* updateCell (Cell)
* Cell.resetState
* Cell.getMyCurrentState
* Cell.getMyNextState
* Cell.setMyCurrentState
* Cell.setMyNextState
* HexagonGrid
* PolygonGrid
* TriangleGrid
* InitializeShapes (hex/tri/poly)
* PolygonGrid.getCoordinates
* colorAllCells
* FillColorsList
* updateCellView
* RPSCell (Cell)
* PercolationCell (Cell)
* SegregationCell (Cell)
* PredatorPreyCell (Cell)
* FireCell (Cell)
* GameOfLifeCell (Cell)

Non-API
* InputHandler
* Main.start
* showPopup
* setupConfig

### API Documentation 
External
* Front end selects simulation type in Main.java by changing SIMULATION string variable (i.e. RPSCell).  This will then trigger the backend to initialize the correct type of Cell for said simulation.  Then the main method will start by filling the cell grid by calling fillCellGrid and stepping through calling the backend model to update each cell according to this specific cell's rules.  The java methods called include updateCell on the backend, as well as updateCellView on the front end.  UpdateCell triggers several other methods to be used, including resetCell as well as determining the correct neighborhood for the cell in question.  

Internal
* In order to add a new simulation, the new developer will create another subclass that extends Cell.java.  Then they will implement the updateCell abstract method which will handle the rules for this new simulation.  Finally, they will need to create a few csv configuration files entitled with the name of this new simulation and then add an additional if statement in Grid.java to check for SIMULATION string being equal to this new simulation type. 

### API Vision
* We could divide some of our classes into smaller subclasses that handle fewer responsibilities each.  We could also organize each package into several sub-packages within Model, View, and Controller packages.  This would make methods easier to find in order to do a task.
* We could simplify methods by creating and using private helper methods.  This would make each method more readable and more easily used.  We could also have stronger naming conventions for each method and class.  
* Instead of taking in CellShape and EdgeType, we could create 27 concrete subclasses of Neighbors abstract class that implements all combinations of the 3 neighborhoods, 3 edge types, and 3 cell shapes.  Therefore, there would be no need to pass the edge type and cell shape as a parameter to the neighborhood.  Instead, there would simply need to be some conditional statements to create the correct of the 27 concrete subclasses.  
* We should add a dropdown menu of all potential simulation types to the front end that would then set the SIMULATION string variable.  Therefore, users do not have to edit the code directly, the could simply a different simulation type from the menu and a cascade of events would correctly select the simulation in the backend. 
* Ideally, for someone to add a new simulation, they would simply have to create one additional subclass that extends Cell abstract class with the new simulation rules.  In order to take out the need to extend our if statements in Grid.java, there might be a way to loop through all the subclass names and find the potential simulation names, and store these names in a List.  Then we can just loop through this list to create our if statements as opposed to manually going in and adding additional cases.  We could also use the indices of the List with all potential simulations and match up the simulation name to its index.   
