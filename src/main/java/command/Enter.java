package command;

import model.Direction;
import model.Player;
import model.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

    public void setPlayer(Player player) {
        this.player = player;
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

    @Override
    public void append(Document document) {
        Element roomElement = document.createElement("room");
        roomElement.setAttribute("id", room.getId() + "");
        roomElement.setAttribute("name", room.getName());
        roomElement.setIdAttribute("id", true);
        document.getDocumentElement().appendChild(roomElement);
    }

    public boolean isPossible() {
        return room.getSide(direction).canGoThrough();
    }

    @Override
    public String toString() {
        return "In the room " + room + ", go " + direction;
    }
}
