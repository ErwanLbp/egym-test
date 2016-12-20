package model;

import factory.MazeFactory;
import log.Log;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The model of the Maze<br/>
 * We deal with only one maze, so it's a Singleton
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Maze {
    /**
     * The unique instance of the Maze
     */
    private static Maze instance;

    /**
     * Private constructor to control the creation of the Maze object
     */
    private Maze() {
        this.rooms = new ArrayList<>();
    }

    /**
     * @return The unique instance of the maze
     */
    public static Maze getInstance() {
        if (instance == null)
            instance = new Maze();
        return instance;
    }

    /**
     * The list of rooms of the maze
     */
    private List<Room> rooms;

    /**
     * To add a room to the maze
     *
     * @param room The room to add to the maze
     */
    public void addRoom(Room room) {
        if (room == null) return;
        // If the room is not already in the rooms list, add it
        for (Room r : rooms) {
            if (r.equals(room)) {
                Log.info("The maze already contains the room " + room);
                return;
            }
        }
        this.rooms.add(room);
    }

    /**
     * To get the room with the id received
     *
     * @param idRoom The id of the room to look for
     * @return The room with the id received, null if it doesn't exist
     */
    public Room getRoom(int idRoom) {
        for (Room room : rooms)
            if (room.getId() == idRoom) return room;
        return null;
    }

    /**
     * Everything is in the name of the function
     *
     * @param objectR The object which we want the room
     * @return The room the object received is in
     */
    public Room inWhichRoomIs(ObjectR objectR) {
        if (objectR == null) return null;
        for (Room room : rooms) {
            if (room.contains(objectR))
                return room;
        }
        return null;
    }

    /**
     * To convert a DOM document in a Maze<br/>
     * It will add rooms to the maze, then add each door
     *
     * @param doc The DOM object to convert
     * @return True if everything went well, otherwise false
     */
    public boolean load(Document doc) {
        // This map will contains the attributes "north", "south", etc. of each room(id)
        Map<Integer, Map<String, Integer>> sidesOfRooms = new HashMap<>();

        // Getting the list of rooms in the maze
        NodeList roomsNL = doc.getElementsByTagName("room");

        Log.info(roomsNL.getLength() + " rooms found in the DOM object");
        for (int i = 0; i < roomsNL.getLength(); i++) {
            // For each room of the Document
            Node nodeRoom = roomsNL.item(i);
            int id;
            String name;
            // Parsing the attributes "id" and "name" of the room
            NamedNodeMap attributes = nodeRoom.getAttributes();
            try {
                id = Integer.parseInt(attributes.getNamedItem("id").getNodeValue());
                name = attributes.getNamedItem("name").getNodeValue();
            } catch (Exception e) {
                Log.error("Error during converting the " + i + "th room of the DOM maze into a Room object in the Maze");
                e.printStackTrace();
                return false;
            }

            // Creating a room and adding it to the rooms list, for now it has no door to other rooms, only walls
            Room room = MazeFactory.getInstance().createRoom(id, name);
            Log.success(room + ",", true);
            this.addRoom(room);


            // This map will contains the sides(String of the Direction) of the current room, then we will add it to ths sidesOfRooms map
            Map<String, Integer> sidesOfCurrentRoom = new HashMap<>(); // FIXME It can be optimized cause we will had the door twice, once for each room of the door

            // Getting the attributes of each direction and adding it to the map if it's not null
            Node north = nodeRoom.getAttributes().getNamedItem("north");
            if (north != null) sidesOfCurrentRoom.put("north", Integer.parseInt(north.getNodeValue()));

            Node south = nodeRoom.getAttributes().getNamedItem("south");
            if (south != null) sidesOfCurrentRoom.put("south", Integer.parseInt(south.getNodeValue()));

            Node east = nodeRoom.getAttributes().getNamedItem("east");
            if (east != null) sidesOfCurrentRoom.put("east", Integer.parseInt(east.getNodeValue()));

            Node west = nodeRoom.getAttributes().getNamedItem("west");
            if (west != null) sidesOfCurrentRoom.put("west", Integer.parseInt(west.getNodeValue()));

            // If we didn't find any of the directions, no need to overload the map sidesOfRooms
            if (!sidesOfCurrentRoom.isEmpty())
                sidesOfRooms.put(id, sidesOfCurrentRoom);


            // Getting the objects contained in the current room
            NodeList objectsNL = ((Element) nodeRoom).getElementsByTagName("object");
            Log.info(objectsNL.getLength() + " objects found", false, true);
            for (int j = 0; j < objectsNL.getLength(); j++) {
                Node nodeObject = objectsNL.item(j);
                // Adding the current object to the room objects and to the map of objects of the maze
                try {
                    ObjectR objectR = MazeFactory.getInstance().createObject(nodeObject.getAttributes().getNamedItem("name").getNodeValue());
                    room.addObject(objectR);
                } catch (Exception e) {
                    Log.error("Error during converting an object of the room " + room + " of the DOM maze into a Room object in the Maze");
                    return false;
                }
            }
        }

        // So now we have the map sidesOfRooms containing each room and the door with other rooms

        // For each door, we create it and add it to the rooms, if there is less than 2 room, no need to do it
        if (rooms.size() > 1) {
            for (Map.Entry<Integer, Map<String, Integer>> roomEntry : sidesOfRooms.entrySet()) {
                for (Map.Entry<String, Integer> directionEntry : roomEntry.getValue().entrySet()) {
                    Room r1 = this.getRoom(roomEntry.getKey());
                    Room r2 = this.getRoom(directionEntry.getValue());
                    Door d = MazeFactory.getInstance().createDoor(r1, r2);
                    // Converting the String of the attribute direction of the DOM object
                    Direction direction = Direction.toDirection(directionEntry.getKey());
                    if (direction == null) {
                        Log.error("Error during converting the DOM Door to Door objects");
                        return false;
                    }
                    r1.setSide(direction, d);
                    r2.setSide(direction.opposite(), d);
                }
            }
        }

        return true;
    }

    /**
     * Set the instance to null, to take a new start<br/>
     * <i>Used only for testing, not really useful otherwise</i>
     */
    public void reset() {
        Log.info("### RESET ###");
        instance = null;
    }
}
