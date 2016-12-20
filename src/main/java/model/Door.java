package model;

/**
 * The model of a door between two rooms<br/>
 * There is no getter/setter because we don't need them, we only need to get the other side of a door, and we can't change a side of a door
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Door implements InBetween {

    private Room r1;
    private Room r2;

    /**
     * @param r1,r2 The two sides of the door
     */
    public Door(Room r1, Room r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    /**
     * @param room The room in wich we are
     * @return The other room opposite of the room received
     */
    public Room getOtherSide(Room room) {
        // If the room received is neither of the two rooms of the door...
        if (room != r1 && room != r2)
            return null;
        return (room == r1) ? r2 : r1;
    }

    /**
     * @return True cause we can go through a door (let's imagine it's more like a opening in the wall ...)
     */
    @Override
    public boolean canGoThrough() {
        return true;
    }
}
