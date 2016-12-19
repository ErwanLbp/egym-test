package command;

import model.Direction;
import model.Player;
import model.Room;

/**
 * <h1>command Enter</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Enter implements Command {

    private Room room;
    private Direction direction;
    private Player player;

    public Enter(Player player, Room room, Direction direction) {
        this.player = player;
        this.room = room;
        this.direction = direction;
    }

    @Override
    public void execute() {
        if (isPossible())
            player.moveTo(direction);
    }

    @Override
    public void undo() {
        player.moveTo(direction.opposite());
    }

    public boolean isPossible() {
        return room.getSide(direction).canGoThrough();
    }

    @Override
    public String toString() {
        return "Enter in the room " + room;
    }
}
