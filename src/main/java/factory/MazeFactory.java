package factory;

import model.Door;
import model.Maze;
import model.Room;
import model.Wall;

/**
 * <h1>factory MazeFactory</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class MazeFactory {

    private static MazeFactory instance;

    private MazeFactory() {
    }

    public static MazeFactory getInstance() {
        if (instance == null)
            instance = new MazeFactory();
        return instance;
    }

    public Maze createMaze() {
        return null;
    }

    public Room createRoom(int id, String name) {
        return new Room(id, name);
    }

    public Wall createWall() {
        return new Wall();
    }

    public Door createDoor(Room r1, Room r2) {
        return new Door(r1, r2);
    }

}
