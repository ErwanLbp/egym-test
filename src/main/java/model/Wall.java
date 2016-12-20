package model;

/**
 * The model of a wall in the maze<br/>
 * It's a wall, we can't go through a wall, we can't see what's behind a wall
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Wall implements InBetween {
    /**
     * @return False because we can't go through a wall (let's consider it's a solid wall)
     */
    @Override
    public boolean canGoThrough() {
        return false;
    }
}
