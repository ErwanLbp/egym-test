package model;

/**
 * <h1>model Direction</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    public Direction opposite() {
        switch (this) {
            case EAST:
                return WEST;
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
        }
        throw new IllegalStateException("Unknown Direction");
    }

    public static Direction toDirection(String direction) {
        if (direction.equals("north")) return NORTH;
        if (direction.equals("south")) return SOUTH;
        if (direction.equals("east")) return EAST;
        if (direction.equals("west")) return WEST;
        System.out.println("Unknow Direction : " + direction);
        return null;
    }
}
