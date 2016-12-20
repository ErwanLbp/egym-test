package game;

import model.Maze;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * @author Erwan LBP
 * @version 1.0
 * @since 20-12-2016
 */
public class GameTest {

    private String testFolder = "test_cases/";

    @Before
    public void reinit() {
        Maze.getInstance().reset();
    }

    @Test
    public void example_given() {
        String currentTestFolder = testFolder + "example_given/";

        String output = currentTestFolder + "output_example.xml";
        String[] args = {
                currentTestFolder + "map_example.xml",
                currentTestFolder + "config_example.txt",
                output
        };

        Game.main(args);
        assertTrue(new File(output).exists());
    }

    @Test
    public void no_object() {
        String currentTestFolder = testFolder + "no_object/";

        String output = currentTestFolder + "output_no_object.xml";
        String[] args = {
                currentTestFolder + "map_no_object.xml",
                currentTestFolder + "config_no_object.txt",
                output
        };

        Game.main(args);
        assertTrue(new File(output).exists());
    }

    @Test
    public void one_room() {
        String currentTestFolder = testFolder + "one_room/";

        String output = currentTestFolder + "output_one_room.xml";
        String[] args = {
                currentTestFolder + "map_one_room.xml",
                currentTestFolder + "config_one_room.txt",
                output
        };

        Game.main(args);
        assertTrue(new File(output).exists());
    }

    @Test
    public void no_arg() {
        String[] args = {};
        Game.main(args);
    }

    @Test
    public void one_arg() {
        String currentTestFolder = testFolder + "example_given/";
        String[] args = {
                currentTestFolder + "map_example.xml",
        };
        Game.main(args);
    }
}
