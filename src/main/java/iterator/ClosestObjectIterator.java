package iterator;

import command.Enter;
import log.Log;
import model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <h1>algorithm Distance</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class ClosestObjectIterator implements Iterator {

    private Room room;
    private List<Room> toGo;
    private List<Integer> visited;
    private ArrayList<Direction> directionsToGo;
    private Iterator iteratorOnDirections;

    public ClosestObjectIterator(Room room) {
        this.room = room;
        toGo = new ArrayList<>();
        visited = new ArrayList<>();
        directionsToGo = null;
    }

    public void setObjectsToFind(List<ObjectR> objectsToFind) {
        for (ObjectR objectR : objectsToFind) {
            Room aRoomToGo = Maze.getInstance().inWhichRoomIs(objectR);
            if (aRoomToGo != null)
                toGo.add(aRoomToGo);
        }
    }

    private boolean findPathToOneRecursive(ArrayList<Direction> dirs, Room currentRoom) {
        if (currentRoom == null)
            return false;
        if (toGo.contains(currentRoom))
            return true;
        if (visited.contains(currentRoom.getId()))
            return false;

        visited.add(currentRoom.getId());

        ArrayList<Direction> listMinSize = null;

        //Enter NORTH
        ArrayList<Direction> lNorth = new ArrayList<>();
        lNorth.add(Direction.NORTH);
        if (findPathToOneRecursive(lNorth, currentRoom.getRoom(Direction.NORTH)))
            listMinSize = lNorth;

        //Enter EAST
        ArrayList<Direction> lEast = new ArrayList<>();
        lEast.add(Direction.EAST);
        if (findPathToOneRecursive(lEast, currentRoom.getRoom(Direction.EAST))) {
            if (listMinSize == null || lEast.size() < listMinSize.size())
                listMinSize = lEast;
        }

        //Enter WEST
        ArrayList<Direction> lWest = new ArrayList<>();
        lWest.add(Direction.WEST);
        if (findPathToOneRecursive(lWest, currentRoom.getRoom(Direction.WEST))) {
            if (listMinSize == null || lWest.size() < listMinSize.size())
                listMinSize = lWest;
        }

        //Enter SOUTH
        ArrayList<Direction> lSouth = new ArrayList<>();
        lSouth.add(Direction.SOUTH);
        if (findPathToOneRecursive(lSouth, currentRoom.getRoom(Direction.SOUTH))) {
            if (listMinSize == null || lSouth.size() < listMinSize.size())
                listMinSize = lSouth;
        }

        if (listMinSize == null)
            return false;

        dirs.addAll(listMinSize);
        return true;
    }

    private void actualize() {
        directionsToGo = new ArrayList<>();
        toGo.remove(room);
        visited = new ArrayList<>();

        findPathToOneRecursive(directionsToGo, room);
        iteratorOnDirections = directionsToGo.iterator();
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Object next() {
        if (directionsToGo == null)
            actualize();

        Direction nextDirection = null;
        Enter enter = null;

        if (!directionsToGo.isEmpty())
            nextDirection = (Direction) iteratorOnDirections.next();

        if (nextDirection != null) {
            enter = new Enter(null, room, nextDirection);
            Log.info(enter + "");

            room = ((Door) room.getSide(nextDirection)).getOtherSide(room);
        }

        if (toGo.contains(room))
            actualize();
        return enter;
    }
}
