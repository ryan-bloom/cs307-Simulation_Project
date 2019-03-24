simulation
====

This project implements a cellular automata simulator.

Names: Ryan Bloom (rab83), Rohan Reddy (rr195), Matt Rose(mjr68)

### Timeline

Start Date: Monday February 18

Finish Date: Friday March 8

Hours Spent: combined ~100+

### Primary Role
Ryan Bloom: My role was primarily focused on backend work.  I worked to fulfill the "simulation" requirements every time that a new set of project requirements were posted (test, basic, complete).  Therefore, I was responsible for creating the Cell abstract and concrete subclasses and implementing all of the different simulation rules.  I also wrote the tests for these classes.  I was also responsible for creating the Neighbors abstract and subclasses and writing tests for these classes as well.  Furthermore, I took on the responsibility of refactoring, organizing, and dividing our classes into the separate MVC packages, since for the "test" part of this project, we had not divided our classes in this manner. In order to do this, I moved and worked with the Grid class, and helped to remove all javafx references from any class not found in the view package.  I, along with both of my partners, also worked to coordinate and lead meetings and tried to help with other parts of the project wherever I could.      

Rohan Reddy: Visualization/gui/user inputs

Matt Rose: I worked on the configuration portion of the project. I created most of the CSV and properties files, as well as the classes that accessed and created them. This lead to me handle exceptions and create many try/catches and popups regarding any possible errors. I created the Data class, the FileCreator class, and edited Main to allow for loading and saving of files. I also helped my teammates with their parts when possible and vice versa.

### Resources Used
1) To understand hexagonal grid layouts:  https://www.redblobgames.com/grids/hexagons/
2) To help turn ImageView into various shapes: https://stackoverflow.com/questions/20708295/put-a-image-in-a-circle-view
3) To understand Game Of Life rule changes for triangle and hexagonal grids: https://wpmedia.wolfram.com/uploads/sites/13/2018/02/15-3-4.pdf
4) To understand enum types further: https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
5) The error handling lab helped with the pop-up messages and custom exceptions: https://coursework.cs.duke.edu/compsci307_2019spring/lab_exceptions/tree/master
6) To help understand FileWriter and creating csv and properties files: https://www.geeksforgeeks.org/writing-a-csv-file-in-java-using-opencsv/
7) All links provided in simulation assignments that described each type of simulation rules (Game of life, Percolation, Fire, RPS, Segregation, Predator Prey)


### Running the Program

View.Main class: This is the main JavaFX application class that starts the program. It requires resource files from src/Resources, and config files from /data.
The config files have a specified format, the first line describes the dimensions in x, y format, and the rest of the lines form a matrix of integers, where each integer corresponds
to a cell state. The integer positioned at 0,0 in this "matrix" will represent the top left cell, so on and so forth. There can be no underlying whitespace in these configuration files,
and only integers will be read from the file. The Controller.Data class does all this work and is therefore essential to starting the program.


We will discuss this in more detail later, but the Style properties file is extremely important in running the program. It dictates the initial simulation type, the shape of cells, the neighborhood types, and the edge types. One way to test different features is to manually change the Style properties files. The types of possible simulations is: Fire1, GameOfLife followed by any of 1-5, Percolation followed by any of 1-5, PredatorPrey1, RPS1, and Segregation1. The possible cell shapes are: Square, Hexagon, Triangle. The possible EdgeTypes are Toroidal, Semitoroidal, and Finite. The possible neighborhood types are Complete, Cardinal, and Corner.

The two important classes that interact with files are the Data and FileCreator class. The Data class constructs the grid from which the Grid is created in one of three ways. It can take a CSV filename as a parameter and construct a 2D array based on the values in the CSV file. It can also take an array of probabilities that add up to one corresponding to the state of the given index and construct a grid randomly based on that array. For example, to construct a Percolation grid, the array [0.3, 0.6, 0.1] could be passed so that there is a 30% chance of a space being blocked (state 0), 60% chance of open (state 1), and 10% of being percolated (state 2). Thirdly, a grid can be constructed using an array with a set amount of spaces with each state. For Percolation, an array of [8, 16, 1] would construct a grid with 8 blocked cells, 16 open cells, and 1 percolated cell.

