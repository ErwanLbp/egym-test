package iterator;

import java.util.Iterator;

/**
 * <h1>PACKAGE_NAME MazeIterator</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class RoomIterator implements Iterator {

    private Iterable room;

    public RoomIterator(Iterable room) {
        this.room = room;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Object next() {
        return null;
    }
}
