package model;

import factory.MazeFactory;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Erwan LBP
 * @version 1.0
 * @since 20-12-2016
 */
public class ObjectRTest {

    @Test
    public void equalTrue() {
        ObjectR o1 = MazeFactory.getInstance().createObject("test_object");
        ObjectR o2 = MazeFactory.getInstance().createObject("test_object");

        assertTrue(o1.equals(o2));
    }

    @Test
    public void equalFalse() {
        ObjectR o1 = MazeFactory.getInstance().createObject("test_object");
        ObjectR o2 = MazeFactory.getInstance().createObject("test_other_object");

        assertFalse(o1.equals(o2));
    }

}
