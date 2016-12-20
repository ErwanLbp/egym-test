package model;

import factory.MazeFactory;
import iterator.ClosestObjectIterator;
import log.Log;

import java.util.*;

/**
 * The model of a room of the maze
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Room implements Iterable {

    private int id;
    private String name;
    private List<ObjectR> objects;
    private Map<Direction, InBetween> sides;

    /**
     * Will set a wall on every side of the room and no objects in it
     *
     * @param id   The id of the room
     * @param name The name of the room
     */
    public Room(int id, String name) {
        this.id = id;
        this.name = name;
        this.objects = new ArrayList<>();
        this.sides = new HashMap<>();
        this.setWallOnAllSides();
    }

    /**
     * @return The id of the room
     */
    public int getId() {
        return id;
    }

    /**
     * @return The name of the room
     */
    public String getName() {
        return name;
    }

    /**
     * @return The list of objects the room contains
     */
    public List<ObjectR> getObjects() {
        return objects;
    }

    /**
     * To add an object to the room, can't add an object that is already in the maze
     *
     * @param objectR The object to add to the room
     */
    public void addObject(ObjectR objectR) {
        if (objectR == null) return;

        // If the object is not already in the objects list, add it
        if (this.contains(objectR)) {
            Log.info("The maze already contains the object " + objectR);
            return;
        }
        this.objects.add(objectR);
    }

    /**
     * Put a wall on every side of the room, used at initialization of the room
     */
    private void setWallOnAllSides() {
        this.sides.put(Direction.EAST, MazeFactory.getInstance().createWall());
        this.sides.put(Direction.NORTH, MazeFactory.getInstance().createWall());
        this.sides.put(Direction.SOUTH, MazeFactory.getInstance().createWall());
        this.sides.put(Direction.WEST, MazeFactory.getInstance().createWall());
    }

    /**
     * Set one side of the room with the inBetween received
     *
     * @param direction The direction of the side
     * @param inBetween The thing we want to set of this side
     */
    public void setSide(Direction direction, InBetween inBetween) {
        this.sides.put(direction, inBetween);
    }

    /**
     * @param direction The direction we want the side of
     * @return The InBetween on the side received
     */
    public InBetween getSide(Direction direction) {
        return this.sides.get(direction);
    }

    /**
     * To get the room behind one side of the room
     *
     * @param direction The side of the room we want to look behind
     * @return The room behind the side received, or null if we can't go through this side
     */
    public Room getRoom(Direction direction) {
        if (getSide(direction).canGoThrough())
            return ((Door) getSide(direction)).getOtherSide(this);
        return null;
    }

    /**
     * @param objectR The object we want to know if is in the room
     * @return True if the object is in the room, otherwise false
     */
    public boolean contains(ObjectR objectR) {
        for (ObjectR obj : objects)
            if (obj.equals(objectR))
                return true;
        return false;
    }

    /**
     * @param o The object we want to compare with
     * @return True if the obejct is a room and has the same id, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;

        Room room = (Room) o;

        return getId() == room.getId();
    }

    /**
     * Unused
     *
     * @return The hashcode of the id
     */
    @Override
    public int hashCode() {
        return getId();
    }

    /**
     * @return An iterator on the room, the iterator chosen is the ClosestObjectIterator
     * @see iterator.ClosestObjectIterator
     */
    @Override
    public Iterator iterator() {
        return new ClosestObjectIterator(this);
    }

    /**
     * @return A string describing the object
     */
    @Override
    public String toString() {
        return "[" + id + "] " + name;
    }
}
