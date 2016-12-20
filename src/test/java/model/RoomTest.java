package model;

import factory.MazeFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Erwan LBP
 * @version 1.0
 * @since 20-12-2016
 */
public class RoomTest {

    private static ObjectR o1, o2;
    private Room r1, r2;

    @BeforeClass
    public static void createMaze() {
        o1 = MazeFactory.getInstance().createObject("o1_room");
        o2 = MazeFactory.getInstance().createObject("o2_room");
    }

    @Before
    public void addObjects() {
        r1 = MazeFactory.getInstance().createRoom(7, "test_room");
        r2 = MazeFactory.getInstance().createRoom(8, "test_room_2");
        Door d = MazeFactory.getInstance().createDoor(r1, r2);

        r1.setSide(Direction.EAST, d);
        r2.setSide(Direction.EAST.opposite(), d);

        r1.getObjects().add(o1);
        r1.getObjects().add(o2);
    }

    @Test
    public void addObjectNull() {
        r1.addObject(null);
        assertEquals(2, r1.getObjects().size());
    }

    @Test
    public void addObject() {
        ObjectR o3 = MazeFactory.getInstance().createObject("o3_room");
        r1.addObject(o3);
        assertEquals(3, r1.getObjects().size());
        assertEquals(o3, r1.getObjects().get(2));
    }

    @Test
    public void addObjectAlreadyIn() {
        r1.addObject(o1);
        System.out.println(r1.getObjects());
        assertEquals(2, r1.getObjects().size());
    }

    @Test
    public void addObjectAlreadyInAnotherRoom() {
        r2.addObject(o1);
        assertEquals(1, r2.getObjects().size());
    }

    @Test
    public void getRoomWall() {
        assertNull(r1.getRoom(Direction.NORTH));
    }

    @Test
    public void getRoom() {
        assertEquals(r2, r1.getRoom(Direction.EAST));
    }

    @Test
    public void equalsTrue() {
        Room r1v2 = MazeFactory.getInstance().createRoom(r1.getId(), "test42_room");
        assertTrue(r1.equals(r1v2));
    }

    @Test
    public void equalsFalse() {
        assertFalse(r1.equals(r2));
    }

}
