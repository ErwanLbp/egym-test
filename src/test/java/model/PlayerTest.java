package model;

import factory.MazeFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Erwan LBP
 * @version 1.0
 * @since 20-12-2016
 */
public class PlayerTest {

    private Player player;
    private static ObjectR o1, o2;
    private static Room r1, r2;

    @BeforeClass
    public static void createMaze() {
        r1 = MazeFactory.getInstance().createRoom(5, "test_player");
        r2 = MazeFactory.getInstance().createRoom(6, "test_player_2");
        Door d = MazeFactory.getInstance().createDoor(r1, r2);

        r1.setSide(Direction.EAST, d);
        r2.setSide(Direction.EAST.opposite(), d);

        o1 = MazeFactory.getInstance().createObject("o1_player");
        o2 = MazeFactory.getInstance().createObject("o2_player");

        r1.addObject(o1);
        r1.addObject(o2);
    }

    @Test
    public void hasFoundEverythingWithEmptyList() {
        player = MazeFactory.getInstance().createPlayer(r1, new ArrayList<>());
        assertTrue(player.hasFoundEverything());
    }

    @Test
    public void hasFoundEverythingFalse() {
        List<String> objects = new ArrayList<>();
        objects.add(o1.getName());
        objects.add(o2.getName());
        player = MazeFactory.getInstance().createPlayer(r1, objects);

        assertFalse(player.hasFoundEverything());
    }

    @Test
    public void hasFoundEverythingTrue() {
        List<String> objects = new ArrayList<>();
        objects.add(o1.getName());
        player = MazeFactory.getInstance().createPlayer(r1, objects);
        player.pickObject(o1);

        assertTrue(player.hasFoundEverything());
    }

    @Test
    public void pickObjectNull() {
        List<String> objects = new ArrayList<>();
        objects.add(o1.getName());
        player = MazeFactory.getInstance().createPlayer(r1, objects);
        assertFalse(player.pickObject(null));
    }

    @Test
    public void pickObjectContains() {
        List<String> objects = new ArrayList<>();
        objects.add(o1.getName());
        player = MazeFactory.getInstance().createPlayer(r1, objects);
        assertTrue(player.pickObject(o1));
    }

    @Test
    public void pickObjectNotContains() {
        List<String> objects = new ArrayList<>();
        objects.add(o2.getName());
        player = MazeFactory.getInstance().createPlayer(r1, objects);
        assertFalse(player.pickObject(o1));
    }

    @Test
    public void moveTo() {
        player = MazeFactory.getInstance().createPlayer(r1, new ArrayList<>());
        assertEquals(r2, player.moveTo(Direction.EAST));
    }


    @Test
    public void moveToWall() {
        player = MazeFactory.getInstance().createPlayer(r1, new ArrayList<>());
        assertEquals(r1, player.moveTo(Direction.NORTH));
    }

    @Test
    public void dropObjectNull() {
        List<String> objects = new ArrayList<>();
        objects.add(o1.getName());
        player = MazeFactory.getInstance().createPlayer(r1, objects);
        player.dropObject(null);
        assertFalse(player.hasFoundEverything());
    }

    @Test
    public void dropObject() {
        List<String> objects = new ArrayList<>();
        objects.add(o1.getName());
        player = MazeFactory.getInstance().createPlayer(r1, objects);
        player.pickObject(o1);
        player.dropObject(o1);
        assertFalse(player.hasFoundEverything());
    }

    @Test
    public void dropObjectNotContains() {
        List<String> objects = new ArrayList<>();
        objects.add(o1.getName());
        player = MazeFactory.getInstance().createPlayer(r1, objects);
        player.dropObject(o2);
        assertFalse(player.hasFoundEverything());
    }

    @Test
    public void seekObject0() {
        List<String> objects = new ArrayList<>();
        objects.add("o3");
        player = MazeFactory.getInstance().createPlayer(r1, objects);
        assertEquals(0, player.seekForObjects().size());
    }

    @Test
    public void seekObject() {
        List<String> objects = new ArrayList<>();
        objects.add(o1.getName());
        objects.add(o2.getName());
        player = MazeFactory.getInstance().createPlayer(r1, objects);
        assertEquals(2, player.seekForObjects().size());
        assertEquals(o1, player.seekForObjects().get(0));
        assertEquals(o2, player.seekForObjects().get(1));
    }

    @Test
    public void objectsStillToFindAll() {
        List<String> objects = new ArrayList<>();
        objects.add(o1.getName());
        objects.add(o2.getName());
        player = MazeFactory.getInstance().createPlayer(r1, objects);
        assertEquals(2, player.getObjectsStillToFind().size());
        assertTrue(player.getObjectsStillToFind().contains(o1));
        assertTrue(player.getObjectsStillToFind().contains(o2));
    }

    @Test
    public void objectsStillToFind1Of2() {
        List<String> objects = new ArrayList<>();
        objects.add(o1.getName());
        objects.add(o2.getName());
        player = MazeFactory.getInstance().createPlayer(r1, objects);
        player.pickObject(o2);
        assertEquals(1, player.getObjectsStillToFind().size());
        assertEquals(o1, player.getObjectsStillToFind().get(0));
    }

    @Test
    public void objectsStillToFindNone() {
        List<String> objects = new ArrayList<>();
        objects.add(o1.getName());
        objects.add(o2.getName());
        player = MazeFactory.getInstance().createPlayer(r1, objects);
        player.pickObject(o2);
        player.pickObject(o1);
        assertEquals(0, player.getObjectsStillToFind().size());
    }

}