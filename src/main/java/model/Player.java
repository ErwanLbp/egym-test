package model;

import factory.MazeFactory;
import log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The model of a player in the maze<br/>
 * There can be several players in the maze because the maze isn't modified
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Player {

    private Room location;
    private Map<ObjectR, Boolean> todolist;

    /**
     * @param location      The room the player starts in
     * @param objectsToFind The list of objects the player has to find
     */
    public Player(Room location, List<String> objectsToFind) {
        this.location = location;
        this.todolist = new HashMap<>();
        // For every object of the list, we add it to the todolist with false because he didn't find it yet
        for (String object : objectsToFind)
            this.todolist.put(MazeFactory.getInstance().createObject(object), false);
        Log.info("Player start in room " + this.location + " and has to find : " + objectsToFind);
    }

    /**
     * @return The room the player currently is
     */
    public Room getLocation() {
        return location;
    }

    /**
     * @param location The new room we want the player to be
     */
    public void setLocation(Room location) {
        this.location = location;
    }

    /**
     * To pick an object in the room the player is<br/>
     * It will check if the room contains the object, and if so, check it on the todolist (e.g pass it to false in the map)
     *
     * @param objectR The object to pick
     * @return True if the player picked the object, otherwise false
     */
    public boolean pickObject(ObjectR objectR) {
        if (todolist.containsKey(objectR)) {
            todolist.replace(objectR, true);
            Log.info("Player picked the object " + objectR);
            return true;
        } else
            return false;
    }

    /**
     * @return True if every object in the todolist has been crossed (e.g passed to false)
     */
    public boolean hasFoundEverything() {
        return !todolist.containsValue(false);
    }

    /**
     * Move the player in the direction, if it's possible, otherwise do nothing
     *
     * @param direction The direction in which the player has to go
     * @return The new room the player is in, or the same room id he hit a wall
     */
    public Room moveTo(Direction direction) {
        // We change the location of the player only if he can go through the side of the room he is in
        if (location.getSide(direction).canGoThrough()) {
            setLocation(location.getRoom(direction));
            Log.info("Player moved to the room " + location);
        }
        return location;
    }

    /**
     * To drop an object he has crossed on his list<br/>
     * It will pass the object to false in the todolist map if it contains this object
     *
     * @param objectR The object to drop
     */
    public void dropObject(ObjectR objectR) {
        // We set the object to false only if the todolist contains it
        if (todolist.containsKey(objectR)) {
            todolist.replace(objectR, false);
            Log.info("Player dropped the object " + objectR);
        }
    }

    /**
     * To look for objects the player wants in the room<br/>
     * <b>It do not pick the objects, call the {@link #pickObject(ObjectR) pickObject} method to do so</b>
     *
     * @return The list of objects the player wants
     */
    public List<ObjectR> seekForObjects() {
        List<ObjectR> objectsInRoom = location.getObjects();
        List<ObjectR> result = new ArrayList<>();
        List<ObjectR> objectsStillToFind = getObjectsStillToFind();
        // If the object in the room is to find on the todolist, add it to the result list
        for (ObjectR obj : objectsInRoom)
            if (objectsStillToFind.contains(obj))
                result.add(obj);
        return result;
    }

    /**
     * To get a list of objects that are still to find on the todolist (e.g still false on the todolist)
     *
     * @return The list of objects still to find in the maze
     */
    public List<ObjectR> getObjectsStillToFind() {
        List<ObjectR> objectsToFind = new ArrayList<>();
        // For every object in the todolist, if it's false, add it to the list
        for (Map.Entry<ObjectR, Boolean> objectEntry : todolist.entrySet()) {
            if (!objectEntry.getValue())
                objectsToFind.add(objectEntry.getKey());
        }
        return objectsToFind;
    }
}
