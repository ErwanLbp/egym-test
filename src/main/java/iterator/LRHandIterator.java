package iterator;

import command.Enter;
import log.Log;
import model.Direction;
import model.Door;
import model.Room;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <h1>iterator LRHandIterator</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class LRHandIterator implements Iterator {

    private Room room;
    private boolean leftHanded;
    private Direction currentDir;
    private List<Integer> visited;

    public LRHandIterator(Room room) {
        this.room = room;
        this.leftHanded = true;
        this.currentDir = Direction.NORTH;
        this.visited = new ArrayList<>();
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Object next() {
        visited.add(room.getId());
        if (visited.contains(room.getId()))
            leftHanded = !leftHanded;

        do {
            currentDir = leftHanded ? currentDir.atLeft() : currentDir.atRight();
        } while (!room.getSide(currentDir).canGoThrough());

        room = ((Door) room.getSide(currentDir)).getOtherSide(room);


        Enter enter = new Enter(null, room, currentDir);
        Log.info(enter + "");
        return enter;
    }
}
