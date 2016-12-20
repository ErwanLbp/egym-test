package model;

/**
 * The interface to modelize the <i>thing</i> that separates two room (I can't call it a wall, but neither a door...)
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public interface InBetween {
    /**
     * @return True if we can go through this <i>thing</i>
     */
    boolean canGoThrough();
}
