

Patterns used : 
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
	