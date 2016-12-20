package command;

import factory.MazeFactory;
import model.Direction;
import model.ObjectR;
import model.Room;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import static org.junit.Assert.assertEquals;

/**
 * @author Erwan LBP
 * @version 1.0
 * @since 20-12-2016
 */
public class HistoricTest {

    private Historic historic;
    private static Enter enter;
    private static PickObject pickObject;
    private static Room room;
    private static ObjectR objectR;

    @BeforeClass
    public static void createCommands() {
        room = MazeFactory.getInstance().createRoom(1, "test");
        objectR = MazeFactory.getInstance().createObject("o_test");
        enter = new Enter(null, room, Direction.NORTH);
        pickObject = new PickObject(null, room, objectR);
    }

    @Before
    public void createHistoric() {
        historic = new Historic();
        historic.storeCommand(enter);
        historic.storeCommand(pickObject);
    }

    @Test
    public void convertToDOM() {
        Document document = historic.convertToDOM();
        assert document != null;

        assertEquals(room.getId() + "", document.getElementById(room.getId() + "").getAttribute("id"));
        assertEquals(objectR.getName(), document.getElementById(objectR.getName()).getAttribute("name"));
    }

}
