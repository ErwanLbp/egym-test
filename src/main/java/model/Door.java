package model;

/**
 * <h1>model Door</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Door implements InBetween {

    private Room r1;
    private Room r2;

    public Door(Room r1, Room r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    public Room getR1() {
        return r1;
    }

    public void setR1(Room r1) {
        this.r1 = r1;
    }

    public Room getR2() {
        return r2;
    }

    public void setR2(Room r2) {
        this.r2 = r2;
    }

    public Room getOtherSide(Room room) {
        if (room != r1 && room != r2)
            return null;
        return (room == r1) ? r2 : r1;
    }

    @Override
    public boolean canGoThrough() {
        return true;
    }
}
