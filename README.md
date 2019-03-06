simulation
====

This project implements a cellular automata simulator.

Names: Ryan Bloom (rab83), Rohan Reddy (), Matt Rose()

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

View.Main class:

Model.Data files needed: 

Interesting data files:

Features implemented:

Assumptions or Simplifications:  When designing and creating our simulation project, there were a few assumptions and simplifications that we made.  

RPS - if multiple colors meet the threshold, first one found takes over 
Pred Prey - if shark next to fish, whichever's index is closer to 0,0 will move first and therefore fish could escape or shark could eat.  

Neighbor classes: originally wanted to create subclass for each neighbor type (including shape, edge type, neighborhoodType) but this would lead to 27 subclasses and bad design in the changing between neighbor types. Now using enums for shape/edge type and only subclasses for neighborhoodType type.

Known Bugs:

Extra credit:


### Notes


### Impressions

