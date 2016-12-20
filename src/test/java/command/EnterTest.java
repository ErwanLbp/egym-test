package command;

import factory.MazeFactory;
import log.Log;
import model.Direction;
import model.Door;
import model.Player;
import model.Room;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * @author Erwan LBP
 * @version 1.0
 * @since 20-12-2016
 */
public class EnterTest {

    private static Player player;
    private static Room r1;
    private static Room r2;

    @BeforeClass
    public static void createMaze() {
        r1 = MazeFactory.getInstance().createRoom(1, "test");
        r2 = MazeFactory.getInstance().createRoom(2, "test2");
        Door d = MazeFactory.getInstance().createDoor(r1, r2);

        r1.setSide(Direction.EAST, d);
        r2.setSide(Direction.EAST.opposite(), d);
    }

    @Before
    public void setPlayer() {
        player = MazeFactory.getInstance().createPlayer(r1, new ArrayList<>());
    }

    @Test
    public void execute() {
        Enter enter = new Enter(player, r1, Direction.EAST);
        enter.execute();
        assertEquals(r2, player.getLocation());
    }

    @Test
    public void executeToAWall() {
        Enter enter = new Enter(player, r1, Direction.WEST);
        enter.execute();
        assertEquals(r1, player.getLocation());
    }

    @Test
    public void undo() {
        Enter enter = new Enter(player, r1, Direction.EAST);
        enter.execute();

        enter.undo();
        assertEquals(r1, player.getLocation());
    }

    @Test
    public void undoToAWall() {
        Enter enter = new Enter(player, r1, Direction.EAST);
        enter.undo();
        assertEquals(r1, player.getLocation());
    }

    @Test
    public void append() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            Log.error("[TEST] Unable to create a DocumentBuilder to convert the route into a DOM object");
        }
        assert builder != null;
        Document document = builder.newDocument();
        Element route = document.createElement("route");
        document.appendChild(route);

        Enter enter = new Enter(player, r1, Direction.EAST);
        enter.append(document);

        Element enterElement = document.getElementById(r1.getId() + "");
        assert enterElement != null;

        assertEquals(r1.getId() + "", enterElement.getAttribute("id"));
        assertEquals(r1.getName() + "", enterElement.getAttribute("name"));
        assertEquals(enterElement, route.getLastChild());
    }
}
