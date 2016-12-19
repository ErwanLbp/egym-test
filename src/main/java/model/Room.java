package model;

import factory.MazeFactory;
import iterator.ClosestObjectIterator;
import log.Log;

import java.util.*;

/**
 * <h1>model Room</h1>
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

    public Room(int id, String name) {
        this.id = id;
        this.name = name;
        this.objects = new ArrayList<>();
        this.sides = new HashMap<>();
        this.setWallOnAllSides();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ObjectR> getObjects() {
        return objects;
    }

    public void setObjects(List<ObjectR> objects) {
        this.objects = objects;
    }

    public Map<Direction, InBetween> getSides() {
        return sides;
    }

    public void setSides(Map<Direction, InBetween> sides) {
        this.sides = sides;
    }

    public void addObject(ObjectR objectR) {
        for (ObjectR o : objects) {
            if (o == objectR) {
                Log.info("The room " + id + " " + name + " already contains the object " + objectR.getName());
                return;
            }
        }
        this.objects.add(objectR);
    }

    private void setWallOnAllSides() {
        this.sides.put(Direction.EAST, MazeFactory.getInstance().createWall());
        this.sides.put(Direction.NORTH, MazeFactory.getInstance().createWall());
        this.sides.put(Direction.SOUTH, MazeFactory.getInstance().createWall());
        this.sides.put(Direction.WEST, MazeFactory.getInstance().createWall());
    }

    public void setSide(Direction direction, InBetween inBetween) {
        this.sides.put(direction, inBetween);
    }

    public InBetween getSide(Direction direction) {
        return this.sides.get(direction);
    }

    public Room getRoom(Direction direction) {
        if (getSide(direction).canGoThrough())
            return ((Door) getSide(direction)).getOtherSide(this);
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;

        Room room = (Room) o;

        return getId() == room.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public Iterator iterator() {
        return new ClosestObjectIterator(this);
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name;
    }

    public boolean contains(ObjectR objectR) {
        return objects.contains(objectR);
    }
}
