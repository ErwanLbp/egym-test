package model;

/**
 * <h1>model Wall</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Wall implements InBetween {

    @Override
    public boolean canGoThrough() {
        return false;
    }
}
