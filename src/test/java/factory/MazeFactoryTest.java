package factory;

import model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Erwan LBP
 * @version 1.0
 * @since 20-12-2016
 */
public class MazeFactoryTest {

    @Test
    public void getInstance() {
        MazeFactory mf1 = MazeFactory.getInstance();
        MazeFactory mf2 = MazeFactory.getInstance();
        assertTrue(mf1 == mf2);
    }

    @Test
    public void createWall() {
        Wall wall = MazeFactory.getInstance().createWall();
        assert wall != null;
    }

    @Test
    public void createObject() {
        ObjectR objectR = MazeFactory.getInstance().createObject("test");
        assert objectR != null;
        assertEquals("test", objectR.getName());
    }

    @Test
    public void createRoom() {
        Room room = MazeFactory.getInstance().createRoom(0, "test");
        assertEquals(0, room.getId());
        assertEquals("test", room.getName());
        assertEquals(0, room.getObjects().size());
        assertFalse(room.getSide(Direction.NORTH).canGoThrough());
        assertFalse(room.getSide(Direction.SOUTH).canGoThrough());
        assertFalse(room.getSide(Direction.EAST).canGoThrough());
        assertFalse(room.getSide(Direction.WEST).canGoThrough());
    }

    @Test
    public void createDoor() {
        Room r1 = MazeFactory.getInstance().createRoom(1, "test_r1");
        Room r2 = MazeFactory.getInstance().createRoom(2, "test_r2");
        Door d = MazeFactory.getInstance().createDoor(r1, r2);
        assertEquals(r1, d.getOtherSide(r2));
        assertEquals(r2, d.getOtherSide(r1));
    }

    @Test
    public void createPlayer() {
        Room r = MazeFactory.getInstance().createRoom(1, "test_r");
        List<String> toFind = new ArrayList<>();
        toFind.add("test_o1");
        toFind.add("test_o2");

        Player player = MazeFactory.getInstance().createPlayer(r, toFind);

        assertEquals(r, player.getLocation());
        assertFalse(player.hasFoundEverything());
        assertEquals(2, player.getObjectsStillToFind().size());
    }

}
