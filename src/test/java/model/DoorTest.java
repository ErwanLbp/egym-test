package model;

import factory.MazeFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Erwan LBP
 * @version 1.0
 * @since 20-12-2016
 */
public class DoorTest {

    private Room r1;
    private Room r2;
    private Door d;

    @Before
    public void createRooms() {
        r1 = MazeFactory.getInstance().createRoom(1, "test_door");
        r2 = MazeFactory.getInstance().createRoom(2, "test_door_2");
        d = MazeFactory.getInstance().createDoor(r1, r2);
    }

    @Test
    public void canGoThrough() {
        assertTrue(d.canGoThrough());
    }

    @Test
    public void getOtherSideFrom1() {
        assertEquals(r2, d.getOtherSide(r1));
    }

    @Test
    public void getOtherSideFrom2() {
        assertEquals(r1, d.getOtherSide(r2));
    }

    @Test
    public void getOtherSideFromOther() {
        assertNull(d.getOtherSide(MazeFactory.getInstance().createRoom(4, "other")));
    }

    @Test
    public void getOtherSideFromNull() {
        assertNull(d.getOtherSide(null));
    }

}
