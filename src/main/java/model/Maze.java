package model;

import log.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>model Maze</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Maze {

    private static Maze instance;

    private Maze() {
        this.rooms = new ArrayList<>();
    }

    public static Maze getInstance() {
        if (instance == null)
            instance = new Maze();
        return instance;
    }

    private List<Room> rooms;

    public boolean addRoom(Room room) {
        this.rooms.add(room);
        return true;
    }

    public Room getRoom(int idRoom) {
        for (Room room : rooms)
            if (room.getId() == idRoom) return room;
        return null;
    }

    public boolean load(Document doc) {
        // This map will contains the attributes "north", "south", etc. of each room(id)
        Map<Integer, Map<String, Integer>> sidesOfRooms = new HashMap<>();

        // Getting the root element of the XMl document
        Element map = doc.getDocumentElement();

        // Getting the list of rooms in the maze
        NodeList rooms = map.getChildNodes();
        Log.info(rooms.getLength() + " rooms found in the DOM object");
        for (int i = 0; i < rooms.getLength(); i++) {
            Node nodeRoom = rooms.item(i);
            int id;
            String name;
            // Parsing the attributes "id" and "name" of the room
            try {
                Node n = nodeRoom.getAttributes().getNamedItem("id");
                if (n == null) System.out.println("est null");
                else System.out.println(n.getNodeValue());
                id = Integer.parseInt(nodeRoom.getAttributes().getNamedItem("id").getNodeValue());
                name = nodeRoom.getAttributes().getNamedItem("name").getNodeValue();
            } catch (Exception e) {
                Log.error("Error during converting the " + i + "th room of the DOM maze into a Room object in the Maze");
                e.printStackTrace();
                return false;
            }

            //Creating a room and adding it to the rooms list, for now it has no door to other rooms
            Room room = new Room(id, name);
            this.rooms.add(room);


            // This map will contains the sides(name) of the current room, then we will add it to ths sidesOfRooms map
            Map<String, Integer> sidesOfCurrentRoom = new HashMap<>(); // TODO optimization, there are some redundance

            // Getting the attributes of each direction and adding it to the map if it's not null
            Node north = nodeRoom.getAttributes().getNamedItem("north");
            if (north != null) sidesOfCurrentRoom.put("north", Integer.parseInt(north.getNodeValue()));

            Node south = nodeRoom.getAttributes().getNamedItem("south");
            if (south != null) sidesOfCurrentRoom.put("south", Integer.parseInt(south.getNodeValue()));

            Node east = nodeRoom.getAttributes().getNamedItem("east");
            if (east != null) sidesOfCurrentRoom.put("east", Integer.parseInt(east.getNodeValue()));

            Node west = nodeRoom.getAttributes().getNamedItem("west");
            if (west != null) sidesOfCurrentRoom.put("west", Integer.parseInt(west.getNodeValue()));

            // if(sidesOfCurrentRoom is not empty)
            sidesOfRooms.put(id, sidesOfCurrentRoom);


            // Getting the objects the current room contains
            NodeList objects = nodeRoom.getChildNodes();
            Log.info(objects.getLength() + " objects found in the room " + room + " of the DOM object");
            for (int j = 0; j < objects.getLength(); j++) {
                Node nodeObject = objects.item(j);
                // Adding the current object to the room objects
                try {
                    room.addObject(new ObjectR(nodeObject.getAttributes().getNamedItem("name").getNodeValue()));
                } catch (Exception e) {
                    Log.error("Error during converting an object of the room " + room + " of the DOM maze into a Room object in the Maze");
                    return false;
                }
            }
        }

        // So now we have the map sidesOfRooms containing each room and the connections with other rooms

        for (Map.Entry<Integer, Map<String, Integer>> roomEntry : sidesOfRooms.entrySet()) {
            for (Map.Entry<String, Integer> directionEntry : roomEntry.getValue().entrySet()) {
                Room r1 = this.getRoom(roomEntry.getKey());
                Room r2 = this.getRoom(directionEntry.getValue());
                Door d = new Door(r1, r2);
                Direction direction = Direction.toDirection(directionEntry.getKey());
                if (direction == null) {
                    Log.error("Error during converting the DOM Door to Door objects");
                    return false;
                }
                r1.setSide(direction, d);
                r2.setSide(direction.opposite(), d);
            }
        }

        return true;
    }

}
