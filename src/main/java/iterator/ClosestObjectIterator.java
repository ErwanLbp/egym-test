package iterator;

import command.Enter;
import model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An iterator on a Room object<br/>
 * It will store a List of Direction to execute to go to the closest object and then will iterate on this list until the object is found. After that it will find the next closest object<br/>
 * <b>This iterator do not find the optimal path to find <u>every</u> time</b><br/>
 * The choice is arbitrary when two paths have the same length
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class ClosestObjectIterator implements Iterator {

    private Room room;
    private List<Room> roomsToGo;
    private List<Integer> roomsIdsVisited;
    private ArrayList<Direction> directionsToGo;
    private Iterator iteratorOnDirections;

    /**
     * @param room The room to iterate on
     */
    public ClosestObjectIterator(Room room) {
        this.room = room;
        roomsToGo = new ArrayList<>();
        roomsIdsVisited = new ArrayList<>();
        directionsToGo = null;
    }

    /**
     * To set the List of Room to go in<br/>
     * Needed because, at the creation of the iterator in the {@link Room#iterator() Room} class, we can't have access to the objects the player wants<br/>
     * <i>There might be a better way to do</i>
     *
     * @param objectsToFind The list of objects the player still have to find
     */
    public void setObjectsToFind(List<ObjectR> objectsToFind) {
        // For every objects in the list to find, we add the room to the list of rooms to go if the room is not already in it
        for (ObjectR objectR : objectsToFind) {
            Room aRoomToGo = Maze.getInstance().inWhichRoomIs(objectR);
            if (aRoomToGo != null && !roomsToGo.contains(aRoomToGo))
                roomsToGo.add(aRoomToGo);
        }
    }

    /**
     * Fill the directionsToGo list with the Directions to go access the room of the closest object<br/>
     * <b>Not optimal</b> because the recursion occurs in the order NORTH, EAST, WEST, SOUTH. So if two paths have the same length (e.g number of directions in the list) it will always choose the first one
     * <i>There might be a better way to choose the right path</i>
     *
     * @param dirs        The list of directions to fill
     * @param currentRoom The current room the function is in
     * @return True if an object to find has been found in this path, otherwise false
     */
    private boolean findPathToOneRecursive(ArrayList<Direction> dirs, Room currentRoom) {
        // When we iterate to a wall, the currentRoom will be null, so this mean we didn't find an object
        if (currentRoom == null) return false;

        // If we are in one of the rooms we have to go (e.g an object needed is in here)
        if (roomsToGo.contains(currentRoom)) return true;

        // If we already have visited this room, it means we didn't find an object needed on this path
        if (roomsIdsVisited.contains(currentRoom.getId())) return false;

        roomsIdsVisited.add(currentRoom.getId());

        // This list will contains the path to an object needed with the minimal length
        ArrayList<Direction> listMinSize = null;

        // We iterate to the North
        ArrayList<Direction> lNorth = new ArrayList<>();
        lNorth.add(Direction.NORTH);
        // If we found an object in the North, it's the first side we iterate so it's the minimum
        if (findPathToOneRecursive(lNorth, currentRoom.getRoom(Direction.NORTH)))
            listMinSize = lNorth;

        // We iterate to the East
        ArrayList<Direction> lEast = new ArrayList<>();
        lEast.add(Direction.EAST);
        // If we found an object in the East and it is closest, then it's the minimum
        if (findPathToOneRecursive(lEast, currentRoom.getRoom(Direction.EAST))) {
            if (listMinSize == null || lEast.size() < listMinSize.size())
                listMinSize = lEast;
        }

        // We iterate to the West
        ArrayList<Direction> lWest = new ArrayList<>();
        lWest.add(Direction.WEST);
        // If we found an object in the West and it is closest, then it's the minimum
        if (findPathToOneRecursive(lWest, currentRoom.getRoom(Direction.WEST))) {
            if (listMinSize == null || lWest.size() < listMinSize.size())
                listMinSize = lWest;
        }

        // We iterate to the South
        ArrayList<Direction> lSouth = new ArrayList<>();
        lSouth.add(Direction.SOUTH);
        // If we found an object in the South and it is closest, then it's the minimum
        if (findPathToOneRecursive(lSouth, currentRoom.getRoom(Direction.SOUTH))) {
            if (listMinSize == null || lSouth.size() < listMinSize.size())
                listMinSize = lSouth;
        }

        // If we found nothing in every directions, then we there is no object needed on this path
        if (listMinSize == null) return false;

        // We add the list with the minimal length to the list of directions, so it will be filled step by step
        dirs.addAll(listMinSize);
        return true;
    }

    /**
     * To fill the attributes with the directions to the closest object<br/>
     * Is reused when we found one of the object and have to find the path to the next closest object
     */
    private void actualize() {
        directionsToGo = new ArrayList<>();
        roomsIdsVisited = new ArrayList<>();

        // The function will fill the list directionsToGo with the directions to go to the closest room we need
        findPathToOneRecursive(directionsToGo, room);
        // And then we will iterate on the list we previously fillled
        iteratorOnDirections = directionsToGo.iterator();
    }

    /**
     * @return Always true because we can always go to another room (a maze with only one room is not a maze)
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    /**
     * To get the next command Enter, with the direction to go
     *
     * @return The command Enter with the direction to the closest object
     * @see command.Enter
     */
    @Override
    public Object next() {
        // if it's the first call, we have to find the path to the closest object
        if (directionsToGo == null)
            actualize();

        Direction nextDirection = null;
        Enter enter = null;

        // If we are not already in the room with an object to find we get the next direction from the list directionsToGo
        if (!directionsToGo.isEmpty())
            nextDirection = (Direction) iteratorOnDirections.next();

        // If we already can pick an object in this room, we don't need to enter another room
        if (nextDirection != null) {
            enter = new Enter(null, room, nextDirection);

            // At the end of the next() function we will have moved to the next room pointed by the list directionsToGo
            room = ((Door) room.getSide(nextDirection)).getOtherSide(room);
        }

        // If now we are in a room we were looking for, we have to find the new path to the closest object
        // We are in a room we had to go in, so we remove the current room from the list of rooms to go
        if (roomsToGo.contains(room)) {
            roomsToGo.remove(room);
            actualize();
        }

        return enter;
    }
}
