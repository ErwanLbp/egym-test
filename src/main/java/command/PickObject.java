package command;

import model.ObjectR;
import model.Player;
import model.Room;

/**
 * <h1>command PickObject</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class PickObject implements Command {

    private Player player;
    private Room room;
    private ObjectR objectR;

    public PickObject(Player player, Room room, ObjectR objectR) {
        this.player = player;
        this.room = room;
        this.objectR = objectR;
    }

    @Override
    public void execute() {
        if (room.contains(objectR))
            player.pickObject(objectR);
    }

    @Override
    public void undo() {
        player.dropObject(objectR);
    }
}
