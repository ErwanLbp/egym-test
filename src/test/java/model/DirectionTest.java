package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Erwan LBP
 * @version 1.0
 * @since 20-12-2016
 */
public class DirectionTest {

    @Test
    public void oppositeNORTH() {
        assertEquals(Direction.SOUTH, Direction.NORTH.opposite());
    }

    @Test
    public void oppositeSOUTH() {
        assertEquals(Direction.NORTH, Direction.SOUTH.opposite());
    }

    @Test
    public void oppositeEAST() {
        assertEquals(Direction.WEST, Direction.EAST.opposite());
    }

    @Test
    public void oppositeWEST() {
        assertEquals(Direction.EAST, Direction.WEST.opposite());
    }

    @Test
    public void atLeftNORTH() {
        assertEquals(Direction.WEST, Direction.NORTH.atLeft());
    }

    @Test
    public void atLeftEAST() {
        assertEquals(Direction.NORTH, Direction.EAST.atLeft());
    }

    @Test
    public void atLeftSOUTH() {
        assertEquals(Direction.EAST, Direction.SOUTH.atLeft());
    }

    @Test
    public void atLeftWEST() {
        assertEquals(Direction.SOUTH, Direction.WEST.atLeft());
    }

    @Test
    public void atRightNORTH() {
        assertEquals(Direction.EAST, Direction.NORTH.atRight());
    }

    @Test
    public void atRightEAST() {
        assertEquals(Direction.SOUTH, Direction.EAST.atRight());
    }

    @Test
    public void atRightSOUTH() {
        assertEquals(Direction.WEST, Direction.SOUTH.atRight());
    }

    @Test
    public void atRightWEST() {
        assertEquals(Direction.NORTH, Direction.WEST.atRight());
    }

    @Test
    public void toDirectionNORTH() {
        assertEquals(Direction.NORTH, Direction.toDirection("north"));
    }

    @Test
    public void toDirectionEAST() {
        assertEquals(Direction.EAST, Direction.toDirection("east"));
    }

    @Test
    public void toDirectionSOUTH() {
        assertEquals(Direction.SOUTH, Direction.toDirection("south"));
    }

    @Test
    public void toDirectionWEST() {
        assertEquals(Direction.WEST, Direction.toDirection("west"));
    }

    @Test
    public void toDirectionOther() {
        assertNull(Direction.toDirection("other"));
    }
}
