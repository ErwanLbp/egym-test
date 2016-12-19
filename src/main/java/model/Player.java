package model;

import factory.MazeFactory;
import log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>model Player</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Player {

    private Room location;
    private Map<ObjectR, Boolean> todolist;

    public Player(Room location, List<String> objectsToFind) {
        this.location = location;
        this.todolist = new HashMap<>();
        for (String object : objectsToFind)
            this.todolist.put(MazeFactory.getInstance().createObject(object), false);
        Log.info("Player start in room " + this.location + " and has to find : " + objectsToFind);
    }

    public Room getLocation() {
        return location;
    }

    public void setLocation(Room location) {
        this.location = location;
    }

    public boolean pickObject(ObjectR objectR) {
        if (todolist.containsKey(objectR)) {
            todolist.replace(objectR, true);
            Log.info("Player picked the object " + objectR);
            return true;
        } else
            return false;
    }

    public boolean hasFoundEverything() {
        return !todolist.containsValue(false);
    }

    public Room moveTo(Direction direction) {
        if (location.getSide(direction).canGoThrough()) {
            setLocation(location.getRoom(direction));
            Log.info("Player moved to the room " + location);
        }
        return location;
    }

    public void dropObject(ObjectR objectR) {
        if (todolist.containsKey(objectR)) {
            todolist.replace(objectR, false);
            Log.info("Player dropped the object " + objectR);
        }
    }

    public List<ObjectR> seekForObjects() {
        List<ObjectR> objectsInRoom = location.getObjects();
        List<ObjectR> result = new ArrayList<>();
        List<ObjectR> objectsStillToFind = getObjectsStillToFind();
        for (ObjectR obj : objectsInRoom)
            if (objectsStillToFind.contains(obj))
                result.add(obj);
        return result;
    }

    public List<ObjectR> getObjectsStillToFind() {
        List<ObjectR> objectsToFind = new ArrayList<>();
        for (Map.Entry<ObjectR, Boolean> objectEntry : todolist.entrySet()) {
            if (!objectEntry.getValue())
                objectsToFind.add(objectEntry.getKey());
        }
        return objectsToFind;
    }
}
