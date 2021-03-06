
============================
=== FILES IN THE ARCHIVE ===
============================
* This README.md 														: README.md
* The folder containing the project										: egym-test-erwan-le-batard-poles/
	+ The sources folder													: src/
	+ The files needed by the program										: map.xml config.txt
	+ The test_cases folder (used for the tests of the GameTest class)		: test_cases/
	+ The log file 															: log.txt
* The javadoc folder  													: javadoc/
* The folder with the Tests Coverage Report								: test-coverage-report/
* The folder with the tests results										: test-results/
* My git logs															: git_log.txt


=================
=== FEW NOTES ===
=================
The program need at least to arguments : 
	- The filename of the map
	- The filename of the configuration
Theses files need to be at the root of the project.
There also need to be a log.txt file there, which will contains the logs of the program.

I assumed that the files were correctly filled :
	- The map file doesn't have an direction's id which doesn't match a room's id
	- The configuration file contains only objects that are in the map file

To execute the tests : 
	- The tests of GameTest needs some tests cases files, it will look for them in "test_cases/". Place this folder at the root of execution of the test


Every line of code is written by me. The Log class is a class a used in some previous projects so it contains more functions than I used in this one


================
=== PATTERNS ===
================
* Iterator on Room
	+ To move in the maze and pick objects
	+ I had a lot of ideas on how to iterate on rooms, the better way to test some of them was to set an iterator pattern. Plus, we can change the iterator in the future just by changing one line
* Singleton on Maze
	+ The game take place on only one maze, so I forbid the creation of multiple instances of Maze
* Abstract Factory on Maze
	+ It simplify the creation of the objects in the maze
	+ It control the creation of the objects, there is not any instanciation of maze's objects other than in the factory
* Singleton on MazeFactory
	+ So that I can create a maze's object anywhere (because it's static). Plus there is no need of two instances of a factory
* Command
	+ There is two commands : Enter a room and Pick and object
	+ This allows me to keep the historic of the commands, so it's easy to save the route at the end


==========================
=== CRITICS OF MY CODE ===
==========================
* My tests don't cover 100% of the code. I covered all the model, the factory, the command, and the game, which are the importants pieces of the project (I join the Coverage Report)
* Sometimes my code is not very "beautiful" because I wanted to make it work, but then I didn't had time to make it better