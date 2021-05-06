# *The GAME COLLECTOR*
### COMP503 Assignment Two
##### The second assignment for the Auckland University of Technology's Programming 2 (COMP503) course in Semester 2 of 2020.

---
##### The lastest release of the project can be found [here](https://github.com/itsnotdapro/TheGameCollector/releases)
---

# How to Use

### Basic Usage

The application has 5 main functions, including:

- Adding games to a collection
- Removing games from a collection
- Updating game entries from a collection
- Sorting a collection
- Searching through a collection

The usage of the UI can be seen below: 

![usage1](docs/sample1.png)
![usage2](docs/sample2.png)

When adding a game, if it's title and platform match that of one in the Metacritic database, it will retrieve additionaly info, including:
- The game's box art
- The release date
- The developer
- A list of the game's genres

![usage3](docs/sample3.png)


### Running in Console Mode

 The program has been written as a Swing UI application, but the console UI can be enabled either by running with the arguments: ```console``` or ```c```
```
java -jar GameCollector.jar console
```

 
Or through the file menu in the top corner as seen here (if started through the console, i.e. `java -jar GameCollector.jar`).
If the application is not run throught the console, this button will not function 

![File menu](docs/RunConsoleExample.png)



---

## Project Outline
This project will be a games collection catalouge. It will allow you to store, edit and remove games of various platforms, genres and purchase dates. It will display a list of titles inputted by the user, and sorted using various methods. The user can rate games in their collection, at that rating can be compared to other games. 

The software will use the Chicken Coop API to help with populating fields, as information such as release year, developers, and images, as it can pull this information from the MetaCritic database from a game's title and platform.

The UI will be powered by Java's Swing framework, as it is the easiest way to communcate with a Java backend. As one of the requirements of the project is console I/O, there will be a Java console UI built in, but hidden unless enabled through console arguments.

As this project is a university assignment, and so all code must be written by me, there are some functions that would normally be taken from libraries, such as a JSON parser, that will have to be hand developed instead.

## Project Structure
The JavaDoc for this project can be found [here](https://itsnotdapro.github.io/TheGameCollector/). 

![uml](docs/Diagram.png)

## Project Requirements

>#### Development Log
> A detailed log of the design, implementation and testing phases, detailing when work was done, how long it took,
> what was accomplished, and any problems encountered

>#### Java Source Code
> - Java console I/O 
> - File I/O
> - Relevant enumerated type utilisation

>#### Data Storage
> - Multiple collections 
> - Multiple collection algorithms

>#### Object Oriented Techniques
> - More than nine classes
> - Sensible relationships
> - Abstract and interface classes are utilised
> - Classes are encapsulated
> - Exceptions are used

>#### Runtime Execution
> - Meaningful, non-trivial problem is solved by the program
> - Program is interactive
> - Program is bug free

>#### Source Code Quality
> - Whitespace is consistent
> - All naming is sensible and meaningful
> - Comprehensively JavaDoc'd

<img align="right" width="300" height="75" src="docs/wmark.png"></img>
