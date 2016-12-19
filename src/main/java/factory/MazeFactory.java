package factory;

import model.*;

import java.util.List;

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

    public ObjectR createObject(String name) {
        return new ObjectR(name);
    }

    public Player createPlayer(Room roomStart, List<String> objectsToFind) {
        return new Player(roomStart, objectsToFind);
    }
}