The FileCreator class writes CSV and properties files based on the given state of a simulation. It writes the values necessary to load the simulation at a later date, including the states of all the cells, the colors of the cells, the Simulation type, etc. 



We expect the program to handle multiple different scenarios without crashing. In Data for the constructor that takes a file name, we checked for number format exceptions such as a non-integer String be parsed to an integer, a CSV file without the sufficient number of elements, a file name input parameter that cannot be found, a URISyntax exception, and a null input parameter. Also, for the constructor that randomly assigns a predetermined number of each state to the grid, it throws an exception if there are not enough states for the given grid size. For the constructor that assigns states based on given probabilities, it throws an exception if the probabilities in the array do not add up to one.
In FileCreator, which writes new CSV files and properties files when the user saves the program by pressing "S" or loads a previously saved program by pressing "L", we also caught many IO exceptions and null pointer exceptions. 
In Grid, an error was thrown if the type of simulation was not found.

Also, in Main, we used try/catches when initializing every Resource Bundle in case it was not found. If this was caught, or any exceptions from Data or FileCreator were caught, a pop-up is shown to the user with a specific error message regarding what went wrong. From there, the program either terminates or continues depending on the severity of the exception.

There are several CSV files in our data folder if you look, and these are just different configurations for display or testing purposes. The program is designed to start with the CSV file named in the Style properties file by default, and then the user can change it from there. The format of the CSV file is as follows: the first two entries represent the height and width of the grid in cells, and the values that follow are the initial states of the grid that are read in line by line into a 2D array in Data.

There are also many properties files in the Resources folder. There is ErrorMessages, which contains the messages that accompany each specific error and are displayed in the pop-ups to the user when they occur. There is Style, which contains certain parameters about how the simulation should look in terms of initial Simulation type, cell shapes, edge types, and neighborhood types. There is also SimulationInfo, which gives the number of states for each simulation. In the Simulation States files, there is the state number and the corresponding state. For example in Game of Life, 0 means dead and 1 means alive. Each of the other simulation specific files correspond to a CSV file; for example, Fire1.properties corresponds to setting up Fire_Config_1.csv. The GUIText properties file contains all of the text that is used in the graphs and buttons in the display. 

Finally, User_Simulation.properties and User_Simulation.csv are the files that are created when a user saves a simulation and can be used to load the simulation later.

There are test files in both the Model package (cell test files) and in the Controller package (neighbor test file) that are used to test our program.  We created separate test files for each cell subclass, but we decided to create one neighbors test file that reaches all of the neighbor subclasses anyway.   

Features implemented: We were able to successfully implement all six types of simulations required for this project, including Game of Life, Percolation, Fire, RPS, Segregation, and Predator Prey.  In order to switch between these simulation types, there is a user input dropdown menu where users can simply select which simulation they want to run, and this dropdown then sends information to our program in order to run the correct simulation.  

We were also able to implement three different possible cell shapes (square, hexagonal, and triangular), three types of neighborhood types (complete, cardinal, and corner), and three different edge conditions (toroidal, finite, and semitoroidal).  The corner neighborhood type was a feature that we made up, where only neighbors touching the current cell's corner are counted.  In other words, it is the exact opposite of the cardinal neighborhood.  The semitoroidal edge type was another creation of ours, and this edge type allows neighbors to jump directly across the screen from left to right and top to bottom, but does not allows for diagonal jumps.  For example, when implementing semitoroidal edges, a square cell in the top left corner would only have 7 neighbors as opposed to 8 because its upper left neighbor does not jump to the bottom right cell in the grid. 

