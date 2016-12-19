package model;

import log.Log;

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
            this.todolist.put(new ObjectR(object), false);
        Log.info("Player start in room " + this.location + " and has to find : " + objectsToFind);
    }

    public Room getLocation() {
        return location;
    }

    public void setLocation(Room location) {
        this.location = location;
    }

    public boolean pickObject(ObjectR objectR) {
        if (location.contains(objectR) && todolist.containsKey(objectR)) {
            todolist.replace(objectR, true);
            return true;
        } else
            return false;
    }

    public boolean hasFoundEverything() {
        return !todolist.containsValue(false);
    }

    public Room moveTo(Direction direction) {
        if (location.getSide(direction).canGoThrough())
            setLocation(location.getRoom(direction));
        return location;
    }

    public void dropObject(ObjectR objectR) {
        if (todolist.containsKey(objectR))
            todolist.replace(objectR, false);
    }
}
