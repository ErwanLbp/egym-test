package model;

import factory.MazeFactory;
import log.Log;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Erwan LBP
 * @version 1.0
 * @since 20-12-2016
 */
public class MazeTest {

    private static ObjectR o1, o2;
    private static Room r1, r2;

    @BeforeClass
    public static void createMaze() {
        o1 = MazeFactory.getInstance().createObject("o1_maze");
        o2 = MazeFactory.getInstance().createObject("o2_maze");

        r1 = MazeFactory.getInstance().createRoom(3, "test_maze");
        r2 = MazeFactory.getInstance().createRoom(4, "test_maze_2");
        Door d = MazeFactory.getInstance().createDoor(r1, r2);

        r1.setSide(Direction.EAST, d);
        r2.setSide(Direction.EAST.opposite(), d);

        r1.addObject(o1);
        r1.addObject(o2);
    }

    @Before
    public void addObjects() {
        Maze.getInstance().addRoom(r1);
        Maze.getInstance().addRoom(r2);
    }

    @Test
    public void getRoomPresent() {
        assertEquals(r1, Maze.getInstance().getRoom(r1.getId()));
    }

    @Test
    public void getRoomNotPresent() {
        assertNull(Maze.getInstance().getRoom(-1));
    }

    @Test
    public void inWhichRoomIsNull() {
        assertNull(Maze.getInstance().inWhichRoomIs(null));
    }

    @Test
    public void inWhichRoomIsPresent() {
        assertEquals(r1, Maze.getInstance().inWhichRoomIs(o1));
    }

    @Test
    public void inWhichRoomIsNotPresent() {
        assertNull(Maze.getInstance().inWhichRoomIs(MazeFactory.getInstance().createObject("o3_maze")));
    }

    @Test
    public void load() {
        Room r3 = MazeFactory.getInstance().createRoom(11, "room_load_maze");
        ObjectR o3 = MazeFactory.getInstance().createObject("object_load_maze");
        r3.addObject(o3);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            Log.error("[TEST] Unable to create a DocumentBuilder to convert the route into a DOM object");
        }
        assert builder != null;
        Document document = builder.newDocument();
        document.appendChild(document.createElement("route"));

        Element roomElement = document.createElement("room");
        roomElement.setAttribute("id", r3.getId() + "");
        roomElement.setAttribute("name", r3.getName());
        roomElement.setIdAttribute("id", true);
        document.getDocumentElement().appendChild(roomElement);

        Element objectElement = document.createElement("object");
        objectElement.setAttribute("name", o3.getName());
        objectElement.setIdAttribute("name", true);
        roomElement.appendChild(objectElement);

        document.getDocumentElement().appendChild(roomElement);

        Maze.getInstance().load(document);
        assertEquals(r3, Maze.getInstance().getRoom(r3.getId()));
        assertEquals(r3, Maze.getInstance().inWhichRoomIs(o3));

        // TODO Not complete, it doesn't test the creation of doors
    }
}