We also implemented several user control features that a user can access while running the simulation. The basic controls for the animation are all keyboard shortcuts (not displayed on the GUI, but they are all listed and commented out in
src/Main.java on handleKeyInput(). Pausing, individual steps, speeding up and slowing down, are all key presses. The types of neighborhoods, the types of plane/grid edges, and the type of shape each individual cell resides in
are all configured initially in resource files, but can be altered using the drop down menus in the GUI. It is best to alter these values before pressing Run, although some alterations can be made during the animation, such as neighborhood or edge types. Changing the cell shape
during the simulation is not recommended - though it seems to work functionally with no bugs, when transforming shapes there is overlap with the old shapes (I could not remove them from the scene efficiently), causing the visualization of the simulation to become cluttered.
There is a toggle for Colors and Images, preselected to Colors. There are as many color picker tools are there are cell states, and they are already initialized to the colors from the resource files to let the user know which cells they are changing. If Image is selected,
the user will be prompted to select ONLY image files from their machine. The user must select exactly the number of images as there are cell states, and in the order the color picker tools are displayed. For example, if the top color picker is Black, and you want to replace Black cells
with a picture of a Dog, you must select the Dog image first.
   

Assumptions or Simplifications:  When designing and creating our simulation project, there were a few assumptions and simplifications that we made.  First, we assumed and ensured that our hexagonal grid set up uses pointed topped (as opposed to flat topped) hexagons and that the grid set up would follow the "even-r" horizontal layout, where each even numbered row of hexagon cells is shifted to the right.  This layout must remain the same because the neighbors of each cell are dependent on whether or not the cell is in an even or odd row.  Additionally along the same lines, when configuring our grid with triangular cells, we made it such that the first triangle in the upper right-hand corner (coordinates 0,0) will be displayed upside down.  This is an essential assumption because, when checking for neighbors of triangle cells, the orientation (upside down or right side up) impacts the neighbors.  Therefore, our code assumes that the firs triangle is upside down and determines the orientation of all other triangle cells based on this.    

We also simplified the loading and saving feature because the user cannot save and load the grid configuration in the same run of the program. Because of the way files are written, the user is only able to load from a previous run of the program. If the user presses the "S" and "L" key and the configuration file has not been written yet, a message pops up telling them to rerun the program if they want to load their configuration.

Other assumptions that we made had to do more with game play settings for various simulations.  With respect to the Game of Life simulation, multiple variations on the rules were found when using hexagonal or triangular cells.  We made the simplification to decide on one of each set of rules to implement.  For hexagonal cell grids, the Game of Life implements the 3,5/2 rule while the triangular grid implements the 2,7/3 rule.  Second, with respect to the RPS simulation, we designed our code such that if multiple colors meet the set threshold (for example, there are > 3 rock and paper cells surrounding a scissor cell), then first type of cell that is found to have more than threshold count takes over the cell in question.  Third, with respect to the predator prey simulation, if a shark is directly next to a fish, whichever's index is closer to 0,0 will move first.  Therefore, the fish could escape if it is closer to the origin, or shark could eat the fish if the shark is closer to the origin.

Known Bugs: One known bug that we have is in the predator prey simulation.  When updating these cells, the cell occasionally calls its update method multiple times per step().  We believe that this occasionally happens when a fish cell moves to the location directly next in line in the grid to be updated (next in line meaning nex i,j coordinate combination in the nested for loops in main.java).  When this happens, we believe that this new fish cell then gets updated again and might move to another location before the animation displays its first initial move.
Another bug that happens when using Hexagonal cells that we could not figure out - the initial configuration file is transposed when displayed on the window. That is, the display will essentially swap rows for columns when displaying the cell states of the initial configuration.

Extra credit:


### Notes


### Impressions
We believe that this project really helped us all improve and understand how to better construct and organize large coding projects.  Upon discussing with the team, we agreed that this project grew into a much larger assignment than we expected, but that our overall design was far better than the first two assignments we had, making new features and requirements more easily implemented.  We believe that our initial planning before starting the test implementation greatly helped make our future work easier and that moving forward, we will always try to come up with a well organized plan with many classes before diving into any project.  

Additionally, the fact that each new set of requirements was broken down into the same three categories every time (simulation, configuration, visualization) really helped us to compartmentalize our work and avoid many merge conflicts.  Although these divisions did help in that regard, the fact that we each took the same section every time slightly inhibited our ability to learn new things (for example, I focused on the simulation requirements and therefore did not learn much about gui development or property file usage).  
