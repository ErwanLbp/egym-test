package game;

import model.Maze;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Erwan LBP
 * @version 1.0
 * @since 20-12-2016
 */
public class GameTest {

    private String testFolder = "src/main/resources/test_cases/";

    @Before
    public void reinit() {
        Maze.getInstance().reset();
    }

    @Test
    public void example_given() {
        String currentTestFolder = testFolder + "example_given/";

        String[] args = {
                currentTestFolder + "map_example.xml",
                currentTestFolder + "config_example.txt",
                currentTestFolder + "output_example.xml"
        };

        Game.main(args);
    }

    @Test
    public void no_object() {
        String currentTestFolder = testFolder + "no_object/";

        String[] args = {
                currentTestFolder + "map_no_object.xml",
                currentTestFolder + "config_no_object.txt",
                currentTestFolder + "output_no_object.xml"
        };

        Game.main(args);
    }

    @Test
    public void one_room() {
        String currentTestFolder = testFolder + "one_room/";

        String[] args = {
                currentTestFolder + "map_one_room.xml",
                currentTestFolder + "config_one_room.txt",
                currentTestFolder + "output_one_room.xml"
        };

        Game.main(args);
    }
}
