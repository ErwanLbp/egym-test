package iterator;

import model.Room;

import java.util.Iterator;
import java.util.Random;

/**
 * <h1>iterator RandomIterator</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class RandomIterator implements Iterator {

    private Iterable roomIte;
    private Random rand;

    public RandomIterator(Iterable room) {
        this.roomIte = room;
        this.rand = new Random();
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Object next() {
        Room room = (Room) roomIte;

        boolean[] tries = new boolean[4];
        for (boolean b : tries)
            b = false;

        return null;
    }
}
