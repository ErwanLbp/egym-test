package command;

import factory.MazeFactory;
import log.Log;
import model.ObjectR;
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
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Erwan LBP
 * @version 1.0
 * @since 20-12-2016
 */
public class PickObjectTest {

    private static Player player;
    private static Room r1;
    private static ObjectR o;

    @BeforeClass
    public static void createMaze() {
        r1 = MazeFactory.getInstance().createRoom(1, "test");
        o = MazeFactory.getInstance().createObject("o");
        r1.addObject(o);
    }

    @Before
    public void setPlayer() {
        List<String> toFind = new ArrayList<>();
        toFind.add(o.getName());
        player = MazeFactory.getInstance().createPlayer(r1, toFind);
    }

    @Test
    public void execute() {
        PickObject pickObject = new PickObject(player, r1, o);
        pickObject.execute();
        assertTrue(player.hasFoundEverything());
    }

    @Test
    public void executeWithObjectNotPresent() {
        ObjectR oNotPresent = MazeFactory.getInstance().createObject("o_not_present");
        PickObject pickObject = new PickObject(player, r1, oNotPresent);
        pickObject.execute();
        assertFalse(player.hasFoundEverything());
    }

    @Test
    public void undo() {
        PickObject pickObject = new PickObject(player, r1, o);
        pickObject.execute();
        pickObject.undo();

        assertFalse(player.hasFoundEverything());
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
        document.appendChild(document.createElement("route"));

        PickObject pickObject = new PickObject(player, r1, o);
        pickObject.append(document);

        Element objectElement = document.getElementById(o.getName() + "");
        assert objectElement != null;

        assertEquals(o.getName() + "", objectElement.getAttribute("name"));
        // TODO Test if it's placed at the right place
    }
}
