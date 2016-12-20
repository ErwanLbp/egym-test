package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * @author Erwan LBP
 * @version 1.0
 * @since 20-12-2016
 */
public class WallTest {

    private Wall wall;

    @Before
    public void createWall() {
        this.wall = new Wall();
    }

    @Test
    public void canGoThrough() {
        assertFalse(wall.canGoThrough());
    }
}
