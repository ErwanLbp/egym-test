package model;

import log.Log;

/**
 * The model of a cardinal direction
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    /**
     * @return The opposite of the direction
     */
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

    /**
     * @param direction The string to convert in a direction
     * @return The Direction matching the string received
     */
    public static Direction toDirection(String direction) {
        if (direction.equals("north")) return NORTH;
        if (direction.equals("south")) return SOUTH;
        if (direction.equals("east")) return EAST;
        if (direction.equals("west")) return WEST;
        Log.error("Unknow Direction : " + direction);
        return null;
    }

    /**
     * @return The Direction at the left of the current one
     */
    public Direction atLeft() {
        switch (this) {
            case EAST:
                return NORTH;
            case NORTH:
                return WEST;
            case SOUTH:
                return EAST;
            case WEST:
                return SOUTH;
        }
        throw new IllegalStateException("Unknown Direction");
    }

    /**
     * @return The Direction at the right of the current one
     */
    public Direction atRight() {
        // It's just the opposite of the direction at the right
        return atLeft().opposite();
    }
}
