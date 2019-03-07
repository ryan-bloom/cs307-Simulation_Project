simulation
====

This project implements a cellular automata simulator.

Names: Ryan Bloom (rab83), Rohan Reddy (rr195), Matt Rose(mjr68)

### Timeline

Start Date: Monday February 18

Finish Date: Friday March 8

Hours Spent: combined ~100+

### Primary Roles
Ryan Bloom: My role was primarily focused on backend work.  I worked to fulfill the "simulation" requirements every time that a new set of project requirements were posted (test, basic, complete).  Therefore, I was responsible for creating the Cell abstract and concrete subclasses and implementing all of the different simulation rules.  I also wrote the tests for these classes.  I was also responsible for creating the Neighbors abstract and subclasses and writing tests for these classes as well.  Furthermore, I took on the responsibility of refactoring, organizing, and dividing our classes into the separate MVC packages, since for the "test" part of this project, we had not divided our classes in this manner. In order to do this, I moved and worked with the Grid class, and helped to remove all javafx references from any class not found in the view package.  I, along with both of my partners, also worked to coordinate and lead meetings and tried to help with other parts of the project wherever I could.      

Rohan Reddy: Visualization/gui/user inputs

Matt Rose: Configuration/properties files

### Resources Used
1) To understand hexagonal grid layouts:  https://www.redblobgames.com/grids/hexagons/
2) To help turn ImageView into various shapes: https://stackoverflow.com/questions/20708295/put-a-image-in-a-circle-view
3) To understand Game Of Life rule changes for triangle and hexagonal grids: https://wpmedia.wolfram.com/uploads/sites/13/2018/02/15-3-4.pdf
4) To understand enum types further: https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
5) All links provided in simulation assignments that described each type of simulation rules (Game of life, Percolation, Fire, RPS, Segregation, Predator Prey)


### Running the Program

View.Main class: ROHAN

Model.Data files needed: MATT

Interesting data files:  
FILES USED TO START THE PROJECT (CLASSES CONTIANING MAIN) @ROHAN 

ERRORS WE EXPECT THE PROGRAM TO CATCH AND HANDLE WITHOUT CRASHING(@MATT)
DATA AND RESOURCE FILES REQUIRED BY THE PROJECT(PROPERTIES AND FORMAT FILES AND WHAT THEY DO)

There are test files in both the Model package (cell test files) and in the Controller package (neighbor test file) that are used to test our program.  We created separate test files for each cell subclass, but we decided to create one neighbors test file that reaches all of the neighbor subclasses anyway.   

Features implemented: We were able to successfully implement all six types of simulations required for this project, including Game of Life, Percolation, Fire, RPS, Segregation, and Predator Prey.  In order to switch between these simulation types, there is a user input dropdown menu where users can simply select which simulation they want to run, and this dropdown then sends information to our program in order to run the correct simulation.  

We were also able to implement three different possible cell shapes (square, hexagonal, and triangular), three types of neighborhood types (complete, cardinal, and corner), and three different edge conditions (toroidal, finite, and semitoroidal).  The corner neighborhood type was a feature that we made up, where only neighbors touching the current cell's corner are counted.  In other words, it is the exact opposite of the cardinal neighborhood.  The semitoroidal edge type was another creation of ours, and this edge type allows neighbors to jump directly across the screen from left to right and top to bottom, but does not allows for diagonal jumps.  For example, when implementing semitoroidal edges, a square cell in the top left corner would only have 7 neighbors as opposed to 8 because its upper left neighbor does not jump to the bottom right cell in the grid. 

We also implemented several user control features that a user can access while running the simulation.... ROHAN HERE
   

Assumptions or Simplifications:  When designing and creating our simulation project, there were a few assumptions and simplifications that we made.  First, we assumed and ensured that our hexagonal grid set up uses pointed topped (as opposed to flat topped) hexagons and that the grid set up would follow the "even-r" horizontal layout, where each even numbered row of hexagon cells is shifted to the right.  This layout must remain the same because the neighbors of each cell are dependent on whether or not the cell is in an even or odd row.  Additionally along the same lines, when configuring our grid with triangular cells, we made it such that the first triangle in the upper right-hand corner (coordinates 0,0) will be displayed upside down.  This is an essential assumption because, when checking for neighbors of triangle cells, the orientation (upside down or right side up) impacts the neighbors.  Therefore, our code assumes that the firs triangle is upside down and determines the orientation of all other triangle cells based on this.    

Other assumptions that we made had to do more with game play settings for various simulations.  With respect to the Game of Life simulation, multiple variations on the rules were found when using hexagonal or triangular cells.  We made the simplification to decide on one of each set of rules to implement.  For hexagonal cell grids, the Game of Life implements the 3,5/2 rule while the triangular grid implements the 2,7/3 rule.  Second, with respect to the RPS simulation, we designed our code such that if multiple colors meet the set threshold (for example, there are > 3 rock and paper cells surrounding a scissor cell), then first type of cell that is found to have more than threshold count takes over the cell in question.  Third, with respect to the predator prey simulation, if a shark is directly next to a fish, whichever's index is closer to 0,0 will move first.  Therefore, the fish could escape if it is closer to the origin, or shark could eat the fish if the shark is closer to the origin.

Known Bugs: One known bug that we have is in the predator prey simulation.  When updating these cells, the cell occasionally calls its update method multiple times per step().  We believe that this occasionally happens when a fish cell moves to the location directly next in line in the grid to be updated (next in line meaning nex i,j coordinate combination in the nested for loops in main.java).  When this happens, we believe that this new fish cell then gets updated again and might move to another location before the animation displays its first initial move.   

Extra credit:


### Notes


### Impressions
We believe that this project really helped us all improve and understand how to better construct and organize large coding projects.  Upon discussing with the team, we agreed that this project grew into a much larger assignment than we expected, but that our overall design was far better than the first two assignments we had, making new features and requirements more easily implemented.  We believe that our initial planning before starting the test implementation greatly helped make our future work easier and that moving forward, we will always try to come up with a well organized plan with many classes before diving into any project.  

Additionally, the fact that each new set of requirements was broken down into the same three categories every time (simulation, configuration, visualization) really helped us to compartmentalize our work and avoid many merge conflicts.  Although these divisions did help in that regard, the fact that we each took the same section every time slightly inhibited our ability to learn new things (such as gui and user inputs)
