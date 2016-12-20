package factory;

import model.*;

import java.util.List;

/**
 * Abstract factory to create the objects needed in the maze<br/>
 * It permit to control the creation, so we can later add some styles, why not a magic maze ?
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class MazeFactory {

    /**
     * The unique instance of MazeFactory
     */
    private static MazeFactory instance;

    /**
     * Private constructor to control the creation of the MazeFactory object
     */
    private MazeFactory() {
    }

    /**
     * To get the instance of MazeFactory, the instance is created at the first call
     *
     * @return The unique instance of MazeFactory
     */
    public static MazeFactory getInstance() {
        if (instance == null)
            instance = new MazeFactory();
        return instance;
    }

    /**
     * @param id   The id of the room to create
     * @param name The name of the room to create
     * @return A Room object with the specified parameters
     */
    public Room createRoom(int id, String name) {
        return new Room(id, name);
    }

    /**
     * @return A Wall object
     */
    public Wall createWall() {
        return new Wall();
    }

    /**
     * @param r1,r2 The two sides of the door
     * @return A Door object with the specified parameters
     */
    public Door createDoor(Room r1, Room r2) {
        return new Door(r1, r2);
    }

    /**
     * @param name The name of the objectR to create
     * @return A ObjectR object with the specified parameter
     */
    public ObjectR createObject(String name) {
        return new ObjectR(name);
    }

    /**
     * @param roomStart     The room the player starts in
     * @param objectsToFind The list of objects the player has to find
     * @return A Player object with the specified parameters
     */
    public Player createPlayer(Room roomStart, List<String> objectsToFind) {
        return new Player(roomStart, objectsToFind);
    }
}
